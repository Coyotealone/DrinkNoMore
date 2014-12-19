/**************************************************************************
 * StatistiquesShowFragment.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.statistiques;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coyote.drinknomore.R;
import com.coyote.drinknomore.entity.Statistiques;
import com.coyote.drinknomore.harmony.util.DateUtils;
import com.coyote.drinknomore.harmony.view.DeleteDialog;
import com.coyote.drinknomore.harmony.view.HarmonyFragment;
import com.coyote.drinknomore.harmony.view.MultiLoader;
import com.coyote.drinknomore.harmony.view.MultiLoader.UriLoadedCallback;
import com.coyote.drinknomore.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.coyote.drinknomore.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.coyote.drinknomore.provider.utils.StatistiquesProviderUtils;
import com.coyote.drinknomore.provider.StatistiquesProviderAdapter;
import com.coyote.drinknomore.provider.contract.StatistiquesContract;

/** Statistiques show fragment.
 *
 * This fragment gives you an interface to show a Statistiques.
 * 
 * @see android.app.Fragment
 */
public class StatistiquesShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Statistiques model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** date View. */
    protected TextView dateView;
    /** nberreurs View. */
    protected TextView nberreursView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Statistiques. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.dateView =
            (TextView) view.findViewById(
                    R.id.statistiques_date);
        this.nberreursView =
            (TextView) view.findViewById(
                    R.id.statistiques_nberreurs);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.statistiques_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.statistiques_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getDate() != null) {
            this.dateView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getDate()));
        }
        if (this.model.getNberreurs() != null) {
            this.nberreursView.setText(String.valueOf(this.model.getNberreurs()));
        }
        } else {
            this.dataLayout.setVisibility(View.GONE);
            this.emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(
                        R.layout.fragment_statistiques_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Statistiques) intent.getParcelableExtra(StatistiquesContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Statistiques to get the data from.
     */
    public void update(Statistiques item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    StatistiquesProviderAdapter.STATISTIQUES_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    StatistiquesShowFragment.this.onStatistiquesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.init();
        }
    }

    /**
     * Called when the entity has been loaded.
     * 
     * @param c The cursor of this entity
     */
    public void onStatistiquesLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            StatistiquesContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }

    /**
     * Calls the StatistiquesEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    StatistiquesEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(StatistiquesContract.PARCEL, this.model);
        intent.putExtras(extras);

        this.getActivity().startActivity(intent);
    }
    /**
     * Shows a confirmation dialog.
     */
    @Override
    public void onClickDelete() {
        new DeleteDialog(this.getActivity(), this).show();
    }

    @Override
    public void onDeleteDialogClose(boolean ok) {
        if (ok) {
            new DeleteTask(this.getActivity(), this.model).execute();
        }
    }
    
    /** 
     * Called when delete task is done.
     */    
    public void onPostDelete() {
        if (this.deleteCallback != null) {
            this.deleteCallback.onItemDeleted();
        }
    }

    /**
     * This class will remove the entity into the DB.
     * It runs asynchronously.
     */
    private class DeleteTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private android.content.Context ctx;
        /** Entity to delete. */
        private Statistiques item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build StatistiquesSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Statistiques item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new StatistiquesProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                StatistiquesShowFragment.this.onPostDelete();
            }
            super.onPostExecute(result);
        }
        
        

    }

    /**
     * Callback for item deletion.
     */ 
    public interface DeleteCallback {
        /** Called when current item has been deleted. */
        void onItemDeleted();
    }
}


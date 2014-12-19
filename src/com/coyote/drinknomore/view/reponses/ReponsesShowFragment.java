/**************************************************************************
 * ReponsesShowFragment.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.reponses;


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
import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.harmony.view.DeleteDialog;
import com.coyote.drinknomore.harmony.view.HarmonyFragment;
import com.coyote.drinknomore.harmony.view.MultiLoader;
import com.coyote.drinknomore.harmony.view.MultiLoader.UriLoadedCallback;
import com.coyote.drinknomore.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.coyote.drinknomore.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.coyote.drinknomore.provider.utils.ReponsesProviderUtils;
import com.coyote.drinknomore.provider.ReponsesProviderAdapter;
import com.coyote.drinknomore.provider.contract.ReponsesContract;
import com.coyote.drinknomore.provider.contract.QuestionsContract;

/** Reponses show fragment.
 *
 * This fragment gives you an interface to show a Reponses.
 * 
 * @see android.app.Fragment
 */
public class ReponsesShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Reponses model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** solution View. */
    protected TextView solutionView;
    /** arguments View. */
    protected TextView argumentsView;
    /** question View. */
    protected TextView questionView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Reponses. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.solutionView =
            (TextView) view.findViewById(
                    R.id.reponses_solution);
        this.argumentsView =
            (TextView) view.findViewById(
                    R.id.reponses_arguments);
        this.questionView =
            (TextView) view.findViewById(
                    R.id.reponses_question);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.reponses_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.reponses_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getSolution() != null) {
            this.solutionView.setText(this.model.getSolution());
        }
        if (this.model.getArguments() != null) {
            this.argumentsView.setText(this.model.getArguments());
        }
        if (this.model.getQuestion() != null) {
            this.questionView.setText(
                    String.valueOf(this.model.getQuestion().getId()));
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
                        R.layout.fragment_reponses_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Reponses) intent.getParcelableExtra(ReponsesContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Reponses to get the data from.
     */
    public void update(Reponses item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    ReponsesProviderAdapter.REPONSES_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ReponsesShowFragment.this.onReponsesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/question"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ReponsesShowFragment.this.onQuestionLoaded(c);
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
    public void onReponsesLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            ReponsesContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onQuestionLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setQuestion(QuestionsContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setQuestion(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the ReponsesEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    ReponsesEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(ReponsesContract.PARCEL, this.model);
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
        private Reponses item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build ReponsesSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Reponses item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new ReponsesProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                ReponsesShowFragment.this.onPostDelete();
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


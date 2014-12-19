/**************************************************************************
 * QuestionsShowFragment.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.questions;


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
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.harmony.view.DeleteDialog;
import com.coyote.drinknomore.harmony.view.HarmonyFragment;
import com.coyote.drinknomore.harmony.view.MultiLoader;
import com.coyote.drinknomore.harmony.view.MultiLoader.UriLoadedCallback;
import com.coyote.drinknomore.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.coyote.drinknomore.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.coyote.drinknomore.provider.utils.QuestionsProviderUtils;
import com.coyote.drinknomore.provider.QuestionsProviderAdapter;
import com.coyote.drinknomore.provider.contract.QuestionsContract;
import com.coyote.drinknomore.provider.contract.ReponsesContract;

/** Questions show fragment.
 *
 * This fragment gives you an interface to show a Questions.
 * 
 * @see android.app.Fragment
 */
public class QuestionsShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Questions model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** enigme View. */
    protected TextView enigmeView;
    /** arguments View. */
    protected TextView argumentsView;
    /** reponse View. */
    protected TextView reponseView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Questions. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.enigmeView =
            (TextView) view.findViewById(
                    R.id.questions_enigme);
        this.argumentsView =
            (TextView) view.findViewById(
                    R.id.questions_arguments);
        this.reponseView =
            (TextView) view.findViewById(
                    R.id.questions_reponse);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.questions_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.questions_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getEnigme() != null) {
            this.enigmeView.setText(this.model.getEnigme());
        }
        if (this.model.getArguments() != null) {
            this.argumentsView.setText(this.model.getArguments());
        }
        if (this.model.getReponse() != null) {
            String reponseValue = "";
            for (Reponses item : this.model.getReponse()) {
                reponseValue += item.getId() + ",";
            }
            this.reponseView.setText(reponseValue);
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
                        R.layout.fragment_questions_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Questions) intent.getParcelableExtra(QuestionsContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Questions to get the data from.
     */
    public void update(Questions item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    QuestionsProviderAdapter.QUESTIONS_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    QuestionsShowFragment.this.onQuestionsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/reponse"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    QuestionsShowFragment.this.onReponseLoaded(c);
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
    public void onQuestionsLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            QuestionsContract.cursorToItem(
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
    public void onReponseLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setReponse(ReponsesContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setReponse(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the QuestionsEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    QuestionsEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(QuestionsContract.PARCEL, this.model);
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
        private Questions item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build QuestionsSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Questions item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new QuestionsProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                QuestionsShowFragment.this.onPostDelete();
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


/**************************************************************************
 * QuestionsEditFragment.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.questions;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.coyote.drinknomore.R;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;
import com.coyote.drinknomore.harmony.view.HarmonyFragment;
import com.coyote.drinknomore.harmony.widget.MultiEntityWidget;
import com.coyote.drinknomore.menu.SaveMenuWrapper.SaveMenuInterface;
import com.coyote.drinknomore.provider.QuestionsProviderAdapter;
import com.coyote.drinknomore.provider.utils.QuestionsProviderUtils;
import com.coyote.drinknomore.provider.utils.ReponsesProviderUtils;
import com.coyote.drinknomore.provider.contract.QuestionsContract;
import com.coyote.drinknomore.provider.contract.ReponsesContract;

/** Questions create fragment.
 *
 * This fragment gives you an interface to edit a Questions.
 *
 * @see android.app.Fragment
 */
public class QuestionsEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Questions model = new Questions();

    /** curr.fields View. */
    /** enigme View. */
    protected EditText enigmeView;
    /** arguments View. */
    protected EditText argumentsView;
    /** The reponse chooser component. */
    protected MultiEntityWidget reponseWidget;
    /** The reponse Adapter. */
    protected MultiEntityWidget.EntityAdapter<Reponses>
            reponseAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.enigmeView = (EditText) view.findViewById(
                R.id.questions_enigme);
        this.argumentsView = (EditText) view.findViewById(
                R.id.questions_arguments);
        this.reponseAdapter =
                new MultiEntityWidget.EntityAdapter<Reponses>() {
            @Override
            public String entityToString(Reponses item) {
                return String.valueOf(item.getId());
            }
        };
        this.reponseWidget = (MultiEntityWidget) view.findViewById(
                        R.id.questions_reponse_button);
        this.reponseWidget.setAdapter(this.reponseAdapter);
        this.reponseWidget.setTitle(R.string.questions_reponse_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getEnigme() != null) {
            this.enigmeView.setText(this.model.getEnigme());
        }
        if (this.model.getArguments() != null) {
            this.argumentsView.setText(this.model.getArguments());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setEnigme(this.enigmeView.getEditableText().toString());

        this.model.setArguments(this.argumentsView.getEditableText().toString());

        this.model.setReponse(this.reponseAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.enigmeView.getText().toString().trim())) {
            error = R.string.questions_enigme_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.argumentsView.getText().toString().trim())) {
            error = R.string.questions_arguments_invalid_field_error;
        }
        if (this.reponseAdapter.getCheckedItems().isEmpty()) {
            error = R.string.questions_reponse_invalid_field_error;
        }
    
        if (error > 0) {
            Toast.makeText(this.getActivity(),
                this.getActivity().getString(error),
                Toast.LENGTH_SHORT).show();
        }
        return error == 0;
    }
    @Override
    public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(R.layout.fragment_questions_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Questions) intent.getParcelableExtra(
                QuestionsContract.PARCEL);

        this.initializeComponent(view);
        this.loadData();

        return view;
    }

    /**
     * This class will update the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class EditTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final Questions entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final QuestionsEditFragment fragment,
                    final Questions entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.questions_progress_save_title),
                    this.ctx.getString(
                            R.string.questions_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new QuestionsProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("QuestionsEditFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result > 0) {
                final HarmonyFragmentActivity activity =
                        (HarmonyFragmentActivity) this.ctx;
                activity.setResult(HarmonyFragmentActivity.RESULT_OK);
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(this.ctx.getString(
                        R.string.questions_error_edit));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                                int which) {

                            }
                        });
                builder.show();
            }

            this.progress.dismiss();
        }
    }


    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class LoadTask extends AsyncTask<Void, Void, Void> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Progress Dialog. */
        private ProgressDialog progress;
        /** Fragment. */
        private QuestionsEditFragment fragment;
        /** reponse list. */
        private ArrayList<Reponses> reponseList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final QuestionsEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.questions_progress_load_relations_title),
                this.ctx.getString(
                    R.string.questions_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.reponseList = 
                new ReponsesProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onReponseLoaded(this.reponseList);

            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new EditTask(this, this.model).execute();
        }
    }

    /**
     * Called when reponse have been loaded.
     * @param items The loaded items
     */
    protected void onReponseLoaded(ArrayList<Reponses> items) {
        this.reponseAdapter.loadData(items);
        ArrayList<Reponses> modelItems = new ArrayList<Reponses>();
        for (Reponses item : items) {
            if (item.getQuestion() != null && item.getQuestion().getId() == this.model.getId()) {
                modelItems.add(item);
                this.reponseAdapter.checkItem(item, true);
            }
        }
        this.model.setReponse(modelItems);
    }
}

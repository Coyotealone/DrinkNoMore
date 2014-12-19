/**************************************************************************
 * ReponsesCreateFragment.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.reponses;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
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
import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.entity.Questions;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;
import com.coyote.drinknomore.harmony.view.HarmonyFragment;

import com.coyote.drinknomore.harmony.widget.SingleEntityWidget;
import com.coyote.drinknomore.menu.SaveMenuWrapper.SaveMenuInterface;
import com.coyote.drinknomore.provider.utils.ReponsesProviderUtils;
import com.coyote.drinknomore.provider.utils.QuestionsProviderUtils;

/**
 * Reponses create fragment.
 *
 * This fragment gives you an interface to create a Reponses.
 */
public class ReponsesCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Reponses model = new Reponses();

    /** Fields View. */
    /** solution View. */
    protected EditText solutionView;
    /** arguments View. */
    protected EditText argumentsView;
    /** The question chooser component. */
    protected SingleEntityWidget questionWidget;
    /** The question Adapter. */
    protected SingleEntityWidget.EntityAdapter<Questions> 
                questionAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.solutionView =
            (EditText) view.findViewById(R.id.reponses_solution);
        this.argumentsView =
            (EditText) view.findViewById(R.id.reponses_arguments);
        this.questionAdapter = 
                new SingleEntityWidget.EntityAdapter<Questions>() {
            @Override
            public String entityToString(Questions item) {
                return String.valueOf(item.getId());
            }
        };
        this.questionWidget =
            (SingleEntityWidget) view.findViewById(R.id.reponses_question_button);
        this.questionWidget.setAdapter(this.questionAdapter);
        this.questionWidget.setTitle(R.string.reponses_question_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getSolution() != null) {
            this.solutionView.setText(this.model.getSolution());
        }
        if (this.model.getArguments() != null) {
            this.argumentsView.setText(this.model.getArguments());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setSolution(this.solutionView.getEditableText().toString());

        this.model.setArguments(this.argumentsView.getEditableText().toString());

        this.model.setQuestion(this.questionAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.solutionView.getText().toString().trim())) {
            error = R.string.reponses_solution_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.argumentsView.getText().toString().trim())) {
            error = R.string.reponses_arguments_invalid_field_error;
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
        final View view = inflater.inflate(
                R.layout.fragment_reponses_create,
                container,
                false);

        this.initializeComponent(view);
        this.loadData();
        return view;
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class CreateTask extends AsyncTask<Void, Void, Uri> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to persist. */
        private final Reponses entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final ReponsesCreateFragment fragment,
                final Reponses entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.reponses_progress_save_title),
                    this.ctx.getString(
                            R.string.reponses_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new ReponsesProviderUtils(this.ctx).insert(
                        this.entity);

            return result;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (result != null) {
                final HarmonyFragmentActivity activity =
                                         (HarmonyFragmentActivity) this.ctx;
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.reponses_error_create));
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
        private ReponsesCreateFragment fragment;
        /** question list. */
        private ArrayList<Questions> questionList;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final ReponsesCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.reponses_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.reponses_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.questionList = 
                new QuestionsProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.questionAdapter.loadData(this.questionList);
            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new CreateTask(this, this.model).execute();
        }
    }
}

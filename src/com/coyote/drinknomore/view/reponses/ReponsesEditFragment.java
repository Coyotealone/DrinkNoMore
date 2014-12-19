/**************************************************************************
 * ReponsesEditFragment.java, drinknomore Android
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
import android.content.Intent;
import android.database.sqlite.SQLiteException;

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
import com.coyote.drinknomore.provider.contract.ReponsesContract;
import com.coyote.drinknomore.provider.contract.QuestionsContract;

/** Reponses create fragment.
 *
 * This fragment gives you an interface to edit a Reponses.
 *
 * @see android.app.Fragment
 */
public class ReponsesEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Reponses model = new Reponses();

    /** curr.fields View. */
    /** solution View. */
    protected EditText solutionView;
    /** arguments View. */
    protected EditText argumentsView;
    /** The question chooser component. */
    protected SingleEntityWidget questionWidget;
    /** The question Adapter. */
    protected SingleEntityWidget.EntityAdapter<Questions>
            questionAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.solutionView = (EditText) view.findViewById(
                R.id.reponses_solution);
        this.argumentsView = (EditText) view.findViewById(
                R.id.reponses_arguments);
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

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getSolution() != null) {
            this.solutionView.setText(this.model.getSolution());
        }
        if (this.model.getArguments() != null) {
            this.argumentsView.setText(this.model.getArguments());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_reponses_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Reponses) intent.getParcelableExtra(
                ReponsesContract.PARCEL);

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
        private final Reponses entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final ReponsesEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new ReponsesProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("ReponsesEditFragment", e.getMessage());
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
                        R.string.reponses_error_edit));
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
        private ReponsesEditFragment fragment;
        /** question list. */
        private ArrayList<Questions> questionList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final ReponsesEditFragment fragment) {
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
            this.fragment.onQuestionLoaded(this.questionList);

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
     * Called when question have been loaded.
     * @param items The loaded items
     */
    protected void onQuestionLoaded(ArrayList<Questions> items) {
        this.questionAdapter.loadData(items);
        
        if (this.model.getQuestion() != null) {
            for (Questions item : items) {
                if (item.getId() == this.model.getQuestion().getId()) {
                    this.questionAdapter.selectItem(item);
                }
            }
        }
    }
}

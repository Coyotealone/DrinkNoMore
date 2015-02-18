/**************************************************************************
 * StatistiquesCreateFragment.java, drinknomore Android
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 **************************************************************************/

package com.coyote.drinknomore.view.statistiques;



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
import com.coyote.drinknomore.entity.Statistiques;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;
import com.coyote.drinknomore.harmony.view.HarmonyFragment;
import com.coyote.drinknomore.harmony.widget.DateTimeWidget;

import com.coyote.drinknomore.menu.SaveMenuWrapper.SaveMenuInterface;
import com.coyote.drinknomore.provider.utils.StatistiquesProviderUtils;

/**
 * Statistiques create fragment.
 * This fragment gives you an interface to create a Statistiques.
 */
public class StatistiquesCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Statistiques model = new Statistiques();

    /** Fields View. */
    /** date DateTime View. */
    protected DateTimeWidget dateView;
    /** nberreurs View. */
    protected EditText nberreursView;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.dateView =
                (DateTimeWidget) view.findViewById(R.id.statistiques_date);
        this.nberreursView =
            (EditText) view.findViewById(R.id.statistiques_nberreurs);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getDate() != null) {
            this.dateView.setDateTime(this.model.getDate());
        }
        if (this.model.getNberreurs() != null) {
            this.nberreursView.setText(String.valueOf(this.model.getNberreurs()));
        }


    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setDate(this.dateView.getDateTime());

        this.model.setNberreurs(Integer.parseInt(
                    this.nberreursView.getEditableText().toString()));

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (this.dateView.getDateTime() == null) {
            error = R.string.statistiques_date_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.nberreursView.getText().toString().trim())) {
            error = R.string.statistiques_nberreurs_invalid_field_error;
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
                R.layout.fragment_statistiques_create,
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
        private final Statistiques entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is called
         */
        public CreateTask(final StatistiquesCreateFragment fragment,
                final Statistiques entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.statistiques_progress_save_title),
                    this.ctx.getString(
                            R.string.statistiques_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new StatistiquesProviderUtils(this.ctx).insert(
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
                                R.string.statistiques_error_create));
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


    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new CreateTask(this, this.model).execute();
        }
    }
}

/**************************************************************************
 * StatistiquesEditFragment.java, drinknomore Android
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
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.coyote.drinknomore.R;
import com.coyote.drinknomore.entity.Statistiques;
import com.coyote.drinknomore.harmony.view.HarmonyFragment;
import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;
import com.coyote.drinknomore.harmony.widget.DateTimeWidget;
import com.coyote.drinknomore.menu.SaveMenuWrapper.SaveMenuInterface;
import com.coyote.drinknomore.provider.contract.StatistiquesContract;
import com.coyote.drinknomore.provider.utils.StatistiquesProviderUtils;
import com.google.common.base.Strings;

/** Statistiques create fragment.
 * This fragment gives you an interface to edit a Statistiques.
 *
 * @see android.app.Fragment
 */
public class StatistiquesEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Statistiques model = new Statistiques();

    /** curr.fields View. */
    /** date DateTime View. */
    protected DateTimeWidget dateView;
    /** nberreurs View. */
    protected EditText nberreursView;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.dateView = (DateTimeWidget) view.findViewById(
                R.id.statistiques_date);
        this.nberreursView = (EditText) view.findViewById(
                R.id.statistiques_nberreurs);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getDate() != null) {
            this.dateView.setDateTime(this.model.getDate());
        }
        if (this.model.getNberreurs() != null) {
            this.nberreursView.setText(String.valueOf(this.model.getNberreurs()));
        }


    }

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_statistiques_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Statistiques) intent.getParcelableExtra(
                StatistiquesContract.PARCEL);

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
        private final Statistiques entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is called
         */
        public EditTask(final StatistiquesEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new StatistiquesProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("StatistiquesEditFragment", e.getMessage());
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
                        R.string.statistiques_error_edit));
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
            new EditTask(this, this.model).execute();
        }
    }

}

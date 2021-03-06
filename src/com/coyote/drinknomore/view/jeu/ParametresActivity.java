/**************************************************************************
 * ParametresActivity.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Coyote
 * Licence     : 
 * Last update : Feb 19, 2015
 **************************************************************************/

package com.coyote.drinknomore.view.jeu;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

import com.coyote.drinknomore.ChoicesActivity;
import com.coyote.drinknomore.Fonctions;
import com.coyote.drinknomore.R;
import com.coyote.drinknomore.harmony.widget.TimeWidget;

public class ParametresActivity extends Activity {

    public static final String PREFS_PARAMETERS = "prefFileParameters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_parametres);

        /**
         * Restore preferences.
         */
        SharedPreferences settings = getSharedPreferences(PREFS_PARAMETERS, 0);
        final CheckBox cb_horaire_lundi = (CheckBox) this
                .findViewById(R.id.cb_horaire_lundi);
        final CheckBox cb_horaire_mardi = (CheckBox) this
                .findViewById(R.id.cb_horaire_mardi);
        final CheckBox cb_horaire_mercredi = (CheckBox) this
                .findViewById(R.id.cb_horaire_mercredi);
        final CheckBox cb_horaire_jeudi = (CheckBox) this
                .findViewById(R.id.cb_horaire_jeudi);
        final CheckBox cb_horaire_vendredi = (CheckBox) this
                .findViewById(R.id.cb_horaire_vendredi);
        final CheckBox cb_horaire_samedi = (CheckBox) this
                .findViewById(R.id.cb_horaire_samedi);
        final CheckBox cb_horaire_dimanche = (CheckBox) this
                .findViewById(R.id.cb_horaire_dimanche);
        final TimeWidget timewidget = (TimeWidget) this
                .findViewById(R.id.timeWidget_Parametres_horaire);

        cb_horaire_lundi.setChecked(
                settings.getBoolean(getString(R.string.parametres_cb_horaire_lundi),
                        false));
        cb_horaire_mardi.setChecked(settings.getBoolean(
                getString(R.string.parametres_cb_horaire_mardi),
                false));
        cb_horaire_mercredi.setChecked(settings.getBoolean(
                getString(R.string.parametres_cb_horaire_mercredi), false));
        cb_horaire_jeudi.setChecked(settings.getBoolean(
                getString(R.string.parametres_cb_horaire_jeudi),
                false));
        cb_horaire_vendredi.setChecked(settings.getBoolean(
                getString(R.string.parametres_cb_horaire_vendredi), false));
        cb_horaire_samedi.setChecked(settings.getBoolean(
                getString(R.string.parametres_cb_horaire_samedi),
                false));
        cb_horaire_dimanche.setChecked(settings.getBoolean(
                getString(R.string.parametres_cb_horaire_dimanche), false));

        String valuetime = settings.getString(getString(R.string.parametres_timewidget_horaire),
                null);
        if (valuetime != null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
            timewidget.setTime(formatter.parseDateTime(valuetime));

        }

        Button btnParametresvalider = (Button) this.findViewById(R.id.btnParametres_valider);
        btnParametresvalider.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(PREFS_PARAMETERS, 0);

                /**
                 * Set values in editor.
                 */
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(getString(R.string.parametres_cb_horaire_lundi),
                        cb_horaire_lundi.isChecked());
                editor.putBoolean(getString(R.string.parametres_cb_horaire_mardi),
                        cb_horaire_mardi.isChecked());
                editor.putBoolean(getString(R.string.parametres_cb_horaire_mercredi),
                        cb_horaire_mercredi.isChecked());
                editor.putBoolean(getString(R.string.parametres_cb_horaire_jeudi),
                        cb_horaire_jeudi.isChecked());
                editor.putBoolean(getString(R.string.parametres_cb_horaire_vendredi),
                        cb_horaire_vendredi.isChecked());
                editor.putBoolean(getString(R.string.parametres_cb_horaire_samedi),
                        cb_horaire_samedi.isChecked());
                editor.putBoolean(getString(R.string.parametres_cb_horaire_dimanche),
                        cb_horaire_dimanche.isChecked());

                String valuesave = Fonctions.splitTime((String) timewidget
                        .getTime().toDate().toString());
                if (valuesave.equals("")) {
                    valuesave = null;
                }
                editor.putString(getString(R.string.parametres_timewidget_horaire), valuesave);
                /**
                 * Commit values.
                 */
                editor.commit();
                /**
                 * Show view Parametres.
                 */
                Intent intent = new Intent(ParametresActivity.this, ChoicesActivity.class);
                startActivity(intent);

            }
        });
    }

}

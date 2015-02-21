/**************************************************************************
 * JeuActivity.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Coyote
 * Licence     : 
 * Last update : Feb 19, 2015
 **************************************************************************/

package com.coyote.drinknomore.view.jeu;

import java.util.ArrayList;
import java.util.Calendar;

import org.joda.time.DateTime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.coyote.drinknomore.ChoicesActivity;
import com.coyote.drinknomore.Fonctions;
import com.coyote.drinknomore.R;
import com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase;
import com.coyote.drinknomore.data.base.ReponsesSQLiteAdapterBase;
import com.coyote.drinknomore.data.base.StatistiquesSQLiteAdapterBase;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.entity.Statistiques;

public class JeuActivity extends Activity {

    /** * name SharedPreferences about game. */
    public static final String PREFS_GAME = "prefFileJeu";
    /** * name SharedPreferences about parameters. */
    public static final String PREFS_PARAMETERS = "prefFileParameters";

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** * Show view activity_jeu. */
        this.setContentView(R.layout.activity_jeu);
        /**
         * {@value #viewTxtEnigme} TextView.
         * init by id view activity_jeu
         */
        final TextView txtviewEnigme = (TextView) findViewById(R.id.jeu_txtEnigme);
        /**
         * {@value #rdbtnRep1} RadioButton.
         * init by id view activity_jeu
         */
        final RadioButton rdbtnRep1 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep1);
        /**
         * {@value #rdbtnRep2} RadioButton.
         * init by id view activity_jeu
         */
        final RadioButton rdbtnRep2 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep2);
        /**
         * {@value #rdbtnRep3} RadioButton.
         * init by id view activity_jeu
         */
        final RadioButton rdbtnRep3 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep3);
        /**
         * {@value #rdbtnRep4} RadioButton.
         * init by id view activity_jeu
         */
        final RadioButton rdbtnRep4 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep4);
        /**
         * {@value #rdgpRep} RadioGroup.
         * init by id view activity_jeu
         */
        final RadioGroup rdgpRep = (RadioGroup) findViewById(R.id.radioReponses);
        
        final SharedPreferences settingsParameters = getSharedPreferences(PREFS_PARAMETERS, 0);
        /**
         * {@value #settingsGame} SharePreferences.
         * @param String PREFS_GAME
         * @param Integer Mode init 0 private
         */
        final SharedPreferences settingsGame = getSharedPreferences(PREFS_GAME, 0);

        Calendar calendarNow = Calendar.getInstance();
        Fonctions fcts = new Fonctions();
        
        /**
         * @see Fonctions\checkDay()
         */
        Boolean checkday = fcts.checkDay(settingsParameters, calendarNow.get(Calendar.DAY_OF_WEEK));

        if (checkday == true && (fcts.compareDateTime(settingsParameters) > 0)) {
            Toast.makeText(JeuActivity.this,
                    R.string.jeu_dont_play, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(JeuActivity.this, ChoicesActivity.class);
            /**
             * show new intent.
             * @param intent Intent
             */
            startActivity(intent);
        }

        /**
         * {@value #questions} Questions
         * @see com.coyote.drinknomore.entity.Questions
         */
        final Questions questions;
        /**
         * {@value #stats} Statistiques
         * @see com.coyote.drinknomore.entity.Statistiques
         */
        final Statistiques stats = new Statistiques();
        /**
         * {@value #questionsSQL} QuestionsSQLiteAdapaterBase
         * @see com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase
         * @param this instance courante
         */
        final QuestionsSQLiteAdapterBase questionsSql = new QuestionsSQLiteAdapterBase(this);
        /**
         * {@value #reponsesSql} ReponsesSQLiteAdapaterBase
         * @see com.coyote.drinknomore.data.base.ReponsesSQLiteAdapterBase
         * @param this instance courante
         */
        final ReponsesSQLiteAdapterBase reponsesSql = new ReponsesSQLiteAdapterBase(this);
        /**
         * {@value #statsSql} StatistiquesSQLiteAdapaterBase
         * @see com.coyote.drinknomore.data.base.ReponsesSQLiteAdapterBase
         * @param this instance courante
         */
        final StatistiquesSQLiteAdapterBase statsSql = new StatistiquesSQLiteAdapterBase(this);
        /**
         * open db questionsSql.
         */
        questionsSql.open();
        /**
         * open db reponsesSql.
         */
        reponsesSql.open();
        /**
         * String containing the question recovered database.
         * {@value #enigme} String
         * init empty
         */
        String enigme = "";
        /**
         * String containing the good response recovered database.
         * {@value #reponses} String
         * init empty
         */
        String reponses = "";
        /**
         * Array String containing responses recovered database.
         * {@value #choixreponses} String[]
         * init null
         */
        String[] choixreponses = null;
        /**
         * Array int containing all id Questions.
         * {@value #getId()}
         */
        int[] nbQuestions = questionsSql.getId();

        if (nbQuestions.length < 1) {
            Toast.makeText(JeuActivity.this,
                    getString(R.string.jeu_error_data), Toast.LENGTH_SHORT).show();
            /**
             * init intent in new view
             * @param JeuActivity.this
             * @param ChoicesActivity.class
             */
            Intent intent = new Intent(JeuActivity.this, ChoicesActivity.class);
            /**
             * show new intent.
             * @param intent instance courante
             */
            startActivity(intent);
        }
        else {
            int randomNum = Fonctions.randomId(nbQuestions.length);
            /**
             * init questions compared to id in db.
             * value #questionsSql.getByID
             * @param int value array nbQuestions
             */
            questions = questionsSql.getByID(nbQuestions[randomNum]);
            /**
             * set enigme with getEnigme().
             * value #questions.getEnigme()
             */
            enigme = questions.getEnigme();
            /**
             * set reponses with getArguments().
             * value #questions.getArguments()
             */
            reponses = questions.getArguments();
            /**
             * set text view enigme.
             * {@value #enigme}
             */
            txtviewEnigme.setText(enigme);
            /**
             * split choixreponses.
             * @param ;
             * @return array String
             */
            choixreponses = reponses.split(";");
            /**
             * set text radioButton.
             * value #choixreponses[0]
             */
            if (choixreponses.length > 3) {
                rdbtnRep1.setText(choixreponses[0]);
                /**
                 * set text radioButton.
                 * value #choixreponses[1]
                 */
                rdbtnRep2.setText(choixreponses[1]);
                /**
                 * set text radioButton.
                 * value #choixreponses[2]
                 */
                rdbtnRep3.setText(choixreponses[2]);
                /**
                 * set text radioButton.
                 * value #choixreponses[3]
                 */
                rdbtnRep4.setText(choixreponses[3]);
            }

            Button btnJeuvalider = (Button) this.findViewById(R.id.jeu_btnValider);
            /**
             * Action onClickButton btnJeuvalider.
             */
            btnJeuvalider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                    
                    /**
                     * {@value #error} Integer.
                     * Integer to calculate count error, init about settingsGame
                     */
                    Integer error = settingsGame.getInt("error", 0);
                    //settingsGame.getInt(String.valueOf(R.string.jeu_error_data), 0);
                    /**
                     * String reponse containing value reponse about question.
                     * @value empty
                     */
                    String reponse = "";
                    /**
                     *  Array containing all responses.
                     */
                    ArrayList<Reponses> allreponses = questions.getReponse();
                    /**
                     * set string reponse about object allreponses with function getSolution().
                     */
                    if (allreponses.size() > 0) {
                        reponse = allreponses.get(0).getSolution();
                    }
                    /**
                     * find radiobutton checked.
                     * @value #getCheckedRadioButtonId()
                     */
                    int selectedId = rdgpRep.getCheckedRadioButtonId();
                    /**
                     * Find the radiobutton by returned id.
                     */
                    RadioButton radionbuttonReponse = (RadioButton) findViewById(selectedId);
                    /**
                     *  set verifreponse about radio button check.
                     */
                    String verifreponse = radionbuttonReponse.getText().toString();
                    /**
                     * check value response checked and response question.
                     */
                    if (reponse.equals(verifreponse)) {
                        /**
                         * open db stats.
                         */
                        statsSql.open();
                        /**
                         * set number errors.
                         * @value #setNberreurs
                         * @param int error
                         */
                        stats.setNberreurs(error);
                        /**
                         * set date
                         * @value #setDate
                         * @param DateTime.now()
                         */
                        stats.setDate(DateTime.now());
                        /**
                         * Set values in editor.
                         */
                        SharedPreferences.Editor editorGame = settingsGame.edit();
                        /**
                         * put error in editorGame.
                         */
                        editorGame.putInt("error", 0);
                        /**
                         * Commit values.
                         */
                        editorGame.commit();
                        /**
                         * insert stats in db.
                         * @param stats Objects Statistiques
                         */
                        statsSql.insert(stats);
                        /**
                         * send toast
                         * @param JeuActivity.this
                         * @param String R.string.Jeu_BonneReponse
                         * @param int Toast.LENGTH_SHORT
                         */
                        Toast.makeText(JeuActivity.this,allreponses.get(0).getArguments(), Toast.LENGTH_LONG).show();
                        /**
                         * init intent in new view
                         * @param JeuActivity.this
                         * @param ChoicesActivity.class
                         */
                        Intent intent = new Intent(JeuActivity.this, ChoicesActivity.class);
                        /**
                         * show new intent.
                         * @param intent view ChoicesActivity
                         */
                        startActivity(intent);
                    }
                    else {
                        
                        /**
                         * Set values in editor.
                         */
                        SharedPreferences.Editor editorGame = settingsGame.edit();
                        /**
                         * Increment number error.
                         */
                        error++;
                        /**
                         * Put error in editorGame.
                         */
                        editorGame.putInt("error", error);
                        /**
                         * Commit values.
                         */
                        editorGame.commit();

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);

                        /**
                         * Set title Dialog.
                         */
                        alertDialogBuilder.setTitle(R.string.jeu_mauvaisereponse);

                        /**
                         * Set dialog message.
                         */
                        alertDialogBuilder
                        .setMessage(allreponses.get(0).getArguments())
                        .setCancelable(false)
                        .setPositiveButton(R.string.btn_ok,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                /**
                                 * If this button is clicked, close current activity.
                                 */
                                JeuActivity.this.finish();
                                Intent intent = new Intent(JeuActivity.this, JeuActivity.class);
                                startActivity(intent);
                            }
                        });
                        /**
                         * Create alert dialog.
                         */
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        /**
                         * Show it.
                         */
                        alertDialog.show();
                    }
                }
            });
        }
    }

}

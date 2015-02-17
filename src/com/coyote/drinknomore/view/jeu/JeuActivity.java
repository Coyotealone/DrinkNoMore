package com.coyote.drinknomore.view.jeu;

import java.util.ArrayList;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
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

    /** * name SharedPreferences about game */
    public static final String PREFS_GAME = "prefFileJeu";
    /** * name SharedPreferences about parameters */
    public static final String PREFS_PARAMETERS = "prefFileParameters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** * Show view activity_jeu */
        this.setContentView(R.layout.activity_jeu);
        /**
         * {@value #settings} SharePreferences
         * @param String PREFS_NAME
         * @param Integer Mode init 0
         */
        final SharedPreferences settings = getSharedPreferences(PREFS_GAME, 0);
        /**
         * {@value #viewTxtEnigme} TextView
         * init by id view activity_jeu
         */
        final TextView txtviewEnigme = (TextView) findViewById(R.id.jeu_txtEnigme);
        /**
         * {@value #rdbtnRep1} RadioButton
         * init by id view activity_jeu
         */
        final RadioButton rdbtnRep1 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep1);
        /**
         * {@value #rdbtnRep2} RadioButton
         * init by id view activity_jeu
         */
        final RadioButton rdbtnRep2 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep2);
        /**
         * {@value #rdbtnRep3} RadioButton
         * init by id view activity_jeu
         */
        final RadioButton rdbtnRep3 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep3);
        /**
         * {@value #rdbtnRep4} RadioButton
         * init by id view activity_jeu
         */
        final RadioButton rdbtnRep4 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep4);
        /**
         * {@value #rdgpRep} RadioGroup
         * init by id view activity_jeu
         */
        final RadioGroup rdgpRep = (RadioGroup) findViewById(R.id.radioReponses);

        final SharedPreferences settingsParameters = getSharedPreferences(PREFS_PARAMETERS, 0);

        Calendar calendarNow = Calendar.getInstance();
        String dayOfweek = Fonctions.nameDay(calendarNow.get(Calendar.DAY_OF_WEEK));

        Boolean checkday = checkday(settingsParameters, dayOfweek);

        if(checkday == true && (compareDateTime(settingsParameters, calendarNow) > 0)) {
            Toast.makeText(JeuActivity.this,
                    "Tu ne peux pas jouer !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(JeuActivity.this, ChoicesActivity.class);
            /**
             * show new intent
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
         * open db questionsSql
         */
        questionsSql.open();
        /**
         * open db reponsesSql
         */
        reponsesSql.open();
        /**
         * String containing the question recovered database
         * {@value #enigme} String
         * init empty
         */
        String enigme = "";
        /**
         * String containing the good response recovered database
         * {@value #reponses} String
         * init empty
         */
        String reponses = "";
        /**
         * Array String containing responses recovered database
         * {@value #choixreponses} String[]
         * init null
         */
        String[] choixreponses = null;
        /**
         * Array Integer containing number errors
         * {@value #erreurs} Integer[]
         * init 0 if SharedPreferences empty
         */
        final Integer[] erreurs = {settings.getInt(getString(R.string.Jeu_Nb_Erreurs), 0)};
        /**
         * Array int containing all id Questions
         * {@value #getId()}
         */
        int[] nbQuestions = questionsSql.getId();

        if (nbQuestions.length < 1) {
            Toast.makeText(JeuActivity.this,
                    getString(R.string.Jeu_ErreurDonnees), Toast.LENGTH_SHORT).show();
            /**
             * init intent in new view
             * @param JeuActivity.this
             * @param ChoicesActivity.class
             */
            Intent intent = new Intent(JeuActivity.this, ChoicesActivity.class);
            /**
             * show new intent
             * @param intent instance courante
             */
            startActivity(intent);
        }
        else {
            int randomNum = Fonctions.randomId(nbQuestions.length);
            /**
             * init questions compared to id in db
             * {@value #questionsSql.getByID}
             * @param int value array nbQuestions
             */
            questions = questionsSql.getByID(nbQuestions[randomNum]);
            /**
             * set enigme with getEnigme()
             * {@value #questions.getEnigme()}
             */
            enigme = questions.getEnigme();
            /**
             * set reponses with getArguments()
             * {@value #questions.getArguments()}
             */
            reponses = questions.getArguments();
            /**
             * set text view enigme
             * {@value #enigme}
             */
            txtviewEnigme.setText(enigme);
            /**
             * split choixreponses
             * @param ;
             * @return array String
             */
            choixreponses = reponses.split(";");
            /**
             * set text radioButton
             * {@value #choixreponses[0]}
             */
            if (choixreponses.length > 3) {
                rdbtnRep1.setText(choixreponses[0]);
                /**
                 * set text radioButton
                 * {@value #choixreponses[1]}
                 */
                rdbtnRep2.setText(choixreponses[1]);
                /**
                 * set text radioButton
                 * {@value #choixreponses[2]}
                 */
                rdbtnRep3.setText(choixreponses[2]);
                /**
                 * set text radioButton
                 * {@value #choixreponses[3]}
                 */
                rdbtnRep4.setText(choixreponses[3]);
                /**
                 * Button init by id activity_jeu
                 *
                 */
            }

            Button btnJeuvalider = (Button) this.findViewById(R.id.jeu_btnValider);
            /**
             * Action onClickButton btnJeuvalider
             */
            btnJeuvalider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * String reponse containing value reponse about question
                     * @value empty
                     */
                    String reponse = "";
                    /**
                     *
                     */
                    SharedPreferences.Editor editor = settings.edit();
                    /**
                     *  Array containing all responses
                     */
                    ArrayList<Reponses> allreponses = questions.getReponse();
                    /**
                     * set string reponse about object allreponses with function getSolution()
                     */
                    if (allreponses.size() > 0) {
                        reponse = allreponses.get(0).getSolution();
                    }
                    /**
                     * find radiobutton checked
                     * @value #getCheckedRadioButtonId()
                     */
                    int selectedId = rdgpRep.getCheckedRadioButtonId();
                    /**
                     * Find the radiobutton by returned id
                     */
                    RadioButton radionbuttonReponse = (RadioButton) findViewById(selectedId);
                    /**
                     *  set verifreponse about radio button check
                     */
                    String verifreponse = radionbuttonReponse.getText().toString();
                    /**
                     * check value response checked and response question
                     */
                    if (reponse.equals(verifreponse)) {
                        /**
                         * open db stats
                         */
                        statsSql.open();
                        /**
                         * set number errors
                         * @value #setNberreurs
                         * @param int erreurs[0]
                         */
                        stats.setNberreurs(erreurs[0]);
                        /**
                         * set date
                         * @value #setDate
                         * @param DateTime.now()
                         */
                        stats.setDate(DateTime.now());
                        /**
                         * insert stats in db
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
                         * show new intent
                         * @param intent view ChoicesActivity
                         */
                        startActivity(intent);
                    }
                    /**
                     *
                     */
                    else {
                        /**
                         * incremented number errors
                         */
                        erreurs[0]++;
                        /**
                         * set number errors in SharedPreferences
                         * @param String R.string.Jeu_Nb_Erreurs
                         * @param Integer erreurs[0]
                         */
                        editor.putInt(getString(R.string.Jeu_Nb_Erreurs), erreurs[0]);
                        /**
                         * send toast
                         * @param JeuActivity.this
                         * @param String R.string.Jeu_MauvaiseReponse
                         * @param int Toast.LENGTH_SHORT
                         */
                        Toast.makeText(JeuActivity.this,
                                getString(R.string.Jeu_MauvaiseReponse), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private Boolean checkday(SharedPreferences settingsParameters, String dayOfweek) {
        Boolean checkday = false;
        switch (dayOfweek) {
            case "1":
                checkday = settingsParameters.getBoolean(
                getString(R.string.Parametres_cb_horaire_dimanche), false);
                break;
            case "2": 
                checkday = settingsParameters.getBoolean(
                getString(R.string.Parametres_cb_horaire_lundi), false);
                break;
            case "3": 
                checkday = settingsParameters.getBoolean(
                getString(R.string.Parametres_cb_horaire_mardi), false);
                break;
            case "4": 
                checkday = settingsParameters.getBoolean(
                getString(R.string.Parametres_cb_horaire_mercredi), false);
                break;
            case "5": 
                checkday = settingsParameters.getBoolean(
                getString(R.string.Parametres_cb_horaire_jeudi), false);
                break;
            case "6": 
                checkday = settingsParameters.getBoolean(
                getString(R.string.Parametres_cb_horaire_vendredi), false);
                break;
            case "7": 
                checkday = settingsParameters.getBoolean(
                getString(R.string.Parametres_cb_horaire_samedi), false);
                break;
            default: 
                checkday = false;
                break;
        }
        return checkday;
    }

    private Integer compareDateTime(SharedPreferences settingsParameters, Calendar calendarNow) {
        String timeSave = settingsParameters.getString(
                getString(R.string.Parametres_timeWidget_horaire),null);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        DateTime datetimeSave = formatter.parseDateTime(timeSave);
        String timeNow = String.valueOf(calendarNow.get(Calendar.HOUR)) + ':' 
                + String.valueOf(calendarNow.get(Calendar.MINUTE));
        DateTime datetimeNow = formatter.parseDateTime(timeNow);
        return datetimeSave.compareTo(datetimeNow);
    }
}

package com.coyote.drinknomore.view.jeu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.coyote.drinknomore.Fonctions;
import com.coyote.drinknomore.HomeActivity;
import com.coyote.drinknomore.R;
import com.coyote.drinknomore.data.StatistiquesSQLiteAdapter;
import com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase;
import com.coyote.drinknomore.data.base.ReponsesSQLiteAdapterBase;
import com.coyote.drinknomore.data.base.StatistiquesSQLiteAdapterBase;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.entity.Statistiques;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Random;

public class JeuActivity extends Activity {

    /**
     * name SharedPreferences
     * @value #PREFS_NAME String
     */
    public static final String PREFS_NAME = "prefFileJeu";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //Show view activity_jeu
        this.setContentView(R.layout.activity_jeu);

        /**
         * {@value #settings} SharePreferences
         * @param String PREFS_NAME
         * @param Integer Mode init 0
         */
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        /**
         *  {@value #viewTxtEnigme} TextView
         *  init by id view activity_jeu
         */
        final TextView txtviewEnigme = (TextView) findViewById(R.id.jeu_txtEnigme);
        /**
         *  {@value #rdbtnRep1} RadioButton
         *  init by id view activity_jeu
         */
        final RadioButton rdbtnRep1 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep1);
        /**
         *  {@value #rdbtnRep2} RadioButton
         *  init by id view activity_jeu
         */
        final RadioButton rdbtnRep2 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep2);
        /**
         *  {@value #rdbtnRep3} RadioButton
         *  init by id view activity_jeu
         */
        final RadioButton rdbtnRep3 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep3);
        /**
         *  {@value #rdbtnRep4} RadioButton
         *  init by id view activity_jeu
         */
        final RadioButton rdbtnRep4 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep4);
        /**
         *  {@value #rdgpRep} RadioGroup
         *  init by id view activity_jeu
         */
        final RadioGroup rdgpRep = (RadioGroup) findViewById(R.id.radioReponses);
        /**
         *  {@value #questions} Questions
         *  @see com.coyote.drinknomore.entity.Questions
         */
        final Questions questions;
        /**
         *  {@value #stats} Statistiques
         *  @see com.coyote.drinknomore.entity.Statistiques
         */
        final Statistiques stats = new Statistiques();
        /**
         *  {@value #questionsSQL} QuestionsSQLiteAdapaterBase
         *  @see com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase
         *  @param this
         */
        final QuestionsSQLiteAdapterBase questionsSQL = new QuestionsSQLiteAdapterBase(this);
        /**
         *  {@value #reponsesSQL} ReponsesSQLiteAdapaterBase
         *  @see com.coyote.drinknomore.data.base.ReponsesSQLiteAdapterBase
         *  @param this
         */
        final ReponsesSQLiteAdapterBase reponsesSQL = new ReponsesSQLiteAdapterBase(this);
        /**
         *  {@value #statsSQL} StatistiquesSQLiteAdapaterBase
         *  @see com.coyote.drinknomore.data.base.ReponsesSQLiteAdapterBase
         *  @param this
         */
        final StatistiquesSQLiteAdapterBase statsSQL = new StatistiquesSQLiteAdapterBase(this);

        /**
         *  open db questionsSQL
         */
        questionsSQL.open();
        /**
         *  open db reponsesSQL
         */
        reponsesSQL.open();

        /**
         * String containing the question recovered database
         * {@value #enigme} String
         * init empty
         */
        String enigme = "";
        /**
         *  String containing the good response recovered database
         *  {@value #reponses} String
         *  init empty
         */
        String reponses = "";
        /**
         *  Array String containing responses recovered database
         *  {@value #choixreponses} String[]
         *  init null
         */
        String[] choixreponses = null;
        /**
         *  Array Integer containing number errors
         *  {@value #erreurs} Integer[]
         *  init 0 if SharedPreferences empty
         */
        final Integer[] erreurs = {settings.getInt("nb_erreurs", 0)};

        /**
         * Array int containing all id Questions
         * {@value #getId()}
         */
        int[] nbQuestions = questionsSQL.getId();
        /**
         * int minimum into random
         * {@value 0}
         */
        int min = 0;
        /**
         * int maximum into random
         * {@value nbQuestions.length - 1}
         */
        int max = nbQuestions.length - 1;
        /**
         * {@value #new Random}
         */
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        /**
         * init questions compared to id in db
         * {@value #questionsSQL.getByID}
         * @param int value array nbQuestions
         */
        questions = questionsSQL.getByID(nbQuestions[randomNum]);
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
        txtviewEnigme.setText(enigme);
        choixreponses = reponses.split(";");
        rdbtnRep1.setText(choixreponses[0]);
        rdbtnRep2.setText(choixreponses[1]);
        rdbtnRep3.setText(choixreponses[2]);
        rdbtnRep4.setText(choixreponses[3]);

        Button btnJeu_valider = (Button) this
                .findViewById(R.id.jeu_btnValider);
        btnJeu_valider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String reponse = "";
                RadioButton RadioButtonReponse;
                SharedPreferences.Editor editor = settings.edit();

                ArrayList<Reponses> allreponses = questions.getReponse();
                reponse = allreponses.get(0).getSolution();

                int selectedId = rdgpRep.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButtonReponse = (RadioButton) findViewById(selectedId);

                Integer verifreponse = Integer.parseInt(RadioButtonReponse.getText().toString());

                if(Integer.parseInt(reponse) == verifreponse) {
                    statsSQL.open();
                    stats.setNberreurs(erreurs[0]);
                    stats.setDate(DateTime.now());
                    statsSQL.insert(stats);
                    Toast.makeText(JeuActivity.this,
                            "Bonne réponse :)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(JeuActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    erreurs[0]++;
                    editor.putInt("nb_erreurs", erreurs[0]);
                    Toast.makeText(JeuActivity.this,
                            "Try Again !", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}

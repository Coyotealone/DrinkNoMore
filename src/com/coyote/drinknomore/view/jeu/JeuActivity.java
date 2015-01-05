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

    public static final String PREFS_NAME = "prefFileJeu";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_jeu);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        final TextView viewTxtEnigne = (TextView) findViewById(R.id.jeu_txtEnigme);
        final RadioButton RadioReponse1 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep1);
        final RadioButton RadioReponse2 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep2);
        final RadioButton RadioReponse3 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep3);
        final RadioButton RadioReponse4 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep4);
        final RadioGroup RadioReponses = (RadioGroup) findViewById(R.id.radioReponses);
        final Questions questions;
        final Statistiques stats = new Statistiques();

        final QuestionsSQLiteAdapterBase questionsSQL = new QuestionsSQLiteAdapterBase(this);
        final ReponsesSQLiteAdapterBase reponsesSQL = new ReponsesSQLiteAdapterBase(this);
        final StatistiquesSQLiteAdapterBase statsSQL = new StatistiquesSQLiteAdapterBase(this);

        questionsSQL.open();
        reponsesSQL.open();

        String enigme = "";
        String reponses = "";
        String[] choixreponses = null;
        final Integer[] erreurs = {settings.getInt("nb_erreurs", 0)};

        int[] nbQuestions = questionsSQL.getId();
        int min = 0;
        int max = nbQuestions.length - 1;
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        questions = questionsSQL.getByID(nbQuestions[randomNum]);
        enigme = questions.getEnigme();
        reponses = questions.getArguments();
        viewTxtEnigne.setText(enigme);
        choixreponses = reponses.split(";");
        RadioReponse1.setText(choixreponses[0]);
        RadioReponse2.setText(choixreponses[1]);
        RadioReponse3.setText(choixreponses[2]);
        RadioReponse4.setText(choixreponses[3]);

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

                int selectedId = RadioReponses.getCheckedRadioButtonId();

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

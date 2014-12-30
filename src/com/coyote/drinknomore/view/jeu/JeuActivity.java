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
import com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase;
import com.coyote.drinknomore.data.base.ReponsesSQLiteAdapterBase;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;

import java.util.ArrayList;
import java.util.Random;

public class JeuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_jeu);

        final TextView viewTxtEnigne = (TextView) findViewById(R.id.jeu_txtEnigme);
        final RadioButton RadioReponse1 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep1);
        final RadioButton RadioReponse2 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep2);
        final RadioButton RadioReponse3 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep3);
        final RadioButton RadioReponse4 = (RadioButton) findViewById(R.id.Jeu_Radio_Rep4);
        final RadioGroup RadioReponses = (RadioGroup) findViewById(R.id.radioReponses);
        final Questions questions;

        final QuestionsSQLiteAdapterBase questionsSQL = new QuestionsSQLiteAdapterBase(this);
        final ReponsesSQLiteAdapterBase reponsesSQL = new ReponsesSQLiteAdapterBase(this);

        questionsSQL.open();
        reponsesSQL.open();
        String enigme = "";
        String reponses = "";
        String[] choixreponses = null;
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
                ArrayList<Reponses> allreponses = questions.getReponse();
                reponse = allreponses.get(0).getSolution();

                int selectedId = RadioReponses.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButtonReponse = (RadioButton) findViewById(selectedId);

                Integer verifreponse = Integer.parseInt(RadioButtonReponse.getText().toString());

                if(Integer.parseInt(reponse) == verifreponse) {
                    Toast.makeText(JeuActivity.this,
                            "Bonne réponse :)", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(JeuActivity.this,
                            "Try Again !", Toast.LENGTH_SHORT).show();
                }

                //if(reponse)
                /*String valueReponse = "";
                EditText editTxtReponse = (EditText) findViewById(R.id.jeu_txtReponse);
                valueReponse = editTxtReponse.getText().toString();
                String enigme = "";
                String reponse = "";
                ArrayList<Reponses> tabreponses;
                for (Questions questions1 : questionsSQL.getAll()) {
                    enigme = questions1.getEnigme();
                    for (Reponses reponses : tabreponses = questions1.getReponse()) {
                        reponse = reponses.getSolution();
                    };
                };
                if (reponse == valueReponse) {
                    Intent intent = new Intent(JeuActivity.this, HomeActivity.class);
                    startActivity(intent);
                    //questions.close();
                } else {
                    Intent intent = new Intent(JeuActivity.this, JeuActivity.class);
                    startActivity(intent);
                    //questions.close();
                }*/
            }
        });
    }
}

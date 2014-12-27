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
import android.widget.TextView;

import com.coyote.drinknomore.Fonctions;
import com.coyote.drinknomore.HomeActivity;
import com.coyote.drinknomore.R;
import com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase;
import com.coyote.drinknomore.entity.Questions;

import java.util.ArrayList;

public class JeuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_jeu);

        TextView viewTxtEnigne = (TextView) findViewById(R.id.jeu_txtEnigme);
        final QuestionsSQLiteAdapterBase questions = new QuestionsSQLiteAdapterBase(this);
        questions.open();
        String enigme = "";
        String reponse = "";
        for (Questions questions1 : questions.getAll()) {
            enigme = questions1.getEnigme();
            reponse = questions1.getReponse().toString();
        }
        ;
        viewTxtEnigne.setText(enigme);

        Button btnJeu_valider = (Button) this
                .findViewById(R.id.jeu_btnValider);
        btnJeu_valider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String valueReponse = "";
                EditText editTxtReponse = (EditText) findViewById(R.id.jeu_txtReponse);
                valueReponse = editTxtReponse.getText().toString();
                String enigme = "";
                String reponse = "";
                for (Questions questions1 : questions.getAll()) {
                    enigme = questions1.getEnigme();
                    reponse = questions1.getReponse().toString();
                }
                ;
                if(reponse == valueReponse) {
                    Intent intent = new Intent(JeuActivity.this,
                            HomeActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(JeuActivity.this,
                            JeuActivity.class);
                    startActivity(intent);
                }






            }
        });


	}

}

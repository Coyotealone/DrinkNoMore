/**************************************************************************
 * HomeActivity.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;
import com.coyote.drinknomore.view.reponses.ReponsesListActivity;
import com.coyote.drinknomore.view.jeu.JeuActivity;
import com.coyote.drinknomore.view.jeu.ParametresActivity;
import com.coyote.drinknomore.view.questions.QuestionsListActivity;
import com.coyote.drinknomore.view.statistiques.StatistiquesListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
        implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        
        this.initButtons();
    }

    /**
     * Initialize the buttons click listeners.
     */
    private void initButtons() {
        this.findViewById(R.id.reponses_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.questions_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.statistiques_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.btn_parametres)
						.setOnClickListener(this);
        this.findViewById(R.id.btn_play)
        				.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.reponses_list_button:
                intent = new Intent(this,
                        ReponsesListActivity.class);
                break;

            case R.id.questions_list_button:
                intent = new Intent(this,
                        QuestionsListActivity.class);
                break;

            case R.id.statistiques_list_button:
                intent = new Intent(this,
                        StatistiquesListActivity.class);
                break;
            
            case R.id.btn_parametres:
            	intent = new Intent(this,
            			ParametresActivity.class);
            	break;
            	
            case R.id.btn_play:
            	intent = new Intent(this,
            			JeuActivity.class);
            	break;

            default:
                intent = null;
                break;
        }

        if (intent != null) {
            this.startActivity(intent);
        }
    }

}

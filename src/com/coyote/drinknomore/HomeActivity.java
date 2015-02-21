/**************************************************************************
 * HomeActivity.java, drinknomore Android
 * Copyright 2014
 * Description :
 * Author(s)   : Coyote
 * Licence     :
 * Last update : Feb 19, 2014
 **************************************************************************/

package com.coyote.drinknomore;

import android.content.Intent;
import android.os.Bundle;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;
import com.coyote.drinknomore.view.youtube.YoutubeActivity;

/**
 * Home Activity.
 * Show YoutubeActivity by default
 */
public class HomeActivity extends HarmonyFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        Fonctions fcts = new Fonctions();
        /** @see Fonctions/associateQuestionsReponses() */
        fcts.associateQuestionsReponses(this);
        Intent intent = new Intent(this, YoutubeActivity.class);
        this.startActivity(intent);
    }

}

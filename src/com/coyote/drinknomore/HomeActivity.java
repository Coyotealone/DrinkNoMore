/**************************************************************************
 * HomeActivity.java, drinknomore Android
 *
 * Copyright 2014
 * Description :
 * Author(s)   : Harmony
 * Licence     :
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;
import com.coyote.drinknomore.view.youtube.YoutubeActivity;
import android.content.Intent;
import android.os.Bundle;

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
		fcts.AssociateQuestionsReponses(this);
		Intent intent = new Intent(this, YoutubeActivity.class);
		this.startActivity(intent);
	}

}

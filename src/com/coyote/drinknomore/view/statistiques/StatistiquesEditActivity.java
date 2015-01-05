/**************************************************************************
 * StatistiquesEditActivity.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.statistiques;

import com.coyote.drinknomore.R;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Statistiques edit Activity.
 *
 * This only contains a StatistiquesEditFragment.
 *
 * @see android.app.Activity
 */
public class StatistiquesEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_statistiques_edit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

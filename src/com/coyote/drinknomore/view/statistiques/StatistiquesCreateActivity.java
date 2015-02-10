/**************************************************************************
 * StatistiquesCreateActivity.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.view.statistiques;

import com.coyote.drinknomore.R;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * Statistiques create Activity.
 *
 * This only contains a StatistiquesCreateFragment.
 *
 * @see android.app.Activity
 */
public class StatistiquesCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_statistiques_create);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

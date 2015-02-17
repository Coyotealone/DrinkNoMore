/**************************************************************************
 * StatistiquesEditActivity.java, drinknomore Android
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 **************************************************************************/

package com.coyote.drinknomore.view.statistiques;

import android.os.Bundle;

import com.coyote.drinknomore.R;
import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;

/** Statistiques edit Activity.
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

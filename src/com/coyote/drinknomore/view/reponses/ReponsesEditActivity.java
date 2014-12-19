/**************************************************************************
 * ReponsesEditActivity.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.reponses;

import com.coyote.drinknomore.R;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Reponses edit Activity.
 *
 * This only contains a ReponsesEditFragment.
 *
 * @see android.app.Activity
 */
public class ReponsesEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_reponses_edit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

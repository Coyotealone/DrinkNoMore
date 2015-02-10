/**************************************************************************
 * ReponsesCreateActivity.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.view.reponses;

import com.coyote.drinknomore.R;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * Reponses create Activity.
 *
 * This only contains a ReponsesCreateFragment.
 *
 * @see android.app.Activity
 */
public class ReponsesCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_reponses_create);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

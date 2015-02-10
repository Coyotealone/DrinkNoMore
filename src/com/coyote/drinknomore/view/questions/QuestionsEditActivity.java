/**************************************************************************
 * QuestionsEditActivity.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.view.questions;

import com.coyote.drinknomore.R;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Questions edit Activity.
 *
 * This only contains a QuestionsEditFragment.
 *
 * @see android.app.Activity
 */
public class QuestionsEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_questions_edit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

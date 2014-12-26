/**************************************************************************
 * QuestionsShowActivity.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.questions;

import com.coyote.drinknomore.R;

import com.coyote.drinknomore.harmony.view.HarmonyFragmentActivity;
import com.coyote.drinknomore.view.questions.QuestionsShowFragment.DeleteCallback;
import android.os.Bundle;

/** Questions show Activity.
 *
 * This only contains a QuestionsShowFragment.
 *
 * @see android.app.Activity
 */
public class QuestionsShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_questions_show);
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}

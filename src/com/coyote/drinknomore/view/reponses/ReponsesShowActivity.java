/**************************************************************************
 * ReponsesShowActivity.java, drinknomore Android
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
import com.coyote.drinknomore.view.reponses.ReponsesShowFragment.DeleteCallback;
import android.os.Bundle;

/** Reponses show Activity.
 *
 * This only contains a ReponsesShowFragment.
 *
 * @see android.app.Activity
 */
public class ReponsesShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_reponses_show);
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}

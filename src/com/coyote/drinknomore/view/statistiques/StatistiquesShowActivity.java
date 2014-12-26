/**************************************************************************
 * StatistiquesShowActivity.java, drinknomore Android
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
import com.coyote.drinknomore.view.statistiques.StatistiquesShowFragment.DeleteCallback;
import android.os.Bundle;

/** Statistiques show Activity.
 *
 * This only contains a StatistiquesShowFragment.
 *
 * @see android.app.Activity
 */
public class StatistiquesShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_statistiques_show);
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}

/**************************************************************************
 * HarmonyFragmentActivity.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.harmony.view;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.coyote.drinknomore.menu.DrinknomoreMenu;
import com.coyote.drinknomore.DrinknomoreApplication;
import com.coyote.drinknomore.DrinknomoreApplicationBase.DeviceType;

/**
 * Custom FragmentActivity for harmony projects.
 * This fragment activity helps you use the menu wrappers, detect alone if
 * you're in tablet/dual mode.
 */
public abstract class HarmonyFragmentActivity extends SherlockFragmentActivity {
    /** Hack number for support v4 onActivityResult. */
    protected static final int SUPPORT_V4_RESULT_HACK = 0xFFFF;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = true;

        try {
            DrinknomoreMenu.getInstance(this).clear(menu);
            DrinknomoreMenu.getInstance(this).updateMenu(menu,
                                                                          this);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        if (result) {
            result = super.onCreateOptionsMenu(menu);
        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result;
        try {
            result = DrinknomoreMenu.getInstance(this).dispatch(
                                                                    item, this);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                                                  Intent data) {
        try {
            DrinknomoreMenu.getInstance(this).onActivityResult(
                                           requestCode, resultCode, data, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Is this device in tablet mode ?
     *
     * @return true if tablet mode
     */
    public boolean isDualMode() {
        return DrinknomoreApplication.getDeviceType(this).equals(DeviceType.TABLET);
    }
}

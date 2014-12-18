/**************************************************************************
 * DrinknomoreMenu.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.menu;


import android.support.v4.app.Fragment;

import com.coyote.drinknomore.menu.base.DrinknomoreMenuBase;

/**
 * DrinknomoreMenu.
 * 
 * This class is an engine used to manage the different menus of your application.
 * Its use is quite simple :
 * Create a class called [YourMenuName]MenuWrapper in this package and
 * make it implement the interface MenuWrapperBase.
 * (For examples, please see CrudCreateMenuWrapper and CrudEditDeleteMenuWrapper in
 * this package.)
 * When this is done, just call this harmony command :
 * script/console.sh orm:menu:update.
 * This will auto-generate a group id for your menu.
 */
public class DrinknomoreMenu
                extends DrinknomoreMenuBase {

    /** Singleton unique instance. */
    private static volatile DrinknomoreMenu singleton;

    /**
     * Constructor.
     * @param ctx The android.content.Context
     * @throws Exception If something bad happened
     */
    public DrinknomoreMenu(final android.content.Context ctx) throws Exception {
        super(ctx);
    }

    /**
     * Constructor.
     * @param ctx The context
     * @param fragment The parent fragment
     * @throws Exception If something bad happened
     */
    public DrinknomoreMenu(final android.content.Context ctx,
                        final Fragment fragment) throws Exception {
        super(ctx, fragment);
    }

    /** Get unique instance.
     * @param ctx The context
     * @return DrinknomoreMenu instance
     * @throws Exception If something bad happened
     */
    public static final synchronized DrinknomoreMenu getInstance(
                        final android.content.Context ctx) throws Exception {
        return getInstance(ctx, null);
    }

    /** Get unique instance.
     * @param ctx The context
     * @param fragment The parent fragment
     * @return DrinknomoreMenu instance
     * @throws Exception If something bad happened
     */
    public static final synchronized DrinknomoreMenu getInstance(
            final android.content.Context ctx, final Fragment fragment) throws Exception {
        if (singleton == null) {
            singleton = new DrinknomoreMenu(ctx, fragment);
        }  else {
            singleton.ctx = ctx;
            singleton.fragment = fragment;
        }

        return singleton;
    }
}

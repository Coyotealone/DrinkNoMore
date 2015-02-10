/**************************************************************************
 * DrinknomoreSQLiteOpenHelper.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.data;

import com.coyote.drinknomore.data.base.DrinknomoreSQLiteOpenHelperBase;

import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class DrinknomoreSQLiteOpenHelper
                    extends DrinknomoreSQLiteOpenHelperBase {

    /**
     * Constructor.
     * @param ctx context
     * @param name name
     * @param factory factory
     * @param version version
     */
    public DrinknomoreSQLiteOpenHelper(final android.content.Context ctx,
           final String name, final CursorFactory factory, final int version) {
        super(ctx, name, factory, version);
    }

}

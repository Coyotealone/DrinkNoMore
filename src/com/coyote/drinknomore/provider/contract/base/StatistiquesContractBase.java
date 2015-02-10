/**************************************************************************
 * StatistiquesContractBase.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import com.coyote.drinknomore.entity.Statistiques;



import com.coyote.drinknomore.provider.contract.StatistiquesContract;
import com.coyote.drinknomore.harmony.util.DateUtils;

/** Drinknomore contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class StatistiquesContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            StatistiquesContract.TABLE_NAME + "." + COL_ID;

    /** date. */
    public static final String COL_DATE =
            "date";
    /** Alias. */
    public static final String ALIASED_COL_DATE =
            StatistiquesContract.TABLE_NAME + "." + COL_DATE;

    /** nberreurs. */
    public static final String COL_NBERREURS =
            "nberreurs";
    /** Alias. */
    public static final String ALIASED_COL_NBERREURS =
            StatistiquesContract.TABLE_NAME + "." + COL_NBERREURS;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Statistiques";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Statistiques";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
            StatistiquesContract.COL_ID,
            StatistiquesContract.COL_DATE,
            StatistiquesContract.COL_NBERREURS
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
            StatistiquesContract.ALIASED_COL_ID,
            StatistiquesContract.ALIASED_COL_DATE,
            StatistiquesContract.ALIASED_COL_NBERREURS
    };


    /**
     * Converts a Statistiques into a content values.
     *
     * @param item The Statistiques to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Statistiques item) {
        final ContentValues result = new ContentValues();

            result.put(StatistiquesContract.COL_ID,
                String.valueOf(item.getId()));

            if (item.getDate() != null) {
                result.put(StatistiquesContract.COL_DATE,
                    item.getDate().toString(ISODateTimeFormat.dateTime()));
            }

            if (item.getNberreurs() != null) {
                result.put(StatistiquesContract.COL_NBERREURS,
                    String.valueOf(item.getNberreurs()));
            }


        return result;
    }

    /**
     * Converts a Cursor into a Statistiques.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Statistiques 
     */
    public static Statistiques cursorToItem(final android.database.Cursor cursor) {
        Statistiques result = new Statistiques();
        StatistiquesContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Statistiques entity.
     * @param cursor Cursor object
     * @param result Statistiques entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Statistiques result) {
        if (cursor.getCount() != 0) {
            int index;

                index = cursor.getColumnIndexOrThrow(StatistiquesContract.COL_ID);
                result.setId(
                        cursor.getInt(index));

                index = cursor.getColumnIndexOrThrow(StatistiquesContract.COL_DATE);
                final DateTime dtDate =
                        DateUtils.formatISOStringToDateTime(
                                cursor.getString(index));
                if (dtDate != null) {
                        result.setDate(
                                dtDate);
                } else {
                    result.setDate(new DateTime());
                }

                index = cursor.getColumnIndexOrThrow(StatistiquesContract.COL_NBERREURS);
                result.setNberreurs(
                        cursor.getInt(index));


        }
    }

    /**
     * Convert Cursor of database to Array of Statistiques entity.
     * @param cursor Cursor object
     * @return Array of Statistiques entity
     */
    public static ArrayList<Statistiques> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Statistiques> result = new ArrayList<Statistiques>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Statistiques item;
            do {
                item = StatistiquesContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}

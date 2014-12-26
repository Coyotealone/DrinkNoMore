/**************************************************************************
 * ReponsesContractBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.entity.Questions;



import com.coyote.drinknomore.provider.contract.ReponsesContract;

/** Drinknomore contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ReponsesContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ReponsesContract.TABLE_NAME + "." + COL_ID;

    /** solution. */
    public static final String COL_SOLUTION =
            "solution";
    /** Alias. */
    public static final String ALIASED_COL_SOLUTION =
            ReponsesContract.TABLE_NAME + "." + COL_SOLUTION;

    /** arguments. */
    public static final String COL_ARGUMENTS =
            "arguments";
    /** Alias. */
    public static final String ALIASED_COL_ARGUMENTS =
            ReponsesContract.TABLE_NAME + "." + COL_ARGUMENTS;

    /** question_id. */
    public static final String COL_QUESTION_ID =
            "question_id";
    /** Alias. */
    public static final String ALIASED_COL_QUESTION_ID =
            ReponsesContract.TABLE_NAME + "." + COL_QUESTION_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Reponses";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Reponses";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
            ReponsesContract.COL_ID,
            ReponsesContract.COL_SOLUTION,
            ReponsesContract.COL_ARGUMENTS,
            ReponsesContract.COL_QUESTION_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
            ReponsesContract.ALIASED_COL_ID,
            ReponsesContract.ALIASED_COL_SOLUTION,
            ReponsesContract.ALIASED_COL_ARGUMENTS,
            ReponsesContract.ALIASED_COL_QUESTION_ID
    };


    /**
     * Converts a Reponses into a content values.
     *
     * @param item The Reponses to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Reponses item) {
        final ContentValues result = new ContentValues();

            result.put(ReponsesContract.COL_ID,
                String.valueOf(item.getId()));

            if (item.getSolution() != null) {
                result.put(ReponsesContract.COL_SOLUTION,
                    item.getSolution());
            }

            if (item.getArguments() != null) {
                result.put(ReponsesContract.COL_ARGUMENTS,
                    item.getArguments());
            }

            if (item.getQuestion() != null) {
                result.put(ReponsesContract.COL_QUESTION_ID,
                    item.getQuestion().getId());
            } else {
                result.put(ReponsesContract.COL_QUESTION_ID, (String) null);
            }


        return result;
    }

    /**
     * Converts a Cursor into a Reponses.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Reponses 
     */
    public static Reponses cursorToItem(final android.database.Cursor cursor) {
        Reponses result = new Reponses();
        ReponsesContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Reponses entity.
     * @param cursor Cursor object
     * @param result Reponses entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Reponses result) {
        if (cursor.getCount() != 0) {
            int index;

                index = cursor.getColumnIndexOrThrow(ReponsesContract.COL_ID);
                result.setId(
                        cursor.getInt(index));

                index = cursor.getColumnIndexOrThrow(ReponsesContract.COL_SOLUTION);
                result.setSolution(
                        cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(ReponsesContract.COL_ARGUMENTS);
                result.setArguments(
                        cursor.getString(index));

                if (result.getQuestion() == null) {
                    final Questions question = new Questions();
                    index = cursor.getColumnIndexOrThrow(ReponsesContract.COL_QUESTION_ID);
                    if (!cursor.isNull(index)) {
                        question.setId(cursor.getInt(index));
                        result.setQuestion(question);
                        }
                }


        }
    }

    /**
     * Convert Cursor of database to Array of Reponses entity.
     * @param cursor Cursor object
     * @return Array of Reponses entity
     */
    public static ArrayList<Reponses> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Reponses> result = new ArrayList<Reponses>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Reponses item;
            do {
                item = ReponsesContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}

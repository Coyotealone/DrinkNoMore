/**************************************************************************
 * ReponsesContractBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2014
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

    /** reponse. */
    public static final String COL_REPONSE =
            "reponse";
    /** Alias. */
    public static final String ALIASED_COL_REPONSE =
            ReponsesContract.TABLE_NAME + "." + COL_REPONSE;

    /** arguments. */
    public static final String COL_ARGUMENTS =
            "arguments";
    /** Alias. */
    public static final String ALIASED_COL_ARGUMENTS =
            ReponsesContract.TABLE_NAME + "." + COL_ARGUMENTS;

    /** questions_id. */
    public static final String COL_QUESTIONS_ID =
            "questions_id";
    /** Alias. */
    public static final String ALIASED_COL_QUESTIONS_ID =
            ReponsesContract.TABLE_NAME + "." + COL_QUESTIONS_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Reponses";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Reponses";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
            ReponsesContract.COL_ID,
            ReponsesContract.COL_REPONSE,
            ReponsesContract.COL_ARGUMENTS,
            ReponsesContract.COL_QUESTIONS_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
            ReponsesContract.ALIASED_COL_ID,
            ReponsesContract.ALIASED_COL_REPONSE,
            ReponsesContract.ALIASED_COL_ARGUMENTS,
            ReponsesContract.ALIASED_COL_QUESTIONS_ID
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

            if (item.getReponse() != null) {
                result.put(ReponsesContract.COL_REPONSE,
                    item.getReponse());
            }

            if (item.getArguments() != null) {
                result.put(ReponsesContract.COL_ARGUMENTS,
                    item.getArguments());
            }

            if (item.getQuestions() != null) {
                result.put(ReponsesContract.COL_QUESTIONS_ID,
                    item.getQuestions().getId());
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

                index = cursor.getColumnIndexOrThrow(ReponsesContract.COL_REPONSE);
                result.setReponse(
                        cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(ReponsesContract.COL_ARGUMENTS);
                result.setArguments(
                        cursor.getString(index));

                if (result.getQuestions() == null) {
                    final Questions questions = new Questions();
                    index = cursor.getColumnIndexOrThrow(ReponsesContract.COL_QUESTIONS_ID);
                    questions.setId(cursor.getInt(index));
                    result.setQuestions(questions);
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

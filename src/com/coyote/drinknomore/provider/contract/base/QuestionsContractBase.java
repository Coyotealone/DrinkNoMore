/**************************************************************************
 * QuestionsContractBase.java, drinknomore Android
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

import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;



import com.coyote.drinknomore.provider.contract.QuestionsContract;

/** Drinknomore contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class QuestionsContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            QuestionsContract.TABLE_NAME + "." + COL_ID;

    /** question. */
    public static final String COL_QUESTION =
            "question";
    /** Alias. */
    public static final String ALIASED_COL_QUESTION =
            QuestionsContract.TABLE_NAME + "." + COL_QUESTION;

    /** arguments. */
    public static final String COL_ARGUMENTS =
            "arguments";
    /** Alias. */
    public static final String ALIASED_COL_ARGUMENTS =
            QuestionsContract.TABLE_NAME + "." + COL_ARGUMENTS;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Questions";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Questions";
    /** Global Fields. */
    public static final String[] COLS = new String[] {
            QuestionsContract.COL_ID,
            QuestionsContract.COL_QUESTION,
            QuestionsContract.COL_ARGUMENTS,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
            QuestionsContract.ALIASED_COL_ID,
            QuestionsContract.ALIASED_COL_QUESTION,
            QuestionsContract.ALIASED_COL_ARGUMENTS,
    };


    /**
     * Converts a Questions into a content values.
     *
     * @param item The Questions to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Questions item) {
        final ContentValues result = new ContentValues();

            result.put(QuestionsContract.COL_ID,
                String.valueOf(item.getId()));

            if (item.getQuestion() != null) {
                result.put(QuestionsContract.COL_QUESTION,
                    item.getQuestion());
            }

            if (item.getArguments() != null) {
                result.put(QuestionsContract.COL_ARGUMENTS,
                    item.getArguments());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Questions.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Questions 
     */
    public static Questions cursorToItem(final android.database.Cursor cursor) {
        Questions result = new Questions();
        QuestionsContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Questions entity.
     * @param cursor Cursor object
     * @param result Questions entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Questions result) {
        if (cursor.getCount() != 0) {
            int index;

                index = cursor.getColumnIndexOrThrow(QuestionsContract.COL_ID);
                result.setId(
                        cursor.getInt(index));

                index = cursor.getColumnIndexOrThrow(QuestionsContract.COL_QUESTION);
                result.setQuestion(
                        cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(QuestionsContract.COL_ARGUMENTS);
                result.setArguments(
                        cursor.getString(index));


        }
    }

    /**
     * Convert Cursor of database to Array of Questions entity.
     * @param cursor Cursor object
     * @return Array of Questions entity
     */
    public static ArrayList<Questions> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Questions> result = new ArrayList<Questions>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Questions item;
            do {
                item = QuestionsContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}

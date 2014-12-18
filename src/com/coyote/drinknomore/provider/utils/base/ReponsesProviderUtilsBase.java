/**************************************************************************
 * ReponsesProviderUtilsBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.coyote.drinknomore.provider.utils.ProviderUtils;
import com.coyote.drinknomore.criterias.base.CriteriaExpression;
import com.coyote.drinknomore.criterias.base.CriteriaExpression.GroupType;

import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.entity.Questions;

import com.coyote.drinknomore.provider.ReponsesProviderAdapter;
import com.coyote.drinknomore.provider.QuestionsProviderAdapter;
import com.coyote.drinknomore.provider.DrinknomoreProvider;
import com.coyote.drinknomore.provider.contract.ReponsesContract;
import com.coyote.drinknomore.provider.contract.QuestionsContract;

/**
 * Reponses Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ReponsesProviderUtilsBase
            extends ProviderUtils<Reponses> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "ReponsesProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public ReponsesProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Reponses item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = ReponsesContract.itemToContentValues(item);
        itemValues.remove(ReponsesContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                ReponsesProviderAdapter.REPONSES_URI)
                        .withValues(itemValues)
                        .build());


        try {
            ContentProviderResult[] results =
                    prov.applyBatch(DrinknomoreProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }


    /**
     * Delete from DB.
     * @param item Reponses
     * @return number of row affected
     */
    public int delete(final Reponses item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = ReponsesProviderAdapter.REPONSES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Reponses
     */
    public Reponses query(final Reponses item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Reponses
     */
    public Reponses query(final int id) {
        Reponses result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(ReponsesContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            ReponsesProviderAdapter.REPONSES_URI,
            ReponsesContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = ReponsesContract.cursorToItem(cursor);
            cursor.close();

            if (result.getQuestions() != null) {
                result.setQuestions(
                    this.getAssociateQuestions(result));
            }
        }

        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Reponses>
     */
    public ArrayList<Reponses> queryAll() {
        ArrayList<Reponses> result =
                    new ArrayList<Reponses>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ReponsesProviderAdapter.REPONSES_URI,
                ReponsesContract.ALIASED_COLS,
                null,
                null,
                null);

        result = ReponsesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Reponses>
     */
    public ArrayList<Reponses> query(CriteriaExpression expression) {
        ArrayList<Reponses> result =
                    new ArrayList<Reponses>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ReponsesProviderAdapter.REPONSES_URI,
                ReponsesContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = ReponsesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Reponses
     
     * @return number of rows updated
     */
    public int update(final Reponses item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = ReponsesContract.itemToContentValues(
                item);

        Uri uri = ReponsesProviderAdapter.REPONSES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());



        try {
            ContentProviderResult[] results = prov.applyBatch(DrinknomoreProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /** Relations operations. */
    /**
     * Get associate Questions.
     * @param item Reponses
     * @return Questions
     */
    public Questions getAssociateQuestions(
            final Reponses item) {
        Questions result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor questionsCursor = prov.query(
                QuestionsProviderAdapter.QUESTIONS_URI,
                QuestionsContract.ALIASED_COLS,
                QuestionsContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getQuestions().getId())},
                null);

        if (questionsCursor.getCount() > 0) {
            questionsCursor.moveToFirst();
            result = QuestionsContract.cursorToItem(questionsCursor);
        } else {
            result = null;
        }
        questionsCursor.close();

        return result;
    }

}

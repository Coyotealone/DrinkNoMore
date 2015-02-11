/**************************************************************************
 * StatistiquesProviderUtilsBase.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
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

import com.coyote.drinknomore.entity.Statistiques;

import com.coyote.drinknomore.provider.StatistiquesProviderAdapter;
import com.coyote.drinknomore.provider.DrinknomoreProvider;
import com.coyote.drinknomore.provider.contract.StatistiquesContract;

/**
 * Statistiques Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class StatistiquesProviderUtilsBase
            extends ProviderUtils<Statistiques> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "StatistiquesProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public StatistiquesProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Statistiques item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = StatistiquesContract.itemToContentValues(item);
        itemValues.remove(StatistiquesContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                StatistiquesProviderAdapter.STATISTIQUES_URI)
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
     * @param item Statistiques
     * @return number of row affected
     */
    public int delete(final Statistiques item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = StatistiquesProviderAdapter.STATISTIQUES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Statistiques
     */
    public Statistiques query(final Statistiques item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Statistiques
     */
    public Statistiques query(final int id) {
        Statistiques result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(StatistiquesContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            StatistiquesProviderAdapter.STATISTIQUES_URI,
            StatistiquesContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = StatistiquesContract.cursorToItem(cursor);
            cursor.close();

        }

        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Statistiques>
     */
    public ArrayList<Statistiques> queryAll() {
        ArrayList<Statistiques> result =
                    new ArrayList<Statistiques>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                StatistiquesProviderAdapter.STATISTIQUES_URI,
                StatistiquesContract.ALIASED_COLS,
                null,
                null,
                null);

        result = StatistiquesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Statistiques>
     */
    public ArrayList<Statistiques> query(CriteriaExpression expression) {
        ArrayList<Statistiques> result =
                    new ArrayList<Statistiques>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                StatistiquesProviderAdapter.STATISTIQUES_URI,
                StatistiquesContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = StatistiquesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Statistiques
     
     * @return number of rows updated
     */
    public int update(final Statistiques item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = StatistiquesContract.itemToContentValues(
                item);

        Uri uri = StatistiquesProviderAdapter.STATISTIQUES_URI;
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

    
}

/**************************************************************************
 * QuestionsProviderUtilsBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
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
import com.coyote.drinknomore.criterias.base.Criterion;
import com.coyote.drinknomore.criterias.base.Criterion.Type;
import com.coyote.drinknomore.criterias.base.value.ArrayValue;
import com.coyote.drinknomore.criterias.base.CriteriaExpression;
import com.coyote.drinknomore.criterias.base.CriteriaExpression.GroupType;

import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;

import com.coyote.drinknomore.provider.QuestionsProviderAdapter;
import com.coyote.drinknomore.provider.ReponsesProviderAdapter;
import com.coyote.drinknomore.provider.DrinknomoreProvider;
import com.coyote.drinknomore.provider.contract.QuestionsContract;
import com.coyote.drinknomore.provider.contract.ReponsesContract;

/**
 * Questions Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class QuestionsProviderUtilsBase
            extends ProviderUtils<Questions> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "QuestionsProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public QuestionsProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Questions item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = QuestionsContract.itemToContentValues(item);
        itemValues.remove(QuestionsContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                QuestionsProviderAdapter.QUESTIONS_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getReponse() != null && item.getReponse().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(ReponsesContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getReponse().size(); i++) {
                inValue.addValue(String.valueOf(item.getReponse().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(ReponsesProviderAdapter.REPONSES_URI)
                    .withValueBackReference(
                            ReponsesContract
                                    .COL_QUESTION_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }

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
     * @param item Questions
     * @return number of row affected
     */
    public int delete(final Questions item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = QuestionsProviderAdapter.QUESTIONS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Questions
     */
    public Questions query(final Questions item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Questions
     */
    public Questions query(final int id) {
        Questions result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(QuestionsContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            QuestionsProviderAdapter.QUESTIONS_URI,
            QuestionsContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = QuestionsContract.cursorToItem(cursor);
            cursor.close();

            result.setReponse(
                this.getAssociateReponse(result));
        }

        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Questions>
     */
    public ArrayList<Questions> queryAll() {
        ArrayList<Questions> result =
                    new ArrayList<Questions>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                QuestionsProviderAdapter.QUESTIONS_URI,
                QuestionsContract.ALIASED_COLS,
                null,
                null,
                null);

        result = QuestionsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Questions>
     */
    public ArrayList<Questions> query(CriteriaExpression expression) {
        ArrayList<Questions> result =
                    new ArrayList<Questions>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                QuestionsProviderAdapter.QUESTIONS_URI,
                QuestionsContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = QuestionsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Questions
     
     * @return number of rows updated
     */
    public int update(final Questions item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = QuestionsContract.itemToContentValues(
                item);

        Uri uri = QuestionsProviderAdapter.QUESTIONS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getReponse() != null && item.getReponse().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new reponse for Questions
            CriteriaExpression reponseCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(ReponsesContract.COL_ID);
            crit.addValue(values);
            reponseCrit.add(crit);


            for (Reponses reponse : item.getReponse()) {
                values.addValue(
                    String.valueOf(reponse.getId()));
            }
            selection = reponseCrit.toSQLiteSelection();
            selectionArgs = reponseCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    ReponsesProviderAdapter.REPONSES_URI)
                    .withValue(
                            ReponsesContract.COL_QUESTION_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated reponse
            crit.setType(Type.NOT_IN);
            reponseCrit.add(ReponsesContract.COL_QUESTION_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    ReponsesProviderAdapter.REPONSES_URI)
                    .withValue(
                            ReponsesContract.COL_QUESTION_ID,
                            null)
                    .withSelection(
                            reponseCrit.toSQLiteSelection(),
                            reponseCrit.toSQLiteSelectionArgs())
                    .build());
        }


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
     * Get associate Reponse.
     * @param item Questions
     * @return Reponses
     */
    public ArrayList<Reponses> getAssociateReponse(
            final Questions item) {
        ArrayList<Reponses> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor reponsesCursor = prov.query(
                ReponsesProviderAdapter.REPONSES_URI,
                ReponsesContract.ALIASED_COLS,
                ReponsesContract.ALIASED_COL_QUESTION_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = ReponsesContract.cursorToItems(
                        reponsesCursor);
        reponsesCursor.close();

        return result;
    }

}

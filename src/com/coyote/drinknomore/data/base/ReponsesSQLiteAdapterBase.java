/**************************************************************************
 * ReponsesSQLiteAdapterBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.coyote.drinknomore.data.SQLiteAdapter;
import com.coyote.drinknomore.data.ReponsesSQLiteAdapter;
import com.coyote.drinknomore.data.QuestionsSQLiteAdapter;
import com.coyote.drinknomore.provider.contract.ReponsesContract;
import com.coyote.drinknomore.provider.contract.QuestionsContract;
import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.entity.Questions;


import com.coyote.drinknomore.DrinknomoreApplication;


/** Reponses adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ReponsesAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public class ReponsesSQLiteAdapterBase
                        extends SQLiteAdapter<Reponses> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ReponsesDBAdapter";


    /**
     * Get the table name used in DB for your Reponses entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ReponsesContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Reponses entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ReponsesContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Reponses entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ReponsesContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ReponsesContract.TABLE_NAME    + " ("
        
         + ReponsesContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ReponsesContract.COL_SOLUTION    + " VARCHAR NOT NULL,"
         + ReponsesContract.COL_ARGUMENTS    + " VARCHAR NOT NULL,"
         + ReponsesContract.COL_QUESTION_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + ReponsesContract.COL_QUESTION_ID + ") REFERENCES " 
             + QuestionsContract.TABLE_NAME 
                + " (" + QuestionsContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ReponsesSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Reponses entity to Content Values for database.
     * @param item Reponses entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Reponses item) {
        return ReponsesContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Reponses entity.
     * @param cursor android.database.Cursor object
     * @return Reponses entity
     */
    public Reponses cursorToItem(final android.database.Cursor cursor) {
        return ReponsesContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Reponses entity.
     * @param cursor android.database.Cursor object
     * @param result Reponses entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Reponses result) {
        ReponsesContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Reponses by id in database.
     *
     * @param id Identify of Reponses
     * @return Reponses entity
     */
    public Reponses getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Reponses result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getQuestion() != null) {
            final QuestionsSQLiteAdapter questionAdapter =
                    new QuestionsSQLiteAdapter(this.ctx);
            questionAdapter.open(this.mDatabase);
            
            result.setQuestion(questionAdapter.getByID(
                            result.getQuestion().getId()));
        }
        return result;
    }

    /**
     * Find & read Reponses by question.
     * @param questionId questionId
     * @param orderBy Order by string (can be null)
     * @return List of Reponses entities
     */
     public android.database.Cursor getByQuestion(final int questionId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = ReponsesContract.COL_QUESTION_ID + "= ?";
        String idSelectionArgs = String.valueOf(questionId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }

    /**
     * Read All Reponsess entities.
     *
     * @return List of Reponses entities
     */
    public ArrayList<Reponses> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Reponses> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Reponses entity into database.
     *
     * @param item The Reponses entity to persist
     * @return Id of the Reponses entity
     */
    public long insert(final Reponses item) {
        if (DrinknomoreApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ReponsesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ReponsesContract.itemToContentValues(item);
        values.remove(ReponsesContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ReponsesContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Reponses entity into database whether.
     * it already exists or not.
     *
     * @param item The Reponses entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Reponses item) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.update(item);
        } else {
            // Item doesn't exist => create it
            final long id = this.insert(item);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * Update a Reponses entity into database.
     *
     * @param item The Reponses entity to persist
     * @return count of updated entities
     */
    public int update(final Reponses item) {
        if (DrinknomoreApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ReponsesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ReponsesContract.itemToContentValues(item);
        final String whereClause =
                 ReponsesContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Reponses entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DrinknomoreApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB(" 
                    + ReponsesContract.TABLE_NAME 
                    + ")"
                    + " id : "+ id);
        }
        
        final String whereClause =
                ReponsesContract.COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param reponses The entity to delete
     * @return count of updated entities
     */
    public int delete(final Reponses reponses) {
        return this.remove(reponses.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Reponses corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DrinknomoreApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                ReponsesContract.ALIASED_COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ReponsesContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Reponses entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = ReponsesContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ReponsesContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }


}


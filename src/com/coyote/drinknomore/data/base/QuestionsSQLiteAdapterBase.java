/**************************************************************************
 * QuestionsSQLiteAdapterBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.coyote.drinknomore.data.SQLiteAdapter;
import com.coyote.drinknomore.data.QuestionsSQLiteAdapter;
import com.coyote.drinknomore.data.ReponsesSQLiteAdapter;
import com.coyote.drinknomore.provider.contract.QuestionsContract;
import com.coyote.drinknomore.provider.contract.ReponsesContract;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;


import com.coyote.drinknomore.DrinknomoreApplication;


/** Questions adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit QuestionsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class QuestionsSQLiteAdapterBase
                        extends SQLiteAdapter<Questions> {

    /** TAG for debug purpose. */
    protected static final String TAG = "QuestionsDBAdapter";


    /**
     * Get the table name used in DB for your Questions entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return QuestionsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Questions entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = QuestionsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Questions entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return QuestionsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + QuestionsContract.TABLE_NAME    + " ("
        
         + QuestionsContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + QuestionsContract.COL_QUESTION    + " VARCHAR NOT NULL,"
         + QuestionsContract.COL_ARGUMENTS    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public QuestionsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Questions entity to Content Values for database.
     * @param item Questions entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Questions item) {
        return QuestionsContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Questions entity.
     * @param cursor android.database.Cursor object
     * @return Questions entity
     */
    public Questions cursorToItem(final android.database.Cursor cursor) {
        return QuestionsContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Questions entity.
     * @param cursor android.database.Cursor object
     * @param result Questions entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Questions result) {
        QuestionsContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Questions by id in database.
     *
     * @param id Identify of Questions
     * @return Questions entity
     */
    public Questions getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Questions result = this.cursorToItem(cursor);
        cursor.close();

        final ReponsesSQLiteAdapter reponseAdapter =
                new ReponsesSQLiteAdapter(this.ctx);
        reponseAdapter.open(this.mDatabase);
        android.database.Cursor reponseCursor = reponseAdapter
                    .getByQuestions(
                            result.getId(),
                            ReponsesContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setReponse(reponseAdapter.cursorToItems(reponseCursor));
        return result;
    }


    /**
     * Read All Questionss entities.
     *
     * @return List of Questions entities
     */
    public ArrayList<Questions> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Questions> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Questions entity into database.
     *
     * @param item The Questions entity to persist
     * @return Id of the Questions entity
     */
    public long insert(final Questions item) {
        if (DrinknomoreApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + QuestionsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                QuestionsContract.itemToContentValues(item);
        values.remove(QuestionsContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    QuestionsContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getReponse() != null) {
            ReponsesSQLiteAdapterBase reponseAdapter =
                    new ReponsesSQLiteAdapter(this.ctx);
            reponseAdapter.open(this.mDatabase);
            for (Reponses reponses
                        : item.getReponse()) {
                reponses.setQuestions(item);
                reponseAdapter.insertOrUpdate(reponses);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Questions entity into database whether.
     * it already exists or not.
     *
     * @param item The Questions entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Questions item) {
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
     * Update a Questions entity into database.
     *
     * @param item The Questions entity to persist
     * @return count of updated entities
     */
    public int update(final Questions item) {
        if (DrinknomoreApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + QuestionsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                QuestionsContract.itemToContentValues(item);
        final String whereClause =
                 QuestionsContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Questions entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DrinknomoreApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB(" 
                    + QuestionsContract.TABLE_NAME 
                    + ")"
                    + " id : "+ id);
        }
        
        final String whereClause =
                QuestionsContract.COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param questions The entity to delete
     * @return count of updated entities
     */
    public int delete(final Questions questions) {
        return this.remove(questions.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Questions corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DrinknomoreApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                QuestionsContract.ALIASED_COL_ID 
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                QuestionsContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Questions entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = QuestionsContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                QuestionsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }


}


/**************************************************************************
 * StatistiquesSQLiteAdapterBase.java, drinknomore Android
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
import com.coyote.drinknomore.data.SQLiteAdapter;
import com.coyote.drinknomore.provider.contract.StatistiquesContract;
import com.coyote.drinknomore.entity.Statistiques;
import com.coyote.drinknomore.DrinknomoreApplication;

/** Statistiques adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit StatistiquesAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public class StatistiquesSQLiteAdapterBase
extends SQLiteAdapter<Statistiques> {

	/** TAG for debug purpose. */
	protected static final String TAG = "StatistiquesDBAdapter";


	/**
	 * Get the table name used in DB for your Statistiques entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return StatistiquesContract.TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your Statistiques entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = StatistiquesContract.TABLE_NAME;
		return result;
	}

	/**
	 * Get the column names from the Statistiques entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return StatistiquesContract.ALIASED_COLS;
	}

	/**
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
		return "CREATE TABLE "
				+ StatistiquesContract.TABLE_NAME    + " ("

         + StatistiquesContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + StatistiquesContract.COL_DATE    + " DATETIME NOT NULL,"
         + StatistiquesContract.COL_NBERREURS    + " INTEGER NOT NULL"


        + ");"
        ;
	}

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public StatistiquesSQLiteAdapterBase(final android.content.Context ctx) {
		super(ctx);
	}

	// Converters

	/**
	 * Convert Statistiques entity to Content Values for database.
	 * @param item Statistiques entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final Statistiques item) {
		return StatistiquesContract.itemToContentValues(item);
	}

	/**
	 * Convert android.database.Cursor of database to Statistiques entity.
	 * @param cursor android.database.Cursor object
	 * @return Statistiques entity
	 */
	public Statistiques cursorToItem(final android.database.Cursor cursor) {
		return StatistiquesContract.cursorToItem(cursor);
	}

	/**
	 * Convert android.database.Cursor of database to Statistiques entity.
	 * @param cursor android.database.Cursor object
	 * @param result Statistiques entity
	 */
	public void cursorToItem(final android.database.Cursor cursor, final Statistiques result) {
		StatistiquesContract.cursorToItem(cursor, result);
	}

	//// CRUD Entity ////
	/**
	 * Find & read Statistiques by id in database.
	 *
	 * @param id Identify of Statistiques
	 * @return Statistiques entity
	 */
	public Statistiques getByID(final int id) {
		final android.database.Cursor cursor = this.getSingleCursor(id);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final Statistiques result = this.cursorToItem(cursor);
		cursor.close();

		return result;
	}


	/**
	 * Read All Statistiquess entities.
	 *
	 * @return List of Statistiques entities
	 */
	public ArrayList<Statistiques> getAll() {
		final android.database.Cursor cursor = this.getAllCursor();
		final ArrayList<Statistiques> result = this.cursorToItems(cursor);
		cursor.close();

		return result;
	}



	/**
	 * Insert a Statistiques entity into database.
	 *
	 * @param item The Statistiques entity to persist
	 * @return Id of the Statistiques entity
	 */
	public long insert(final Statistiques item) {
		if (DrinknomoreApplication.DEBUG) {
			android.util.Log.d(TAG, "Insert DB(" + StatistiquesContract.TABLE_NAME + ")");
		}

		final ContentValues values =
				StatistiquesContract.itemToContentValues(item);
		values.remove(StatistiquesContract.COL_ID);
		int insertResult;
		if (values.size() != 0) {
			insertResult = (int) this.insert(
					null,
					values);
		} else {
			insertResult = (int) this.insert(
					StatistiquesContract.COL_ID,
					values);
		}
		item.setId(insertResult);
		return insertResult;
	}

	/**
	 * Either insert or update a Statistiques entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The Statistiques entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final Statistiques item) {
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
	 * Update a Statistiques entity into database.
	 *
	 * @param item The Statistiques entity to persist
	 * @return count of updated entities
	 */
	public int update(final Statistiques item) {
		if (DrinknomoreApplication.DEBUG) {
			android.util.Log.d(TAG, "Update DB(" + StatistiquesContract.TABLE_NAME + ")");
		}

		final ContentValues values =
				StatistiquesContract.itemToContentValues(item);
		final String whereClause =
				StatistiquesContract.COL_ID
				+ " = ?";
		final String[] whereArgs =
				new String[] {String.valueOf(item.getId()) };

		return this.update(
				values,
				whereClause,
				whereArgs);
	}


	/**
	 * Delete a Statistiques entity of database.
	 *
	 * @param id id
	 * @return count of updated entities
	 */
	public int remove(final int id) {
		if (DrinknomoreApplication.DEBUG) {
			android.util.Log.d(
					TAG,
					"Delete DB(" 
							+ StatistiquesContract.TABLE_NAME 
							+ ")"
							+ " id : "+ id);
		}

		final String whereClause =
				StatistiquesContract.COL_ID 
				+ " = ?";
		final String[] whereArgs = new String[] {
				String.valueOf(id)};

		return this.delete(
				whereClause,
				whereArgs);
	}

	/**
	 * Deletes the given entity.
	 * @param statistiques The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final Statistiques statistiques) {
		return this.remove(statistiques.getId());
	}

	/**
	 *  Internal android.database.Cursor.
	 * @param id id
	 *  @return A android.database.Cursor pointing to the Statistiques corresponding
	 *        to the given id.
	 */
	protected android.database.Cursor getSingleCursor(final int id) {
		if (DrinknomoreApplication.DEBUG) {
			android.util.Log.d(TAG, "Get entities id : " + id);
		}

		final String whereClause =
				StatistiquesContract.ALIASED_COL_ID 
				+ " = ?";
		final String[] whereArgs = new String[] {String.valueOf(id)};

		return this.query(
				StatistiquesContract.ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
	}


	/**
	 * Query the DB to find a Statistiques entity.
	 *
	 * @param id The id of the entity to get from the DB
	 *
	 * @return The cursor pointing to the query's result
	 */
	public android.database.Cursor query(final int id) {

		String selection = StatistiquesContract.ALIASED_COL_ID + " = ?";


		String[] selectionArgs = new String[1];
		selectionArgs[0] = String.valueOf(id);

		return this.query(
				StatistiquesContract.ALIASED_COLS,
				selection,
				selectionArgs,
				null,
				null,
				null);
	}


}


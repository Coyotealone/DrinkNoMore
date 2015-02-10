/**************************************************************************
 * ProviderAdapterBase.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.provider.base;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import android.database.ContentObserver;

import android.net.Uri;

import com.coyote.drinknomore.data.base.SQLiteAdapterBase;
import com.coyote.drinknomore.provider.DrinknomoreProvider;

/**
 * ProviderAdapterBase<T>. 
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * @param <T> must extends Serializable
 */
public abstract class ProviderAdapterBase<T> {
    /** TAG for debug purpose. */
    public static final String TAG = "ProviderAdapterBase<T>";
    /** android.content.Context. */
    protected android.content.Context ctx;
    /** SQLiteAdapterBase. */
    protected SQLiteAdapterBase<T> adapter;
    /** database. */
    protected SQLiteDatabase db;
    /** matched uri ids. */
    protected ArrayList<Integer> uriIds = new ArrayList<Integer>();
    /** General provider item for the application. */
    protected DrinknomoreProviderBase provider;

    /**
     * Provider Adapter Base constructor.
     *
     * @param context The context.
     */
    public ProviderAdapterBase(
            final DrinknomoreProviderBase provider,
            final SQLiteAdapterBase<T> adapter) {
        this.provider = provider;
        this.adapter = adapter;
        this.ctx = provider.getContext();
        if (provider.getDatabase() != null) {
            this.db = this.adapter.open(provider.getDatabase());
        } else {
            this.db = this.adapter.open();
        }
    }

    /**
     * Delete the entities matching with uri from the DB.
     *
     * @param uri URI
     * @param selection SELECT clause for SQL
     * @param selectionArgs SELECT arguments for SQL
     *
     * @return how many token deleted
     */
    public abstract int delete(final Uri uri,
                            String selection,
                            String[] selectionArgs);


    /**
     * Insert the entities matching with uri from the DB.
     *
     * @param uri URI
     * @param values ContentValues to insert
     *
     * @return how many token inserted
     */
    public abstract Uri insert(final Uri uri,
                            final ContentValues values);

    /**
     * Send a query to the DB.
     *
     * @param uri URI
     * @param projection Columns to work with
     * @param selection SELECT clause for SQL
     * @param selectionArgs SELECT arguments for SQL
     * @param sortOrder ORDER BY clause
     *
     * @return A cursor pointing to the result of the query
     */
    public abstract android.database.Cursor query(final Uri uri,
                        final String[] projection,
                        final String selection,
                        final String[] selectionArgs,
                        final String sortOrder);


    /**
     * Update the entities matching with uri from the DB.
     *
     * @param uri URI
     * @param values ContentValues to update
     * @param selection SELECT clause for SQL
     * @param selectionArgs SELECT arguments for SQL
     *
     * @return how many token update
     */
    public abstract int update(final Uri uri,
                        final ContentValues values,
                          final String selection,
                        final String[] selectionArgs);

    /**
     * Get the entity from the URI.
     *
     * @param uri URI
     *
     * @return A String representing the entity name
     */
    public abstract String getType(final Uri uri);

    /**
     * Get database.
     *
     * @return database
     */
    public SQLiteDatabase getDb() {
        return this.db;
    }

    /**
     * Get the entity URI.
     *
     * @return The URI
     */
    public abstract Uri getUri();

    /**
     * Tells if this provider adapter match the given uri.
     *
     * @param the uri
     *
     * @return true if the uri is matched by this adapter
     */
    public boolean match(Uri uri) {
        return this.uriIds.contains(
                DrinknomoreProvider.getUriMatcher().match(uri));
    }

    /**
     * Use this method to notify an Uri change.
     *
     * @param uri The uri that has changed
     * @param observer The observer that initiated the change
     */
    public void notifyUri(Uri uri, ContentObserver observer) {
        this.provider.notifyUri(uri, observer);
    }
}

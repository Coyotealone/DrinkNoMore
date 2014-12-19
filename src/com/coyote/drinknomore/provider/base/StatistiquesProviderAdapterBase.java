/**************************************************************************
 * StatistiquesProviderAdapterBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.coyote.drinknomore.entity.Statistiques;
import com.coyote.drinknomore.provider.ProviderAdapter;
import com.coyote.drinknomore.provider.DrinknomoreProvider;
import com.coyote.drinknomore.provider.contract.StatistiquesContract;
import com.coyote.drinknomore.data.StatistiquesSQLiteAdapter;

/**
 * StatistiquesProviderAdapterBase.
 */
public abstract class StatistiquesProviderAdapterBase
                extends ProviderAdapter<Statistiques> {

    /** TAG for debug purpose. */
    protected static final String TAG = "StatistiquesProviderAdapter";

    /** STATISTIQUES_URI. */
    public      static Uri STATISTIQUES_URI;

    /** statistiques type. */
    protected static final String statistiquesType =
            "statistiques";

    /** STATISTIQUES_ALL. */
    protected static final int STATISTIQUES_ALL =
            540624411;
    /** STATISTIQUES_ONE. */
    protected static final int STATISTIQUES_ONE =
            540624412;


    /**
     * Static constructor.
     */
    static {
        STATISTIQUES_URI =
                DrinknomoreProvider.generateUri(
                        statistiquesType);
        DrinknomoreProvider.getUriMatcher().addURI(
                DrinknomoreProvider.authority,
                statistiquesType,
                STATISTIQUES_ALL);
        DrinknomoreProvider.getUriMatcher().addURI(
                DrinknomoreProvider.authority,
                statistiquesType + "/#",
                STATISTIQUES_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public StatistiquesProviderAdapterBase(
            DrinknomoreProviderBase provider) {
        super(
            provider,
            new StatistiquesSQLiteAdapter(provider.getContext()));

        this.uriIds.add(STATISTIQUES_ALL);
        this.uriIds.add(STATISTIQUES_ONE);
    }

    @Override
    public String getType(final Uri uri) {
        String result;
        final String single =
                "vnc.android.cursor.item/"
                    + DrinknomoreProvider.authority + ".";
        final String collection =
                "vnc.android.cursor.collection/"
                    + DrinknomoreProvider.authority + ".";

        int matchedUri = DrinknomoreProviderBase
                .getUriMatcher().match(uri);

        switch (matchedUri) {
            case STATISTIQUES_ALL:
                result = collection + "statistiques";
                break;
            case STATISTIQUES_ONE:
                result = single + "statistiques";
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int delete(
            final Uri uri,
            String selection,
            String[] selectionArgs) {
        int matchedUri = DrinknomoreProviderBase
                    .getUriMatcher().match(uri);
        int result = -1;
        switch (matchedUri) {
            case STATISTIQUES_ONE:
                String id = uri.getPathSegments().get(1);
                selection = StatistiquesContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case STATISTIQUES_ALL:
                result = this.adapter.delete(
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }
    
    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        int matchedUri = DrinknomoreProviderBase
                .getUriMatcher().match(uri);
                Uri result = null;
        int id = 0;
        switch (matchedUri) {
            case STATISTIQUES_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(StatistiquesContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            STATISTIQUES_URI,
                            String.valueOf(id));
                }
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    public android.database.Cursor query(final Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        final String sortOrder) {

        int matchedUri = DrinknomoreProviderBase.getUriMatcher()
                .match(uri);
        android.database.Cursor result = null;

        switch (matchedUri) {

            case STATISTIQUES_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case STATISTIQUES_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;
            
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int update(
            final Uri uri,
            final ContentValues values,
            String selection,
            String[] selectionArgs) {

        
        int matchedUri = DrinknomoreProviderBase.getUriMatcher()
                .match(uri);
        int result = -1;
        switch (matchedUri) {
            case STATISTIQUES_ONE:
                selectionArgs = new String[1];
                selection = StatistiquesContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case STATISTIQUES_ALL:
                result = this.adapter.update(
                            values,
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }



    /**
     * Get the entity URI.
     * @return The URI
     */
    @Override
    public Uri getUri() {
        return STATISTIQUES_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = StatistiquesContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    StatistiquesContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}


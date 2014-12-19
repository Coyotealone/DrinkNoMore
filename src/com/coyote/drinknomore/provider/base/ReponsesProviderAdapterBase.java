/**************************************************************************
 * ReponsesProviderAdapterBase.java, drinknomore Android
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



import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.provider.ProviderAdapter;
import com.coyote.drinknomore.provider.DrinknomoreProvider;
import com.coyote.drinknomore.provider.contract.ReponsesContract;
import com.coyote.drinknomore.data.ReponsesSQLiteAdapter;
import com.coyote.drinknomore.data.QuestionsSQLiteAdapter;

/**
 * ReponsesProviderAdapterBase.
 */
public abstract class ReponsesProviderAdapterBase
                extends ProviderAdapter<Reponses> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ReponsesProviderAdapter";

    /** REPONSES_URI. */
    public      static Uri REPONSES_URI;

    /** reponses type. */
    protected static final String reponsesType =
            "reponses";

    /** REPONSES_ALL. */
    protected static final int REPONSES_ALL =
            362515515;
    /** REPONSES_ONE. */
    protected static final int REPONSES_ONE =
            362515516;

    /** REPONSES_QUESTION. */
    protected static final int REPONSES_QUESTION =
            362515517;

    /**
     * Static constructor.
     */
    static {
        REPONSES_URI =
                DrinknomoreProvider.generateUri(
                        reponsesType);
        DrinknomoreProvider.getUriMatcher().addURI(
                DrinknomoreProvider.authority,
                reponsesType,
                REPONSES_ALL);
        DrinknomoreProvider.getUriMatcher().addURI(
                DrinknomoreProvider.authority,
                reponsesType + "/#",
                REPONSES_ONE);
        DrinknomoreProvider.getUriMatcher().addURI(
                DrinknomoreProvider.authority,
                reponsesType + "/#" + "/question",
                REPONSES_QUESTION);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ReponsesProviderAdapterBase(
            DrinknomoreProviderBase provider) {
        super(
            provider,
            new ReponsesSQLiteAdapter(provider.getContext()));

        this.uriIds.add(REPONSES_ALL);
        this.uriIds.add(REPONSES_ONE);
        this.uriIds.add(REPONSES_QUESTION);
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
            case REPONSES_ALL:
                result = collection + "reponses";
                break;
            case REPONSES_ONE:
                result = single + "reponses";
                break;
            case REPONSES_QUESTION:
                result = single + "reponses";
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
            case REPONSES_ONE:
                String id = uri.getPathSegments().get(1);
                selection = ReponsesContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case REPONSES_ALL:
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
            case REPONSES_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ReponsesContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            REPONSES_URI,
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
        android.database.Cursor reponsesCursor;

        switch (matchedUri) {

            case REPONSES_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case REPONSES_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;
            
            case REPONSES_QUESTION:
                reponsesCursor = this.queryById(
                        uri.getPathSegments().get(1));
                
                if (reponsesCursor.getCount() > 0) {
                    reponsesCursor.moveToFirst();
                    int questionId = reponsesCursor.getInt(
                            reponsesCursor.getColumnIndex(
                                    ReponsesContract.COL_QUESTION_ID));
                    
                    QuestionsSQLiteAdapter questionsAdapter = new QuestionsSQLiteAdapter(this.ctx);
                    questionsAdapter.open(this.getDb());
                    result = questionsAdapter.query(questionId);
                }
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
            case REPONSES_ONE:
                selectionArgs = new String[1];
                selection = ReponsesContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case REPONSES_ALL:
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
        return REPONSES_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = ReponsesContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    ReponsesContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}


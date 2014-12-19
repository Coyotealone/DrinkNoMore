/**************************************************************************
 * QuestionsProviderAdapterBase.java, drinknomore Android
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



import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.provider.ProviderAdapter;
import com.coyote.drinknomore.provider.DrinknomoreProvider;
import com.coyote.drinknomore.provider.contract.QuestionsContract;
import com.coyote.drinknomore.provider.contract.ReponsesContract;
import com.coyote.drinknomore.data.QuestionsSQLiteAdapter;
import com.coyote.drinknomore.data.ReponsesSQLiteAdapter;

/**
 * QuestionsProviderAdapterBase.
 */
public abstract class QuestionsProviderAdapterBase
                extends ProviderAdapter<Questions> {

    /** TAG for debug purpose. */
    protected static final String TAG = "QuestionsProviderAdapter";

    /** QUESTIONS_URI. */
    public      static Uri QUESTIONS_URI;

    /** questions type. */
    protected static final String questionsType =
            "questions";

    /** QUESTIONS_ALL. */
    protected static final int QUESTIONS_ALL =
            221733165;
    /** QUESTIONS_ONE. */
    protected static final int QUESTIONS_ONE =
            221733166;

    /** QUESTIONS_REPONSE. */
    protected static final int QUESTIONS_REPONSE =
            221733167;

    /**
     * Static constructor.
     */
    static {
        QUESTIONS_URI =
                DrinknomoreProvider.generateUri(
                        questionsType);
        DrinknomoreProvider.getUriMatcher().addURI(
                DrinknomoreProvider.authority,
                questionsType,
                QUESTIONS_ALL);
        DrinknomoreProvider.getUriMatcher().addURI(
                DrinknomoreProvider.authority,
                questionsType + "/#",
                QUESTIONS_ONE);
        DrinknomoreProvider.getUriMatcher().addURI(
                DrinknomoreProvider.authority,
                questionsType + "/#" + "/reponse",
                QUESTIONS_REPONSE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public QuestionsProviderAdapterBase(
            DrinknomoreProviderBase provider) {
        super(
            provider,
            new QuestionsSQLiteAdapter(provider.getContext()));

        this.uriIds.add(QUESTIONS_ALL);
        this.uriIds.add(QUESTIONS_ONE);
        this.uriIds.add(QUESTIONS_REPONSE);
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
            case QUESTIONS_ALL:
                result = collection + "questions";
                break;
            case QUESTIONS_ONE:
                result = single + "questions";
                break;
            case QUESTIONS_REPONSE:
                result = collection + "questions";
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
            case QUESTIONS_ONE:
                String id = uri.getPathSegments().get(1);
                selection = QuestionsContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case QUESTIONS_ALL:
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
            case QUESTIONS_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(QuestionsContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            QUESTIONS_URI,
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
        int questionsId;

        switch (matchedUri) {

            case QUESTIONS_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case QUESTIONS_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;
            
            case QUESTIONS_REPONSE:
                questionsId = Integer.parseInt(uri.getPathSegments().get(1));
                ReponsesSQLiteAdapter reponseAdapter = new ReponsesSQLiteAdapter(this.ctx);
                reponseAdapter.open(this.getDb());
                result = reponseAdapter.getByQuestion(questionsId, ReponsesContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case QUESTIONS_ONE:
                selectionArgs = new String[1];
                selection = QuestionsContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case QUESTIONS_ALL:
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
        return QUESTIONS_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = QuestionsContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    QuestionsContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}


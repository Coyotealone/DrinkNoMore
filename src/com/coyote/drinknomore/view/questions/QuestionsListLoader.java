/**************************************************************************
 * QuestionsListLoader.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.view.questions;


import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.coyote.drinknomore.criterias.base.CriteriaExpression;

/**
 * Questions Loader.
 */
public class QuestionsListLoader
                extends CursorLoader {

    /**
     * Constructor.
     * @param ctx context
     */
    public QuestionsListLoader(
            final android.content.Context ctx) {
        super(ctx);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param uri The URI associated with this loader
     * @param projection The projection to use
     * @param selection The selection
     * @param selectionArgs The selection Args
     * @param sortOrder The sort order
     */
    public QuestionsListLoader(
                    android.content.Context ctx,
                    Uri uri,
                    String[] projection,
                    String selection,
                    String[] selectionArgs,
                    String sortOrder) {
        super(ctx,
                uri,
                projection,
                selection,
                selectionArgs,
                sortOrder);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param uri The URI associated with this loader
     * @param projection The projection to use
     * @param expression The CriteriaExpression
     * @param sortOrder The sort order
     */
    public QuestionsListLoader(
                    android.content.Context ctx,
                    Uri uri,
                    String[] projection,
                    CriteriaExpression expression,
                    String sortOrder) {
        super(ctx,
                uri,
                projection,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                sortOrder);
    }
}

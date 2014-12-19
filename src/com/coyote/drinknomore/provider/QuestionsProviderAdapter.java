/**************************************************************************
 * QuestionsProviderAdapter.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.provider;

import com.coyote.drinknomore.provider.base.QuestionsProviderAdapterBase;
import com.coyote.drinknomore.provider.base.DrinknomoreProviderBase;

/**
 * QuestionsProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class QuestionsProviderAdapter
                    extends QuestionsProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public QuestionsProviderAdapter(
            final DrinknomoreProviderBase provider) {
        super(provider);
    }
}


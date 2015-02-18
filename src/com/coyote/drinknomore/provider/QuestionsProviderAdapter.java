/**************************************************************************
 * QuestionsProviderAdapter.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
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
     * @param provider
     */
    public QuestionsProviderAdapter(
            final DrinknomoreProviderBase provider) {
        super(provider);
    }
}


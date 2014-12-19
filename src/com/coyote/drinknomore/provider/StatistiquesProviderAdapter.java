/**************************************************************************
 * StatistiquesProviderAdapter.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.provider;

import com.coyote.drinknomore.provider.base.StatistiquesProviderAdapterBase;
import com.coyote.drinknomore.provider.base.DrinknomoreProviderBase;

/**
 * StatistiquesProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class StatistiquesProviderAdapter
                    extends StatistiquesProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public StatistiquesProviderAdapter(
            final DrinknomoreProviderBase provider) {
        super(provider);
    }
}


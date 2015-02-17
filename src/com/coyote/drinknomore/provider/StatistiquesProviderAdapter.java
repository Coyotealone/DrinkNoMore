/**************************************************************************
 * StatistiquesProviderAdapter.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
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
     * @param provider
     */
    public StatistiquesProviderAdapter(
            final DrinknomoreProviderBase provider) {
        super(provider);
    }
}


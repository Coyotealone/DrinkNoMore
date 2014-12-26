/**************************************************************************
 * ReponsesProviderAdapter.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 26, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.provider;

import com.coyote.drinknomore.provider.base.ReponsesProviderAdapterBase;
import com.coyote.drinknomore.provider.base.DrinknomoreProviderBase;

/**
 * ReponsesProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class ReponsesProviderAdapter
                    extends ReponsesProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public ReponsesProviderAdapter(
            final DrinknomoreProviderBase provider) {
        super(provider);
    }
}


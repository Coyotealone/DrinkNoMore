/**************************************************************************
 * ProviderAdapter.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.provider;

import com.coyote.drinknomore.provider.base.ProviderAdapterBase;
import com.coyote.drinknomore.provider.base.DrinknomoreProviderBase;
import com.coyote.drinknomore.data.base.SQLiteAdapterBase;

/**
 * ProviderAdapter<T>. 
 *
 * Feel free to add your custom generic methods here.
 *
 * @param <T> must extends Serializable
 */
public abstract class ProviderAdapter<T> extends ProviderAdapterBase<T> {

    /**
     * Provider Adapter Base constructor.
     * 
     * @param provider
     * @param adapter
     */
    public ProviderAdapter(
            final DrinknomoreProviderBase provider,
            final SQLiteAdapterBase<T> adapter) {
        super(provider, adapter);
    }
}

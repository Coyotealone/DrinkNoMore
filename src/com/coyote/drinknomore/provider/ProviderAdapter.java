/**************************************************************************
 * ProviderAdapter.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2014
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
     * @param context The context.
     */
    public ProviderAdapter(
                final DrinknomoreProviderBase provider,
                final SQLiteAdapterBase<T> adapter) {
        super(provider, adapter);
    }
}

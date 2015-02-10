/**************************************************************************
 * StatistiquesTestProviderBase.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.coyote.drinknomore.provider.StatistiquesProviderAdapter;
import com.coyote.drinknomore.provider.utils.StatistiquesProviderUtils;
import com.coyote.drinknomore.provider.contract.StatistiquesContract;

import com.coyote.drinknomore.data.StatistiquesSQLiteAdapter;

import com.coyote.drinknomore.entity.Statistiques;

import com.coyote.drinknomore.fixture.StatistiquesDataLoader;

import java.util.ArrayList;
import com.coyote.drinknomore.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Statistiques database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit StatistiquesTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class StatistiquesTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected StatistiquesSQLiteAdapter adapter;

    protected Statistiques entity;
    protected ContentResolver provider;
    protected StatistiquesProviderUtils providerUtils;

    protected ArrayList<Statistiques> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new StatistiquesSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Statistiques>();        
        this.entities.addAll(StatistiquesDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += StatistiquesDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new StatistiquesProviderUtils(this.getContext());
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /********** Direct Provider calls. *******/

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        Uri result = null;
        if (this.entity != null) {
            Statistiques statistiques = StatistiquesUtils.generateRandom(this.ctx);

            try {
                ContentValues values = StatistiquesContract.itemToContentValues(statistiques);
                values.remove(StatistiquesContract.COL_ID);
                result = this.provider.insert(StatistiquesProviderAdapter.STATISTIQUES_URI, values);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertNotNull(result);
            Assert.assertTrue(Integer.parseInt(result.getPathSegments().get(1)) > 0);        
            
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Statistiques result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        StatistiquesProviderAdapter.STATISTIQUES_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = StatistiquesContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            StatistiquesUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Statistiques> result = null;
        try {
            android.database.Cursor c = this.provider.query(StatistiquesProviderAdapter.STATISTIQUES_URI, this.adapter.getCols(), null, null, null);
            result = StatistiquesContract.cursorToItems(c);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            Statistiques statistiques = StatistiquesUtils.generateRandom(this.ctx);

            try {
                statistiques.setId(this.entity.getId());

                ContentValues values = StatistiquesContract.itemToContentValues(statistiques);
                result = this.provider.update(
                    Uri.parse(StatistiquesProviderAdapter.STATISTIQUES_URI
                        + "/"
                        + statistiques.getId()),
                    values,
                    null,
                    null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertTrue(result > 0);
        }
    }

    /** Test case UpdateAll Entity */
    @SmallTest
    public void testUpdateAll() {
        int result = -1;
        if (this.entities.size() > 0) {
            Statistiques statistiques = StatistiquesUtils.generateRandom(this.ctx);

            try {
                ContentValues values = StatistiquesContract.itemToContentValues(statistiques);
                values.remove(StatistiquesContract.COL_ID);

                result = this.provider.update(StatistiquesProviderAdapter.STATISTIQUES_URI, values, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertEquals(result, this.nbEntities);
        }
    }

    /** Test case Delete Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            try {
                result = this.provider.delete(
                        Uri.parse(StatistiquesProviderAdapter.STATISTIQUES_URI
                            + "/" 
                            + this.entity.getId()),
                        null,
                        null);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Assert.assertTrue(result >= 0);
        }

    }

    /** Test case DeleteAll Entity */
    @SmallTest
    public void testDeleteAll() {
        int result = -1;
        if (this.entities.size() > 0) {

            try {
                result = this.provider.delete(StatistiquesProviderAdapter.STATISTIQUES_URI, null, null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertEquals(result, this.nbEntities);
        }
    }

    /****** Provider Utils calls ********/

    /** Test case Read Entity by provider utils. */
    @SmallTest
    public void testUtilsRead() {
        Statistiques result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            StatistiquesUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Statistiques> result = null;
        result = this.providerUtils.queryAll();

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity by provider utils. */
    @SmallTest
    public void testUtilsUpdate() {
        int result = -1;
        if (this.entity != null) {
            Statistiques statistiques = StatistiquesUtils.generateRandom(this.ctx);

            statistiques.setId(this.entity.getId());
            result = this.providerUtils.update(statistiques);

            Assert.assertTrue(result > 0);
        }
    }


    /** Test case Delete Entity by provider utils. */
    @SmallTest
    public void testUtilsDelete() {
        int result = -1;
        if (this.entity != null) {
            result = this.providerUtils.delete(this.entity);
            Assert.assertTrue(result >= 0);
        }

    }
}

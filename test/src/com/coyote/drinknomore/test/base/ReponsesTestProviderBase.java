/**************************************************************************
 * ReponsesTestProviderBase.java, drinknomore Android
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

import com.coyote.drinknomore.provider.ReponsesProviderAdapter;
import com.coyote.drinknomore.provider.utils.ReponsesProviderUtils;
import com.coyote.drinknomore.provider.contract.ReponsesContract;

import com.coyote.drinknomore.data.ReponsesSQLiteAdapter;

import com.coyote.drinknomore.entity.Reponses;

import com.coyote.drinknomore.fixture.ReponsesDataLoader;

import java.util.ArrayList;
import com.coyote.drinknomore.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Reponses database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ReponsesTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ReponsesTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected ReponsesSQLiteAdapter adapter;

    protected Reponses entity;
    protected ContentResolver provider;
    protected ReponsesProviderUtils providerUtils;

    protected ArrayList<Reponses> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new ReponsesSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Reponses>();        
        this.entities.addAll(ReponsesDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += ReponsesDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new ReponsesProviderUtils(this.getContext());
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
            Reponses reponses = ReponsesUtils.generateRandom(this.ctx);

            try {
                ContentValues values = ReponsesContract.itemToContentValues(reponses);
                values.remove(ReponsesContract.COL_ID);
                result = this.provider.insert(ReponsesProviderAdapter.REPONSES_URI, values);

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
        Reponses result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        ReponsesProviderAdapter.REPONSES_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = ReponsesContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ReponsesUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Reponses> result = null;
        try {
            android.database.Cursor c = this.provider.query(ReponsesProviderAdapter.REPONSES_URI, this.adapter.getCols(), null, null, null);
            result = ReponsesContract.cursorToItems(c);
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
            Reponses reponses = ReponsesUtils.generateRandom(this.ctx);

            try {
                reponses.setId(this.entity.getId());

                ContentValues values = ReponsesContract.itemToContentValues(reponses);
                result = this.provider.update(
                    Uri.parse(ReponsesProviderAdapter.REPONSES_URI
                        + "/"
                        + reponses.getId()),
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
            Reponses reponses = ReponsesUtils.generateRandom(this.ctx);

            try {
                ContentValues values = ReponsesContract.itemToContentValues(reponses);
                values.remove(ReponsesContract.COL_ID);

                result = this.provider.update(ReponsesProviderAdapter.REPONSES_URI, values, null, null);
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
                        Uri.parse(ReponsesProviderAdapter.REPONSES_URI
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
                result = this.provider.delete(ReponsesProviderAdapter.REPONSES_URI, null, null);

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
        Reponses result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            ReponsesUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Reponses> result = null;
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
            Reponses reponses = ReponsesUtils.generateRandom(this.ctx);

            reponses.setId(this.entity.getId());
            result = this.providerUtils.update(reponses);

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

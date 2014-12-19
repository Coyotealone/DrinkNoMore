/**************************************************************************
 * ReponsesTestDBBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.test.base;

import java.util.ArrayList;

import android.test.suitebuilder.annotation.SmallTest;

import com.coyote.drinknomore.data.ReponsesSQLiteAdapter;
import com.coyote.drinknomore.entity.Reponses;

import com.coyote.drinknomore.fixture.ReponsesDataLoader;

import com.coyote.drinknomore.test.utils.*;

import junit.framework.Assert;

/** Reponses database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ReponsesTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ReponsesTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected ReponsesSQLiteAdapter adapter;

    protected Reponses entity;
    protected ArrayList<Reponses> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new ReponsesSQLiteAdapter(this.ctx);
        this.adapter.open();

        this.entities = new ArrayList<Reponses>();        
        this.entities.addAll(ReponsesDataLoader.getInstance(this.ctx).getMap().values());
        if (entities.size()>0){
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += ReponsesDataLoader.getInstance(this.ctx).getMap().size();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        this.adapter.close();

        super.tearDown();
    }

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        int result = -1;
        if (this.entity != null) {
            Reponses reponses = ReponsesUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(reponses);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Reponses result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getId());

            ReponsesUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            Reponses reponses = ReponsesUtils.generateRandom(this.ctx);
            reponses.setId(this.entity.getId());

            result = (int) this.adapter.update(reponses);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            result = (int) this.adapter.remove(this.entity.getId());
            Assert.assertTrue(result >= 0);
        }
    }
    
    /** Test the get all method. */
    @SmallTest
    public void testAll() {
        int result = this.adapter.getAll().size();
        int expectedSize = this.nbEntities;
        Assert.assertEquals(expectedSize, result);
    }
}

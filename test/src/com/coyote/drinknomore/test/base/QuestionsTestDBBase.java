/**************************************************************************
 * QuestionsTestDBBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.test.base;

import java.util.ArrayList;

import android.test.suitebuilder.annotation.SmallTest;

import com.coyote.drinknomore.data.QuestionsSQLiteAdapter;
import com.coyote.drinknomore.entity.Questions;


import com.coyote.drinknomore.test.utils.*;

import junit.framework.Assert;

/** Questions database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit QuestionsTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class QuestionsTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected QuestionsSQLiteAdapter adapter;

    protected Questions entity;
    protected ArrayList<Questions> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new QuestionsSQLiteAdapter(this.ctx);
        this.adapter.open();

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
            Questions questions = QuestionsUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(questions);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Questions result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getId());

            QuestionsUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            Questions questions = QuestionsUtils.generateRandom(this.ctx);
            questions.setId(this.entity.getId());

            result = (int) this.adapter.update(questions);

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

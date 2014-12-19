/**************************************************************************
 * QuestionsTestProviderBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 19, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.coyote.drinknomore.provider.QuestionsProviderAdapter;
import com.coyote.drinknomore.provider.utils.QuestionsProviderUtils;
import com.coyote.drinknomore.provider.contract.QuestionsContract;

import com.coyote.drinknomore.data.QuestionsSQLiteAdapter;

import com.coyote.drinknomore.entity.Questions;

import com.coyote.drinknomore.fixture.QuestionsDataLoader;

import java.util.ArrayList;
import com.coyote.drinknomore.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Questions database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit QuestionsTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class QuestionsTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected QuestionsSQLiteAdapter adapter;

    protected Questions entity;
    protected ContentResolver provider;
    protected QuestionsProviderUtils providerUtils;

    protected ArrayList<Questions> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new QuestionsSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Questions>();        
        this.entities.addAll(QuestionsDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += QuestionsDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new QuestionsProviderUtils(this.getContext());
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
            Questions questions = QuestionsUtils.generateRandom(this.ctx);

            try {
                ContentValues values = QuestionsContract.itemToContentValues(questions);
                values.remove(QuestionsContract.COL_ID);
                result = this.provider.insert(QuestionsProviderAdapter.QUESTIONS_URI, values);

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
        Questions result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        QuestionsProviderAdapter.QUESTIONS_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = QuestionsContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            QuestionsUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Questions> result = null;
        try {
            android.database.Cursor c = this.provider.query(QuestionsProviderAdapter.QUESTIONS_URI, this.adapter.getCols(), null, null, null);
            result = QuestionsContract.cursorToItems(c);
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
            Questions questions = QuestionsUtils.generateRandom(this.ctx);

            try {
                questions.setId(this.entity.getId());

                ContentValues values = QuestionsContract.itemToContentValues(questions);
                result = this.provider.update(
                    Uri.parse(QuestionsProviderAdapter.QUESTIONS_URI
                        + "/"
                        + questions.getId()),
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
            Questions questions = QuestionsUtils.generateRandom(this.ctx);

            try {
                ContentValues values = QuestionsContract.itemToContentValues(questions);
                values.remove(QuestionsContract.COL_ID);

                result = this.provider.update(QuestionsProviderAdapter.QUESTIONS_URI, values, null, null);
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
                        Uri.parse(QuestionsProviderAdapter.QUESTIONS_URI
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
                result = this.provider.delete(QuestionsProviderAdapter.QUESTIONS_URI, null, null);

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
        Questions result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            QuestionsUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Questions> result = null;
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
            Questions questions = QuestionsUtils.generateRandom(this.ctx);

            questions.setId(this.entity.getId());
            result = this.providerUtils.update(questions);

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

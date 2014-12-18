/**************************************************************************
 * ReponsesUtilsBase.java, drinknomore Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Dec 18, 2014
 *
 **************************************************************************/
package com.coyote.drinknomore.test.utils.base;


import junit.framework.Assert;
import com.coyote.drinknomore.entity.Reponses;



import com.coyote.drinknomore.test.utils.TestUtils;

import com.coyote.drinknomore.test.utils.QuestionsUtils;


public abstract class ReponsesUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Reponses generateRandom(android.content.Context ctx){
        Reponses reponses = new Reponses();

        reponses.setId(TestUtils.generateRandomInt(0,100) + 1);
        reponses.setReponse("reponse_"+TestUtils.generateRandomString(10));
        reponses.setArguments("arguments_"+TestUtils.generateRandomString(10));
        reponses.setQuestions(QuestionsUtils.generateRandom(ctx));

        return reponses;
    }

    public static boolean equals(Reponses reponses1,
            Reponses reponses2){
        return equals(reponses1, reponses2, true);
    }
    
    public static boolean equals(Reponses reponses1,
            Reponses reponses2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(reponses1);
        Assert.assertNotNull(reponses2);
        if (reponses1!=null && reponses2 !=null){
            Assert.assertEquals(reponses1.getId(), reponses2.getId());
            Assert.assertEquals(reponses1.getReponse(), reponses2.getReponse());
            Assert.assertEquals(reponses1.getArguments(), reponses2.getArguments());
            if (reponses1.getQuestions() != null
                    && reponses2.getQuestions() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(reponses1.getQuestions().getId(),
                            reponses2.getQuestions().getId());
                }
            }
        }

        return ret;
    }
}


/**************************************************************************
 * QuestionsUtilsBase.java, drinknomore Android
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
import com.coyote.drinknomore.entity.Questions;



import com.coyote.drinknomore.test.utils.TestUtils;
import com.coyote.drinknomore.entity.Reponses;
import com.coyote.drinknomore.test.utils.ReponsesUtils;

import java.util.ArrayList;

public abstract class QuestionsUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Questions generateRandom(android.content.Context ctx){
        Questions questions = new Questions();

        questions.setId(TestUtils.generateRandomInt(0,100) + 1);
        questions.setQuestion("question_"+TestUtils.generateRandomString(10));
        questions.setArguments("arguments_"+TestUtils.generateRandomString(10));
        ArrayList<Reponses> relatedReponses = new ArrayList<Reponses>();
        relatedReponses.add(ReponsesUtils.generateRandom(ctx));
        questions.setReponse(relatedReponses);

        return questions;
    }

    public static boolean equals(Questions questions1,
            Questions questions2){
        return equals(questions1, questions2, true);
    }
    
    public static boolean equals(Questions questions1,
            Questions questions2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(questions1);
        Assert.assertNotNull(questions2);
        if (questions1!=null && questions2 !=null){
            Assert.assertEquals(questions1.getId(), questions2.getId());
            Assert.assertEquals(questions1.getQuestion(), questions2.getQuestion());
            Assert.assertEquals(questions1.getArguments(), questions2.getArguments());
            if (questions1.getReponse() != null
                    && questions2.getReponse() != null) {
                Assert.assertEquals(questions1.getReponse().size(),
                    questions2.getReponse().size());
                if (checkRecursiveId) {
                    for (Reponses reponse1 : questions1.getReponse()) {
                        boolean found = false;
                        for (Reponses reponse2 : questions2.getReponse()) {
                            if (reponse1.getId() == reponse2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated reponse (id = %s) in Questions (id = %s)",
                                        reponse1.getId(),
                                        questions1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}


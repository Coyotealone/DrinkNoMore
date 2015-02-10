/**************************************************************************
 * ReponsesUtilsBase.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.test.utils.base;


import junit.framework.Assert;
import com.coyote.drinknomore.entity.Reponses;



import com.coyote.drinknomore.test.utils.TestUtils;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.fixture.QuestionsDataLoader;


import java.util.ArrayList;

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
        reponses.setSolution("solution_"+TestUtils.generateRandomString(10));
        reponses.setArguments("arguments_"+TestUtils.generateRandomString(10));
        ArrayList<Questions> questions =
            new ArrayList<Questions>();
        questions.addAll(QuestionsDataLoader.getInstance(ctx).getMap().values());
        if (!questions.isEmpty()) {
            reponses.setQuestion(questions.get(TestUtils.generateRandomInt(0, questions.size())));
        }

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
            Assert.assertEquals(reponses1.getSolution(), reponses2.getSolution());
            Assert.assertEquals(reponses1.getArguments(), reponses2.getArguments());
            if (reponses1.getQuestion() != null
                    && reponses2.getQuestion() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(reponses1.getQuestion().getId(),
                            reponses2.getQuestion().getId());
                }
            }
        }

        return ret;
    }
}


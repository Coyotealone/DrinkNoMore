/**************************************************************************
 * StatistiquesUtilsBase.java, drinknomore Android
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
import com.coyote.drinknomore.entity.Statistiques;



import com.coyote.drinknomore.test.utils.TestUtils;


public abstract class StatistiquesUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Statistiques generateRandom(android.content.Context ctx){
        Statistiques statistiques = new Statistiques();

        statistiques.setId(TestUtils.generateRandomInt(0,100) + 1);
        statistiques.setDate(TestUtils.generateRandomDateTime());
        statistiques.setNberreurs(TestUtils.generateRandomInt(0,100));

        return statistiques;
    }

    public static boolean equals(Statistiques statistiques1,
            Statistiques statistiques2){
        return equals(statistiques1, statistiques2, true);
    }
    
    public static boolean equals(Statistiques statistiques1,
            Statistiques statistiques2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(statistiques1);
        Assert.assertNotNull(statistiques2);
        if (statistiques1!=null && statistiques2 !=null){
            Assert.assertEquals(statistiques1.getId(), statistiques2.getId());
            Assert.assertEquals(statistiques1.getDate(), statistiques2.getDate());
            Assert.assertEquals(statistiques1.getNberreurs(), statistiques2.getNberreurs());
        }

        return ret;
    }
}


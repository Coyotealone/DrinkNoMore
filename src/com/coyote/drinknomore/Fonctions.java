package com.coyote.drinknomore;

import android.content.Context;

import com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;

import java.util.Random;

public class Fonctions {

    /**
     *
     * @param value
     * @return String
     */
	public static String SplitTime(String value) {
		String[] nospace = value.split(" ");
		String result = "";
		if (nospace.length > 3) {
			String[] nodoublepoint = nospace[3].split(":");
			if (nodoublepoint.length > 2)
				result = nodoublepoint[0] + ":" + nodoublepoint[1];
		}
		return result;
	}

    public static Integer RandomId(int maxId)
    {
        int min = 0;
        /**
         * int maximum into random
         * {@value nbQuestions.length - 1}
         */
        int max = maxId - 1;
        /**
         * {@value #new Random}
         */
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}

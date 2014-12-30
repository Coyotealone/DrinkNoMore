package com.coyote.drinknomore;

import android.content.Context;

import com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;

public class Fonctions {

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

    public void SetDB(Context context)
    {
        Reponses reponse1 = new Reponses();
        reponse1.setSolution("25");
        reponse1.setArguments(".");

        Questions question1 = new Questions();
        question1.setEnigme("5x5");
        question1.setArguments("25;20;10");
        //question1.setReponse(reponse1);
        QuestionsSQLiteAdapterBase questions = new QuestionsSQLiteAdapterBase(context);
        questions.open();
        questions.close();
    }

}

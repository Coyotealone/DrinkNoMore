package com.coyote.drinknomore;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase;
import com.coyote.drinknomore.data.base.ReponsesSQLiteAdapterBase;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;

/**
 * 
 * @author Coyote
 *
 */
public class Fonctions {

	/**
	 * 
	 */
	public static final String PREFS_PARAMETERS = "prefFileParameters";

	/**
	 * Function to split time
	 * @param valuetime String
	 * @return time format String
	 */
	public static String SplitTime(String valuetime) {
		String[] nospace = valuetime.split(" ");
		String result = "";
		if (nospace.length > 3) {
			String[] nodoublepoint = nospace[3].split(":");
			if (nodoublepoint.length > 2)
				result = nodoublepoint[0] + ":" + nodoublepoint[1];
		}
		return result;
	}

	/**
	 * Function random about id Question
	 * @param maxId Integer
	 * @return id of Questions
	 */
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

	/**
	 * Function to find Parameters about integer day
	 * @param day Integer
	 * @return String correspondant au numéro de la journée
	 */
	public static String NameDay(int day)
	{
		String dayString;
		switch (day) {
		case 1:  dayString = "Parametres_cb_horaire_dimanche";
		break;
		case 2:  dayString = "Parametres_cb_horaire_lundi";
		break;
		case 3:  dayString = "Parametres_cb_horaire_mardi";
		break;
		case 4:  dayString = "Parametres_cb_horaire_mercredi";
		break;
		case 5:  dayString = "Parametres_cb_horaire_jeudi";
		break;
		case 6:  dayString = "Parametres_cb_horaire_vendredi";
		break;
		case 7:  dayString = "Parametres_cb_horaire_samedi";
		break;
		default: dayString = "Invalid day";
		break;
		}
		return dayString;
	}

	/**
	 * Function to associate Question Reponse in db
	 * @param ctx Context
	 * @return true if associated is OK else false
	 */
	public Boolean AssociateQuestionsReponses(Context ctx)
	{
		QuestionsSQLiteAdapterBase questionsSQL = new QuestionsSQLiteAdapterBase(ctx);
		ReponsesSQLiteAdapterBase reponsesSQL = new ReponsesSQLiteAdapterBase(ctx);
		questionsSQL.open();
		reponsesSQL.open();
		int[] nbQuestions = questionsSQL.getId();
		if(nbQuestions.length > 0)
		{
			Questions questions = new Questions();
			Reponses reponses = new Reponses();
			ArrayList<Reponses> tabReponses = new ArrayList<Reponses>();
			
			for(Integer i=0; i<nbQuestions.length; i++)
			{
				questions = questionsSQL.getByID(nbQuestions[i]);
				reponses = reponsesSQL.getByID(nbQuestions[i]);
				reponses.setQuestion(questions);
				tabReponses.add(reponses);
				questions.setReponse(tabReponses);
				questionsSQL.insertOrUpdate(questions);
				reponsesSQL.insertOrUpdate(reponses);
			}
			
			return true;
		}
		return false;
	}
}

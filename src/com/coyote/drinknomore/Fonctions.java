package com.coyote.drinknomore;

import android.content.Context;
import android.content.SharedPreferences;

import com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Random;

public class Fonctions {

    public static final String PREFS_PARAMETERS = "prefFileParameters";

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
}

/**************************************************************************
 * Fonctions.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Coyote
 * Licence     : 
 * Last update : Feb 19, 2015
 **************************************************************************/

package com.coyote.drinknomore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.content.SharedPreferences;

import com.coyote.drinknomore.data.base.QuestionsSQLiteAdapterBase;
import com.coyote.drinknomore.data.base.ReponsesSQLiteAdapterBase;
import com.coyote.drinknomore.entity.Questions;
import com.coyote.drinknomore.entity.Reponses;

/** 
 * Class regroup functions.
 * @author Coyote
 */
public class Fonctions {

    /**
     * Function to split time.
     * @param String valuetime Time formated in string
     * @return String result Time formated HH:mm in string
     */
    public static String splitTime(String valuetime) {
        String[] nospace = valuetime.split(" ");
        String result = "";
        if (nospace.length > 3) {
            String[] nodoublepoint = nospace[3].split(":");
            if (nodoublepoint.length > 2) {
                result = nodoublepoint[0] + ":" + nodoublepoint[1];
            }
        }
        return result;
    }

    /**
     * Function random about id Question.
     * @param Integer maxId Count id Questions
     * @return Integer randomNum id of Questions
     */
    public static Integer randomId(int maxId) {
        /**
         * int minimun into random.
         */
        int min = 0;
        
        /**
         * int maximum into random.
         * value nbQuestions.length - 1.
         */
        int max = maxId - 1;
        /**
         * value #new Random.
         */
        Random rand = new Random();
        /**
         *  NextInt is normally exclusive of the top value, so add 1 to make it inclusive.
         */
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * Function to associate Question Reponse in db.
     * @param Context ctx
     * @return Boolean true if associated is OK else false
     */
    public Boolean associateQuestionsReponses(Context ctx) {
        QuestionsSQLiteAdapterBase questionsSql = new QuestionsSQLiteAdapterBase(ctx);
        ReponsesSQLiteAdapterBase reponsesSql = new ReponsesSQLiteAdapterBase(ctx);
        questionsSql.open();
        reponsesSql.open();
        int[] nbQuestions = questionsSql.getId();
        if (nbQuestions.length > 0) {
            Questions questions = new Questions();
            Reponses reponses = new Reponses();
            ArrayList<Reponses> tabReponses = new ArrayList<Reponses>();

            for (Integer i = 0; i < nbQuestions.length ; i++) {
                questions = questionsSql.getByID(nbQuestions[i]);
                reponses = reponsesSql.getByID(nbQuestions[i]);
                reponses.setQuestion(questions);
                tabReponses.add(reponses);
                questions.setReponse(tabReponses);
                questionsSql.insertOrUpdate(questions);
                reponsesSql.insertOrUpdate(reponses);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Function to return string about settingsParameters with number of the day
     * @param SharedPreferences settingsParameters File settings parameters
     * @param Integer dayOfweek Number of the day
     * @return Boolean if true day selected in settings else false
     */
    public Boolean checkDay(SharedPreferences settingsParameters, Integer dayOfweek) {
        Boolean checkday = false;
        switch (dayOfweek) {
            case 1:
                checkday = settingsParameters.getBoolean("cb_horaire_dimanche", false);
                break;
            case 2: 
                checkday = settingsParameters.getBoolean("cb_horaire_lundi", false);
                break;
            case 3: 
                checkday = settingsParameters.getBoolean("cb_horaire_mardi", false);
                break;
            case 4: 
                checkday = settingsParameters.getBoolean("cb_horaire_mercredi", false);
                break;
            case 5: 
                checkday = settingsParameters.getBoolean("cb_horaire_jeudi", false);
                break;
            case 6: 
                checkday = settingsParameters.getBoolean("cb_horaire_vendredi", false);
                break;
            case 7: 
                checkday = settingsParameters.getBoolean("cb_horaire_samedi", false);
                break;
            default: 
                checkday = false;
                break;
        }
        return checkday;
    }

    /**
     * Function to compare format time
     * @param SharedPreferences settingsParameters
     * @param Calendar calendarNow
     * @return Integer Difference about the time in settings and time now
     */
    public Integer compareDateTime(SharedPreferences settingsParameters) {
        Calendar calendarNow = Calendar.getInstance();
        String timeSave = settingsParameters.getString("timeWidget_Parametres_horaire",null);
        if (timeSave == null)
        {
            return -1;
        }
        else
        {
            String timeNow = String.valueOf(calendarNow.get(Calendar.HOUR_OF_DAY)) + ':' 
                    + String.valueOf(calendarNow.get(Calendar.MINUTE));
            DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
            DateTime datetimeNow = formatter.parseDateTime(timeNow);
            DateTime datetimeSave = formatter.parseDateTime(timeSave);
            
            return datetimeSave.compareTo(datetimeNow);
        }
    }
}

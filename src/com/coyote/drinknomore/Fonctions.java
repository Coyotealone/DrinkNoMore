package com.coyote.drinknomore;

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

}

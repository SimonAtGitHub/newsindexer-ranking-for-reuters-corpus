package edu.buffalo.cse.irf14.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;

public class StringUtil {

	/**
	 * Checks if a regex matches with the input
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public static boolean matchRegex(String input, String regex) {
		Pattern pattern = Pattern.compile(regex);
		boolean flag = false;
		Matcher m = pattern.matcher(input);
		if (m.find()) {
			flag = true;
		}

		return flag;
	}

	public static String convertStrArrToString(String[] strArr) {
		if (null != strArr && strArr.length > 0) {
			String stringEnclosedInArray = Arrays.deepToString(strArr);
			// The above method returns with enclosing brackets, remove them.
			int length = stringEnclosedInArray.length();
			if (length > 2) {
				stringEnclosedInArray = stringEnclosedInArray.substring(1,
						length - 1);
			} else {
				stringEnclosedInArray = "";
			}
			return stringEnclosedInArray;
		}
		return null;
	}

	private String convertTokenStreamToString(TokenStream tstream) {

		ArrayList<String> list = new ArrayList<String>();
		String s;
		Token t;

		while (tstream.hasNext()) {
			t = tstream.next();

			if (t != null) {
				s = t.toString();

				if (s != null && !s.isEmpty())
					list.add(s);
			}
		}

		String[] rv = new String[list.size()];
		rv = list.toArray(rv);
		tstream = null;
		list = null;
		return rv.toString();
	}

	/**
	 * Converts string array to string
	 * 
	 * @param strArr
	 * @return
	 */
	public static String convertStrArrToString2(String[] array) {
		StringBuilder builder = new StringBuilder();
		for (String str : array) {
			builder.append(str);
			// builder.append(" ");
		}
		/*
		 * if(builder.length()>1){ builder.substring(0, builder.length()-1); }
		 */
		return builder.toString();
	}

}

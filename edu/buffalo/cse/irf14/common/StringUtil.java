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

	/**
	 * Method to convert TokenStream to a String
	 * @param tstream
	 * @return
	 */
	public static String convertTokenStreamToString(TokenStream tstream) {

		String outputStr="";
		Token t;

		while (tstream.hasNext()) {
			t = tstream.next();
			outputStr = outputStr+t.toString();
			outputStr = outputStr + CommonConstants.WHITESPACE;
		}
		if(outputStr!=null){
			outputStr =outputStr.trim();
		}
		return outputStr;
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

	/**
	 * Method that checks for an empty or blank string
	 */
	public static Boolean isEmpty(String str){
		Boolean flag = false;
		if(str==null || str.length()==0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * Method that checks if a string is not empty
	 */
	public static Boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
}

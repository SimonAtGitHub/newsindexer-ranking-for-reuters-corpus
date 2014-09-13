package edu.buffalo.cse.irf14.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * Checks if a regex matches with the input
	 * @param input
	 * @param regex
	 * @return
	 */
	public static boolean matchRegex(String input,String regex){
		Pattern pattern = Pattern.compile(regex);
		boolean flag=false;
        Matcher m = pattern.matcher(input);
        if(m.find()){
        	flag = true;
        }
       
        return flag;
	}
	 
}

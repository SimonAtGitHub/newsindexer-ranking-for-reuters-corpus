/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

/**
 * @author nikhillo
 * Enum that defines different {@link TokenFilter} types
 */
public enum TokenFilterType {
	SYMBOL, DATE, NUMERIC, CAPITALIZATION, STOPWORD, STEMMER, ACCENT, SPECIALCHARS;
	
	public Class getClass(TokenFilterType type){
		TokenFilter filter = null;
		switch(type){
		   case NUMERIC:
			   filter=new NumberRule(null);
			   break;
		}
		return filter.getClass();
	}
};

package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.common.RegExp;
import edu.buffalo.cse.irf14.common.StringUtil;

public class NumberRule extends TokenFilter{
	TokenStream stream;

	public NumberRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	/**
	 * 1. Processes the current token if it is not null
	 * 2. Moves the pointer to the next token and returns true if there is any more tokens to 
	 *    be processed
	 */
	@Override
	public boolean increment() throws TokenizerException {
		if(stream.getCurrent()!=null){
			   applyFilter();
		}
		if(stream.hasNext()){
			stream.next();
			return true;
		}
		return false;
	}

	@Override
	public TokenStream getStream() {
		return stream;
	}
	
	/**
	 * Function that applies rule to the current token
	 */
	public void applyFilter(){
		    Token token = stream.getCurrent();
			String termText = token.getTermText();
			//if the token is a real number delete the token
			if(StringUtil.matchRegex(termText,RegExp.REGEX_REAL_NUM)){
				stream.remove();
				stream.next();
			}
			//if the token is a composite number (i.e. fractions or percentages ,replace
			//only the digits
			else if(StringUtil.matchRegex(termText,RegExp.REGEX_COMPOSITE_NUM)){
				termText=termText.replaceAll(RegExp.REGEX_NUM_PERIOD, "");
			}
			token.setTermText(termText);
	}

}

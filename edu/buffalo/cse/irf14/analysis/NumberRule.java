package edu.buffalo.cse.irf14.analysis;

import java.text.Normalizer;

import edu.buffalo.cse.irf14.common.RegExp;
import edu.buffalo.cse.irf14.common.StringUtil;

public class NumberRule extends TokenFilter{
	TokenStream stream;

	public NumberRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		
		return false;
	}

	@Override
	public TokenStream getStream() {
		filterNumbers();
		return stream;
	}
	
	//TODO- Discard dates
	private void filterNumbers(){
		while (stream.hasNext()) {
			Token token = stream.next();
			String termText = token.getTermText();
			//if the token is a real number delete the token
			if(StringUtil.matchRegex(termText,RegExp.REGEX_REAL_NUM)){
				stream.remove();
				continue;
			}
			//if the token is a composite number (i.e. fractions or percentages ,replace
			//only the digits
			else if(StringUtil.matchRegex(termText,RegExp.REGEX_COMPOSITE_NUM)){
				termText=termText.replaceAll(RegExp.REGEX_NUM_PERIOD, "");
			}
			token.setTermText(termText);
		}
	}

}

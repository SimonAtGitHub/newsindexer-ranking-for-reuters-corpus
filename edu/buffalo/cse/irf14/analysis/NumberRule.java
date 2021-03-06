package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.common.RegExp;
import edu.buffalo.cse.irf14.common.StringUtil;

public class NumberRule extends TokenFilter {

	public NumberRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	/**
	 * Function that applies rule to the current token
	 */
	public void applyFilter() {
		Token token = stream.getCurrent();
		if (token != null) {
			String termText = token.getTermText();
			// Don't apply this rule if the Token Doesn't start with a digit
			if (termText.isEmpty()) {
				return;
			}
			// If the token is a formatted date or time, ignore it
			if (token.isDatetime()) {
				// Do nothing
				return;
			}
			// if the token is a real number delete the token
			else if (StringUtil.matchRegex(termText, RegExp.REGEX_REAL_NUM)) {
				stream.remove();
			}
			// if the token is a composite number (i.e. fractions or
			// percentages
			// ,replace
			// only the digits
			else if (StringUtil
					.matchRegex(termText, RegExp.REGEX_COMPOSITE_NUM)) {
				termText = termText.replaceAll(RegExp.REGEX_NUM_PERIOD, "");
				if (termText.isEmpty()) {
					stream.remove();
				} else {
					token.setTermText(termText);
				}
			}
		}
	}
}

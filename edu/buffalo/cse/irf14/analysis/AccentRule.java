package edu.buffalo.cse.irf14.analysis;

import java.text.Normalizer;

/**
 * Reference taken from
 * http://stackoverflow.com/questions/15190656/easy-way-to-remove
 * -utf-8-accents-from-a-string
 * 
 * @author Priyankar
 * 
 */

public class AccentRule extends TokenFilter {

	public AccentRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public void applyFilter() {
		filterAccents();
	}

	private void filterAccents() {
		Token token = stream.getCurrent();
		if (token != null) {
			String termText = token.getTermText();
			// Don't process if starts with digits
			if (!(Character.isDigit(termText.charAt(0)))) {
				termText = Normalizer.normalize(termText, Normalizer.Form.NFD);
				termText = termText.replaceAll(
						"\\p{InCombiningDiacriticalMarks}+", "");
				token.setTermText(termText);
			}
		}
	}
}

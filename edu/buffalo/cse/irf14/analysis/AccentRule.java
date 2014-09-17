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

	TokenStream stream;

	public AccentRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		while (stream.hasNext()) {
			Token token = stream.next();

		}
		return false;
	}

	@Override
	public TokenStream getStream() {
		filterAccents();
		return stream;
	}

	private void filterAccents() {
		while (stream.hasNext()) {
			Token token = stream.next();
			String termText = token.getTermText();
			termText = Normalizer.normalize(termText, Normalizer.Form.NFD);
			termText = termText.replaceAll(
					"\\p{InCombiningDiacriticalMarks}+", "");
			token.setTermText(termText);
		}
	}
}

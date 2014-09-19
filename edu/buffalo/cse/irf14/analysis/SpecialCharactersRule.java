package edu.buffalo.cse.irf14.analysis;

/**
 * @author animeshk
 *
 */
public class SpecialCharactersRule extends TokenFilter {

	TokenStream stream;

	public SpecialCharactersRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		// stream.next();
		return false;
	}

	// To be changed
	@Override
	public TokenStream getStream() {
		while (stream.hasNext()) {
			Token token = stream.next();
			String termText = token.getTermText();
			if (termText != null) {
				termText = filterSpecialCharacters(termText);
				if (termText == null || termText.isEmpty()) {
					stream.remove();
					continue;
				}
				token.setTermText(termText);
				System.out.println(token.getTermText());
			}
		}
		return stream;
	}

	private String filterSpecialCharacters(String termText) {
		// We assume here that all the filters higher in priority have finished
		// their job and we just have to preserve the ones where special
		// characters were preserved and apply filtering after that. For Eg. In
		// 6-6, B-44, !true, em?ty
		// if(!termText.matches(regex))
		return termText;
	}

}

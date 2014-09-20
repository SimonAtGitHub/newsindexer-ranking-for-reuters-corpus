package edu.buffalo.cse.irf14.analysis;

/**
 * This class does nothing but call the stem() on a word. Stemming is done by
 * Porter Algorithm implemented by Tartarus. <br>
 * http://tartarus.org/martin/PorterStemmer/java.txt
 *
 */
public class StemmerRule extends TokenFilter {
	Stemmer stemmer;

	public StemmerRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
		stemmer = new Stemmer();
	}

	@Override
	public void applyFilter() {
		Token token = stream.getCurrent();
		String termText = token.getTermText();
		// Apply the stemming only if the termText is not null and
		// begins with alphabet.
		if (termText != null) {
			if (termText.matches("^[a-zA-Z]+")) {
				stemmer.add(termText.toCharArray(), termText.length());
				stemmer.stem();
				termText = stemmer.toString();
			}
			token.setTermText(termText);
		}

	}
}

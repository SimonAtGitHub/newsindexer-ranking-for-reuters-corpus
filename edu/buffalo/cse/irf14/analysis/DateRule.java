package edu.buffalo.cse.irf14.analysis;

/**
 * @author animeshk
 *
 */
public class DateRule extends TokenFilter {

	public DateRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public void applyFilter() {
		Token token = stream.getCurrent();
		String termText = token.getTermText();
		termText = handleDate(termText);
		token.setTermText(termText);

	}

	private String handleDate(String termText) {

		return termText;
	}

}

package edu.buffalo.cse.irf14.analysis;


public class TokenFilterSymbol extends TokenFilter{

	TokenStream stream;
	public TokenFilterSymbol(TokenStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public void increment() throws TokenizerException {
		stream.next();
	}
	
    //To be changed
	@Override
	public TokenStream getStream() {
		while(stream.hasNext()){
			Token token=stream.next();
			String termText=token.getTermText();
			if(termText!=null){
				token.setTermText(termText.replaceAll("boy", ""));
			}
		}
		return stream;
	}

}

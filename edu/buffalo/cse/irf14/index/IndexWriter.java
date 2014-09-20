/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.util.ArrayList;
import java.util.Arrays;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;

/**
 * @author nikhillo
 * Class responsible for writing indexes to disk
 */
public class IndexWriter {
	/**
	 * Default constructor
	 * @param indexDir : The root directory to be sued for indexing
	 */
	public IndexWriter(String indexDir) {
		//TODO : YOU MUST IMPLEMENT THIS
	}
	
	/**
	 * Method to add the given Document to the index
	 * This method should take care of reading the filed values, passing
	 * them through corresponding analyzers and then indexing the results
	 * for each indexable field within the document. 
	 * @param d : The Document to be added
	 * @throws IndexerException : In case any error occurs
	 */
	public void addDocument(Document d) throws IndexerException {
		//TODO : YOU MUST IMPLEMENT THIS
		Tokenizer tknizer = new Tokenizer();
		AnalyzerFactory fact = AnalyzerFactory.getInstance();

		
		try {
			String[] contentArr = d.getField(FieldNames.CONTENT);
			String content=Arrays.deepToString(contentArr);
			TokenStream stream = tknizer.consume(content);
			Analyzer analyzer ;
			analyzer = fact.getAnalyzerForField(FieldNames.CONTENT,
					stream);

			while (analyzer.increment()) {

			}
			
			stream.reset();
			
			String streamTest = convertTokenStreamToString(stream);
			System.out.println("Content ::: "+streamTest);

		} catch (TokenizerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private String convertTokenStreamToString(TokenStream tstream){

		ArrayList<String> list = new ArrayList<String>();
		String s;
		Token t;

		while (tstream.hasNext()) {
			t = tstream.next();

			if (t != null) {
				s = t.toString();
				
				if (s!= null && !s.isEmpty())
					list.add(s);	
			}
		}
		
		String[] rv = new String[list.size()];
		rv = list.toArray(rv);
		tstream = null;
		list = null;
		System.out.println(rv);
		return rv.toString();
	}
	
	/**
	 * Method that indicates that all open resources must be closed
	 * and cleaned and that the entire indexing operation has been completed.
	 * @throws IndexerException : In case any error occurs
	 */
	public void close() throws IndexerException {
		//TODO
	}
}

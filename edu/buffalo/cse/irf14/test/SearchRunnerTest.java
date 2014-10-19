package edu.buffalo.cse.irf14.test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import org.junit.Test;

import edu.buffalo.cse.irf14.SearchRunner;
import edu.buffalo.cse.irf14.SearchRunner.ScoringModel;
import edu.buffalo.cse.irf14.index.Posting;
public class SearchRunnerTest {
	
	public static final String INDEX_DIR = "H:\\projects\\newsindexer\\index";
	
	public static final String CORPUS_DIR = "H:\\projects\\newsindexer\\training";
	
	public static final String OUTPUT_FILE = "H:\\projects\\newsindexer\\index\\queryOutput.txt";
	
	public static final String INPUT_FILE = "H:\\projects\\newsindexer\\index\\queryInput.txt";
	
	public static final char MODE = 'Q';
    @Test
	public void testExecuteQuery() {
    	SearchRunner searchRunner;
    	List<Posting> postings=null;
		try {
			searchRunner = new SearchRunner(INDEX_DIR, CORPUS_DIR, MODE, new PrintStream(OUTPUT_FILE));
/*	    	postings=searchRunner.executeQuery("Term:priyankar");
	    	postings=searchRunner.executeQuery("Term:priyankar AND Term:nandi");
	    	postings=searchRunner.executeQuery("Term:priyankar OR Term:nandi");
	    	postings=searchRunner.executeQuery("Term:priyankar NOT Term:nandi");
	    	postings=searchRunner.executeQuery("( Term:priyankar AND Term:nandi ) OR Term:animesh");
	    	postings=searchRunner.executeQuery("Term:animesh OR ( Term:priyankar AND Term:nandi )");
	    	postings=searchRunner.executeQuery("Term:animesh OR Term:priyankar OR Term:nandi");
	    	postings=searchRunner.executeQuery("Term:chandrakant OR ( Term:animesh OR Term:priyankar OR Term:nandi )");*/
	    	postings=searchRunner.executeQuery("Term:\"juhi har\"");
	        System.out.println("\nPostings retrieved");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    @Test
   	public void testQuery() {
       	SearchRunner searchRunner;
       	List<Posting> postings=null;
   		try {
   			searchRunner = new SearchRunner(INDEX_DIR, CORPUS_DIR, MODE, new PrintStream(OUTPUT_FILE));
   			searchRunner.query("\"Term:juhi har\"", ScoringModel.OKAPI);
   			//searchRunner.query("( Term:abcd )", ScoringModel.TFIDF);
   	        System.out.println("\nPostings retrieved");
   		} catch (FileNotFoundException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   	}
    
    @Test
   	public void testQueryEvaluation() {
       	SearchRunner searchRunner;
       	List<Posting> postings=null;
   		try {
   			searchRunner = new SearchRunner(INDEX_DIR, CORPUS_DIR, MODE, new PrintStream(OUTPUT_FILE));
   			searchRunner.query(new File(INPUT_FILE));
   			//searchRunner.query("( Term:abcd )", ScoringModel.TFIDF);
   	        System.out.println("\nPostings retrieved");
   		} catch (FileNotFoundException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   	}
}

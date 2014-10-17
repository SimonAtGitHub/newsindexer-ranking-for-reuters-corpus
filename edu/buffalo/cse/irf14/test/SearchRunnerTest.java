package edu.buffalo.cse.irf14.test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import org.junit.Test;

import edu.buffalo.cse.irf14.SearchRunner;
import edu.buffalo.cse.irf14.SearchRunner.ScoringModel;
import edu.buffalo.cse.irf14.index.Posting;
public class SearchRunnerTest {
	
    @Test
	public void testExecuteQuery() {
    	SearchRunner searchRunner;
    	List<Posting> postings=null;
		try {
			searchRunner = new SearchRunner("H:\\projects\\IR\\newsindexer\\index", "H:\\projects\\IR\\newsindexer\\training", 'Q', new PrintStream("H:\\projects\\IR\\newsindexer\\index\\queryOutput.txt"));
	    	postings=searchRunner.executeQuery("Term:priyankar");
	    	postings=searchRunner.executeQuery("Term:priyankar AND Term:nandi");
	    	postings=searchRunner.executeQuery("Term:priyankar OR Term:nandi");
	    	postings=searchRunner.executeQuery("Term:priyankar NOT Term:nandi");
	    	postings=searchRunner.executeQuery("( Term:priyankar AND Term:nandi ) OR Term:animesh");
	    	postings=searchRunner.executeQuery("Term:animesh OR ( Term:priyankar AND Term:nandi )");
	    	postings=searchRunner.executeQuery("Term:animesh OR Term:priyankar OR Term:nandi");
	    	postings=searchRunner.executeQuery("Term:chandrakant OR ( Term:animesh OR Term:priyankar OR Term:nandi )");
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
   			searchRunner = new SearchRunner("H:\\projects\\IR\\newsindexer\\index", "H:\\projects\\IR\\newsindexer\\training", 'Q', new PrintStream("H:\\projects\\IR\\newsindexer\\index\\queryOutput.txt"));
   			searchRunner.query("( Term:priyankar AND Term:nandi )", ScoringModel.TFIDF);
   			//searchRunner.query("( Term:abcd )", ScoringModel.TFIDF);
   	        System.out.println("\nPostings retrieved");
   		} catch (FileNotFoundException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   	}
}

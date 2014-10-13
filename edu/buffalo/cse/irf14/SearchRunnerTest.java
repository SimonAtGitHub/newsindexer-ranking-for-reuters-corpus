package edu.buffalo.cse.irf14;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import edu.buffalo.cse.irf14.SearchRunner.ScoringModel;

public class SearchRunnerTest {
   public static void main(String [] args){
	   try {
		//long startime = System.currentTimeMillis();  
		SearchRunner searchRunner = new SearchRunner("H:\\projects\\IR\\newsindexer\\index", "H:\\projects\\IR\\newsindexer\\training", 'Q', new PrintStream("H:\\projects\\IR\\newsindexer\\index\\queryOutput.txt"));
		searchRunner.query("( Term:priyankar AND Term:nandi )", ScoringModel.TFIDF);
		//long endtime = System.currentTimeMillis();
		//System.out.println("\nQuery executed in Total time " + ((endtime - startime) / 1000.));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}

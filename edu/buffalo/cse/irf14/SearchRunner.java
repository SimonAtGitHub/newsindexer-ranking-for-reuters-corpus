package edu.buffalo.cse.irf14;

import java.io.File;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.common.CommonConstants;
import edu.buffalo.cse.irf14.common.CommonUtil;
import edu.buffalo.cse.irf14.common.StringUtil;
import edu.buffalo.cse.irf14.common.TermIndexDetails;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.Posting;
import edu.buffalo.cse.irf14.index.PostingScoreComparator;
import edu.buffalo.cse.irf14.index.PostingWrapper;

/**
 * Main class to run the searcher.
 * As before implement all TODO methods unless marked for bonus
 * @author nikhillo
 *
 */
public class SearchRunner {
	public enum ScoringModel {TFIDF, OKAPI};
	
	private String indexDir;
	private String corpusDir;
	private char mode;
	private PrintStream stream;
	
	private Map<Integer, String> docDictionary;
	
	private Map<String, Integer> termDictionary;
	
	private Map<String, Integer> authorDictionary;
	
	private Map<String, Integer> categoryDictionary;
	
	private Map<String, Integer> placeDictionary;
	
	private Map<Integer, PostingWrapper> termIndex;
	
	private Map<Integer, PostingWrapper> authorIndex;
	
	private Map<Integer, PostingWrapper> categoryIndex;
	
	private Map<Integer, PostingWrapper> placeIndex;
	
	/*
	 * Set consisting of all the terms in the query
	 */
	Set<String> termSet ;
	
	/**
	 * Default (and only public) constuctor
	 * @param indexDir : The directory where the index resides
	 * @param corpusDir : Directory where the (flattened) corpus resides
	 * @param mode : Mode, one of Q or E
	 * @param stream: Stream to write output to
	 */
	public SearchRunner(String indexDir, String corpusDir, 
			char mode, PrintStream stream) {
		//TODO: IMPLEMENT THIS METHOD
		this.indexDir=indexDir;
		this.corpusDir=corpusDir;
		this.mode=mode;
		this.stream=stream;
		//load the dictionaries
		docDictionary = (Map<Integer, String>) CommonUtil.readObject(indexDir + File.separatorChar+ CommonConstants.DOCUMENT_DICTIONARY_FILENAME);
		termDictionary = (Map<String, Integer>) CommonUtil.readObject(indexDir + File.separatorChar+ CommonConstants.TERM_DICTIONARY_FILENAME);
		authorDictionary = (Map<String, Integer>) CommonUtil.readObject(indexDir + File.separatorChar+ CommonConstants.AUTHOR_DICTIONARY_FILENAME);
		categoryDictionary = (Map<String, Integer>) CommonUtil.readObject(indexDir + File.separatorChar+ CommonConstants.CATEGORY_DICTIONARY_FILENAME);
		placeDictionary = (Map<String, Integer>) CommonUtil.readObject(indexDir + File.separatorChar+ CommonConstants.PLACE_DICTIONARY_FILENAME);
		//load the indexes
		termIndex = (Map<Integer, PostingWrapper>) CommonUtil.readObject(indexDir + File.separatorChar+ CommonConstants.TERM_INDEX_FILENAME);
		authorIndex = (Map<Integer, PostingWrapper>) CommonUtil.readObject(indexDir + File.separatorChar+ CommonConstants.AUTHOR_INDEX_FILENAME);
		categoryIndex = (Map<Integer, PostingWrapper>) CommonUtil.readObject(indexDir + File.separatorChar+ CommonConstants.CATEGORY_INDEX_FILENAME);
		placeIndex = (Map<Integer, PostingWrapper>) CommonUtil.readObject(indexDir + File.separatorChar+ CommonConstants.PLACE_INDEX_FILENAME);
		
	}

	/**
	 * Method to execute given query in the Q mode
	 * @param userQuery : Query to be parsed and executed
	 * @param model : Scoring Model to use for ranking results
	 */
	public void query(String userQuery, ScoringModel model) {
		   //TODO: IMPLEMENT THIS METHOD
		   //TODO- call queryParser to parse the query
		   //execute the query
		   long startime = System.currentTimeMillis(); 
		   List<Posting> postings=executeQuery(userQuery);
		   //calculate the score based on the Scoring model of each document
		   //with respect to the query terms
		   calculateScore(postings,model);
		   termSet = new HashSet<String>();
		   
		   //print the execution details
		   System.out.println("===============================================================");
		   long endtime = System.currentTimeMillis();
		   System.out.println("Query: " + userQuery);
		   System.out.println("Query time: " + ((endtime - startime)));
		   int rank=1;
		   Collections.sort(postings, new PostingScoreComparator());
		   for(Posting posting:postings){
			   System.out.println("Result Title: "+docDictionary.get(posting.getDocId())+
					               "   Result Rank: "+  rank+
					               "   Result Relevancy: "+  posting.getScore());
			   rank++;
		   }
		   System.out.println("===============================================================");
	}
	
	/**
	 * Method to execute queries in E mode
	 * @param queryFile : The file from which queries are to be read and executed
	 */
	public void query(File queryFile) {
		//TODO: IMPLEMENT THIS METHOD
	}
	
	/**
	 * General cleanup method
	 */
	public void close() {
		//TODO : IMPLEMENT THIS METHOD
	}
	
	/**
	 * Method to indicate if wildcard queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean wildcardSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF WILDCARD BONUS ATTEMPTED
		return false;
	}
	
	/**
	 * Method to get substituted query terms for a given term with wildcards
	 * @return A Map containing the original query term as key and list of
	 * possible expansions as values if exist, null otherwise
	 */
	public Map<String, List<String>> getQueryTerms() {
		//TODO:IMPLEMENT THIS METHOD IFF WILDCARD BONUS ATTEMPTED
		return null;
		
	}
	
	/**
	 * Method to indicate if speel correct queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean spellCorrectSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF SPELLCHECK BONUS ATTEMPTED
		return false;
	}
	
	/**
	 * Method to get ordered "full query" substitutions for a given misspelt query
	 * @return : Ordered list of full corrections (null if none present) for the given query
	 */
	public List<String> getCorrections() {
		//TODO: IMPLEMENT THIS METHOD IFF SPELLCHECK EXECUTED
		return null;
	}
	
   /**
    * Function that executes the parsed query
    * @param query
    */
   public List<Posting> executeQuery(String query){
	   Stack<List<Posting>> valueStack = new Stack<List<Posting>>();
	   Stack<String> operatorStack = new Stack<String>();
	   termSet = new HashSet<String>();
	   String [] queryStrArr=query.split(CommonConstants.WHITESPACE);
	   if(null!=queryStrArr && queryStrArr.length>0){
		   for(String str:queryStrArr){
			   str = str.trim();
			   if(str.equals(CommonConstants.FIRST_BRACKET_OPEN)
					   || str.equals(CommonConstants.OPERATOR_AND)
					   || str.equals(CommonConstants.OPERATOR_OR)
					   || str.equals(CommonConstants.OPERATOR_NOT)){
				   operatorStack.push(str);
			   }
			   //pop from the stack until a first bracket is encountered
			   else if (str.equals(CommonConstants.FIRST_BRACKET_CLOSE)){
				   List<Posting> firstPosting = null;
				   List<Posting> secondPosting = null;
				   List<Posting> mergedPostings = null;
				   String operator = null;
				   //until and unless a closing bracket is encountered on operator stack
				   //pop two elements from value stack and one element from operator stack
				   //after combining the values with the operator, push the result into the value stack
				   while(!CommonConstants.FIRST_BRACKET_OPEN.equals(operatorStack.peek())){
					   firstPosting = valueStack.pop();
					   secondPosting = valueStack.pop();
					   operator = operatorStack.pop();

					   if(operator.equals(CommonConstants.OPERATOR_AND)){
						   mergedPostings=mergePostingsAnd(firstPosting,secondPosting);
					   }
					   else if(operator.equals(CommonConstants.OPERATOR_OR)){
						   mergedPostings=mergePostingsOr(firstPosting,secondPosting);
					   }
					   else if(operator.equals(CommonConstants.OPERATOR_NOT)){
						   mergedPostings=mergePostingsNot(firstPosting,secondPosting);
					   }
					
					   valueStack.push(mergedPostings);
				   }
				   //pop one more time to remove the closing first bracket
				   operatorStack.pop();
				   
			   }
			   // the string is a term e.g. Author:Rushdie Term:Hello,push the postings list to the stack
			   else{
				   
				   //TODO exclude the term preceeded by NOT
				   termSet.add(str);
				   String analyzedTerm=getAnalyzedTerm(str);
				   PostingWrapper postingWrapper=getPostings(indexDir,analyzedTerm,getRawIndexOfTheTerm(str));
				   valueStack.push(postingWrapper.getPostings());
			   }
		   }
		   
		   //while the operator stack is not empty, pop two elements from value stack and one element from operator stack
		   //after combining the values with the operator, push the result into the value stack 
		   while(!operatorStack.isEmpty()){
			   List<Posting> firstPosting = valueStack.pop();
			   List<Posting> secondPosting = valueStack.pop();
			   List<Posting> mergedPostings = null;
			   String operator = operatorStack.pop();

			   if(operator.equals(CommonConstants.OPERATOR_AND)){
				   mergedPostings=mergePostingsAnd(firstPosting,secondPosting);
			   }
			   else if(operator.equals(CommonConstants.OPERATOR_OR)){
				   mergedPostings=mergePostingsOr(firstPosting,secondPosting);
			   }
			   else if(operator.equals(CommonConstants.OPERATOR_NOT)){
				   mergedPostings=mergePostingsNot(firstPosting,secondPosting);
			   }
			
			   valueStack.push(mergedPostings);
		   }
	   }
	   return valueStack.pop();
   }
   
   /**
    * Method to merge the two postings list based on operators 
    * @param firstPostings - List of first postings
    * @param secondPostings - List of second postings
    * @param operator - AND
    * @return
    */
	private List<Posting> mergePostingsAnd(List<Posting> firstPostings,List<Posting> secondPostings) {
		
		List<Posting> outputPostings = new LinkedList<Posting>();
		//declare the variables to be used for merging
		Posting firstPosting;
		Posting secondPosting;
		Integer firstDocId;
		Integer secondDocId;
		//define the iterators over the two lists
		Iterator<Posting> firstIterator=firstPostings.iterator();
		Iterator<Posting> secondIterator=secondPostings.iterator();
		firstPosting = firstIterator.next();
		secondPosting = secondIterator.next();
		//merge the postings
		while(firstPosting!=null && secondPosting!=null){
			firstDocId = firstPosting.getDocId();
			secondDocId = secondPosting.getDocId();
			//if the docIds are equal ADD
			if(firstDocId.equals(secondDocId)){
				outputPostings.add(firstPosting);
				if(firstIterator.hasNext()){
					firstPosting=firstIterator.next();
				}else{
					firstPosting=null;
				}
				if(secondIterator.hasNext()){
					secondPosting=secondIterator.next();
				}
				else{
					secondPosting=null;
				}
			}
			//if the first docId is greater than the second
			else if(firstDocId<secondDocId){
					firstPosting=firstIterator.next();
			}
			//if the second docId is greater than the first
			else if(firstDocId>secondDocId){
					secondPosting=secondIterator.next();
			}
		}
		return outputPostings;
  }

   /**
    * Method to merge the two postings list based on operators 
    * @param firstPostings - List of first postings
    * @param secondPostings - List of second postings
    * @param operator - OR
    * @return
    */
	private List<Posting> mergePostingsOr(List<Posting> firstPostings,List<Posting> secondPostings) {
		
		List<Posting> outputPostings = new LinkedList<Posting>();
		//declare the variables to be used for merging
		Posting firstPosting;
		Posting secondPosting;
		Integer firstDocId;
		Integer secondDocId;
		//define the iterators over the two lists
		Iterator<Posting> firstIterator=firstPostings.iterator();
		Iterator<Posting> secondIterator=secondPostings.iterator();
		firstPosting = firstIterator.next();
		secondPosting = secondIterator.next();
		//merge the postings
		while(firstPosting!=null && secondPosting!=null){
			firstDocId = firstPosting.getDocId();
			secondDocId = secondPosting.getDocId();
			//if the docIds are equal ADD
			if(firstDocId.equals(secondDocId)){
				outputPostings.add(firstPosting);
				if(firstIterator.hasNext()){
					firstPosting=firstIterator.next();
				}else{
					firstPosting=null;
				}
				if(secondIterator.hasNext()){
					secondPosting=secondIterator.next();
				}
				else{
					secondPosting=null;
				}
			}
			//if the first docId is greater than the second ADD
			else if(firstDocId<secondDocId){
				    outputPostings.add(firstPosting);
				    if(firstIterator.hasNext()){
						firstPosting=firstIterator.next();
					}else{
						firstPosting=null;
					}
			}
			//if the second docId is greater than the first ADD
			else if(firstDocId>secondDocId){
				outputPostings.add(secondPosting);
				if(secondIterator.hasNext()){
					secondPosting=secondIterator.next();
				}
				else{
					secondPosting=null;
				}
			}
		}
		
		//add the remaining elements of the first list(if any)
		while(firstPosting!=null){
			outputPostings.add(firstPosting);
			if(firstIterator.hasNext()){
				firstPosting=firstIterator.next();
			}
			else{
				firstPosting=null;
			}
		}
		
		//add the remaining elements of the second list(if any)
	    while(secondPosting!=null){
					outputPostings.add(secondPosting);
					if(secondIterator.hasNext()){
						secondPosting=secondIterator.next();
					}
					else{
						secondPosting=null;
					}
		}
		return outputPostings;
  }
	
   /**
    * Method to merge the two postings list based on operators 
    * @param firstPostings - List of first postings
    * @param secondPostings - List of second postings
    * @param operator - NOT
    * @return
    */
	private List<Posting> mergePostingsNot(List<Posting> firstPostings,List<Posting> secondPostings) {
		
		List<Posting> outputPostings = new LinkedList<Posting>();
		//declare the variables to be used for merging
		Posting firstPosting;
		Posting secondPosting;
		Integer firstDocId;
		Integer secondDocId;
		//define the iterators over the two lists
		Iterator<Posting> firstIterator=firstPostings.iterator();
		Iterator<Posting> secondIterator=secondPostings.iterator();
		firstPosting = firstIterator.next();
		secondPosting = secondIterator.next();
		//merge the postings
		while(firstPosting!=null && secondPosting!=null){
			firstDocId = firstPosting.getDocId();
			secondDocId = secondPosting.getDocId();
			//if the docIds are equal
			if(firstDocId.equals(secondDocId)){
				if(firstIterator.hasNext()){
					firstPosting=firstIterator.next();
				}else{
					firstPosting=null;
				}
				if(secondIterator.hasNext()){
					secondPosting=secondIterator.next();
				}
				else{
					secondPosting=null;
				}
			}
			//if the first docId is greater than the second ADD
			else if(firstDocId<secondDocId){
				    outputPostings.add(firstPosting);
				    if(firstIterator.hasNext()){
						firstPosting=firstIterator.next();
					}else{
						firstPosting=null;
					}
			}
			//if the second docId is greater than the first
			else if(firstDocId>secondDocId){
				if(secondIterator.hasNext()){
					secondPosting=secondIterator.next();
				}
				else{
					secondPosting=null;
				}
			}
		}
		//add the remaining elements of the first list(if any)
		while(firstPosting!=null){
			outputPostings.add(firstPosting);
			if(firstIterator.hasNext()){
				firstPosting=firstIterator.next();
			}else{
				firstPosting=null;
			}
		}
		return outputPostings;
  }
	
	/**
	 * Method to get the analyzed term based on the index type
	 * Input should be of the type Term:hello or Term:”hello world”
	 * @param string
	 * @return
	 */
	private String getAnalyzedTerm(String string) {
		
		TermIndexDetails termIndexDetails = null;
		String rawIndexType = null;
		String rawQueryString = null;
		String analyzedStr = null;
		
		//parse the input string to get the raw index type and the raw string
		
		if(StringUtil.isNotEmpty(string)){
			String [] strArr = string.split(CommonConstants.COLON);
			/*after splitting length of the string array should be 2.
			 * First Index contain the raw index type and the second index contains the raw string
			 */
			if(strArr.length!=2){
				System.out.println("\nQuery not supported");
				return null;
			}
			else{
				rawIndexType = strArr[0];
				rawQueryString = strArr[1];
				//in case of phrase queries remove the double quotes
				rawQueryString=rawQueryString.replaceAll(CommonConstants.DOUBLE_QUOTES,"");
				termIndexDetails = CommonUtil.getTermIndexDetails(rawIndexType);
			}
		}
		
		//Analyze the query term
		if(StringUtil.isNotEmpty(rawQueryString) && termIndexDetails!=null){
			Tokenizer tknizer = new Tokenizer();
			AnalyzerFactory fact = AnalyzerFactory.getInstance();
			
			try {
				TokenStream stream = tknizer.consume(rawQueryString);
				Analyzer analyzer = fact.getAnalyzerForField(termIndexDetails.getFieldName(),
						stream);

				while (analyzer.increment()) {

				}

				stream.reset();
				analyzedStr=StringUtil.convertTokenStreamToString(stream);
			} catch (TokenizerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return analyzedStr;
	}
	
	/**
	 * Method to get the index type e.g. TERM , AUTHOR
	 * Input should be of the type Term:hello or Term:”hello world”
	 * @param string
	 * @return
	 */
	private String getRawIndexOfTheTerm(String string){
		String indexType =null;
		if(StringUtil.isNotEmpty(string)){
			String [] strArr = string.split(CommonConstants.COLON);
			if(strArr!=null && strArr.length>0)
			{
				indexType = strArr[0];
				
			}
		}
		return indexType;
	}
	
	/**
	 * Method to find the postings list of a term
	 * @param term - analyzed term
	 * @param indexType - index type where the term is to be searched
	 * @return
	 */
	private PostingWrapper getPostings(String indexDir,String term,String termType) {
		
		PostingWrapper postingWrapper = null;
		//get the index type based on the raw string index type. Term: gets coverted to IndexTypeTerm
		IndexType indexType= CommonUtil.getTermIndexType(termType);
		
		Map<String, Integer> dictionaryForIndexType=getDictionaryForIndexType(indexType);
		Map<Integer, PostingWrapper> indexMap=getInvIndexForIndexType(indexType);
        // get the term id from the dictionary
		Integer termId = (Integer) dictionaryForIndexType.get(term);
		if (termId != null) {
			// get the postings list from the index
			postingWrapper = (PostingWrapper) indexMap
					.get(termId);
		}
		return postingWrapper;
   }
	
	/**
	 * Finds the dictionary on the index type 
	 */
	private Map<String,Integer> getDictionaryForIndexType(IndexType type) {
		Map<String,Integer> dictionary = new HashMap<String,Integer>();
		switch (type) {
			case TERM:
				dictionary = termDictionary;
				break;
			case AUTHOR:
				dictionary = authorDictionary;
				break;
			case CATEGORY:
				dictionary = categoryDictionary;
				break;
			case PLACE:
				dictionary = placeDictionary;
				break;
			default:
				break;
		}
		return dictionary;
	}
	
	/**
	 * Finds the index file name based on the index type 
	 */
	private Map<Integer,PostingWrapper> getInvIndexForIndexType(IndexType type) {
		Map<Integer,PostingWrapper> index = new HashMap<Integer,PostingWrapper>();
		switch (type) {
			case TERM:
				index = termIndex;
				break;
			case AUTHOR:
				index = authorIndex;
				break;
			case CATEGORY:
				index = categoryIndex;
				break;
			case PLACE:
				index = placeIndex;
				break;
			default:
				break;
		}
		return index;
	}
	
	/**
	 * Calculate the score of the document with respect to the query and the
	 * relevance model passed. Score is calculated in term-at-a-time fashion
	 * @param postings - List of final postings
	 * @param model - Scoring Model used
	 */
	private void calculateScore(List<Posting> mergedPostings, ScoringModel model){
		
		//e.g. Term:Computer
		for(String term:termSet){
			
			String analyzedTerm=getAnalyzedTerm(term); //analyzedTerm - comput
			String termType=getRawIndexOfTheTerm(term); //termType - Term
			
			//get the index type based on the raw string index type. Term: gets converted to IndexType TERM
			IndexType indexType= CommonUtil.getTermIndexType(termType);
			
			Map<String, Integer> dictionaryForIndexType=getDictionaryForIndexType(indexType); // Term dictionary
			Map<Integer, PostingWrapper> indexMap=getInvIndexForIndexType(indexType); //Term Index
			
			//get the postings list
			PostingWrapper postingWrapper=getPostings(indexDir,analyzedTerm,termType);
			
			// get the term id from the dictionary
			Integer termId = (Integer) dictionaryForIndexType.get(analyzedTerm);
			if (termId != null) {
				// get the postings list from the index
				postingWrapper = (PostingWrapper) indexMap
						.get(termId);
				List<Posting> termPostings = postingWrapper.getPostings();
				
				//Should have avoided O(n2). But doing this in the interest of time.
				for(Posting mergedPosting:mergedPostings){
					for(Posting termPosting:termPostings){
						if(mergedPosting.equals(termPosting)){
							//compute the tf
							int termfrequency = termPosting.getFrequency();
							double tf = 1+ Math.log10(termfrequency);
							//compute the idf
							int N = docDictionary.size();
							int docFrequency = postingWrapper.getTotalFrequency();
							double idf = Math.log10(N/docFrequency);
							//compute the tf-idf score
							double tf_idf = tf*idf;
							//TODO - change the below line
							if(null==mergedPosting.getScore()){
								mergedPosting.setScore(0.0);
							}
							double score = mergedPosting.getScore() + tf_idf;
							mergedPosting.setScore(score);
							break;
						}
					}
				}
				
			}
		}
		//System.out.println("\nScore calculated");
	}
}
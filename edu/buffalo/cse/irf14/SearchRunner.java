package edu.buffalo.cse.irf14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import edu.buffalo.cse.irf14.common.DocMetaData;
import edu.buffalo.cse.irf14.common.QueryResult;
import edu.buffalo.cse.irf14.common.StringUtil;
import edu.buffalo.cse.irf14.common.TermIndexDetails;
import edu.buffalo.cse.irf14.index.DocumentIdComparator;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.Posting;
import edu.buffalo.cse.irf14.index.PostingScoreComparator;
import edu.buffalo.cse.irf14.index.PostingWrapper;
import edu.buffalo.cse.irf14.query.Query;
import edu.buffalo.cse.irf14.query.QueryParser;

/**
 * Main class to run the searcher. As before implement all TODO methods unless
 * marked for bonus
 * 
 * @author nikhillo
 * 
 */
public class SearchRunner {
	public enum ScoringModel {
		TFIDF, OKAPI
	};

	private String indexDir;
	private String corpusDir;
	private char mode;
	private PrintStream stream;

	DecimalFormat decimalFormat = new DecimalFormat("00.00000");

	private Map<Integer, DocMetaData> docDictionary;

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
	Set<String> termSet;

	/**
	 * Default (and only public) constuctor
	 * 
	 * @param indexDir
	 *            : The directory where the index resides
	 * @param corpusDir
	 *            : Directory where the (flattened) corpus resides
	 * @param mode
	 *            : Mode, one of Q or E
	 * @param stream
	 *            : Stream to write output to
	 */
	public SearchRunner(String indexDir, String corpusDir, char mode,
			PrintStream stream) {
		// TODO: IMPLEMENT THIS METHOD
		this.indexDir = indexDir;
		this.corpusDir = corpusDir;
		this.mode = mode;
		this.stream = stream;
		// load the dictionaries
		docDictionary = (Map<Integer, DocMetaData>) CommonUtil
				.readObject(indexDir + File.separatorChar
						+ CommonConstants.DOCUMENT_DICTIONARY_FILENAME);
		termDictionary = (Map<String, Integer>) CommonUtil
				.readObject(indexDir + File.separatorChar
						+ CommonConstants.TERM_DICTIONARY_FILENAME);
		authorDictionary = (Map<String, Integer>) CommonUtil
				.readObject(indexDir + File.separatorChar
						+ CommonConstants.AUTHOR_DICTIONARY_FILENAME);
		categoryDictionary = (Map<String, Integer>) CommonUtil
				.readObject(indexDir + File.separatorChar
						+ CommonConstants.CATEGORY_DICTIONARY_FILENAME);
		placeDictionary = (Map<String, Integer>) CommonUtil.readObject(indexDir
				+ File.separatorChar
				+ CommonConstants.PLACE_DICTIONARY_FILENAME);
		// load the indexes
		termIndex = (Map<Integer, PostingWrapper>) CommonUtil
				.readObject(indexDir + File.separatorChar
						+ CommonConstants.TERM_INDEX_FILENAME);
		authorIndex = (Map<Integer, PostingWrapper>) CommonUtil
				.readObject(indexDir + File.separatorChar
						+ CommonConstants.AUTHOR_INDEX_FILENAME);
		categoryIndex = (Map<Integer, PostingWrapper>) CommonUtil
				.readObject(indexDir + File.separatorChar
						+ CommonConstants.CATEGORY_INDEX_FILENAME);
		placeIndex = (Map<Integer, PostingWrapper>) CommonUtil
				.readObject(indexDir + File.separatorChar
						+ CommonConstants.PLACE_INDEX_FILENAME);

	}

	/**
	 * Method to execute given query in the Q mode
	 * 
	 * @param userQuery
	 *            : Query to be parsed and executed
	 * @param model
	 *            : Scoring Model to use for ranking results
	 */
	public void query(String userQuery, ScoringModel model) {
		// TODO: IMPLEMENT THIS METHOD
		// call queryParser to parse the query
		Query query = QueryParser.parse(userQuery, CommonConstants.OPERATOR_OR);
		String parsedQuery = query.getQuery();
		// execute the query
		long startime = System.currentTimeMillis();
		List<Posting> postings = executeQuery(parsedQuery);
		// calculate the score based on the Scoring model of each document
		// with respect to the query terms
		if (ScoringModel.TFIDF.equals(model)) {
/*			List<Posting> tempList = new ArrayList<Posting>();
			tempList.add(postings.get(0));*/
			calculateTfIdfScore(postings);
		} else if (ScoringModel.OKAPI.equals(model)) {
			calculateOkapiScore(postings);
		}
		termSet = new HashSet<String>();

		// print the execution details
		stream.println("===============================================================");
		System.out.println("===============================================================");
		long endtime = System.currentTimeMillis();
		stream.println("Query: " + userQuery);
		System.out.println("Query: " + userQuery);
		stream.println("Query time: " + ((endtime - startime)));
		System.out.println("Query time: " + ((endtime - startime)));
		stream.println("===============================================================");
		System.out.println("===============================================================");
		int rank = 1;
		if(postings.size()>0)
		{
		   Collections.sort(postings, new PostingScoreComparator());
		}
		Map<String, Integer> fileNameMap = new HashMap<String, Integer>();
		for (Posting posting : postings) {
			// handle duplicate files
			String fileName = docDictionary.get(posting.getDocId())
					.getFileName();
			if (fileNameMap.containsKey(fileName)) {
				continue;
			} else {
				fileNameMap.put(fileName, posting.getDocId());
			}
			stream.println("Result Rank: " + rank);
			System.out.println("Result Rank: " + rank);
			stream.println("Result Title: " + fileName);
			System.out.println("Result Title: " + fileName);
			stream.println("Result Snippet: "
					+ docDictionary.get(posting.getDocId()).getResultSnippet()
					+ "...");
			System.out.println("Result Snippet: "
					+ docDictionary.get(posting.getDocId()).getResultSnippet()
					+ "...");
			stream.println("Result Relevancy: " + posting.getScore());
			System.out.println("Result Relevancy: " + posting.getScore());
			rank++;
			stream.println("===============================================================");
			System.out.println("===============================================================");
		}
	}

	/**
	 * Method to execute queries in E mode
	 * 
	 * @param queryFile
	 *            : The file from which queries are to be read and executed
	 */
	public void query(File queryFile) {
		// TODO: IMPLEMENT THIS METHOD
		if (queryFile == null || !queryFile.exists()) {
			System.out.println("\nFile does not exits");
			return;
		}
		try {
			// Read the file contents into a buffer
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(queryFile));
			// final list that contains all the result outputs of the queries of
			// the input file
			List<QueryResult> queryResults = new ArrayList<QueryResult>();
			// line buffer to read a line once at a time
			String line;
			String strArr[] = null;
			List<Posting> finalPostings = new ArrayList<Posting>();
			int numResults = 0;
			int numQueries = 0;
			// find the number of queries
			line = reader.readLine();
			if (line != null) {
				strArr = line.split(CommonConstants.EQUAL_SIGN);
				try {
					numQueries = Integer.parseInt(strArr[1]);
				} catch (Exception e) {
					System.out.println("Invalid query format");
					return;
				}
			}
			while ((line = reader.readLine()) != null) {
				String queryId;
				String query = "";
				strArr = line.split(CommonConstants.COLON);
				queryId = strArr[0];
				//System.out.println("\nEvaluating query for "+queryId);
				// merge the query parts that got splitted
				for (int i = 1; i < strArr.length; i++) {
					query = query + strArr[i] + CommonConstants.COLON;
				}
				if (query.length() > 0) {
					query = query.substring(0, query.length() - 1);
				}
				// remove the second brackets from beginning and end
				query = query.replaceAll("\\{", "");
				query = query.replaceAll("\\}", "");
				// call queryParser to parse the query
				Query queryObj = QueryParser.parse(query,
						CommonConstants.OPERATOR_OR);
				String parsedQuery = queryObj.getQuery();
				finalPostings = executeQuery(parsedQuery);
				if(finalPostings==null){
					finalPostings = new ArrayList<Posting>();
				}
				if (finalPostings.size() > 0) {
					// calculate the score based on the Scoring model of each document
					//with respect to the query terms
					calculateTfIdfScore(finalPostings);
					termSet = new HashSet<String>();
					numResults++;
					QueryResult queryResult = new QueryResult();
					queryResult.setQueryId(queryId);
					queryResult.setResultPostings(finalPostings);
					queryResults.add(queryResult);
				}
				//System.out.println(finalPostings);
			}
			// Print the result to a file
			stream.println("numResults=" + numResults);
			for (QueryResult queryResult : queryResults) {

				/**
				 * map of fileIds to detect duplicates
				 */
				Map<String, Integer> fileIdMap = new HashMap<String, Integer>();
				stream.print("\n"+queryResult.getQueryId() + CommonConstants.COLON
						+ CommonConstants.WHITESPACE);
				stream.print(CommonConstants.SECOND_BRACKET_OPEN);
				List<Posting> resultPostings = queryResult.getResultPostings();
				if(resultPostings.size()>0){
				     Collections.sort(resultPostings, new PostingScoreComparator());
				}
				int counter = 1;
				for (Posting posting : resultPostings) {
					// print only the first 10 ordered results
					if (counter > 10) {
						break;
					}
					DocMetaData docMetaData = docDictionary.get(posting
							.getDocId());
					String fileId = docMetaData.getFileName();
					if (fileIdMap.containsKey(fileId)) {
						continue;
					} else {
						fileIdMap.put(fileId, posting.getDocId());
					}
					Double score = posting.getScore();
					stream.print(fileId + CommonConstants.HASH + score);
					//if (counter < resultPostings.size() && counter < 10) {
					if (counter < resultPostings.size()) {
						stream.print(CommonConstants.COMMA);
					}
					counter++;
				}
				stream.println(CommonConstants.SECOND_BRACKET_CLOSE);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * General cleanup method
	 */
	public void close() {
		// TODO : IMPLEMENT THIS METHOD
	}

	/**
	 * Method to indicate if wildcard queries are supported
	 * 
	 * @return true if supported, false otherwise
	 */
	public static boolean wildcardSupported() {
		// TODO: CHANGE THIS TO TRUE ONLY IF WILDCARD BONUS ATTEMPTED
		return false;
	}

	/**
	 * Method to get substituted query terms for a given term with wildcards
	 * 
	 * @return A Map containing the original query term as key and list of
	 *         possible expansions as values if exist, null otherwise
	 */
	public Map<String, List<String>> getQueryTerms() {
		// TODO:IMPLEMENT THIS METHOD IFF WILDCARD BONUS ATTEMPTED
		return null;

	}

	/**
	 * Method to indicate if speel correct queries are supported
	 * 
	 * @return true if supported, false otherwise
	 */
	public static boolean spellCorrectSupported() {
		// TODO: CHANGE THIS TO TRUE ONLY IF SPELLCHECK BONUS ATTEMPTED
		return false;
	}

	/**
	 * Method to get ordered "full query" substitutions for a given misspelt
	 * query
	 * 
	 * @return : Ordered list of full corrections (null if none present) for the
	 *         given query
	 */
	public List<String> getCorrections() {
		// TODO: IMPLEMENT THIS METHOD IFF SPELLCHECK EXECUTED
		return null;
	}

	/**
	 * Function that executes the parsed query
	 * 
	 * @param query
	 */
	public List<Posting> executeQuery(String query) {
		Stack<List<Posting>> valueStack = new Stack<List<Posting>>();
		Stack<String> operatorStack = new Stack<String>();
		termSet = new HashSet<String>();
		String[] queryStrArr = query.split(CommonConstants.WHITESPACE);
		if (null != queryStrArr && queryStrArr.length > 0) {
			for (int i = 0; i < queryStrArr.length; i++) {
				String str = queryStrArr[i];

				// handling of phrase queries
				if (str.contains(CommonConstants.DOUBLE_QUOTES)) {
					str = queryStrArr[i] + CommonConstants.WHITESPACE+queryStrArr[i + 1];
					queryStrArr[i + 1] = "";
				}

				if (str.equals(CommonConstants.FIRST_BRACKET_OPEN)
						|| str.equals(CommonConstants.OPERATOR_AND)
						|| str.equals(CommonConstants.OPERATOR_OR)
						|| str.equals(CommonConstants.OPERATOR_NOT)) {
					operatorStack.push(str);
				}
				// pop from the stack until a first bracket is encountered
				else if (str.equals(CommonConstants.FIRST_BRACKET_CLOSE)) {
					List<Posting> firstPosting = null;
					List<Posting> secondPosting = null;
					List<Posting> mergedPostings = null;
					String operator = null;
					// until and unless a closing bracket is encountered on
					// operator stack
					// pop two elements from value stack and one element from
					// operator stack
					// after combining the values with the operator, push the
					// result into the value stack
					while (!CommonConstants.FIRST_BRACKET_OPEN
							.equals(operatorStack.peek())) {
						firstPosting = valueStack.pop();
						secondPosting = valueStack.pop();
						operator = operatorStack.pop();

						if (operator.equals(CommonConstants.OPERATOR_AND)) {
							mergedPostings = mergePostingsAnd(firstPosting,
									secondPosting);
						} else if (operator.equals(CommonConstants.OPERATOR_OR)) {
							mergedPostings = mergePostingsOr(firstPosting,
									secondPosting);
						} else if (operator
								.equals(CommonConstants.OPERATOR_NOT)) {
							mergedPostings = mergePostingsNot(firstPosting,
									secondPosting);
						}

						valueStack.push(mergedPostings);
					}
					// pop one more time to remove the closing first bracket
					operatorStack.pop();

				}
				// the string is a term e.g. Author:Rushdie Term:Hello,push the
				// postings list to the stack
				else if (StringUtil.isNotEmpty(str)) {
					List<Posting> postings = null;
					if (str.contains(CommonConstants.DOUBLE_QUOTES)) {
						postings = findPostingsPhraseQueries(str);
					} else {
						// TODO exclude the term preceeded by NOT
						termSet.add(str);
						String analyzedTerm = getAnalyzedTerm(str);
						PostingWrapper postingWrapper = getPostings(indexDir,
								analyzedTerm, getRawIndexOfTheTerm(str));
						if (postingWrapper == null) {
							postingWrapper = new PostingWrapper();
						}
						postings = postingWrapper.getPostings();
					}
					valueStack.push(postings);
				}
			}

			// while the operator stack is not empty, pop two elements from
			// value stack and one element from operator stack
			// after combining the values with the operator, push the result
			// into the value stack
			while (!operatorStack.isEmpty()) {
				List<Posting> firstPosting = valueStack.pop();
				List<Posting> secondPosting = valueStack.pop();
				List<Posting> mergedPostings = null;
				String operator = operatorStack.pop();

				if (operator.equals(CommonConstants.OPERATOR_AND)) {
					mergedPostings = mergePostingsAnd(firstPosting,
							secondPosting);
				} else if (operator.equals(CommonConstants.OPERATOR_OR)) {
					mergedPostings = mergePostingsOr(firstPosting,
							secondPosting);
				} else if (operator.equals(CommonConstants.OPERATOR_NOT)) {
					mergedPostings = mergePostingsNot(firstPosting,
							secondPosting);
				}

				valueStack.push(mergedPostings);
			}
		}
		return valueStack.pop();
	}

	/**
	 * Method to merge the two postings list based on operators
	 * 
	 * @param firstPostings
	 *            - List of first postings
	 * @param secondPostings
	 *            - List of second postings
	 * @param operator
	 *            - AND
	 * @return
	 */
	private List<Posting> mergePostingsAnd(List<Posting> firstPostings,
			List<Posting> secondPostings) {

		List<Posting> outputPostings = new ArrayList<Posting>();
		if(null!=firstPostings && null!=secondPostings){
			for (Posting posting : firstPostings) {
				if (secondPostings.contains(posting)) {
					outputPostings.add(posting);
				}
			}
		}
		Collections.sort(outputPostings, new DocumentIdComparator());
		return outputPostings;
	}

	/**
	 * Method to merge the two postings list based on operators
	 * 
	 * @param firstPostings
	 *            - List of first postings
	 * @param secondPostings
	 *            - List of second postings
	 * @param operator
	 *            - OR
	 * @return
	 */
	private List<Posting> mergePostingsOr(List<Posting> firstPostings,
			List<Posting> secondPostings) {

		List<Posting> outputPostings = new ArrayList<Posting>();
		if(null!=firstPostings){
		   outputPostings.addAll(firstPostings);
		}
		if(secondPostings!=null){
			for (Posting posting : secondPostings) {
				if (!outputPostings.contains(posting)) {
					outputPostings.add(posting);
				}
			}
		}
		Collections.sort(outputPostings, new DocumentIdComparator());
		return outputPostings;
	}

	/**
	 * Method to merge the two postings list based on operators
	 * 
	 * @param firstPostings
	 *            - List of first postings
	 * @param secondPostings
	 *            - List of second postings
	 * @param operator
	 *            - NOT
	 * @return
	 */
	private List<Posting> mergePostingsNot(List<Posting> firstPostings,
			List<Posting> secondPostings) {

		List<Posting> outputPostings = new ArrayList<Posting>();
		for (Posting posting : firstPostings) {
			if (!secondPostings.contains(posting)) {
				outputPostings.add(posting);
			}
		}
		Collections.sort(outputPostings, new DocumentIdComparator());
		return outputPostings;
	}

	/**
	 * Method to get the analyzed term based on the index type Input should be
	 * of the type Term:hello or Term:â€�hello worldâ€�
	 * 
	 * @param string
	 * @return
	 */
	private String getAnalyzedTerm(String string) {

		TermIndexDetails termIndexDetails = null;
		String rawIndexType = null;
		String rawQueryString = null;
		String analyzedStr = null;

		// parse the input string to get the raw index type and the raw string

		if (StringUtil.isNotEmpty(string)) {
			String[] strArr = string.split(CommonConstants.COLON);
			/*
			 * after splitting length of the string array should be 2. First
			 * Index contain the raw index type and the second index contains
			 * the raw string
			 */
			if (strArr.length != 2) {
				System.out.println("\nQuery not supported");
				return null;
			} else {
				rawIndexType = strArr[0];
				rawQueryString = strArr[1];
				// in case of phrase queries remove the double quotes
				rawQueryString = rawQueryString.replaceAll(
						CommonConstants.DOUBLE_QUOTES, "");
				termIndexDetails = CommonUtil.getTermIndexDetails(rawIndexType);
			}
		}

		// Analyze the query term
		if (StringUtil.isNotEmpty(rawQueryString) && termIndexDetails != null) {
			Tokenizer tknizer = new Tokenizer();
			AnalyzerFactory fact = AnalyzerFactory.getInstance();

			try {
				TokenStream stream = tknizer.consume(rawQueryString);
				Analyzer analyzer = fact.getAnalyzerForField(
						termIndexDetails.getFieldName(), stream);

				while (analyzer.increment()) {

				}

				stream.reset();
				analyzedStr = StringUtil.convertTokenStreamToString(stream);
			} catch (TokenizerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return analyzedStr;
	}

	/**
	 * Method to get the index type e.g. TERM , AUTHOR Input should be of the
	 * type Term:hello or Term:â€�hello worldâ€�
	 * 
	 * @param string
	 * @return
	 */
	private String getRawIndexOfTheTerm(String string) {
		String indexType = null;
		if (StringUtil.isNotEmpty(string)) {
			String[] strArr = string.split(CommonConstants.COLON);
			if (strArr != null && strArr.length > 0) {
				indexType = strArr[0];

			}
		}
		return indexType;
	}

	/**
	 * Method to find the postings list of a term
	 * 
	 * @param term
	 *            - analyzed term
	 * @param indexType
	 *            - index type where the term is to be searched
	 * @return
	 */
	private PostingWrapper getPostings(String indexDir, String term,
			String termType) {

		PostingWrapper postingWrapper = null;
		// get the index type based on the raw string index type. Term: gets
		// coverted to IndexTypeTerm
		IndexType indexType = CommonUtil.getTermIndexType(termType);

		Map<String, Integer> dictionaryForIndexType = getDictionaryForIndexType(indexType);
		Map<Integer, PostingWrapper> indexMap = getInvIndexForIndexType(indexType);
		// get the term id from the dictionary
		Integer termId = (Integer) dictionaryForIndexType.get(term);
		if (termId != null) {
			// get the postings list from the index
			postingWrapper = (PostingWrapper) indexMap.get(termId);
		}
		return postingWrapper;
	}

	/**
	 * Finds the dictionary on the index type
	 */
	private Map<String, Integer> getDictionaryForIndexType(IndexType type) {
		Map<String, Integer> dictionary = new HashMap<String, Integer>();
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
	private Map<Integer, PostingWrapper> getInvIndexForIndexType(IndexType type) {
		Map<Integer, PostingWrapper> index = new HashMap<Integer, PostingWrapper>();
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
	 * 
	 * @param postings
	 *            - List of final postings
	 * @param model
	 *            - Scoring Model used
	 */
	private void calculateTfIdfScore(List<Posting> mergedPostings) {

		// e.g. Term:Computer
		for (String term : termSet) {

			String analyzedTerm = getAnalyzedTerm(term); // analyzedTerm -comput
			String termType = getRawIndexOfTheTerm(term); // termType - Term

			// get the index type based on the raw string index type. Term: gets
			// converted to IndexType TERM
			IndexType indexType = CommonUtil.getTermIndexType(termType);

			Map<String, Integer> dictionaryForIndexType = getDictionaryForIndexType(indexType); // Term dictionary
			Map<Integer, PostingWrapper> indexMap = getInvIndexForIndexType(indexType); // Term Index

			// get the postings list
			PostingWrapper postingWrapper = getPostings(indexDir, analyzedTerm,
					termType);

			// get the term id from the dictionary
			Integer termId = (Integer) dictionaryForIndexType.get(analyzedTerm);
			if (termId != null) {
				// get the postings list from the index
				postingWrapper = (PostingWrapper) indexMap.get(termId);
				List<Posting> termPostings = postingWrapper.getPostings();
                int docFrequency = termPostings.size();
				
				// Should have avoided O(n2). But doing this in the interest of
				// time.
				for (Posting mergedPosting : mergedPostings) {
					for (Posting termPosting : termPostings) {
						if (mergedPosting.equals(termPosting)) {
							// compute the tf
							int termfrequency = termPosting.getFrequency();
							double tf = 1 + Math.log10(termfrequency);
							// compute the idf
							int N = docDictionary.size();
							//int docFrequency = postingWrapper.getTotalFrequency();
							double idf = Math.log10(N / docFrequency);
							// compute the tf-idf score
							double tf_idf = tf * idf;
							// TODO - change the below line
							if (null == mergedPosting.getScore()) {
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
		
		
		// apply the formatting of score and normalization

		for (Posting posting : mergedPostings) {
			DocMetaData docMetaData = docDictionary.get(posting
					.getDocId());
			if (posting.getScore() == null) {
				posting.setScore(0.0);
			}
			double score = posting.getScore();
			//System.out.println("Before normalizing Score is "+score);
			score = (score * 100) / docMetaData.getLength();
			//System.out.println("After normalizing Score is "+score);
			// format the score
			score = Double.parseDouble(decimalFormat.format(score));
			if(score>1){
				score = 1;
			}
			posting.setScore(score);
			// docDictionary.get(posting.getDocId());
		}
		//System.out.println("\nScore calculated");
	}

	/**
	 * Calculate the score of the document with respect to the query and the
	 * relevance model passed. OKAPI model is used for computation.Score is
	 * calculated in term-at-a-time fashion
	 * 
	 * @param postings
	 *            - List of final postings
	 * @param model
	 *            - Scoring Model used
	 */
	private void calculateOkapiScore(List<Posting> mergedPostings) {

		double k1 = 1.2;// tuning parameter for document term frequency
		double b = .75; // tuning parameter for document length
		// compute the average document length
		double lavg = calcAvgDocLength();

		// e.g. Term:Computer
		for (String term : termSet) {

			String analyzedTerm = getAnalyzedTerm(term); // analyzedTerm -
															// comput
			String termType = getRawIndexOfTheTerm(term); // termType - Term

			// get the index type based on the raw string index type. Term: gets
			// converted to IndexType TERM
			IndexType indexType = CommonUtil.getTermIndexType(termType);

			Map<String, Integer> dictionaryForIndexType = getDictionaryForIndexType(indexType); // Term
																								// dictionary
			Map<Integer, PostingWrapper> indexMap = getInvIndexForIndexType(indexType); // Term
																						// Index

			// get the postings list
			PostingWrapper postingWrapper = getPostings(indexDir, analyzedTerm,
					termType);

			// get the term id from the dictionary
			Integer termId = (Integer) dictionaryForIndexType.get(analyzedTerm);
			if (termId != null) {
				// get the postings list from the index
				postingWrapper = (PostingWrapper) indexMap.get(termId);
				List<Posting> termPostings = postingWrapper.getPostings();

				// Should have avoided O(n2). But doing this in the interest of
				// time.
				for (Posting mergedPosting : mergedPostings) {
					for (Posting termPosting : termPostings) {
						if (mergedPosting.equals(termPosting)) {
							// compute the tf
							int tf = termPosting.getFrequency();
							// compute the idf
							int N = docDictionary.size();
							int docFrequency = postingWrapper
									.getTotalFrequency();
							double idf = Math.log10(N / docFrequency);
							long ld = docDictionary.get(
									mergedPosting.getDocId()).getLength();
							// compute the variant
							double var = (k1 + 1) / tf
									/ (k1 * ((1 - b) + b * (ld / lavg)) + tf);
							// TODO - change the below line
							if (null == mergedPosting.getScore()) {
								mergedPosting.setScore(0.0);
							}
							double score = mergedPosting.getScore()
									+ (idf * var);
							mergedPosting.setScore(score);
							break;
						}
					}
				}

				// apply the formatting of score and normalization

				for (Posting posting : mergedPostings) {
					if (posting.getScore() == null) {
						posting.setScore(0.0);
					}
					double score = posting.getScore();
					score = score / 10;
					// format the score
					score = Double.parseDouble(decimalFormat.format(score));
					if(score>1){
						score = 1;
					}
					posting.setScore(score);
				}

			}
		}
		// System.out.println("\nScore calculated");
	}

	/**
	 * Returns the average document length in the corpus
	 * 
	 * @return
	 */
	private double calcAvgDocLength() {
		int numDocs = docDictionary.size();
		long totalLen = 0;
		double lavg = 0;
		Set<Integer> docIds = docDictionary.keySet();
		for (Integer docId : docIds) {
			DocMetaData docMetadata = docDictionary.get(docId);
			totalLen = totalLen + docMetadata.getLength();
		}
		lavg = totalLen / numDocs;
		return lavg;
	}

	/**
	 * Method to return the final postings list of phrase queries
	 * 
	 * @param str
	 * @return
	 */
	List<Posting> findPostingsPhraseQueries(String str) {
		List<Posting> finalPostings = new ArrayList<Posting>();
		try {
			str = str.replaceAll(CommonConstants.DOUBLE_QUOTES, "");// Term:"Hello World"->Term:Hello World
			String[] strArr = str.split(CommonConstants.WHITESPACE);
			String secondTermRaw = strArr[1];// World
			String[] strArr2 = strArr[0].split(CommonConstants.COLON);
			String firstTermRaw = strArr2[1];// Hello
			String indexRaw = strArr2[0];// Term

			// For Hello
			termSet.add(indexRaw+CommonConstants.COLON+secondTermRaw);
			String analyzedTerm = getAnalyzedTerm(indexRaw+CommonConstants.COLON+firstTermRaw);
			PostingWrapper postingWrapper = getPostings(indexDir, analyzedTerm,
					indexRaw);
			if (postingWrapper == null) {
				postingWrapper = new PostingWrapper();
			}
			List<Posting> firstPostings = new ArrayList<Posting>();
			firstPostings = postingWrapper.getPostings();

			// For World
			termSet.add(indexRaw+CommonConstants.COLON+secondTermRaw);
			analyzedTerm = getAnalyzedTerm(indexRaw+CommonConstants.COLON+secondTermRaw);
			postingWrapper = getPostings(indexDir, analyzedTerm, indexRaw);
			List<Posting> secondPostings = new ArrayList<Posting>();
			secondPostings = postingWrapper.getPostings();
			
			//merge the postings of hello and world based on their positions
			if (firstPostings != null && secondPostings != null
					&& firstPostings.size() > 0 && secondPostings.size() > 0) {
				finalPostings = mergePostingsPhraseQueries(firstPostings,secondPostings);
			}
		} catch (StackOverflowError e) {
			System.out.println("\nException in parsing phrase query ");
		}
		return finalPostings;
	}
	
   /**
    * Method to merge the two postings list based on operators 
    * @param firstPostings - List of first postings
    * @param secondPostings - List of second postings
    * @param operator - AND
    * @return
    */
	private List<Posting> mergePostingsPhraseQueries(List<Posting> firstPostings,List<Posting> secondPostings) {
		
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
				
				List<Integer> firstPositions = firstPosting.getPositions();
				List<Integer> secondPositions = secondPosting.getPositions();
				//add the doc in the outputlist if the postion of the second term 
				//is greater than 1 than the first term in the doc
				for(Integer position:secondPositions){
					if(firstPositions.contains(position-1)){
						outputPostings.add(firstPosting);
						break;
					}
				}
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
}

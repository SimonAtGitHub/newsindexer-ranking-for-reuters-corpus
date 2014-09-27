/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.buffalo.cse.irf14.common.CommonConstants;
import edu.buffalo.cse.irf14.common.CommonUtil;

/**
 * @author nikhillo Class that emulates reading data back from a written index
 */
public class IndexReader {

	/**
	 * The root directory from which the index is to be read.
	 */
	String indexDir;

	/**
	 * Type of the index
	 */
	IndexType type;

	/**
	 * Filename of the dictionary for the given type
	 */
	String dictionaryFileName;
	/**
	 * Filename of the index for the given type
	 */
	String indexFileName;

	/**
	 * Map in the dictionary for the index type
	 */
	Map<String, Integer> dictionaryForIndexType = null;
	/**
	 * Map in the index for the type
	 */
	Map<Integer, PostingWrapper> indexMap = null;
	/**
	 * Document dictionary
	 *
	 */
	Map<Integer, String> docDictionary = null;

	/**
	 * Default constructor
	 * 
	 * @param indexDir
	 *            : The root directory from which the index is to be read. This
	 *            will be exactly the same directory as passed on IndexWriter.
	 *            In case you make subdirectories etc., you will have to handle
	 *            it accordingly.
	 * @param type
	 *            The {@link IndexType} to read from
	 */
	public IndexReader(String indexDir, IndexType type) {
		// TODO
		this.indexDir = indexDir;
		this.type = type;

		String dictionaryFilePath = CommonUtil
				.getDictionaryPath(indexDir, type);

		String indexFilePath = CommonUtil.getIndexPath(indexDir, type);

		String docDictionaryFilePath = indexDir + File.separatorChar
				+ CommonConstants.DOCUMENT_DICTIONARY_FILENAME;

		dictionaryForIndexType = (Map<String, Integer>) readObject(dictionaryFilePath);

		indexMap = (Map<Integer, PostingWrapper>) readObject(indexFilePath);

		docDictionary = (Map<Integer, String>) readObject(docDictionaryFilePath);

	}

	/**
	 * Get total number of terms from the "key" dictionary associated with this
	 * index. A postings list is always created against the "key" dictionary
	 * 
	 * @return The total number of terms
	 */
	public int getTotalKeyTerms() {
		// TODO : YOU MUST IMPLEMENT THIS
		int size = dictionaryForIndexType.size();
		return size;
	}

	/**
	 * Get total number of terms from the "value" dictionary associated with
	 * this index. A postings list is always created with the "value" dictionary
	 * 
	 * @return The total number of terms
	 */
	public int getTotalValueTerms() {
		// TODO: YOU MUST IMPLEMENT THIS
		int size = docDictionary.size();
		return size;
	}

	/**
	 * Method to get the postings for a given term. You can assume that the raw
	 * string that is used to query would be passed through the same Analyzer as
	 * the original field would have been.
	 * 
	 * @param term
	 *            : The "analyzed" term to get postings for
	 * @return A Map containing the corresponding fileid as the key and the
	 *         number of occurrences as values if the given term was found, null
	 *         otherwise.
	 */
	public Map<String, Integer> getPostings(String term) {
		// TODO:YOU MUST IMPLEMENT THIS
		// prepare the map to be returned for postings
		Map<String, Integer> postingsMap = null;
		// get the term id from the dictionary
		Integer termId = (Integer) dictionaryForIndexType.get(term);
		if (termId != null) {
			// get the postings list from the index
			PostingWrapper postingWrapper = (PostingWrapper) indexMap
					.get(termId);
			List<Posting> postings = postingWrapper.getPostings();

			if (postings != null && postings.size() > 0) {
				postingsMap = new HashMap<String, Integer>();
				for (Posting posting : postings) {
					String fileId = docDictionary.get(posting.getDocId());
					postingsMap.put(fileId, posting.getFrequency());
				}
			}
		}

		return postingsMap;
	}

	/**
	 * Method to get the top k terms from the index in terms of the total number
	 * of occurrences.
	 * 
	 * @param k
	 *            : The number of terms to fetch
	 * @return : An ordered list of results. Must be <=k fr valid k values null
	 *         for invalid k values
	 */
	public List<String> getTopK(int k) {
		// TODO YOU MUST IMPLEMENT THIS
		List<String> topTerms = null;
		if (k > 0) {
			Map<Integer, PostingWrapper> sortedMap = CommonUtil
					.sortByTotalFrequency(indexMap);
			int counter = 1;
			topTerms = new ArrayList<String>();

			for (Map.Entry<Integer, PostingWrapper> entry : sortedMap
					.entrySet()) {
				if (counter == k + 1) {
					break;
				}
				// get the term key from the index
				Integer termKey = entry.getKey();
				// get the term value from the dictionary
				// Inefficient but doing it as we are not maintaing a reverse
				// dictionary
				for (Entry<String, Integer> entry1 : dictionaryForIndexType
						.entrySet()) {
					if (termKey.equals(entry1.getValue())) {
						String str = entry1.getKey();
						topTerms.add(str);
						break;
					}
				}
				counter++;
			}
		}
		return topTerms;
	}

	/**
	 * Method to implement a simple boolean AND query on the given index
	 * 
	 * @param terms
	 *            The ordered set of terms to AND, similar to getPostings() the
	 *            terms would be passed through the necessary Analyzer.
	 * @return A Map (if all terms are found) containing FileId as the key and
	 *         number of occurrences as the value, the number of occurrences
	 *         would be the sum of occurrences for each participating term.
	 *         return null if the given term list returns no results BONUS ONLY
	 */
	public Map<String, Integer> query(String... terms) {
		// TODO : BONUS ONLY
		// First Get the postings for each of the term
		if (terms.length > 0) {
			ArrayList<Map<String, Integer>> postingsList = new ArrayList<Map<String, Integer>>();
			int min = 0;
			for (int i = 0; i < terms.length; i++) {
				Map<String, Integer> postings = getPostings(terms[i]);
				postingsList.add(postings);
				// Find the minimum since result of boolean 'and' query can
				// never be more than the size of the smallest of each
				// Constituent.
				if (postings.size() < min) {
					min = i;
				}
			}

			// Now traverse over the elements of postings for each term with
			// minimum number of
			// posting and then fetch results.
			Map<String, Integer> postingToBeTraversed = postingsList.get(min);
			Map<String, Integer> queryResult = new TreeMap<String, Integer>();
			for (String key : postingToBeTraversed.keySet()) {
				// Get the termFrequency for each fileID
				int termFrequency = postingToBeTraversed.get(key);
				// Check if others also contain the same key, if they don't
				// remove it. Key we are talking about here is the FileId
				boolean exists = true;
				for (Map<String, Integer> map : postingsList) {
					if (!map.equals(postingToBeTraversed)) {
						// If any map doesn't contain the key
						if (!map.containsKey(key)) {
							exists = false;
						} else {
							termFrequency = termFrequency + map.get(key);
						}
					}
				}
				// It it exists in all maps, add the key and value to the
				// queryReuslt
				if (exists) {
					queryResult.put(key, termFrequency);
				}
			}
			if (queryResult.size() < 1) {
				return null;
			}
			return queryResult;
		}
		return null;
	}

	/**
	 * Reads a object given by the path
	 * 
	 * @param filePath
	 * @return
	 */
	public Object readObject(String filePath) {
		Object retObject = null;
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			retObject = in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			retObject = null;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			retObject = null;
		}
		return retObject;
	}
}

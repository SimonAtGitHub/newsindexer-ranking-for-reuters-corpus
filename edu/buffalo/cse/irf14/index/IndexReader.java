/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

import edu.buffalo.cse.irf14.common.CommonUtil;

/**
 * @author nikhillo
 * Class that emulates reading data back from a written index
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
	Map<String,Integer> dictionaryMap=null;
	/**
	 * Map in the index for the type
	 */
	Map<String,Integer> indexMap = null;
	/**
	 * Default constructor
	 * @param indexDir : The root directory from which the index is to be read.
	 * This will be exactly the same directory as passed on IndexWriter. In case 
	 * you make subdirectories etc., you will have to handle it accordingly.
	 * @param type The {@link IndexType} to read from
	 */
	public IndexReader(String indexDir, IndexType type) {
		//TODO
		this.indexDir = indexDir;
		this.type = type;
		
		String dictionaryFilePath = CommonUtil.getDictionaryPath(indexDir, type);
		
		String indexFilePath = CommonUtil.getIndexPath(indexDir, type);
		
		dictionaryMap = (Map<String,Integer>)readObject(dictionaryFilePath);
		
		indexMap = (Map<String,Integer>)readObject(indexFilePath);
		
	}
	
	/**
	 * Get total number of terms from the "key" dictionary associated with this 
	 * index. A postings list is always created against the "key" dictionary
	 * @return The total number of terms
	 */
	public int getTotalKeyTerms() {
		//TODO : YOU MUST IMPLEMENT THIS
		int size = dictionaryMap.size();
		return size;
	}
	
	/**
	 * Get total number of terms from the "value" dictionary associated with this 
	 * index. A postings list is always created with the "value" dictionary
	 * @return The total number of terms
	 */
	public int getTotalValueTerms() {
		//TODO: YOU MUST IMPLEMENT THIS
		int size = indexMap.size();
		return size;
	}
	
	/**
	 * Method to get the postings for a given term. You can assume that
	 * the raw string that is used to query would be passed through the same
	 * Analyzer as the original field would have been.
	 * @param term : The "analyzed" term to get postings for
	 * @return A Map containing the corresponding fileid as the key and the 
	 * number of occurrences as values if the given term was found, null otherwise.
	 */
	public Map<String, Integer> getPostings(String term) {
		//TODO:YOU MUST IMPLEMENT THIS
		return null;
	}
	
	/**
	 * Method to get the top k terms from the index in terms of the total number
	 * of occurrences.
	 * @param k : The number of terms to fetch
	 * @return : An ordered list of results. Must be <=k fr valid k values
	 * null for invalid k values
	 */
	public List<String> getTopK(int k) {
		//TODO YOU MUST IMPLEMENT THIS
		return null;
	}
	
	/**
	 * Method to implement a simple boolean AND query on the given index
	 * @param terms The ordered set of terms to AND, similar to getPostings()
	 * the terms would be passed through the necessary Analyzer.
	 * @return A Map (if all terms are found) containing FileId as the key 
	 * and number of occurrences as the value, the number of occurrences 
	 * would be the sum of occurrences for each participating term. return null
	 * if the given term list returns no results
	 * BONUS ONLY
	 */
	public Map<String, Integer> query(String...terms) {
		//TODO : BONUS ONLY
		return null;
	}
	
	/**
	 * Reads a object given by the path
	 * @param filePath
	 * @return
	 */
	public Object readObject(String filePath){
		  Object retObject=null;
	      try
	      {
	         FileInputStream fileIn = new FileInputStream(filePath);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         retObject = in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         retObject=null;
	      }catch(ClassNotFoundException c)
	      {
	         c.printStackTrace();
	         retObject=null;
	      }
	      return retObject;
	}
}

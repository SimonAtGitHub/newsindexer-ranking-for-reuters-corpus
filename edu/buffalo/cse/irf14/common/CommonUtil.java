package edu.buffalo.cse.irf14.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.index.AuthorDictionary;
import edu.buffalo.cse.irf14.index.AuthorIndex;
import edu.buffalo.cse.irf14.index.CategoryDictionary;
import edu.buffalo.cse.irf14.index.CategoryIndex;
import edu.buffalo.cse.irf14.index.DocumentDictionary;
import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.NewsDictionary;
import edu.buffalo.cse.irf14.index.NewsIndex;
import edu.buffalo.cse.irf14.index.PlaceDictionary;
import edu.buffalo.cse.irf14.index.PlaceIndex;
import edu.buffalo.cse.irf14.index.Posting;
import edu.buffalo.cse.irf14.index.PostingWrapper;
import edu.buffalo.cse.irf14.index.TermDictionary;
import edu.buffalo.cse.irf14.index.TermIndex;

public class CommonUtil {
	/**
	 * returns the corresponding index based on field
	 * 
	 * @param fieldName
	 * @return
	 */
	public static NewsIndex getIndexByType(FieldNames fieldName) {
		NewsIndex newIndex = null;
		switch (fieldName) {
		case CATEGORY:
			newIndex = CategoryIndex.getInstance();
			break;
		case TITLE:
			newIndex = TermIndex.getInstance();
			break;
		case AUTHOR:
			newIndex = AuthorIndex.getInstance();
			break;
		case AUTHORORG:
			newIndex = AuthorIndex.getInstance();
			break;
		case PLACE:
			newIndex = PlaceIndex.getInstance();
			break;
		case CONTENT:
			newIndex = TermIndex.getInstance();
			break;
		case NEWSDATE:
			newIndex = TermIndex.getInstance();
			break;
		default:
			break;
		}
		return newIndex;
	}

	/**
	 * returns the corresponding dictionary object based on field
	 * 
	 * @param fieldName
	 * @return
	 */
	public static NewsDictionary getDictionaryByType(FieldNames fieldName) {
		NewsDictionary newsDictionary = null;
		switch (fieldName) {
		case CATEGORY:
			newsDictionary = CategoryDictionary.getInstance();
			break;
		case TITLE:
			newsDictionary = TermDictionary.getInstance();
			break;
		case AUTHOR:
			newsDictionary = AuthorDictionary.getInstance();
			break;
		case AUTHORORG:
			newsDictionary = AuthorDictionary.getInstance();
			break;
		case PLACE:
			newsDictionary = PlaceDictionary.getInstance();
			break;
		case CONTENT:
			newsDictionary = TermDictionary.getInstance();
			break;
		case NEWSDATE:
			newsDictionary = TermDictionary.getInstance();
			break;
		default:
			break;
		}
		return newsDictionary;
	}

	/**
	 * Finds the dictionary file name based on the index type Returns the
	 * dictionary path based on the type
	 */
	public static String getDictionaryPath(String indexDir, IndexType type) {
		String filePath = null;
		String fileName = null;
		switch (type) {
		case TERM:
			fileName = CommonConstants.TERM_DICTIONARY_FILENAME;
			break;
		case AUTHOR:
			fileName = CommonConstants.AUTHOR_DICTIONARY_FILENAME;
			break;
		case CATEGORY:
			fileName = CommonConstants.CATEGORY_DICTIONARY_FILENAME;
			break;
		case PLACE:
			fileName = CommonConstants.PLACE_DICTIONARY_FILENAME;
			break;
		default:
			break;
		}
		if (fileName != null) {
			filePath = indexDir + File.separatorChar + fileName;
		}
		return filePath;
	}

	/**
	 * Finds the index file name based on the index type Returns the dictionary
	 * path based on the type
	 */
	public static String getIndexPath(String indexDir, IndexType type) {
		String filePath = null;
		String fileName = null;
		switch (type) {
		case TERM:
			fileName = CommonConstants.TERM_INDEX_FILENAME;
			break;
		case AUTHOR:
			fileName = CommonConstants.AUTHOR_INDEX_FILENAME;
			break;
		case CATEGORY:
			fileName = CommonConstants.CATEGORY_INDEX_FILENAME;
			break;
		case PLACE:
			fileName = CommonConstants.PLACE_INDEX_FILENAME;
			break;
		default:
			break;
		}
		if (fileName != null) {
			filePath = indexDir + File.separatorChar + fileName;
		}
		return filePath;
	}
	
	
	/**
	 * Finds the index file name based on the index type
	 */
	public static String getDictionaryPath(IndexType type) {
		String fileName = null;
		switch (type) {
				case TERM:
					fileName = CommonConstants.TERM_DICTIONARY_FILENAME;
					break;
				case AUTHOR:
					fileName = CommonConstants.AUTHOR_DICTIONARY_FILENAME;
					break;
				case CATEGORY:
					fileName = CommonConstants.CATEGORY_DICTIONARY_FILENAME;
					break;
				case PLACE:
					fileName = CommonConstants.PLACE_DICTIONARY_FILENAME;
					break;
				default:
			        break;
		}
		return fileName;
	}

	/**
	 * Finds the index file name based on the index type 
	 */
	public static String getIndexPath(IndexType type) {
		String fileName = null;
		switch (type) {
			case TERM:
				fileName = CommonConstants.TERM_INDEX_FILENAME;
				break;
			case AUTHOR:
				fileName = CommonConstants.AUTHOR_INDEX_FILENAME;
				break;
			case CATEGORY:
				fileName = CommonConstants.CATEGORY_INDEX_FILENAME;
				break;
			case PLACE:
				fileName = CommonConstants.PLACE_INDEX_FILENAME;
				break;
			default:
				break;
		}
		return fileName;
	}
	
	/**
	 * Method that sorts the index by the total no. of frequencies of a term
	 * @param map
	 * @return
	 * Reference taken from - http://javarevisited.blogspot.com/2012/12/how-to-sort-hashmap-java-by-key-and-value.html
	 */
	
	public static <Integer extends Comparable,PostingWrapper extends Comparable> Map<Integer,PostingWrapper> sortByTotalFrequency(Map<Integer,PostingWrapper> map){
        List<Map.Entry<Integer,PostingWrapper>> entries = new LinkedList<Map.Entry<Integer,PostingWrapper>>(map.entrySet());
      
        Collections.sort(entries, new Comparator<Map.Entry<Integer,PostingWrapper>>() {

            @Override
            public int compare(Entry<Integer, PostingWrapper> o1, Entry<Integer, PostingWrapper> o2) {
            	PostingWrapper wrapper1 = o1.getValue();
            	PostingWrapper wrapper2 = o2.getValue();
            	return o1.getValue().compareTo(o2.getValue());
                
            }
        });
      
        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<Integer,PostingWrapper> sortedMap = new LinkedHashMap<Integer,PostingWrapper>();
      
        for(Map.Entry<Integer,PostingWrapper> entry: entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
      
        return sortedMap;
    }
	
	
	
	
	/**
	 * Finds the indexType based on the raw String
	 * If the termType  is "Term" dictionary is TERM
	 */
	public static IndexType getTermIndexType(String termType) {
		
		IndexType indexType = null;
		
		if(termType.equalsIgnoreCase(CommonConstants.TYPE_TERM)){
			indexType = IndexType.TERM;
		}else if(termType.equalsIgnoreCase(CommonConstants.TYPE_CATEGORY)){
			indexType = IndexType.CATEGORY;
		}else if(termType.equalsIgnoreCase(CommonConstants.TYPE_AUTHOR)){
			indexType = IndexType.AUTHOR;
		}else if(termType.equalsIgnoreCase(CommonConstants.TYPE_PLACE)){
			indexType = IndexType.PLACE;
		}
		
		return indexType;
   }
	
	
	
	/**
	 * Reads a object given by the path
	 * 
	 * @param filePath
	 * @return
	 */
	public static Object readObject(String filePath) {
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
	
	
	/**
	 * Finds the indexing details  based on the raw Index Type
	 */
	public static TermIndexDetails getTermIndexDetails(String termType) {
		
		TermIndexDetails termIndexDetails = new TermIndexDetails();
		
		if(termType.equalsIgnoreCase(CommonConstants.TYPE_TERM)){
			termIndexDetails.setIndexType(IndexType.TERM);
			termIndexDetails.setDictionaryName(CommonConstants.TERM_DICTIONARY_FILENAME);
			termIndexDetails.setIndexName(CommonConstants.TERM_INDEX_FILENAME);
			termIndexDetails.setFieldName(FieldNames.CONTENT);
		}else if(termType.equalsIgnoreCase(CommonConstants.TYPE_CATEGORY)){
			termIndexDetails.setIndexType(IndexType.CATEGORY);
			termIndexDetails.setDictionaryName(CommonConstants.CATEGORY_DICTIONARY_FILENAME);
			termIndexDetails.setIndexName(CommonConstants.CATEGORY_INDEX_FILENAME);
			termIndexDetails.setFieldName(FieldNames.CATEGORY);
		}else if(termType.equalsIgnoreCase(CommonConstants.TYPE_AUTHOR)){
			termIndexDetails.setIndexType(IndexType.AUTHOR);
			termIndexDetails.setDictionaryName(CommonConstants.AUTHOR_DICTIONARY_FILENAME);
			termIndexDetails.setIndexName(CommonConstants.AUTHOR_INDEX_FILENAME);
			termIndexDetails.setFieldName(FieldNames.AUTHOR);
		}else if(termType.equalsIgnoreCase(CommonConstants.TYPE_PLACE)){
			termIndexDetails.setIndexType(IndexType.PLACE);
			termIndexDetails.setDictionaryName(CommonConstants.PLACE_DICTIONARY_FILENAME);
			termIndexDetails.setIndexName(CommonConstants.PLACE_INDEX_FILENAME);
			termIndexDetails.setFieldName(FieldNames.PLACE);
		}
		return termIndexDetails;
   }

   /**
    * Find the maximum score in the postings list
    * @param postings
    * @return
    */
   public static Double findMaxScore(List<Posting> postings){
	   Double max=0.0;
	   if(postings!=null){
		   for(Posting posting:postings){
			   Double score = posting.getScore();
			   if(score>max)
				   max = score;
		   }
	   }
	   return max;
   }
 


}



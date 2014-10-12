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
	 * Method to get the analyzed term based on the index type
	 * Input should be of the type Term:hello or Term:”hello world”
	 * @param string
	 * @return
	 */
	public static String getAnalyzedTerm(String string) {
		
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
				termIndexDetails = getTermIndexDetails(rawIndexType);
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
	 * Finds the indexType based on the raw String
	 * If the termType  is "Term" dictionary is TERM
	 */
	public static IndexType getTermIndexType(String termType) {
		
		IndexType indexType = null;
		
		if(termType.equals(CommonConstants.TYPE_TERM)){
			indexType = IndexType.TERM;
		}else if(termType.equals(CommonConstants.TYPE_CATEGORY)){
			indexType = IndexType.CATEGORY;
		}else if(termType.equals(CommonConstants.TYPE_AUTHOR)){
			indexType = IndexType.AUTHOR;
		}else if(termType.equals(CommonConstants.TYPE_PLACE)){
			indexType = IndexType.PLACE;
		}
		
		return indexType;
   }
	
	
	/**
	 * Finds the indexing details  based on the raw Index Type
	 */
	public static TermIndexDetails getTermIndexDetails(String termType) {
		
		TermIndexDetails termIndexDetails = new TermIndexDetails();
		
		if(termType.equals(CommonConstants.TYPE_TERM)){
			termIndexDetails.setIndexType(IndexType.TERM);
			termIndexDetails.setDictionaryName(CommonConstants.TERM_DICTIONARY_FILENAME);
			termIndexDetails.setIndexName(CommonConstants.TERM_INDEX_FILENAME);
			termIndexDetails.setFieldName(FieldNames.CONTENT);
		}else if(termType.equals(CommonConstants.TYPE_CATEGORY)){
			termIndexDetails.setIndexType(IndexType.CATEGORY);
			termIndexDetails.setDictionaryName(CommonConstants.CATEGORY_DICTIONARY_FILENAME);
			termIndexDetails.setIndexName(CommonConstants.CATEGORY_INDEX_FILENAME);
			termIndexDetails.setFieldName(FieldNames.CATEGORY);
		}else if(termType.equals(CommonConstants.TYPE_AUTHOR)){
			termIndexDetails.setIndexType(IndexType.AUTHOR);
			termIndexDetails.setDictionaryName(CommonConstants.AUTHOR_DICTIONARY_FILENAME);
			termIndexDetails.setIndexName(CommonConstants.AUTHOR_INDEX_FILENAME);
			termIndexDetails.setFieldName(FieldNames.AUTHOR);
		}else if(termType.equals(CommonConstants.TYPE_PLACE)){
			termIndexDetails.setIndexType(IndexType.PLACE);
			termIndexDetails.setDictionaryName(CommonConstants.PLACE_DICTIONARY_FILENAME);
			termIndexDetails.setIndexName(CommonConstants.PLACE_INDEX_FILENAME);
			termIndexDetails.setFieldName(FieldNames.PLACE);
		}
		return termIndexDetails;
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
	 * Method to find the postings list of a term
	 * @param term - analyzed term
	 * @param indexType - index type where the term is to be searched
	 * @return
	 */
	public static PostingWrapper getPostings(String indexDir,String term,String termType) {
		
		PostingWrapper postingWrapper = null;
		//get the index type based on the raw string index type. Term: gets coverted to IndexTypeTerm
		IndexType indexType = getTermIndexType(termType);
		//load the document and the index
		IndexReader indexReader = new IndexReader(indexDir, indexType);
		Map<String, Integer> dictionaryForIndexType=indexReader.getDictionaryForIndexType();
		Map<Integer, PostingWrapper> indexMap=indexReader.getIndexMap();
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
     * Method to merge the two postings list based on operators 
     * @param firstPostings - List of first postings
     * @param secondPostings - List of second postings
     * @param operator - AND
     * @return
     */
	public static List<Posting> mergePostingsAnd(List<Posting> firstPostings,List<Posting> secondPostings) {
		
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
	public static List<Posting> mergePostingsOr(List<Posting> firstPostings,List<Posting> secondPostings) {
		
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
		while(firstPosting!=null || secondPosting!=null){
			firstDocId = firstPosting.getDocId();
			secondDocId = secondPosting.getDocId();
			//if the docIds are equal
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
		return outputPostings;
   }
	
    /**
     * Method to merge the two postings list based on operators 
     * @param firstPostings - List of first postings
     * @param secondPostings - List of second postings
     * @param operator - NOT
     * @return
     */
	public static List<Posting> mergePostingsNot(List<Posting> firstPostings,List<Posting> secondPostings) {
		
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
		while(firstPosting!=null || secondPosting!=null){
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
		return outputPostings;
   }
}



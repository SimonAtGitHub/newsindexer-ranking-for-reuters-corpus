package edu.buffalo.cse.irf14.common;

import java.io.File;

import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.index.AuthorDictionary;
import edu.buffalo.cse.irf14.index.AuthorIndex;
import edu.buffalo.cse.irf14.index.CategoryDictionary;
import edu.buffalo.cse.irf14.index.CategoryIndex;
import edu.buffalo.cse.irf14.index.DocumentDictionary;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.NewsDictionary;
import edu.buffalo.cse.irf14.index.NewsIndex;
import edu.buffalo.cse.irf14.index.PlaceDictionary;
import edu.buffalo.cse.irf14.index.PlaceIndex;
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
}

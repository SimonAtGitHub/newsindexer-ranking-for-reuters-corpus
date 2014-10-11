package edu.buffalo.cse.irf14.common;

import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.index.IndexType;

/**
 * Stores the indexing details of a term
 * based on the type of the term
 * @author Priyankar
 *
 */

public class TermIndexDetails {
     
	/**
	 * Dictionary file name of the term type
	 */
	private String dictionaryName;
	
	/**
	 * Index File name of the term type
	 */
	private String indexName;
	
	/**	
	 * Index type of the field
	 */
	private IndexType indexType;
	
	/**	
	 * get the field name for index type
	 * e.g. Term - FieldNames.CONTENT 
	 */
	private FieldNames fieldName;

	/**
	 * @return the dictionaryName
	 */
	public String getDictionaryName() {
		return dictionaryName;
	}

	/**
	 * @param dictionaryName the dictionaryName to set
	 */
	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	/**
	 * @return the indexName
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * @param indexName the indexName to set
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * @return the indexType
	 */
	public IndexType getIndexType() {
		return indexType;
	}

	/**
	 * @param indexType the indexType to set
	 */
	public void setIndexType(IndexType indexType) {
		this.indexType = indexType;
	}

	/**
	 * @return the fieldNames
	 */
	public FieldNames getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldNames the fieldNames to set
	 */
	public void setFieldName(FieldNames fieldName) {
		this.fieldName = fieldName;
	}
	
	
}

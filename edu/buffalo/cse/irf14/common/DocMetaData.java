package edu.buffalo.cse.irf14.common;

import java.io.Serializable;

/**
 * Stores the metadata for a document
 * @author Priyankar
 *
 */
public class DocMetaData implements Serializable{

	/**
	 * stores the filename
	 */
	private String fileName;
	
	/**
	 * stores the length of the file
	 */
	private Long length;
	
	/**
	 * stores the result snippet for the document
	 */
	private String resultSnippet;

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the length
	 */
	public Long getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(Long length) {
		this.length = length;
	}

	/**
	 * @return the resultSnippet
	 */
	public String getResultSnippet() {
		return resultSnippet;
	}

	/**
	 * @param resultSnippet the resultSnippet to set
	 */
	public void setResultSnippet(String resultSnippet) {
		this.resultSnippet = resultSnippet;
	}
	
	
}

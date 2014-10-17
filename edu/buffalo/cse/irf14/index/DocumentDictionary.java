/**
 * This dictionary contains a Document to document id mapping. 
 */
package edu.buffalo.cse.irf14.index;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.buffalo.cse.irf14.common.DocMetaData;

public class DocumentDictionary implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -4534140919144215754L;
	/**
	 * Map which holds the mapping between doc Id and File Id
	 * Doc id has been kept as the key as it would be used to seach for the file id 
	 * during retrieval
	 */
	private Map<Integer,DocMetaData> map=new HashMap<Integer,DocMetaData>();
	
	transient private static int sequence=0;
	
	/**
	 * singleton instance of the class
	 */
	transient private static DocumentDictionary instance = null;

	/**
	 * Static method to return an instance of the Document dictionary class.
	 * 
	 * @return An instance of the factory
	 */
	public static DocumentDictionary getInstance() {
		// TODO : YOU MUST IMPLEMENT THIS METHOD
		if (instance == null) {
			instance = new DocumentDictionary();
		}
		return instance;
	}
	
	/**
	 * @return the map
	 */
	public Map<Integer, DocMetaData> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<Integer, DocMetaData> map) {
		this.map = map;
	}

	/**
	 * Sequence generator
	 * @return
	 */
	public int nextVal(){
		return ++sequence;
	}
	

	
	
}

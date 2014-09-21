/**
 * This dictionary contains a Document to document id mapping. 
 */
package edu.buffalo.cse.irf14.index;

import java.io.Serializable;

public class AuthorDictionary extends NewsDictionary implements Serializable{
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4534140919144215754L;
	
	
	/**
	 * singleton instance of the class
	 */
	transient private static AuthorDictionary instance = null;

	/**
	 * Static method to return an instance of the Document dictionary class.
	 * 
	 * @return An instance of the factory
	 */
	public static AuthorDictionary getInstance() {
		// TODO : YOU MUST IMPLEMENT THIS METHOD
		if (instance == null) {
			instance = new AuthorDictionary();
		}
		return instance;
	}
	
	

	
	
}

package edu.buffalo.cse.irf14.index;

import java.io.Serializable;

public class AuthorIndex extends NewsIndex implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1055208171330794968L;
	/**
	 * singleton instance of the class
	 */
	transient private static AuthorIndex instance = null;

	/**
	 * Static method to return an instance of the AuthorIndex class.
	 * 
	 * @return An instance of the factory
	 */
	public static AuthorIndex getInstance() {
		// TODO : YOU MUST IMPLEMENT THIS METHOD
		if (instance == null) {
			instance = new AuthorIndex();
		}
		return instance;
	}
	

	
	

}

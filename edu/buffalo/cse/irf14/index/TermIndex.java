package edu.buffalo.cse.irf14.index;

import java.io.Serializable;

public class TermIndex extends NewsIndex implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1055208171330794968L;
	/**
	 * singleton instance of the class
	 */
	transient private static TermIndex instance = null;

	/**
	 * Static method to return an instance of the TermIndex class.
	 * 
	 * @return An instance of the factory
	 */
	public static TermIndex getInstance() {
		// TODO : YOU MUST IMPLEMENT THIS METHOD
		if (instance == null) {
			instance = new TermIndex();
		}
		return instance;
	}
	

	
	

}

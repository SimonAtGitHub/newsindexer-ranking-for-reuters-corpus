package edu.buffalo.cse.irf14.index;

import java.io.Serializable;

public class PlaceIndex extends NewsIndex implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1055208171330794968L;
	/**
	 * singleton instance of the class
	 */
	transient private static PlaceIndex instance = null;

	/**
	 * Static method to return an instance of the PlaceIndex class.
	 * 
	 * @return An instance of the factory
	 */
	public static PlaceIndex getInstance() {
		// TODO : YOU MUST IMPLEMENT THIS METHOD
		if (instance == null) {
			instance = new PlaceIndex();
		}
		return instance;
	}
	

	
	

}

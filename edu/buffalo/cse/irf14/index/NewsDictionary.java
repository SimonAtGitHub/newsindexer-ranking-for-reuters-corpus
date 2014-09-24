package edu.buffalo.cse.irf14.index;

import java.util.HashMap;
import java.util.Map;

/**
 * The abstract class for dictionary. All dictionaries must implement this abstract class
 * 
 * @author Priyankar
 *
 */
public abstract class NewsDictionary {
	/**
	 * Map which holds the mapping between dictionary key and dictionary value
	 */
	private Map<String,Integer> map=new HashMap<String,Integer>();
	
	transient private static int sequence=0;

	/**
	 * @return the map
	 */
	public Map<String, Integer> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
	
	/**
	 * Sequence generator
	 * @return
	 */
	public int nextVal(){
		return ++sequence;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NewsDictionary [map=" + map + "]";
	}
	
	


}

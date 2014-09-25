package edu.buffalo.cse.irf14.index;

import java.util.HashMap;
import java.util.Map;

public abstract class NewsIndex {
     private Map<Integer,PostingWrapper> map = new HashMap<Integer,PostingWrapper>();

	/**
	 * @return the map
	 */
	public Map<Integer, PostingWrapper> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<Integer, PostingWrapper> map) {
		this.map = map;
	}

     
}

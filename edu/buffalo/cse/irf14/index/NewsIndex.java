package edu.buffalo.cse.irf14.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NewsIndex {
     private Map<Integer,List<Posting>> map = new HashMap<Integer,List<Posting>>();

	/**
	 * @return the map
	 */
	public Map<Integer, List<Posting>> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<Integer, List<Posting>> map) {
		this.map = map;
	}
     
}

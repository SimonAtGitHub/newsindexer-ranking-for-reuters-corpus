package edu.buffalo.cse.irf14.common;

import java.util.List;

import edu.buffalo.cse.irf14.index.Posting;

public class QueryResult {

	/**
	 * Query Id
	 */
	private String queryId;
	
	/**
	 * Result postings
	 */
	private List<Posting> resultPostings;

	/**
	 * @return the queryId
	 */
	public String getQueryId() {
		return queryId;
	}

	/**
	 * @param queryId the queryId to set
	 */
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	/**
	 * @return the resultPostings
	 */
	public List<Posting> getResultPostings() {
		return resultPostings;
	}

	/**
	 * @param resultPostings the resultPostings to set
	 */
	public void setResultPostings(List<Posting> resultPostings) {
		this.resultPostings = resultPostings;
	}
	
}

package edu.buffalo.cse.irf14.index;

import java.io.Serializable;
import java.util.List;

public class PostingWrapper implements Serializable, Comparable<PostingWrapper> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2773983949295484356L;

	/**
	 * total frequency of a term in the index
	 */
	Integer totalFrequency = 0;
	/**
	 * postings list of a term
	 */
	List<Posting> postings;

	/**
	 * @return the postings
	 */
	public List<Posting> getPostings() {
		return postings;
	}

	/**
	 * @return the totalFrequency
	 */
	public Integer getTotalFrequency() {
		return totalFrequency;
	}

	/**
	 * @param totalFrequency
	 *            the totalFrequency to set
	 */
	public void setTotalFrequency(Integer totalFrequency) {
		this.totalFrequency = totalFrequency;
	}

	/**
	 * @param postings
	 *            the postings to set
	 */
	public void setPostings(List<Posting> postings) {
		this.postings = postings;
	}

	@Override
	public int compareTo(PostingWrapper o) {
		// TODO Auto-generated method stub
		return (o.totalFrequency).compareTo(this.totalFrequency);
	}

	@Override
	public String toString() {
		return "PostingWrapper [totalFrequency=" + totalFrequency
				+ ", postings=" + postings + "]";
	}
}

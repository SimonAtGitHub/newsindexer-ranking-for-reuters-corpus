package edu.buffalo.cse.irf14.common;

import java.util.List;

import org.junit.Test;

import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.Posting;
import edu.buffalo.cse.irf14.index.PostingWrapper;
import static org.junit.Assert.*;

public class CommonUtilTest {

	@Test
	public void testGetAnalyzedTerm() {
		assertEquals("comput",CommonUtil.getAnalyzedTerm("Term:Computer") );
		assertEquals("san Francisco",CommonUtil.getAnalyzedTerm("Term:\"San Francisco\"") );
		/*System.out.println(CommonUtil.getAnalyzedTerm("Term:priyankar"));
		System.out.println(CommonUtil.getAnalyzedTerm("Term:nandi"));*/
	}
	
	@Test
	public void testGetPostings() {
		String indexDir = "H:\\projects\\IR\\newsindexer\\index";
		PostingWrapper postingWrapper=CommonUtil.getPostings(indexDir, "comput","Term");
        System.out.println("\nPostings retrieved");
	}
	
	@Test
	public void testMergePostings() {
		String indexDir = "H:\\projects\\IR\\newsindexer\\index";
		PostingWrapper postingWrapperFirst=CommonUtil.getPostings(indexDir, "priyankar","Term");
		PostingWrapper postingWrapperSecond=CommonUtil.getPostings(indexDir, "nandi","Term");
/*		List<Posting> lstAnd=CommonUtil.mergePostingsAnd(postingWrapperFirst.getPostings(), postingWrapperSecond.getPostings());
		assertEquals(2, lstAnd.size());*/
/*		List<Posting> lstOr=CommonUtil.mergePostingsOr(postingWrapperFirst.getPostings(), postingWrapperSecond.getPostings());
		assertEquals(4, lstOr.size());*/
        List<Posting> lstNot=CommonUtil.mergePostingsNot(postingWrapperFirst.getPostings(), postingWrapperSecond.getPostings());
		assertEquals(1, lstNot.size());
        System.out.println("\nPostings retrieved");
	}

}

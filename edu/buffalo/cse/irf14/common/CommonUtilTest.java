package edu.buffalo.cse.irf14.common;

import org.junit.Test;
import static org.junit.Assert.*;

public class CommonUtilTest {

	@Test
	public void testGetAnalyzedTerm() {
		assertEquals("comput",CommonUtil.getAnalyzedTerm("Term:Computer") );
		assertEquals("san Francisco",CommonUtil.getAnalyzedTerm("Term:\"San Francisco\"") );
	}

}

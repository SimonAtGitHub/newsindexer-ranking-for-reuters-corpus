package edu.buffalo.cse.irf14.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QueryToStringTester {

	@Test
	public void testToStringRepresentations() {
		assertEquals("{ Term:hello }", new Query("hello", "OR").toString());
		assertEquals("{ Term:hello OR Term:world }", new Query("hello world",
				"OR").toString());
		assertEquals("{ Term:\"hello world\" }", new Query("\"hello world\"",
				"OR").toString());
		assertEquals("{ Term:orange AND Term:yellow }", new Query(
				"orange AND yellow", "OR").toString());
		assertEquals("{ [ Term:black OR Term:blue ] AND Term:bruises }",
				new Query("(black OR blue) AND bruises", "OR").toString());
		assertEquals("{ Author:rushdie AND <Term:jihad> }", new Query(
				"Author:rushdie NOT jihad", "OR").toString());
		assertEquals(
				"{ Category:War AND Author:Dutt AND Place:Baghdad AND [ Term:prisoners OR Term:detainees OR Term:rebels ] }",
				new Query(
						"Category:War AND Author:Dutt AND Place:Baghdad AND prisoners detainees rebels",
						"OR").toString());
		assertEquals(
				"{ [ Term:Love AND <Term:War> ] AND [ Category:movies AND <Category:crime> ] }",
				new Query("(Love NOT War) AND Category:(movies NOT crime)",
						"OR").toString());

	}

}

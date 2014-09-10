package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.analysis.test.TFRuleBaseTest;


public class TestToken {
    public static void main(String [] args) throws TokenizerException{
     /* Token token1= new Token(); 
      token1.setTermText("A");
      Token token2= new Token(); 
      token2.setTermText("B");
      Token token3= new Token(); 
      token3.setTermText("C");
      List lst = new ArrayList();
      lst.add(token1);
      lst.add(token2);
      lst.add(token3);
      TokenStream tokenStream = new TokenStream(lst);
      while(tokenStream.hasNext()){
    	  Token token = tokenStream.next();
    	  System.out.println(token.getTermText());
      }*/
		TFRuleBaseTest.runTest("I am a good boy.");
		TFRuleBaseTest.runTest("I am a good boy?");
		TFRuleBaseTest.runTest("I am a good boy?.");
		TFRuleBaseTest.runTest("I am a good-boy.");
       TFRuleBaseTest.runTest("I am a good-3224 boy.");
    }
}

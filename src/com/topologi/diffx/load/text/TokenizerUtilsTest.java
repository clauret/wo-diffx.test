package com.topologi.diffx.load.text;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test case for the tokenizer utility class.
 * 
 * @author Christophe Lauret
 * @version 11 May 2010
 */
public class TokenizerUtilsTest {

  @Test public void testGetLeadingWhiteSpace() {
    Assert.assertEquals(0, TokenizerUtils.getLeadingWhiteSpace(""));
    Assert.assertEquals(0, TokenizerUtils.getLeadingWhiteSpace("x"));
    Assert.assertEquals(0, TokenizerUtils.getLeadingWhiteSpace("x "));
    Assert.assertEquals(0, TokenizerUtils.getLeadingWhiteSpace("xxx  "));
    Assert.assertEquals(1, TokenizerUtils.getLeadingWhiteSpace(" "));
    Assert.assertEquals(1, TokenizerUtils.getLeadingWhiteSpace(" x"));
    Assert.assertEquals(1, TokenizerUtils.getLeadingWhiteSpace(" x  "));
    Assert.assertEquals(2, TokenizerUtils.getLeadingWhiteSpace("\t\tx  x  "));
    Assert.assertEquals(2, TokenizerUtils.getLeadingWhiteSpace("\t\tx x "));
    Assert.assertEquals(4, TokenizerUtils.getLeadingWhiteSpace("\t  \n"));
    Assert.assertEquals(4, TokenizerUtils.getLeadingWhiteSpace("\t  \nx x "));
  }

  @Test public void testGetTrailingWhiteSpace() {
    Assert.assertEquals(0, TokenizerUtils.getTrailingWhiteSpace(""));
    Assert.assertEquals(0, TokenizerUtils.getTrailingWhiteSpace("x"));
    Assert.assertEquals(0, TokenizerUtils.getTrailingWhiteSpace(" x"));
    Assert.assertEquals(0, TokenizerUtils.getTrailingWhiteSpace("  xxx"));
    Assert.assertEquals(1, TokenizerUtils.getTrailingWhiteSpace(" "));
    Assert.assertEquals(1, TokenizerUtils.getTrailingWhiteSpace("x "));
    Assert.assertEquals(1, TokenizerUtils.getTrailingWhiteSpace("  x "));
    Assert.assertEquals(2, TokenizerUtils.getTrailingWhiteSpace(" x  x\t\t"));
    Assert.assertEquals(2, TokenizerUtils.getTrailingWhiteSpace(" x x\t\t"));
    Assert.assertEquals(4, TokenizerUtils.getTrailingWhiteSpace("\t  \n"));
    Assert.assertEquals(4, TokenizerUtils.getTrailingWhiteSpace("x x\t  \n"));
  }

}

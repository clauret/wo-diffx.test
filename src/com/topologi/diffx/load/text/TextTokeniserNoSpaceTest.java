/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.load.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.topologi.diffx.config.WhiteSpaceProcessing;

/**
 * Test case for the text tokenizer that ignores white spaces.
 *
 * @author Christophe Lauret
 * @version 3 April 2005
 */
public final class TextTokeniserNoSpaceTest {

  /**
   * Tests that a <code>NullPointerException</code> is thrown for a </code>null</code>
   * character sequence.
   */
  @Test public void testNull() {
    try {
      TextTokenizer ct = new TokenizerByText(WhiteSpaceProcessing.IGNORE);
      ct.tokenize(null);
      assertTrue(false);
    } catch (NullPointerException ex) {
      assertTrue(true);
    }
  }

  /**
   * Tests that an empty array is returned for empty string.
   */
  @Test public void testEmpty() {
    TextTokenizer ct = new TokenizerByText(WhiteSpaceProcessing.IGNORE);
    assertEquals(0, ct.tokenize("").size());
  }

  /**
   * Tests that the tokenizer counts the correct number of tokens.
   */
  @Test public void testCountToken1() {
    TextTokenizer ct = new TokenizerByText(WhiteSpaceProcessing.IGNORE);
    assertEquals(0, ct.tokenize(" ").size());
    assertEquals(1, ct.tokenize(" a").size());
    assertEquals(1, ct.tokenize("a ").size());
    assertEquals(1, ct.tokenize(" b ").size());
    assertEquals(2, ct.tokenize("b b").size());
    assertEquals(2, ct.tokenize("c c ").size());
    assertEquals(2, ct.tokenize(" c c").size());
    assertEquals(2, ct.tokenize(" d d ").size());
    assertEquals(3, ct.tokenize("d d d").size());
  }

  /**
   * Tests that the tokenizer counts the correct number of tokens.
   */
  @Test public void testCountToken2() {
    TextTokenizer ct = new TokenizerByText(WhiteSpaceProcessing.IGNORE);
    assertEquals(0, ct.tokenize(" ").size());
    assertEquals(1, ct.tokenize("  a").size());
    assertEquals(1, ct.tokenize("aa ").size());
    assertEquals(1, ct.tokenize(" aa").size());
    assertEquals(1, ct.tokenize("a  ").size());
    assertEquals(1, ct.tokenize(" bb ").size());
    assertEquals(2, ct.tokenize("b bb").size());
    assertEquals(2, ct.tokenize("b   bb").size());
    assertEquals(2, ct.tokenize("xx  yy  ").size());
  }

  // TODO
//
//  /**
//   * Tests that the tokeniser finds a word event as token.
//   */
//  @Test public void testWord1() {
//    TextTokeniser ct = new TextTokeniserNoSpace("x");
//    assertEquals(1, ct.countTokens());
//    assertEquals(new WordEvent("x"), ct.nextToken());
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Tests that the tokenizer finds the correct sequence of events.
//   */
//  @Test public void testSeq1() {
//    TextTokeniser ct = new TextTokeniserNoSpace("xx  ");
//    assertEquals(1, ct.countTokens());
//    assertEquals(new WordEvent("xx"), ct.nextToken());
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Tests that the tokenizer finds the correct sequence of events.
//   */
//  @Test public void testSeq2() {
//    TextTokeniser ct = new TextTokeniserNoSpace("  xx");
//    assertEquals(1, ct.countTokens());
//    assertEquals(new WordEvent("xx"), ct.nextToken());
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Tests that the tokenizer finds the correct sequence of events.
//   */
//  @Test public void testSeq3() {
//    TextTokeniser ct = new TextTokeniserNoSpace("  xx  ");
//    assertEquals(1, ct.countTokens());
//    assertEquals(new WordEvent("xx"), ct.nextToken());
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Tests that the tokenizer finds the correct sequence of events.
//   */
//  @Test public void testSeq4() {
//    TextTokeniser ct = new TextTokeniserNoSpace("  xx  yyy  ");
//    assertEquals(2, ct.countTokens());
//    assertEquals(new WordEvent("xx"), ct.nextToken());
//    assertEquals(new WordEvent("yyy"), ct.nextToken());
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Asserts that this was the last token of the tokeniser.
//   *
//   * <p>Checks that the {@link CharactersTokeniser#nextToken()} method throws a
//   * <code>NoSuchElementException</code>.
//   *
//   * @param ct The character tokeniszer to check.
//   */
//  public void assertNoMoreTokens(TextTokenizer ct) {
//    try {
//      ct.nextToken();
//      assertTrue(false);
//    } catch (NoSuchElementException ex) {
//      assertTrue(true);
//    }
//  }

}


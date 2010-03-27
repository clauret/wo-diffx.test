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

import java.util.NoSuchElementException;

import org.junit.Test;

import com.topologi.diffx.event.impl.WordEvent;

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
      TextTokeniser ct = new TextTokeniserNoSpace(null);
      assertTrue(false);
    } catch (NullPointerException ex) {
      assertTrue(true);
    }
  }

  /**
   * Tests that an empty array is returned for empty string.
   */
  @Test public void testEmpty() {
    TextTokeniser ct = new TextTokeniserNoSpace("");
    assertEquals(0, ct.countTokens());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokenizer counts the correct number of tokens.
   */
  @Test public void testCountToken1() {
    assertEquals(0, new TextTokeniserNoSpace(" ").countTokens());
    assertEquals(1, new TextTokeniserNoSpace(" a").countTokens());
    assertEquals(1, new TextTokeniserNoSpace("a ").countTokens());
    assertEquals(1, new TextTokeniserNoSpace(" b ").countTokens());
    assertEquals(2, new TextTokeniserNoSpace("b b").countTokens());
    assertEquals(2, new TextTokeniserNoSpace("c c ").countTokens());
    assertEquals(2, new TextTokeniserNoSpace(" c c").countTokens());
    assertEquals(2, new TextTokeniserNoSpace(" d d ").countTokens());
    assertEquals(3, new TextTokeniserNoSpace("d d d").countTokens());
  }

  /**
   * Tests that the tokenizer counts the correct number of tokens.
   */
  @Test public void testCountToken2() {
    assertEquals(0, new TextTokeniserNoSpace(" ").countTokens());
    assertEquals(1, new TextTokeniserNoSpace("  a").countTokens());
    assertEquals(1, new TextTokeniserNoSpace("aa ").countTokens());
    assertEquals(1, new TextTokeniserNoSpace(" aa").countTokens());
    assertEquals(1, new TextTokeniserNoSpace("a  ").countTokens());
    assertEquals(1, new TextTokeniserNoSpace(" bb ").countTokens());
    assertEquals(2, new TextTokeniserNoSpace("b bb").countTokens());
    assertEquals(2, new TextTokeniserNoSpace("b   bb").countTokens());
    assertEquals(2, new TextTokeniserNoSpace("xx  yy  ").countTokens());
  }

  /**
   * Tests that the tokeniser finds a word event as token.
   */
  @Test public void testWord1() {
    TextTokeniser ct = new TextTokeniserNoSpace("x");
    assertEquals(1, ct.countTokens());
    assertEquals(new WordEvent("x"), ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokenizer finds the correct sequence of events.
   */
  @Test public void testSeq1() {
    TextTokeniser ct = new TextTokeniserNoSpace("xx  ");
    assertEquals(1, ct.countTokens());
    assertEquals(new WordEvent("xx"), ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokenizer finds the correct sequence of events.
   */
  @Test public void testSeq2() {
    TextTokeniser ct = new TextTokeniserNoSpace("  xx");
    assertEquals(1, ct.countTokens());
    assertEquals(new WordEvent("xx"), ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokenizer finds the correct sequence of events.
   */
  @Test public void testSeq3() {
    TextTokeniser ct = new TextTokeniserNoSpace("  xx  ");
    assertEquals(1, ct.countTokens());
    assertEquals(new WordEvent("xx"), ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokenizer finds the correct sequence of events.
   */
  @Test public void testSeq4() {
    TextTokeniser ct = new TextTokeniserNoSpace("  xx  yyy  ");
    assertEquals(2, ct.countTokens());
    assertEquals(new WordEvent("xx"), ct.nextToken());
    assertEquals(new WordEvent("yyy"), ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Asserts that this was the last token of the tokeniser.
   * 
   * <p>Checks that the {@link CharactersTokeniser#nextToken()} method throws a
   * <code>NoSuchElementException</code>.
   * 
   * @param ct The character tokeniszer to check.
   */
  public void assertNoMoreTokens(TextTokeniser ct) {
    try {
      ct.nextToken();
      assertTrue(false);
    } catch (NoSuchElementException ex) {
      assertTrue(true);
    }
  }

}


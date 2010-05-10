/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.load.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.topologi.diffx.config.WhiteSpaceProcessing;
import com.topologi.diffx.event.DiffXEvent;
import com.topologi.diffx.event.TextEvent;
import com.topologi.diffx.event.impl.SpaceEvent;
import com.topologi.diffx.event.impl.WordEvent;

/**
 * Test case for the tokenizer.
 * 
 * @author Christophe Lauret
 * @version 3 April 2005  
 */
public final class TokenizerByWordTest {

  /**
   * Tests that a <code>NullPointerException</code> is thrown for a </code>null</code>
   * character sequence. 
   */
  @Test public void testNull() {
    try {
      TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.IGNORE);
      assertNull(t.tokenize(null));
    } catch (NullPointerException ex) {
      assertTrue(true);
    }
  }

  /**
   * Tests that an empty array is returned for empty string.
   */
  @Test  public void testEmpty() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.IGNORE);
    List<TextEvent> e = t.tokenize("");
    assertEquals(0, e.size());
  }

  /**
   * Tests that the tokeniser counts the correct number of tokens.
   */
  @Test public void testCountToken1() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.IGNORE);
    assertEquals(1, t.tokenize(" ").size());
    assertEquals(2, t.tokenize(" a").size());
    assertEquals(2, t.tokenize("a ").size());
    assertEquals(3, t.tokenize(" b ").size());
    assertEquals(3, t.tokenize("b b").size());
    assertEquals(4, t.tokenize("c c ").size());
    assertEquals(4, t.tokenize(" c c").size());
    assertEquals(5, t.tokenize(" d d ").size());
    assertEquals(5, t.tokenize("d d d").size());
  }

  /**
   * Tests that the tokeniser counts the correct number of tokens.
   */
  @Test public void testCountToken2() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.IGNORE);
    assertEquals(1, t.tokenize(" ").size());
    assertEquals(2, t.tokenize("  a").size());
    assertEquals(2, t.tokenize("aa ").size());
    assertEquals(2, t.tokenize(" aa").size());
    assertEquals(2, t.tokenize("a  ").size());
    assertEquals(3, t.tokenize(" bb ").size());
    assertEquals(3, t.tokenize("b bb").size());
    assertEquals(3, t.tokenize("b   bb").size());
    assertEquals(4, t.tokenize("xx  yy  ").size());
  }

  /**
   * Tests that the tokeniser counts the correct number of tokens.
   */
  @Test public void testCountToken3() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    assertEquals(1, t.tokenize(" ").size());
    assertEquals(3, t.tokenize("  \na").size());
    assertEquals(3, t.tokenize("aa \n").size());
    assertEquals(3, t.tokenize(" \naa").size());
    assertEquals(4, t.tokenize("a \n ").size());
    assertEquals(4, t.tokenize(" bb\n ").size());
    assertEquals(4, t.tokenize("b\n bb").size());
    assertEquals(5, t.tokenize("b \n  bb").size());
    assertEquals(7, t.tokenize("xx \n yy\n  ").size());
  }

  /**
   * Tests that the tokeniser counts the correct number of tokens.
   */
  @Test public void testCountToken4() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    assertEquals(1, t.tokenize("\n").size());
    assertEquals(3, t.tokenize("\n \n").size());
    assertEquals(3, t.tokenize(" \n\n").size());
    assertEquals(3, t.tokenize("\n\n\n").size());
  }

  /**
   * Tests that the tokeniser finds a space event as token.
   */
  @Test public void testSpace1() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    List<TextEvent> e = t.tokenize(" ");
    assertEquals(1, e.size());
    DiffXEvent space = e.get(0);
    assertEquals(new SpaceEvent(" "), space);
    assertSame(SpaceEvent.SINGLE_WHITESPACE, space);
  }

  /**
   * Tests that the tokeniser finds a space event as token.
   */
  @Test public void testSpace2() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    List<TextEvent> e = t.tokenize("  ");
    assertEquals(1, e.size());
    DiffXEvent space = e.get(0);
    assertEquals(new SpaceEvent("  "), space);
    assertSame(SpaceEvent.DOUBLE_WHITESPACE, space);
  }

  /**
   * Tests that the tokeniser finds a space event as token.
   */
  @Test public void testSpace3() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    List<TextEvent> e = t.tokenize("\n");
    assertEquals(1, e.size());
    DiffXEvent space = e.get(0);
    assertEquals(new SpaceEvent("\n"), space);
    assertSame(SpaceEvent.NEW_LINE, space);
  }

  /**
   * Tests that the tokeniser finds a word event as token.
   */
  @Test public void testWord1() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    List<TextEvent> e = t.tokenize("x");
    assertEquals(1, e.size());
    assertEquals(new WordEvent("x"), e.get(0));
  }

  /**
   * Tests that the tokeniser finds the correct sequence of events.
   */
  @Test public void testSeq1() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    List<TextEvent> e = t.tokenize("xx  ");
    assertEquals(2, e.size());
    assertEquals(new WordEvent("xx"), e.get(0));
    assertEquals(new SpaceEvent("  "), e.get(1));
  }

  /**
   * Tests that the tokeniser finds the correct sequence of events.
   */
  @Test public void testSeq2() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    List<TextEvent> e = t.tokenize("  xx");
    assertEquals(2, e.size());
    assertEquals(new SpaceEvent("  "), e.get(0));
    assertEquals(new WordEvent("xx"), e.get(1));
  }

  /**
   * Tests that the tokeniser finds the correct sequence of events.
   */
  @Test public void testSeq3() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    List<TextEvent> e = t.tokenize("  xx\n");
    assertEquals(3, e.size());
    assertEquals(new SpaceEvent("  "), e.get(0));
    assertEquals(new WordEvent("xx"), e.get(1));
    assertEquals(SpaceEvent.NEW_LINE, e.get(2));
  }

  /**
   * Tests that the tokeniser finds the correct sequence of events.
   */
  @Test public void testSeq4() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    List<TextEvent> e = t.tokenize("  xx\n\n");
    assertEquals(4, e.size());
    assertEquals(new SpaceEvent("  "), e.get(0));
    assertEquals(new WordEvent("xx"), e.get(1));
    assertEquals(SpaceEvent.NEW_LINE, e.get(2));
    assertEquals(SpaceEvent.NEW_LINE, e.get(3));
  }

  /**
   * Tests that the tokeniser finds the correct sequence of events.
   */
  @Test public void testSeq5() {
    TextTokenizer t = new TokenizerByWord(WhiteSpaceProcessing.PRESERVE);
    List<TextEvent> e = t.tokenize("  \n\nxx");
    assertEquals(4, e.size());
    assertEquals(new SpaceEvent("  "), e.get(0));
    assertEquals(SpaceEvent.NEW_LINE, e.get(1));
    assertEquals(SpaceEvent.NEW_LINE, e.get(2));
    assertEquals(new WordEvent("xx"), e.get(3));
  }

}


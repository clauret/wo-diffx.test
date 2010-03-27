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

import java.util.NoSuchElementException;

import org.junit.Test;

import com.topologi.diffx.event.DiffXEvent;
import com.topologi.diffx.event.impl.SpaceEvent;
import com.topologi.diffx.event.impl.WordEvent;

/**
 * Test case for the tokenizer.
 * 
 * @author Christophe Lauret
 * @version 3 April 2005  
 */
public final class TextTokeniserByWordTest {

  /**
   * Tests that a <code>NullPointerException</code> is thrown for a </code>null</code>
   * character sequence. 
   */
  @Test public void testNull() {
    try {
      TextTokeniser ct = new TextTokeniserByWord(null);
      assertTrue(false);
    } catch (NullPointerException ex) {
      assertTrue(true);
    }
  }

  /**
   * Tests that an empty array is returned for empty string.
   */
  @Test  public void testEmpty() {
    TextTokeniser ct = new TextTokeniserByWord("");
    assertEquals(0, ct.countTokens());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokeniser counts the correct number of tokens.
   */
  @Test public void testCountToken1() {
    assertEquals(1, new TextTokeniserByWord(" ").countTokens());
    assertEquals(2, new TextTokeniserByWord(" a").countTokens());
    assertEquals(2, new TextTokeniserByWord("a ").countTokens());
    assertEquals(3, new TextTokeniserByWord(" b ").countTokens());
    assertEquals(3, new TextTokeniserByWord("b b").countTokens());
    assertEquals(4, new TextTokeniserByWord("c c ").countTokens());
    assertEquals(4, new TextTokeniserByWord(" c c").countTokens());
    assertEquals(5, new TextTokeniserByWord(" d d ").countTokens());
    assertEquals(5, new TextTokeniserByWord("d d d").countTokens());
  }

  /**
   * Tests that the tokeniser counts the correct number of tokens.
   */
  @Test public void testCountToken2() {
    assertEquals(1, new TextTokeniserByWord(" ").countTokens());
    assertEquals(2, new TextTokeniserByWord("  a").countTokens());
    assertEquals(2, new TextTokeniserByWord("aa ").countTokens());
    assertEquals(2, new TextTokeniserByWord(" aa").countTokens());
    assertEquals(2, new TextTokeniserByWord("a  ").countTokens());
    assertEquals(3, new TextTokeniserByWord(" bb ").countTokens());
    assertEquals(3, new TextTokeniserByWord("b bb").countTokens());
    assertEquals(3, new TextTokeniserByWord("b   bb").countTokens());
    assertEquals(4, new TextTokeniserByWord("xx  yy  ").countTokens());
  }

  /**
   * Tests that the tokeniser counts the correct number of tokens.
   */
  @Test public void testCountToken3() {
    assertEquals(1, new TextTokeniserByWord(" ").countTokens());
    assertEquals(3, new TextTokeniserByWord("  \na").countTokens());
    assertEquals(3, new TextTokeniserByWord("aa \n").countTokens());
    assertEquals(3, new TextTokeniserByWord(" \naa").countTokens());
    assertEquals(4, new TextTokeniserByWord("a \n ").countTokens());
    assertEquals(4, new TextTokeniserByWord(" bb\n ").countTokens());
    assertEquals(4, new TextTokeniserByWord("b\n bb").countTokens());
    assertEquals(5, new TextTokeniserByWord("b \n  bb").countTokens());
    assertEquals(7, new TextTokeniserByWord("xx \n yy\n  ").countTokens());
  }

  /**
   * Tests that the tokeniser counts the correct number of tokens.
   */
  @Test public void testCountToken4() {
    assertEquals(1, new TextTokeniserByWord("\n").countTokens());
    assertEquals(3, new TextTokeniserByWord("\n \n").countTokens());
    assertEquals(3, new TextTokeniserByWord(" \n\n").countTokens());
    assertEquals(3, new TextTokeniserByWord("\n\n\n").countTokens());
  }

  /**
   * Tests that the tokeniser finds a space event as token.
   */
  @Test public void testSpace1() {
    TextTokeniser ct = new TextTokeniserByWord(" ");
    assertEquals(1, ct.countTokens());
    DiffXEvent space = ct.nextToken();
    assertEquals(new SpaceEvent(" "), space);
    assertSame(SpaceEvent.SINGLE_WHITESPACE, space);
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokeniser finds a space event as token.
   */
  @Test public void testSpace2() {
    TextTokeniser ct = new TextTokeniserByWord("  ");
    assertEquals(1, ct.countTokens());
    DiffXEvent space = ct.nextToken();
    assertEquals(new SpaceEvent("  "), space);
    assertSame(SpaceEvent.DOUBLE_WHITESPACE, space);
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokeniser finds a space event as token.
   */
  @Test public void testSpace3() {
    TextTokeniser ct = new TextTokeniserByWord("\n");
    assertEquals(1, ct.countTokens());
    DiffXEvent space = ct.nextToken();
    assertEquals(new SpaceEvent("\n"), space);
    assertSame(SpaceEvent.NEW_LINE, space);
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokeniser finds a word event as token.
   */
  @Test public void testWord1() {
    TextTokeniser ct = new TextTokeniserByWord("x");
    assertEquals(1, ct.countTokens());
    assertEquals(new WordEvent("x"), ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokeniser finds the correct sequence of events.
   */
  @Test public void testSeq1() {
    TextTokeniser ct = new TextTokeniserByWord("xx  ");
    assertEquals(2, ct.countTokens());
    assertEquals(new WordEvent("xx"), ct.nextToken());
    assertEquals(new SpaceEvent("  "), ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokeniser finds the correct sequence of events.
   */
  @Test public void testSeq2() {
    TextTokeniser ct = new TextTokeniserByWord("  xx");
    assertEquals(2, ct.countTokens());
    assertEquals(new SpaceEvent("  "), ct.nextToken());
    assertEquals(new WordEvent("xx"), ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokeniser finds the correct sequence of events.
   */
  @Test public void testSeq3() {
    TextTokeniser ct = new TextTokeniserByWord("  xx\n");
    assertEquals(3, ct.countTokens());
    assertEquals(new SpaceEvent("  "), ct.nextToken());
    assertEquals(new WordEvent("xx"), ct.nextToken());
    assertEquals(SpaceEvent.NEW_LINE, ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokeniser finds the correct sequence of events.
   */
  @Test public void testSeq4() {
    TextTokeniser ct = new TextTokeniserByWord("  xx\n\n");
    assertEquals(4, ct.countTokens());
    assertEquals(new SpaceEvent("  "), ct.nextToken());
    assertEquals(new WordEvent("xx"), ct.nextToken());
    assertEquals(SpaceEvent.NEW_LINE, ct.nextToken());
    assertEquals(SpaceEvent.NEW_LINE, ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Tests that the tokeniser finds the correct sequence of events.
   */
  @Test public void testSeq5() {
    TextTokeniser ct = new TextTokeniserByWord("  \n\nxx");
    assertEquals(4, ct.countTokens());
    assertEquals(new SpaceEvent("  "), ct.nextToken());
    assertEquals(SpaceEvent.NEW_LINE, ct.nextToken());
    assertEquals(SpaceEvent.NEW_LINE, ct.nextToken());
    assertEquals(new WordEvent("xx"), ct.nextToken());
    assertNoMoreTokens(ct);
  }

  /**
   * Asserts that this was the last token of the tokeniser.
   * 
   * <p>Checks that the {@link TextTokeniserByWord#nextToken()} method throws a
   * <code>NoSuchElementException</code>.
   * 
   * @param ct The character tokeniser to check.
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


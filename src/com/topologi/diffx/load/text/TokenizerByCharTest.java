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

import java.util.List;

import org.junit.Test;

import com.topologi.diffx.event.DiffXEvent;
import com.topologi.diffx.event.TextEvent;
import com.topologi.diffx.event.impl.CharactersEvent;
import com.topologi.diffx.event.impl.SpaceEvent;

/**
 * Test case for the tokenizer.
 *
 * @author Christophe Lauret
 * @version 10 May 2010
 */
public final class TokenizerByCharTest {

  /**
   * Tests that a <code>NullPointerException</code> is thrown for a </code>null</code>
   * character sequence.
   */
  @Test public void testNull() {
    try {
      TokenizerByChar t = new TokenizerByChar();
      t.tokenize(null);
      assertTrue(false);
    } catch (NullPointerException ex) {
      assertTrue(true);
    }
  }

  /**
   * Tests that an empty array is returned for empty string.
   */
  @Test public void testEmpty() {
    TokenizerByChar t = new TokenizerByChar();
    List<TextEvent> e = t.tokenize("");
    assertEquals(0, e.size());
  }

  /**
   * Tests that the tokeniser counts the correct number of tokens.
   */
  @Test public void testCountToken1() {
    TokenizerByChar t = new TokenizerByChar();
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
    TokenizerByChar t = new TokenizerByChar();
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
   * Tests that the tokeniser finds a space event as token.
   */
  @Test public void testSpace1() {
    TokenizerByChar t = new TokenizerByChar();
    List<TextEvent> e = t.tokenize(" ");
    assertEquals(1, e.size());
    DiffXEvent space = e.get(0);
    assertEquals(new SpaceEvent(" "), space);
    assertSame(SpaceEvent.SINGLE_WHITESPACE, space);
  }

  /**
   * Tests that the tokeniser finds a space event as token.
   */
  @Test public void testSpace3() {
    TokenizerByChar t = new TokenizerByChar();
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
    TokenizerByChar t = new TokenizerByChar();
    List<TextEvent> e = t.tokenize("x");
    assertEquals(1, e.size());
    assertEquals(new CharactersEvent("x"), e.get(0));
  }

}


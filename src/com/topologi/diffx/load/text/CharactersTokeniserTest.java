/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.load.text;


/**
 * Test case for the tokenizer.
 *
 * @author Christophe Lauret
 * @version 17 December 2004
 */
public final class CharactersTokeniserTest {

  // TODO

//  /**
//   * Tests that a <code>NullPointerException</code> is thrown for a </code>null</code>
//   * character sequence.
//   */
//  @Test public void testNull() {
//    try {
//      TextTokeniser ct = new CharactersTokeniser(null);
//      assertTrue(false);
//    } catch (NullPointerException ex) {
//      assertTrue(true);
//    }
//  }
//
//  /**
//   * Tests that an empty array is returned for empty string.
//   */
//  @Test public void testEmpty() {
//    TextTokeniser ct = new CharactersTokeniser("");
//    assertEquals(0, ct.countTokens());
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Tests that the tokeniser counts the correct number of tokens.
//   */
//  @Test public void testCountToken1() {
//    assertEquals(1, new CharactersTokeniser(" ").countTokens());
//    assertEquals(2, new CharactersTokeniser(" a").countTokens());
//    assertEquals(2, new CharactersTokeniser("a ").countTokens());
//    assertEquals(3, new CharactersTokeniser(" b ").countTokens());
//    assertEquals(3, new CharactersTokeniser("b b").countTokens());
//    assertEquals(4, new CharactersTokeniser("c c ").countTokens());
//    assertEquals(4, new CharactersTokeniser(" c c").countTokens());
//    assertEquals(5, new CharactersTokeniser(" d d ").countTokens());
//    assertEquals(5, new CharactersTokeniser("d d d").countTokens());
//  }
//
//  /**
//   * Tests that the tokeniser counts the correct number of tokens.
//   */
//  @Test public void testCountToken2() {
//    assertEquals(1, new CharactersTokeniser(" ").countTokens());
//    assertEquals(2, new CharactersTokeniser("  a").countTokens());
//    assertEquals(2, new CharactersTokeniser("aa ").countTokens());
//    assertEquals(2, new CharactersTokeniser(" aa").countTokens());
//    assertEquals(2, new CharactersTokeniser("a  ").countTokens());
//    assertEquals(3, new CharactersTokeniser(" bb ").countTokens());
//    assertEquals(3, new CharactersTokeniser("b bb").countTokens());
//    assertEquals(3, new CharactersTokeniser("b   bb").countTokens());
//    assertEquals(4, new CharactersTokeniser("xx  yy  ").countTokens());
//
//  }
//
//  /**
//   * Tests that the tokeniser finds a space event as token.
//   */
//  @Test public void testSpace1() {
//    TextTokeniser ct = new CharactersTokeniser(" ");
//    assertEquals(1, ct.countTokens());
//    DiffXEvent space = ct.nextToken();
//    assertEquals(new SpaceEvent(" "), space);
//    assertSame(SpaceEvent.SINGLE_WHITESPACE, space);
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Tests that the tokeniser finds a space event as token.
//   */
//  @Test public void testSpace2() {
//    TextTokeniser ct = new CharactersTokeniser("  ");
//    assertEquals(1, ct.countTokens());
//    DiffXEvent space = ct.nextToken();
//    assertEquals(new SpaceEvent("  "), space);
//    assertSame(SpaceEvent.DOUBLE_WHITESPACE, space);
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Tests that the tokeniser finds a space event as token.
//   */
//  @Test public void testSpace3() {
//    TextTokeniser ct = new CharactersTokeniser("\n");
//    assertEquals(1, ct.countTokens());
//    DiffXEvent space = ct.nextToken();
//    assertEquals(new SpaceEvent("\n"), space);
//    assertSame(SpaceEvent.NEW_LINE, space);
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Tests that the tokeniser finds a word event as token.
//   */
//  @Test public void testWord1() {
//    TextTokeniser ct = new CharactersTokeniser("x");
//    assertEquals(1, ct.countTokens());
//    assertEquals(new WordEvent("x"), ct.nextToken());
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Tests that the tokeniser finds the correct sequence of events.
//   */
//  @Test public void testSeq1() {
//    TextTokeniser ct = new CharactersTokeniser("xx  ");
//    assertEquals(2, ct.countTokens());
//    assertEquals(new WordEvent("xx"), ct.nextToken());
//    assertEquals(new SpaceEvent("  "), ct.nextToken());
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Tests that the tokeniser finds the correct sequence of events.
//   */
//  @Test public void testSeq2() {
//    TextTokeniser ct = new CharactersTokeniser("  xx");
//    assertEquals(2, ct.countTokens());
//    assertEquals(new SpaceEvent("  "), ct.nextToken());
//    assertEquals(new WordEvent("xx"), ct.nextToken());
//    assertNoMoreTokens(ct);
//  }
//
//  /**
//   * Asserts that this was the last token of the tokeniser.
//   *
//   * <p>Checks that the {@link CharactersTokeniser#nextToken()} method throws a
//   * <code>NoSuchElementException</code>.
//   *
//   * @param ct The character tokeniser to check.
//   */
//  public void assertNoMoreTokens(TextTokeniser ct) {
//    try {
//      ct.nextToken();
//      assertTrue(false);
//    } catch (NoSuchElementException ex) {
//      assertTrue(true);
//    }
//  }

}


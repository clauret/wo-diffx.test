/*
 * Copyright (c) 1999-2012 weborganic systems pty. ltd.
 */
package com.topologi.diffx.xml.esc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

/**
 * @author Christophe Lauret
 * @version 25/06/2014
 *
 */
public abstract class XMLEscapeUTF8TestBase extends XMLEscapeTestBase {

  /**
   * Test the attribute escape method for empty string.
   */
  @Test
  public void testToAttributeValue_EmptyString() throws IOException {
    String got = escapeAttribute("");
    assertEquals("", got);
  }

  /**
   * Test the attribute escapes correctly ASCII characters which doe not require escape
   */
  @Test
  public void testToAttributeValue_ASCII() throws IOException {
    for (int i = 0x20; i < 0x7F; i++) {
      if (i != '<' && i != '&' && i != '"' && i != '\'') {
        String got = escapeAttribute("");
        assertEquals("", got);
        assertAttributeIsNotEscaped(i);
      }
    }
  }

  /**
   * Test the attribute escapes correctly characters which must be escaped.
   */
  @Test
  public void testToAttributeValue_Required() throws IOException {
    assertAttributeIsEscaped('&');
    assertAttributeIsEscaped('<');
    assertAttributeIsEscaped('"');
    assertAttributeIsEscaped('\'');
  }

  /**
   * Test the attribute ignores correctly control characters C0 and C1 except white space.
   */
  @Test
  public void testToAttributeValue_ControlCharacters() throws IOException {
    String c0 = rangeToString(0, 0x20);
    assertEquals("\t\n\r", escapeAttribute(c0));
    String c1 = rangeToString(0x7F, 0xA0);
    assertEquals("", escapeAttribute(c1));
  }

  /**
   * Test the attribute escapes correctly individual characters.
   */
  @Test
  public void testToAttributeValue_BMP() throws IOException {
    for (int i = 0xA0; i < 0xD7FF; i++) {
      assertTextIsNotEscaped(i);
    }
  }

  /**
   * Test the attribute escapes correctly characters outside BMP
   */
  @Test
  public void testToAttributeValue_OutsideBMP() throws IOException {
    int monkey = 0x1f64a; // Speak-no-evil monkey
    assertAttributeIsNotEscaped(monkey);
  }

  /**
   * Test the element escape method for empty string.
   */
  @Test
  public void testToElementText_EmptyString() throws IOException {
    String got = escapeText("");
    assertEquals("", got);
  }

  /**
   * Test the attribute escapes correctly ASCII characters which doe not require escape
   */
  @Test
  public void testToTextValue_ASCII() throws IOException {
    for (int i = 0x20; i < 0x7F; i++) {
      if (i != '<' && i != '>' && i != '&') {
        assertTextIsNotEscaped(i);
      }
    }
  }

  /**
   * Test the attribute escapes correctly characters which must be escaped.
   */
  @Test
  public void testToTextValue_Required() throws IOException {
    assertTextIsEscaped('&');
    assertTextIsEscaped('<');
  }

  /**
   * Test the attribute escapes correctly individual characters.
   */
  @Test
  public void testToTextValue_GreaterThan() throws IOException {
    assertTextIsNotEscaped('>');
    assertEquals("]]&gt;", escapeText("]]>"));
  }

  /**
   * Test the attribute ignores correctly control characters C0 and C1 except white space.
   */
  @Test
  public void testToTextValue_ControlCharacters() throws IOException {
    String c0 = rangeToString(0, 0x20);
    assertEquals("\t\n\r", escapeText(c0));
    String c1 = rangeToString(0x7F, 0xA0);
    assertEquals("", escapeText(c1));
  }

  /**
   * Test the attribute escapes correctly individual characters.
   */
  @Test
  public void testToTextValue_BMP() throws IOException {
    for (int i = 0xA0; i < 0xD7FF; i++) {
      assertTextIsNotEscaped(i);
    }
  }

  /**
   * Test the attribute escapes correctly characters outside BMP
   */
  @Test
  public void testToTextValue_OutsideBMP() throws IOException {
    int monkey = 0x1f64a; // Speak-no-evil monkey
    assertTextIsNotEscaped(monkey);
  }

  @Override
  abstract String escapeAttribute(String value) throws IOException;

  @Override
  abstract String escapeText(String value) throws IOException;

}
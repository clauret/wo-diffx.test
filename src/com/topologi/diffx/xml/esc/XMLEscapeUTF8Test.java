/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.xml.esc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * A test class for the XML escape tool. 
 * 
 * @author Christophe Lauret
 * @version 7 February 2005
 */
public final class XMLEscapeUTF8Test {

  /**
   * The maximum value for an ASCII character.
   */  
  private static final int MAX_ASCII = 0x80;

  /**
   * The instance under testing.
   */
  XMLEscape esc = XMLEscapeUTF8.UTF8_ESCAPE;

// test the attribute escape method -----------------------------------------------------------

  /**
   * Test the attribute escape method for <code>null</code>.
   */
  @Test public void testToAttributeValue_Null() {
    assertNull(esc.toAttributeValue(null));    
  }

  /**
   * Test the attribute escape method for empty string.
   */
  @Test public void testToAttributeValue_EmptyString() {
    assertEquals("", esc.toAttributeValue(""));    
  }

  /**
   * Test the attribute escape method for a number of characters that do not require escaping,
   * check that the correct value is returned.
   */
  @Test public void testToAttributeValue_RegularStrings() {
    String st = "1234567890abcdefghijklmnopqrstuvwxyz";
    char[] c = st.toCharArray();
    for (int i = 0; i < c.length; i++) {
      for (int j = 0; j < (c.length - i); j++) {
        String exp = st.substring(i, i+j);
        assertEquals(exp, esc.toAttributeValue(c, i, j));
      }
    }
  }

  /**
   * Test the attribute escapes correctly individual characters.
   */
  @Test public void testToAttributeValue_IndividualCharsASCII() {
    char[] c = new char[1];
    for (int i = 0; i < MAX_ASCII; i++) {
      c[0] = (char)i;
      String s = new String(c);
      // control characters C0 should be pruned out.
      if (i < 32 && i != 0x9 && i != 0xA && i != 0xD) {
        assertEquals("", esc.toAttributeValue(s));
        assertEquals("", esc.toAttributeValue(c, 0, 1));
      } else if (i == 0x7F) {
        assertEquals("", esc.toAttributeValue(s));
        assertEquals("", esc.toAttributeValue(c, 0, 1));
      } else if (i == (int)'&') {
        assertEquals("&amp;", esc.toAttributeValue(s));
        assertEquals("&amp;", esc.toAttributeValue(c, 0, 1));
      } else if (i == (int)'\'') {
        assertEquals("&apos;", esc.toAttributeValue(s));
        assertEquals("&apos;", esc.toAttributeValue(c, 0, 1));
      } else if (i == (int)'"') {
        assertEquals("&quot;", esc.toAttributeValue(s));
        assertEquals("&quot;", esc.toAttributeValue(c, 0, 1));
      } else if (i == (int)'<') {
        assertEquals("&lt;", esc.toAttributeValue(s));
        assertEquals("&lt;", esc.toAttributeValue(c, 0, 1));
      } else {
        assertEquals(s, esc.toAttributeValue(s));
        assertEquals(s, esc.toAttributeValue(c, 0, 1));
      }
    }
  }

// test the attribute escape method -----------------------------------------------------------

  /**
   * Test the element escape method for <code>null</code>.
   */
  @Test public void testToElementText_Null() {
    assertNull(esc.toElementText(null));    
  }

  /**
   * Test the element escape method for empty string.
   */
  @Test public void testToElementText_EmptyString() {
    assertEquals("", esc.toElementText(""));    
  }

  /**
   * Test the element escape method for a number of characters that do not require escaping,
   * check that the correct value is returned.
   */
  @Test public void testToElementText_RegularStrings() {
    String st = "1234567890abcdefghijklmnopqrstuvwxyz";
    char[] c = st.toCharArray();
    for (int i = 0; i < c.length; i++) {
      for (int j = 0; j < (c.length - i); j++) {
        String exp = st.substring(i, i+j);
        assertEquals(exp, esc.toElementText(c, i, j));
        assertEquals(exp, esc.toElementText(new String(c, i, j)));
      }
    }
  }

  /**
   * Test the attribute escapes correctly individual characters.
   */
  @Test public void testToElementText_IndividualCharsASCII() {
    char[] c = new char[1];
    for (int i = 0; i < MAX_ASCII; i++) {
      c[0] = (char)i;
      System.err.println(i);
      String s = new String(c);
      // control characters C0 should be pruned out.
      if (i < 32 && i != 0x9 && i != 0xA && i != 0xD) {
        assertEquals("", esc.toElementText(s));
        assertEquals("", esc.toElementText(c, 0, 1));
      } else if (i == 0x7F) {
        assertEquals("", esc.toAttributeValue(s));
        assertEquals("", esc.toAttributeValue(c, 0, 1));
      } else if (i == (int)'&') {
        assertEquals("&amp;", esc.toElementText(s));
        assertEquals("&amp;", esc.toElementText(c, 0, 1));
      } else if (i == (int)'<') {
        assertEquals("&lt;", esc.toElementText(s));
        assertEquals("&lt;", esc.toElementText(c, 0, 1));
      } else {
        assertEquals(s, esc.toElementText(s));
        assertEquals(s, esc.toElementText(c, 0, 1));
      }
    }
  }
}

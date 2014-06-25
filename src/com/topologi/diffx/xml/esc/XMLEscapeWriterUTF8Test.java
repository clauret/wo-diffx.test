/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.xml.esc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;


/**
 * A test class for the XML escape tool.
 *
 * @author Christophe Lauret
 * @version 7 February 2005
 */
public final class XMLEscapeWriterUTF8Test extends XMLEscapeUTF8TestBase {

  /**
   * Test the attribute escape method for <code>null</code>.
   */
  @Test
  public void testToAttributeValue_Null() throws IOException {
    String got = escapeAttribute(null);
    assertEquals("", got);
  }

  /**
   * Test the element escape method for <code>null</code>.
   */
  @Test public void testToElementText_Null() throws IOException {
    String got = escapeText(null);
    assertEquals("", got);
  }

  @Override
  public String escapeAttribute(String value) throws IOException {
    StringWriter got = new StringWriter();
    XMLEscapeWriterUTF8 esc = new XMLEscapeWriterUTF8(got);
    esc.writeAttValue(value);
    return got.toString();
  }

  @Override
  public String escapeText(String value) throws IOException {
    StringWriter got = new StringWriter();
    XMLEscapeWriterUTF8 esc = new XMLEscapeWriterUTF8(got);
    esc.writeText(value);
    return got.toString();
  }

}

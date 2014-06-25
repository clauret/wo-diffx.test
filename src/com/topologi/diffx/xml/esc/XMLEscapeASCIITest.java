/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.xml.esc;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * A test class for the XML escape tool.
 *
 * @author Christophe Lauret
 * @version 15 May 2005
 */
public final class XMLEscapeASCIITest extends XMLEscapeASCIITestBase {

  private final XMLEscape esc = XMLEscapeFactory.getInstance("ASCII");

  /**
   * Test the attribute escape method for <code>null</code>.
   */
  @Test public void testToAttributeValue_Null() throws IOException {
    String got = escapeAttribute(null);
    Assert.assertNull(got);
  }

  /**
   * Test the element escape method for <code>null</code>.
   */
  @Test public void testToElementText_Null() throws IOException {
    String got = escapeText(null);
    Assert.assertNull(got);
  }

  @Override
  String escapeAttribute(String value) throws IOException {
    return this.esc.toAttributeValue(value);
  }

  @Override
  String escapeText(String value) throws IOException {
    return this.esc.toElementText(value);
  }

}

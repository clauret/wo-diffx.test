/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.xml;

import java.io.IOException;
import java.io.Writer;

import com.topologi.diffx.xml.XMLWriter;
import com.topologi.diffx.xml.XMLWriterImpl;

/**
 * A test class for the <code>XMLWriterNSImpl</code>
 * 
 * @author Christophe Lauret
 * @version 14 May 2005
 */
public final class XMLWriterImplTest extends BaseXMLWriterTest {

  /**
   * Default constructor. 
   * 
   * @param name Name of the test suite.
   */
  public XMLWriterImplTest(String name) {
    super(name);
  }

  /**
   * @see com.topologi.diffx.xml.BaseXMLWriterTest#makeXMLWriter(java.io.Writer)
   */
  public XMLWriter makeXMLWriter(Writer writer) {
    return new XMLWriterImpl(writer);
  }

// test: unsupported methods ------------------------------------------------------------

  /**
   * Tests that the namesspace aware open element method throws an
   * unsupported open element exception.
   * 
   * @see XMLWriterImpl#openElement(String, String, boolean)
   * 
   * @throws IOException Should an IO error occur.
   */
  public void testUnsupportedOpenElement() throws IOException {
    try {
      this.xml.openElement("", "x", true);
      System.err.println("The XML writer failed to report an unsupported operation.");
      assertTrue(false);
    } catch (UnsupportedOperationException ex) {
      assertTrue(true);
    }
  }

  /**
   * Tests that the namesspace aware empty element method throws an
   * unsupported open element exception.
   * 
   * @see XMLWriterImpl#emptyElement(String, String)
   * 
   * @throws IOException Should an IO error occur.
   */
  public void testUnsupportedEmptyElement() throws IOException {
    try {
      this.xml.openElement("", "x", true);
      System.err.println("The XML writer failed to report an unsupported operation.");
      assertTrue(false);
    } catch (UnsupportedOperationException ex) {
      assertTrue(true);
    }
  }

  /**
   * Tests that the namesspace aware set prefix mapping method throws an
   * unsupported open element exception.
   * 
   * @see XMLWriterImpl#setPrefixMapping(String, String)
   * 
   * @throws IOException Should an IO error occur.
   */
  public void testUnsupportedPrefixMapping() throws IOException {
    try {
      this.xml.setPrefixMapping("", "x");
      System.err.println("The XML writer failed to report an unsupported operation.");
      assertTrue(false);
    } catch (UnsupportedOperationException ex) {
      assertTrue(true);
    }
  }

  /**
   * Tests that the namesspace aware attribute method throws an
   * unsupported open element exception.
   * 
   * @see XMLWriterImpl#attribute(String, String, String)
   * 
   * @throws IOException Should an IO error occur.
   */
  public void testUnsupportedAttributeA() throws IOException {
    try {
      this.xml.attribute("", "x", "m");
      System.err.println("The XML writer failed to report an unsupported operation.");
      assertTrue(false);
    } catch (UnsupportedOperationException ex) {
      assertTrue(true);
    }
  }

  /**
   * Tests that the namesspace aware attribute method throws an
   * unsupported open element exception.
   * 
   * @see XMLWriterImpl#attribute(String, String, int)
   * 
   * @throws IOException Should an IO error occur.
   */
  public void testUnsupportedAttributeB() throws IOException {
    try {
      this.xml.attribute("", "x", 0);
      System.err.println("The XML writer failed to report an unsupported operation.");
      assertTrue(false);
    } catch (UnsupportedOperationException ex) {
      assertTrue(true);
    }
  }

}

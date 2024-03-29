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

import com.topologi.diffx.xml.UndeclaredNamespaceException;
import com.topologi.diffx.xml.XMLWriter;
import com.topologi.diffx.xml.XMLWriterNSImpl;

/**
 * A test class for the <code>XMLWriterNSImpl</code>
 * 
 * @author Christophe Lauret
 * @version 12 May 2005
 */
public final class XMLWriterNSImplTest extends BaseXMLWriterTest {

  /**
   * A namespace uri for testing.
   */
  public static final String URI_TEST_1 = "http://www.test.net/001";

  /**
   * A namespace uri for testing.
   */
  public static final String URI_TEST_2 = "http://www.test.net/002";

  /**
   * Default constructor. 
   * 
   * @param name Name of the test suite.
   */
  public XMLWriterNSImplTest(String name) {
    super(name);
  }

  /**
   * @see com.topologi.diffx.xml.BaseXMLWriterTest#makeXMLWriter(java.io.Writer)
   */
  public XMLWriter makeXMLWriter(Writer writer) {
    return new XMLWriterNSImpl(writer);
  }

  /**
   * Tests that the writer throws an exception when attempting to write an element
   * on an undeclared (not prefixed or mapped) namespace.
   * 
   * @throws IOException Should an I/O error occur.
   */
  public void testUndeclaredNamespaces1() throws IOException {
    try {
      this.xml.openElement("http://www.test.com/2004", "test", false);
      this.xml.closeElement();
      assertTrue(false);
    } catch (UndeclaredNamespaceException ex) {
      assertTrue(true);
    } catch (Exception ex) {
      assertTrue(false);
    }
  }

  /**
   * Tests that the writer does not throw an exception when writing an element
   * which namespace has been declared.
   * 
   * @throws IOException Should an I/O error occur.
   */
  public void testUndeclaredNamespaces2() throws IOException {
    this.xml.setPrefixMapping(URI_TEST_1, "");
    this.xml.openElement(URI_TEST_1, "test", false);
    this.xml.closeElement();
    assertEquivalent("<test xmlns='"+URI_TEST_1+"'/>", getXMLString());
  }

  /**
   * Tests that the writer does not throw an exception when writing an element
   * on the default namespace (using URI = "");
   * 
   * @throws IOException Should an I/O error occur.
   */
  public void testUndeclaredNamespaces3() throws IOException {
    xml.openElement("", "test", false);
    xml.closeElement();
    assertEquivalent("<test/>", getXMLString());
  }

  /**
   * Tests that the writer does not throw an exception when writing an element
   * on the default namespace (using URI = null).
   * 
   * @throws IOException Should an I/O error occur.
   */
  public void testUndeclaredNamespaces4() throws IOException {
    this.xml.openElement(null, "test", false);
    this.xml.closeElement();
    assertEquivalent("<test/>", getXMLString());
  }

  /**
   * Tests that the namespace can change for the default prefix.
   * 
   * @throws IOException Should an I/O error occur.
   */
  public void testNamespaceSwitch0() throws IOException {
    this.xml.setPrefixMapping(URI_TEST_1, "");
    this.xml.openElement(URI_TEST_1, "test", true);
    this.xml.setPrefixMapping("", "");
    this.xml.openElement("", "empty", false);
    this.xml.closeElement();
    this.xml.closeElement();
    this.xml.flush();
    String expected = "<test xmlns='"+URI_TEST_1+"'>" +
                      "<empty xmlns=''/>" +
                      "</test>";
    assertEquivalent(expected, getXMLString());
  }

  /**
   * Tests that the namespace can change for the different prefixes.
   * 
   * @throws IOException Should an I/O error occur.
   */
  public void testNamespaceSwitch1() throws IOException {
    this.xml.setPrefixMapping(URI_TEST_1, "");
    this.xml.openElement(URI_TEST_1, "test", true);
    // switch once
    this.xml.setPrefixMapping("", "");
    this.xml.setPrefixMapping(URI_TEST_1, "xx");
    this.xml.openElement(URI_TEST_1, "empty", false);
    this.xml.closeElement();
    // intermediary elemnt to test
    this.xml.emptyElement(URI_TEST_1, "empty");
    // switch twice
    this.xml.setPrefixMapping("", "");
    this.xml.setPrefixMapping(URI_TEST_1, "xx");
    this.xml.openElement(URI_TEST_1, "empty", false);
    this.xml.closeElement();
    // final element
    this.xml.emptyElement(URI_TEST_1, "empty");
    this.xml.closeElement();
    this.xml.flush();
    // expected
    String expected = "<test xmlns='"+URI_TEST_1+"'>" +
                      "<xx:empty xmlns='' xmlns:xx='"+URI_TEST_1+"'/>" +
                      "<empty/>" +
                      "<xx:empty xmlns='' xmlns:xx='"+URI_TEST_1+"'/>" +
                      "<empty/>" +
                      "</test>";
    assertEquivalent(expected, getXMLString());
  }

}

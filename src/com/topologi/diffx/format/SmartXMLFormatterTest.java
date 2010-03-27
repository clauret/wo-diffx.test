/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.format;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.topologi.diffx.DiffXException;
import com.topologi.diffx.event.impl.AttributeEventNSImpl;
import com.topologi.diffx.event.impl.CloseElementEventNSImpl;
import com.topologi.diffx.event.impl.OpenElementEventNSImpl;
import com.topologi.diffx.load.SAXRecorder;
import com.topologi.diffx.sequence.EventSequence;

/**
 * Test class for the smart XML formatter.
 * 
 * <p>The {@link com.topologi.diffx.format.SmartXMLFormatter} must also pass the
 * {@link com.topologi.diffx.format.BaseXMLFormatterTest}, therefore this class
 * should only contain tests that specific to the <code>SmartXMLFormatter</code>.
 * 
 * @author Christophe Lauret
 * @version 16 December 2004  
 */
public final class SmartXMLFormatterTest {

// class attributes ---------------------------------------------------------------------------

  /**
   * The loader being tested.  
   */
  SAXRecorder recorder = new SAXRecorder();

  /**
   * The formatter being tested.  
   */
  DiffXFormatter formatter = null;

  /**
   * The string writer.  
   */
  StringWriter w = null;

// constructors and jUnit method --------------------------------------------------------------

  /**
   * @see junit.framework.TestCase#setUp()
   */
  @Before public void setUp() throws Exception {
    this.w = new StringWriter();
    this.formatter = new SmartXMLFormatter(this.w);
  }

// opening and closing elements ---------------------------------------------------------------

  /**
   * Test open and closing an element.
   * 
   * @throws DiffXException Should an error occur while parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  @Test public void testOpenAndClose0() throws DiffXException, IOException {
    this.formatter.format(new OpenElementEventNSImpl("a"));
    this.formatter.format(new CloseElementEventNSImpl("a"));
    assertEquivalentToXML("<a/>");
  }

  /**
   * Test open and closing mismatching elements.
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  @Test public void testOpenAndClose1() throws DiffXException, IOException {
    this.formatter.format(new OpenElementEventNSImpl("a"));
    this.formatter.format(new CloseElementEventNSImpl("b"));
    assertEquivalentToXML("<a/>");
  }

  /**
   * Test open and closing mismatching elements.
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  @Test public void testOpenAndClose2() throws DiffXException, IOException {
    this.formatter.format(new OpenElementEventNSImpl("a"));
    this.formatter.insert(new CloseElementEventNSImpl("b"));
    assertEquivalentToXML("<a/>");
  }

  /**
   * Test open and closing mismatching elements.
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  @Test public void testOpenAndClose3() throws DiffXException, IOException {
    this.formatter.format(new OpenElementEventNSImpl("a"));
    this.formatter.delete(new CloseElementEventNSImpl("b"));
    assertEquivalentToXML("<a/>");
  }

// playing with attributes --------------------------------------------------------------------

  /**
   * Test formatting an attribute.
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  @Test public void testAttributes0() throws DiffXException, IOException {
    this.formatter.format(new OpenElementEventNSImpl("a"));
    this.formatter.format(new AttributeEventNSImpl("x", "", "y"));
    this.formatter.format(new CloseElementEventNSImpl("a"));
    assertEquivalentToXML("<a x='y'/>");
  }

  /**
   * Test formatting an attribute.
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  @Test public void testAttributes1() throws DiffXException, IOException {
    this.formatter.format(new OpenElementEventNSImpl("a"));
    this.formatter.insert(new AttributeEventNSImpl("x", "", "y"));
    this.formatter.format(new CloseElementEventNSImpl("a"));
    assertEquivalentToXML("<a x='y'/>");
  }

  /**
   * Test formatting an attribute.
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  @Test public void testAttributes2() throws DiffXException, IOException {
    this.formatter.format(new OpenElementEventNSImpl("a"));
    this.formatter.delete(new AttributeEventNSImpl("x", "", "y"));
    this.formatter.format(new CloseElementEventNSImpl("a"));
    assertEquivalentToXML("<a xmlns:del='http://www.topologi.org/2004/Diff-X/Delete' del:x='y'/>");
  }

// helpers ------------------------------------------------------------------------------------

  /**
   * Tests whether the content generated by the formatter is equivalent to the specified XML. 
   * 
   * @param xml The first XML to test.
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  private void assertEquivalentToXML(String xml) throws DiffXException, IOException {
    // process the XML to get the sequence
    Reader xmlr = new StringReader(xml);
    EventSequence exp = this.recorder.process(new InputSource(xmlr));
    // process the output of the formatter
    Reader xmlr2 = new StringReader(this.w.toString());
    EventSequence seq = this.recorder.process(new InputSource(xmlr2));
    try {
      assertEquals(exp.size(), seq.size());
      assertEquals(exp, seq);
    } catch (AssertionFailedError ex) {
      PrintWriter pw = new PrintWriter(System.err);
      seq.export(pw);
      pw.flush();
      throw ex;
    }
    System.err.println(this.w.toString());
  }

  /**
   * 
   */
  public XMLDiffXFormatter makeFormatter() throws IOException {
    return new SmartXMLFormatter(new StringWriter());
  }

}

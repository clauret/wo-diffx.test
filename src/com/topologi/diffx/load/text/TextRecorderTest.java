/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.load.text;

import java.io.IOException;
import java.io.PrintWriter;

import com.topologi.diffx.DiffXException;
import com.topologi.diffx.event.impl.LineEvent;
import com.topologi.diffx.load.TextRecorder;
import com.topologi.diffx.sequence.EventSequence;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * Test class for the text recorder.
 * 
 * @author Christophe Lauret
 * @version 3 April 2005
 */
public final class TextRecorderTest extends TestCase {

  /**
   * The recorder being tested.  
   */
  TextRecorder recorder = new TextRecorder();

  /**
   * Default constructor.
   * 
   * @param name The name of the loader.
   */
  public TextRecorderTest(String name) {
    super(name);
  }

  /**
   * Tests a simple case.
   * 
   * @throws IOException Should an I/O exception occur.
   * @throws DiffXException Should an error occur while reading the text.
   */
  public void testSimpleLine0() throws IOException, DiffXException {
    String text = "line 1\n"
               +  "line2\n";
    EventSequence exp = new EventSequence();
    exp.addEvent(new LineEvent("line 1", 1));
    exp.addEvent(new LineEvent("line2", 2));
    assertEquals(exp, text);
  }

  /**
   * Tests a simple case.
   * 
   * @throws IOException Should an I/O exception occur.
   * @throws DiffXException Should an error occur while reading the text.
   */
  public void testSimpleLine2() throws IOException, DiffXException {
    String text = "line #1\n"
               +  "line #2\n"
               +  "line #3\n"
               +  "line #4";
    EventSequence exp = new EventSequence();
    exp.addEvent(new LineEvent("line #1", 1));
    exp.addEvent(new LineEvent("line #2", 2));
    exp.addEvent(new LineEvent("line #3", 3));
    exp.addEvent(new LineEvent("line #4", 4));
    assertEquals(exp, text);
  }

  /**
   * Tests a simple case.
   * 
   * @throws IOException Should an I/O exception occur.
   * @throws DiffXException Should an error occur while reading the text.
   */
  public void testEmptyLine() throws IOException, DiffXException {
    String text = "line #1\n"
               +  "\n"
               +  "line #3\n"
               +  "line #4";
    EventSequence exp = new EventSequence();
    exp.addEvent(new LineEvent("line #1", 1));
    exp.addEvent(new LineEvent("", 2));
    exp.addEvent(new LineEvent("line #3", 3));
    exp.addEvent(new LineEvent("line #4", 4));
    assertEquals(exp, text);
  }

  /**
   * Tests a simple case.
   * 
   * <pre>
   *   &lt;a&gt;XX&lt;/a&gt;
   * </pre>
   * 
   * @throws IOException Should an I/O exception occur.
   * @throws DiffXException Should an error occur while reading the text.
   */
  public void testXMLLine0() throws IOException, DiffXException {
    String text = "<a>XX</a>";
    EventSequence exp = new EventSequence();
    exp.addEvent(new LineEvent("<a>XX</a>", 1));
    assertEquals(exp, text);
  }

  /**
   * Tests parsing the &amp;lt;, it should remain the same.
   * 
   * @throws IOException Should an I/O exception occur.
   * @throws DiffXException Should an error occur while reading the text.
   */
  public void testEncoding1() throws IOException, DiffXException {
    String text = "&lt;";
    EventSequence exp = new EventSequence();
    exp.addEvent(new LineEvent("&lt;", 1));
    assertEquals(exp, text);
  }

  /**
   * Tests parsing character &amp;#x8012;, it should remain the same.
   * 
   * @throws IOException Should an I/O exception occur.
   * @throws DiffXException Should an error occur while reading the text.
   */
  public void testEncoding3() throws IOException, DiffXException {
    String xml = "&#x8012;";
    EventSequence exp = new EventSequence();
    exp.addEvent(new LineEvent("&#x8012;", 1));
    assertEquals(exp, xml);
  }

// helpers ------------------------------------------------------------------------------------

  /**
   * Checks that the given XML is equivalent to the given event sequence.
   * 
   * @param exp The expected event sequence.
   * @param xml The XML to test.
   * 
   * @throws IOException    Should an I/O exception occur.
   * @throws DiffXException Should an error occur while parsing XML.
   */
  private void assertEquals(EventSequence exp, String xml) throws IOException, DiffXException {
    // process the strings
    EventSequence seq = this.recorder.process(xml);
    try {
      assertEquals(exp.size(), seq.size());
      assertEquals(exp, seq);
    } catch (AssertionFailedError ex) {
      PrintWriter pw = new PrintWriter(System.err);
      seq.export(pw);
      pw.flush();
      throw ex;
    }
  }

}

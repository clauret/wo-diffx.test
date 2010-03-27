/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.format;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.xml.sax.InputSource;

import com.topologi.diffx.DiffXException;
import com.topologi.diffx.format.XMLDiffXFormatter;
import com.topologi.diffx.load.SAXRecorder;
import com.topologi.diffx.sequence.EventSequence;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * Test class common to all XML formatters.
 * 
 * <p>XML formatters should be able to round trip some XML document, in order words, if they
 * are given a sequence of events generated from one XML, they should be able to generate an
 * XML document that is equivalent.
 * 
 * @author Christophe Lauret
 * @version 12 May 2005  
 */
public abstract class BaseXMLFormatterTest extends TestCase {

// class attributes ---------------------------------------------------------------------------

  /**
   * The loader being tested.  
   */
  private transient SAXRecorder recorder = new SAXRecorder();

  /**
   * The formatter being tested.  
   */
  private transient XMLDiffXFormatter formatter = null;

  /**
   * The string writer.  
   */
  private transient StringWriter w = null;

// constructors and jUnit method --------------------------------------------------------------

  /**
   * Default constructor.
   * 
   * @param name Name of the test.
   */
  public BaseXMLFormatterTest(String name) {
    super(name);
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected final void setUp() throws Exception {
    this.w = new StringWriter();
    this.formatter = makeFormatter(this.w);
  }

  /**
   * Generates the formatter to be tested by this class.
   * 
   * @param writer The writer this formatter should use.
   * 
   * @return The XML Diffx Formatter to use.
   * 
   * @throws IOException Should and error occur
   */
  public abstract XMLDiffXFormatter makeFormatter(Writer writer) throws IOException;

// formatting and round-tripping --------------------------------------------------------------

  /**
   * Tests opening and closing an element.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;&lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testOpenClose0() throws DiffXException, IOException {
    String xml = "<a></a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests with an empty element.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a/&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testEmptyElement0() throws DiffXException, IOException {
    String xml = "<a/>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting text.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;x&lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testText0() throws DiffXException, IOException {
    String xml = "<a>x</a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting text.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;xx y zzz&lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testText1() throws DiffXException, IOException {
    String xml = "<a>xx y zzz</a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting an entity.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;&amp;lt;&lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testEntity0() throws DiffXException, IOException {
    String xml = "<a>&lt;</a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting an entity.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;&amp;#8012;&lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testEntity1() throws DiffXException, IOException {
    String xml = "<a>&#8012;</a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting spaces.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt; &lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testSpace0() throws DiffXException, IOException {
    String xml = "<a> </a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting spaces.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;
   *   &lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testSpace1() throws DiffXException, IOException {
    String xml = "<a>\n</a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting spaces.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;    &lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testSpace2() throws DiffXException, IOException {
    String xml = "<a>    </a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting an element with one child.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;
   *     &lt;b/&gt;
   *   &lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testChild0() throws DiffXException, IOException {
    String xml = "<a>\n  <b/>\n</a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting an element with one child.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;
   *     &lt;b&gt;xx&lt;/b&gt;
   *   &lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testChild1() throws DiffXException, IOException {
    String xml = "<a>\n  <b>xx</b>\n</a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting an element with atributes.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a x='y'/&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testAttributes0() throws DiffXException, IOException {
    String xml = "<a x='y'/>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests formatting an element with atributes.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a x='y' w='z' d='e'/&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testAttributes1() throws DiffXException, IOException {
    String xml = "<a x='y' w='z' d='e'/>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests round-tripping of an XML document.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;
   *     &lt;b&gt;xx&lt;/b&gt;
   *     &lt;c&gt;yy&lt;/c&gt;
   *   &lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testChildren0() throws DiffXException, IOException {
    String xml = "<a>\n  <b>xx</b>\n  <c>yy</c>\n</a>";
    assertRoundTripOK(xml);
  }

  /**
   * Tests round-tripping of an XML document.
   * 
   * <p>Test with the following XML:
   * <pre>
   *   &lt;a&gt;xx &lt;b&gt;yy&lt;/b&gt; zz&lt;/a&gt;
   * </pre>
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  public final void testMixedContent0() throws DiffXException, IOException {
    String xml = "<a>xx <b>yy</b> zz</a>";
    assertRoundTripOK(xml);
  }

//helpers ------------------------------------------------------------------------------------

  /**
   * Prepare the sequences and returns a sequence slicer on them.
   * 
   * @param xml The first XML to test.
   * 
   * @throws DiffXException Should an error occur whilst parsing one of the XML files. 
   * @throws IOException    Should an I/O error occur.
   */
  private void assertRoundTripOK(String xml) throws DiffXException, IOException {
    // process the XML to get the sequence
    Reader xmlr = new StringReader(xml);
    EventSequence exp = this.recorder.process(new InputSource(xmlr));
    // format the sequence
    for (int i = 0; i < exp.size(); i++) {
      this.formatter.format(exp.getEvent(i));
    }
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

}


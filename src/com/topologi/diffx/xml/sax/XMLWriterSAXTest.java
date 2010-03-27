/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.xml.sax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.topologi.diffx.xml.IllegalCloseElementException;
import com.topologi.diffx.xml.UnclosedElementException;

/**
 * A class for testing <code>XMLWriterSAX</code>s implementation.
 * 
 * @author Christophe Lauret
 * @version 26 May 2005
 */
public final class XMLWriterSAXTest {

  /**
   * The XML reader.
   */
  private XMLReader reader;

  /**
   * The XML writer to test 
   */
  private XMLWriterSAX xml;

  /**
   * The content handler used for checking.
   */
  private ContentHandlerChecker handler;

// Setup ------------------------------------------------------------------------------------

  /**
   * Sets up the handler for testing.
   */
  @Before public void setUpHandler() throws SAXException {
    this.handler = new ContentHandlerChecker();
    this.xml = new XMLWriterSAX(this.handler);
    if (reader == null) {
      reader = XMLReaderFactory.createXMLReader();
      reader.setFeature("http://xml.org/sax/features/validation", false);
      reader.setFeature("http://xml.org/sax/features/namespaces", true);
      reader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
    }
  }

// test: openElement / closeElement -----------------------------------------------------------  

  /**
   * Checks that the XML is closed properly.
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testCloseElementA() throws IOException {
    this.xml.openElement("test");
    this.xml.closeElement();
    this.xml.close();
    assertEquivalent("<test/>");
  }

  /**
   * Check that the XML is closed properly.
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testCloseElementB() throws IOException {
    this.xml.openElement("x");
    this.xml.openElement("y");
    this.xml.closeElement();
    this.xml.closeElement();
    this.xml.close();
    assertEquivalent("<x><y/></x>");
  }

  /**
   * Check that the XML is closed properly.
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testCloseElementIllegal() throws IOException {
    this.xml.openElement("test");
    this.xml.closeElement();
    try {
      this.xml.closeElement();
      System.err.println("The XML writer failed to report an unclosed element.");
      assertTrue(false);
    } catch (IllegalCloseElementException ex) {
      assertTrue(true);
    }
  }

// test: element -----------------------------------------------------------------------

  /**
   * Checks that the element method works
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testElementWithText() throws IOException {
    this.xml.element("x", "text");
    this.xml.close();
    assertEquivalent("<x>text</x>");
  }

  /**
   * Checks that the element method works
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testElementNoText() throws IOException {
    this.xml.element("x", "");
    this.xml.close();
    assertEquivalent("<x/>");
  }

  /**
   * Checks that the element method works
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testElementNullText() throws IOException {
    this.xml.element("x", null);
    this.xml.close();
    assertEquivalent("<x/>");
  }

// test: empty element ------------------------------------------------------------------

  /**
   * Checks that the empty element method works.
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testEmptyElement() throws IOException {
    this.xml.emptyElement("x");
    this.xml.close();
    assertEquivalent("<x/>");
  }

// test: attributes ---------------------------------------------------------------------

  /**
   * Checks that the empty element method works.
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testAttributeA() throws IOException {
    this.xml.openElement("x");
    this.xml.attribute("a", "m");
    this.xml.closeElement();
    this.xml.close();
    assertEquivalent("<x a='m'/>");
  }

  /**
   * Checks that the empty element method works.
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testAttributeB() throws IOException {
    this.xml.openElement("x");
    this.xml.attribute("a", "m");
    this.xml.attribute("b", "n");
    this.xml.closeElement();
    this.xml.close();
    assertEquivalent("<x a='m' b='n'/>");
  }

// test: comment ------------------------------------------------------------------------

  /**
   * Checks that the empty element method works.
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testComment() throws IOException {
    this.xml.openElement("root");
	  this.xml.writeComment("comment");
	  this.xml.closeElement();
    this.xml.close();
    assertEquivalent("<root><!-- comment --></root>");
  }

  /**
   * Checks that the empty element method works.
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testCommentNull() throws IOException {
    this.xml.openElement("root");
    this.xml.writeComment(null);
    this.xml.closeElement();
    this.xml.close();
    assertEquivalent("<root></root>");
  }

// test: processing instructions --------------------------------------------------------

  /**
   * Checks that the empty element method works.
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testProcessingInstructionA() throws IOException {
    this.xml.openElement("root");
    this.xml.writePI("x", "y");
    this.xml.closeElement();
    this.xml.close();
    assertEquivalent("<root><?x y?></root>");
  }

// test: close --------------------------------------------------------------------------

  /**
   * Check that the XML writer throws an exception when trying to close it
   * with a remaining open element.
   * 
   * @see UnclosedElementException
   * 
   * @throws IOException If an I/O error occurs. 
   */
  @Test public final void testCloseUnclosedException() throws IOException {
    this.xml.openElement("x");
    try {
      this.xml.close();
      System.err.println("The XML writer failed to report an unclosed element.");
      assertTrue(false);
    } catch (UnclosedElementException ex) {
      assertTrue(true);
    }
  }

// public methods to test checks --------------------------------------------------------

  /**
   * Asserts that the two XML are equivalent.
   * 
   * @param exp The expected XML.
   */
  public final void assertEquivalent(String exp) {
    try {
      reader.setContentHandler(this.handler);
      reader.parse(new InputSource(new StringReader(exp)));
    } catch (Throwable ex) {
      System.err.println("<!-- expected XML was: -->");
      System.err.println(exp);
      throw new AssertionFailedError(ex.getMessage());
    }
  }

  /**
   * 
   * @author Christophe Lauret (Allette Systems)
   * @version 26 May 2005
   */
  private static final class ContentHandlerChecker implements ContentHandler {

    /**
     * The list of events it should produce.
     */
    private List<String> events = new ArrayList<String>();

    /**
     * The next event to check.
     */
    private transient int counter = 0;

    /**
     * The mode.
     */
    private boolean loading = true;

    /**
     * @see ContentHandler#characters(char[], int, int) 
     */
    public void characters(char[] ch, int start, int length) {
      String exp = "characters(\""+new String(ch, start, length)+"\");";
      processEvent(exp);
    }

    /**
     * @see ContentHandler#startElement(String, String, String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String local, String qName, Attributes atts) {
      // ignore the Q name and the attributes for now
      String exp = "startElement(\""+uri+"\", \""+local+"\");";
      processEvent(exp);
    }

    /**
     * @see ContentHandler#endElement(String, String, String)
     */
    public void endElement(String uri, String local, String qName) {
      // ignore the Q name
      String exp = "endElement(\""+uri+"\", "+local+"\");";
      processEvent(exp);
    }

    /**
     * @see ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    public void startPrefixMapping(String prefix, String uri) {
      String exp = "startPrefixMapping(\""+prefix+"\", \""+uri+"\");";
      processEvent(exp);
    }

    /**
     * @see ContentHandler#endPrefixMapping(java.lang.String, java.lang.String)
     */
    public void endPrefixMapping(String prefix) {
      String exp = "endPrefixMapping(\""+prefix+"\");";
      processEvent(exp);
    }

    /**
     * @see ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace(char[] ch, int start, int length) {
      String exp = "ignorableWhitespace(\""+new String(ch, start, length)+"\");";
      processEvent(exp);
    }

    /**
     * @see ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    public void processingInstruction(String target, String data) throws SAXException {
      String exp = "processingInstruction(\""+target+"\", \""+data+"\");";
      processEvent(exp);
    }

    /**
     * @see ContentHandler#skippedEntity(String) 
     */
    public void skippedEntity(String name) {
      assertTrue(false);
    }

    /**
     * This method is ignored.
     * 
     * @see ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    public void setDocumentLocator(Locator locator) {
    }

    /**
     * Does nothing as this event should be generated externally.
     */
    public void startDocument() {
    }

    /**
     * Does nothing as this event should be generated externally.
     */
    public void endDocument() {
      loading = !loading;
    }

    /**
     * Asserts that the event given as a string is the same event
     * as the expected event.
     * 
     * @param exp The expected event as a string.
     */
    private void processEvent(String exp) {
      if (loading) {
        if (true) System.err.println(exp);
        this.events.add(exp); 
      } else {
        assertTrue(counter < events.size());
        assertEquals(exp, events.get(counter++));
      }
    }

  }

}

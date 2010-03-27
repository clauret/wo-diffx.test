/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.sequence;

import java.io.IOException;

import com.topologi.diffx.load.LoadingException;
import com.topologi.diffx.load.SAXRecorder;
import com.topologi.diffx.sequence.EventSequence;
import com.topologi.diffx.sequence.EventSequenceUtils;

import junit.framework.TestCase;


/**
 * Test case for the event sequence utility.  
 * 
 * @author Christophe Lauret
 * @version 9 March 2005
 */
public final class EventSequenceUtilsTest extends TestCase {

  /**
   * Default constructor.
   * 
   * @param name Name of the test.
   */
  public EventSequenceUtilsTest(String name) {
    super(name);
  }

  /**
   * Test the maximum depth.
   * 
   * @throws IOException If an I/O error occurs. 
   * @throws LoadingException If the loader cannot load the XML.
   */
  public void testMaxDepth1() throws IOException, LoadingException {
    EventSequence seq = new SAXRecorder().process("<a/>");
    int max = EventSequenceUtils.getMaxDepth(seq);
    assertEquals(1, max);
  }

  /**
   * Test the maximum depth.
   * 
   * @throws IOException If an I/O error occurs. 
   * @throws LoadingException If the loader cannot load the XML.
   */
  public void testMaxDepth2() throws IOException, LoadingException {
    EventSequence seq = new SAXRecorder().process("<a><a/></a>");
    int max = EventSequenceUtils.getMaxDepth(seq);
    assertEquals(2, max);
  }

  /**
   * Test the maximum depth.
   * 
   * @throws IOException If an I/O error occurs. 
   * @throws LoadingException If the loader cannot load the XML.
   */
  public void testMaxDepth3() throws IOException, LoadingException {
    EventSequence seq = new SAXRecorder().process("<a><b/><b/></a>");
    int max = EventSequenceUtils.getMaxDepth(seq);
    assertEquals(2, max);
  }

  /**
   * Test the maximum depth.
   * 
   * @throws IOException If an I/O error occurs. 
   * @throws LoadingException If the loader cannot load the XML.
   */
  public void testMaxDepth4() throws IOException, LoadingException {
    EventSequence seq = new SAXRecorder().process("<a><b><c/></b><b/></a>");
    int max = EventSequenceUtils.getMaxDepth(seq);
    assertEquals(3, max);
  }

  /**
   * Test the maximum depth.
   * 
   * @throws IOException If an I/O error occurs. 
   * @throws LoadingException If the loader cannot load the XML.
   */
  public void testMaxElementContent0() throws IOException, LoadingException {
    EventSequence seq = new SAXRecorder().process("<a/>");
    int max = EventSequenceUtils.getMaxElementContent(seq);
    assertEquals(0, max);
  }

  /**
   * Test the maximum depth.
   * 
   * @throws IOException If an I/O error occurs. 
   * @throws LoadingException If the loader cannot load the XML.
   */
  public void testMaxElementContent1() throws IOException, LoadingException {
    EventSequence seq = new SAXRecorder().process("<a>x</a>");
    int max = EventSequenceUtils.getMaxElementContent(seq);
    assertEquals(1, max);
  }

  /**
   * Test the maximum depth.
   * 
   * @throws IOException If an I/O error occurs. 
   * @throws LoadingException If the loader cannot load the XML.
   */
  public void testMaxElementContent2() throws IOException, LoadingException {
    EventSequence seq = new SAXRecorder().process("<a>x y</a>");
    int max = EventSequenceUtils.getMaxElementContent(seq);
    assertEquals(3, max);
  }

}

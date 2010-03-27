/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.test;

import java.io.IOException;
import java.io.StringWriter;


import com.topologi.diffx.config.DiffXConfig;
import com.topologi.diffx.event.DiffXEvent;
import com.topologi.diffx.format.DiffXFormatter;
import com.topologi.diffx.sequence.EventSequence;

/**
 * A Diff-X formatter implementation used solely for testing.
 * 
 * <p>This formatter which write exactly what receives using the abstract representation of
 * each event and adding a plus / minus sign for insertions / deletion. This class is useful
 * to test the output of an algorithm.
 * 
 * @see com.topologi.diffx.test.EventUtils 
 * 
 * @author Christophe Lauret
 * @version 16 December 2004
 */
public final class TestFormatter implements DiffXFormatter {

  /**
   * Set to <code>true</code> to show debug info.
   */
  private static final boolean DEBUG = System.getProperty("DEBUG") != null;

  /**
   * Where the output goes.
   */
  private final StringWriter out; 

  /**
   * Creates a new test formatter
   */
  public TestFormatter() {
    this.out = new StringWriter();
  }

  /**
   * Writes the abstract representation.
   * 
   * @see com.topologi.diffx.format.DiffXFormatter#format(com.topologi.diffx.event.DiffXEvent)
   */
  public void format(DiffXEvent e) throws IOException {
    out.write(EventUtils.toAbstractString(e));
    out.flush();
    if (DEBUG) System.err.println(EventUtils.toAbstractString(e));
  }

  /**
   * Writes a plus sign '+' followed by the abstract representation.
   * 
   * @see com.topologi.diffx.format.DiffXFormatter#insert(com.topologi.diffx.event.DiffXEvent)
   */
  public void insert(DiffXEvent e) throws IOException {
    out.write("+"+EventUtils.toAbstractString(e));
    out.flush();
    if (DEBUG) System.err.println("+"+EventUtils.toAbstractString(e));
  }

  /**
   * Writes a minus sign '-' followed by the abstract representation.
   * 
   * @see com.topologi.diffx.format.DiffXFormatter#delete(com.topologi.diffx.event.DiffXEvent)
   */
  public void delete(DiffXEvent e) throws IOException {
    out.write("-"+EventUtils.toAbstractString(e));
    out.flush();
    if (DEBUG) System.err.println("-"+EventUtils.toAbstractString(e));
  }

  /**
   * Ignored as the config does not change the format output in this case.
   * 
   * @see com.topologi.diffx.format.DiffXFormatter#setConfig(com.topologi.diffx.DiffXConfig)
   */
  public void setConfig(DiffXConfig config) {
  }

  /**
   * Formats the entire sequence by formatting each event.
   * 
   * @param seq The event sequence to format
   * 
   * @throws IOException Should an I/O exception be thrown by the <code>format</code> method.
   */
  public void format(EventSequence seq) throws IOException {
    for (int i = 0; i < seq.size(); i++) {
      format(seq.getEvent(i));
    }
    out.flush();
  }

  /**
   * Returns the output of the formatter.
   * 
   * @return The output of the formatter.
   */
  public String getOutput() {
    return this.out.toString();
  }

}

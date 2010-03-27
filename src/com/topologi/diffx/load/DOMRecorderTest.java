/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.load;

import com.topologi.diffx.config.DiffXConfig;
import com.topologi.diffx.load.DOMRecorder;
import com.topologi.diffx.load.XMLRecorder;

/**
 * Test class for the DOM Recorder.
 * 
 * @author Christophe Lauret
 * @version 14 April 2005  
 */
public final class DOMRecorderTest extends XMLRecorderNSTest {

  /**
   * Default constructor.
   * 
   * @param name The name of the loader.
   */
  public DOMRecorderTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   */
  public XMLRecorder makeXMLRecorder(DiffXConfig config) {
    DOMRecorder recorder = new DOMRecorder();
    recorder.setConfig(config);
    return recorder;
  }

}

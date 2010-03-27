/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.algorithm;

import com.topologi.diffx.algorithm.DiffXAlgorithm;
import com.topologi.diffx.algorithm.DiffXKumarRangan;
import com.topologi.diffx.sequence.EventSequence;

/**
 * Test case for Diff-X algorithm using the Smart matrix LCS algorithm.
 * 
 * @author Christophe Lauret
 * @version 7 March 2005
 */
public final class DiffXKumarRanganTest extends BaseAlgorithmLevel1Test {

  /**
   * Default constructor.
   * 
   * @param name Name of the test.
   */
  public DiffXKumarRanganTest(String name) {
    super(name);
  }

  /**
   * @see BaseAlgorithmLevel1Test#makeDiffX(com.topologi.diffx.EventSequence, com.topologi.diffx.EventSequence)
   */
  public DiffXAlgorithm makeDiffX(EventSequence seq1, EventSequence seq2) {
    return new DiffXKumarRangan(seq1, seq2);
  }
}

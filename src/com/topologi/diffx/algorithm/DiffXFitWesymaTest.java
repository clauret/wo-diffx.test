/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.algorithm;

import com.topologi.diffx.algorithm.DiffXAlgorithm;
import com.topologi.diffx.algorithm.DiffXFitWesyma;
import com.topologi.diffx.sequence.EventSequence;

/**
 * Test case for Diff-X algorithm using the Weighted Symmetrical Matrix LCS algorithm.
 * 
 * @author Christophe Lauret
 * @version 4 April 2005
 */
public final class DiffXFitWesymaTest extends BaseAlgorithmLevel1Test {

  /**
   * Default constructor.
   * 
   * @param name Name of the test.
   */
  public DiffXFitWesymaTest(String name) {
    super(name);
  }

  /**
   * @see BaseAlgorithmLevel1Test#makeDiffX(com.topologi.diffx.EventSequence, com.topologi.diffx.EventSequence)
   */
  public DiffXAlgorithm makeDiffX(EventSequence seq1, EventSequence seq2) {
    DiffXFitWesyma algo = new DiffXFitWesyma(seq1, seq2);
    return algo;
  }

}

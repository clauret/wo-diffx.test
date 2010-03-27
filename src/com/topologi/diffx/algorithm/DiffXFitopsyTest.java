/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.algorithm;

import com.topologi.diffx.algorithm.DiffXAlgorithm;
import com.topologi.diffx.algorithm.DiffXFitopsy;
import com.topologi.diffx.sequence.EventSequence;

/**
 * Test case for Diff-X algorithm.
 * 
 * @author Christophe Lauret
 * @version 14 April 2005
 */
public final class DiffXFitopsyTest extends BaseAlgorithmLevel2Test {

  /**
   * Default constructor.
   * 
   * @param name Name of the test.
   */
  public DiffXFitopsyTest(String name) {
    super(name);
  }

  /**
   * @see BaseAlgorithmLevel1Test#makeDiffX(com.topologi.diffx.EventSequence, com.topologi.diffx.EventSequence)
   */
  public DiffXAlgorithm makeDiffX(EventSequence seq1, EventSequence seq2) {
    DiffXFitopsy algo = new DiffXFitopsy(seq1, seq2);
    return algo;
  }
 
}

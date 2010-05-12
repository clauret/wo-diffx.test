/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.event.impl;

import org.junit.Assert;
import org.junit.Test;

import com.topologi.diffx.event.DiffXEvent;
import com.topologi.diffx.event.TextEvent;

/**
 * Test class for the text recorder.
 * 
 * @author Christophe Lauret
 * @version 3 April 2005
 */
public final class IgnorableSpaceEventTest {

  @Test public void testEquals() {
    TextEvent s1 = new IgnorableSpaceEvent("");
    TextEvent s2 = new IgnorableSpaceEvent("");
    Assert.assertEquals(s1, s1);
    Assert.assertSame(s1.getClass(), s2.getClass());
    Assert.assertEquals(s1, s2);
    Assert.assertTrue(s1.equals(s2));
    Assert.assertEquals(s1.hashCode(), s2.hashCode());

  }

}

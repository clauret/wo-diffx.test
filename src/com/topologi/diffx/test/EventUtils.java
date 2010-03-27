/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx.test;

import com.topologi.diffx.event.AttributeEvent;
import com.topologi.diffx.event.DiffXEvent;
import com.topologi.diffx.event.impl.CharEvent;
import com.topologi.diffx.event.impl.CharactersEventBase;
import com.topologi.diffx.event.CloseElementEvent;
import com.topologi.diffx.event.impl.IgnorableSpaceEvent;
import com.topologi.diffx.event.impl.LineEvent;
import com.topologi.diffx.event.OpenElementEvent;
import com.topologi.diffx.event.impl.SpaceEvent;
import com.topologi.diffx.event.impl.WordEvent;

/**
 * Utility class for events and testing.
 * 
 * @author Christophe Lauret
 * @version 3 April 2005
 */
public final class EventUtils {

  /**
   * Prevents creation of instances.
   */
  private EventUtils() {
  }

  /**
   * Returns a simple representation for each event in order to recognise them depending on 
   * their class.
   * 
   * <p>This method will return <code>null</code> if it does not know how to format it. 
   * 
   * @param e The event to format
   * 
   * @return Its 'abstract' representation or <code>null</code>.
   */
  public static String toAbstractString(DiffXEvent e) {
    // TODO: handle unknown event implementations nicely.
    // an element to open
    if (e instanceof OpenElementEvent) {
      return '<'+((OpenElementEvent)e).getName()+'>';

    // an element to close
    } else if (e instanceof CloseElementEvent) {
      return "</"+((CloseElementEvent)e).getName()+'>';

    // an attribute
    } else if (e instanceof AttributeEvent) {
      return "@{"+((AttributeEvent)e).getName()+'='+((AttributeEvent)e).getValue()+'}';

    // a word
    } else if (e instanceof WordEvent) {
      return "$w{"+((CharactersEventBase)e).getCharacters()+'}';

    // a white space event
    } else if (e instanceof SpaceEvent) {
      return "$s{"+((CharactersEventBase)e).getCharacters()+'}';

    // a single character
    } else if (e instanceof CharEvent) {
      return "$c{"+((CharactersEventBase)e).getCharacters()+'}';

    // an ignorable space event
    } else if (e instanceof IgnorableSpaceEvent) {
      return "$i{"+((IgnorableSpaceEvent)e).getCharacters()+'}';
      
    // a single line
    } else if (e instanceof LineEvent) {
      return "$L"+((LineEvent)e).getLineNumber();
    }
    return null;
  }

}

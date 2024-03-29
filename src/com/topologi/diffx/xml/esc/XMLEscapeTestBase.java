/*
 * Copyright (c) 1999-2012 weborganic systems pty. ltd.
 */
package com.topologi.diffx.xml.esc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

/**
 * @author Christophe Lauret
 * @version 25/06/2014
 *
 */
public abstract class XMLEscapeTestBase {

  /**
   *
   */
  public XMLEscapeTestBase() {
    super();
  }

  /**
    * Computes possible entities for the specified code point.
    *
    * @param c the codepoint
    *
    * @return A list of possible entities including numeric and named
    */
  protected List<String> getEntities(int c) {
     List<String> entities = new ArrayList<String>(2);
     if (c == '<') entities.add("&lt;");
     else if (c == '>') entities.add("&gt;");
     else if (c == '&') entities.add("&amp;");
     else if (c == '"') entities.add("&quot;");
     else if (c == '\'') entities.add("&apos;");
     // decimal
     entities.add("&#"+c+";");
     // hexadecimal
     entities.add("&#x"+Integer.toHexString(c)+";");
     return entities;
   }

  /**
    * @param from start of range (inclusive)
    * @param to   end of range (exclusive)
    *
    * @return character range as a string
    */
  protected String rangeToString(int from, int to) {
     StringBuilder raw = new StringBuilder();
     for (int i = from; i < to; i++) {
       raw.append((char)i);
     }
     return raw.toString();
   }

  abstract String escapeAttribute(String value) throws IOException;

  abstract String escapeText(String value) throws IOException;

  protected void assertIsEscaped(int codepoint, String got) throws IOException {
     // Check if result matches
     boolean correct = false;
     List<String> expected = getEntities(codepoint);
     for (String exp : expected) {
       if (exp.equals(got)) correct = true;
     }
     Assert.assertTrue("Expected one of "+expected+" got '"+got+"'", correct);
   }

  protected void assertAttributeIsEscaped(int codepoint) throws IOException {
     String raw = new String(Character.toChars(codepoint));
     String got = escapeAttribute(raw);
     assertIsEscaped(codepoint, got);
   }

  protected void assertAttributeIsNotEscaped(int codepoint) throws IOException {
     String raw = new String(Character.toChars(codepoint));
     String got = escapeAttribute(raw);
     assertEquals(raw, got);
   }

  protected void assertTextIsEscaped(int codepoint) throws IOException {
     String raw = new String(Character.toChars(codepoint));
     String got = escapeText(raw);
     assertIsEscaped(codepoint, got);
   }

  protected void assertTextIsNotEscaped(int codepoint) throws IOException {
     String raw = new String(Character.toChars(codepoint));
     String got = escapeText(raw);
     assertEquals(raw, got);
   }

}
/*
 * This file is part of the DiffX library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package com.topologi.diffx;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.topologi.diffx.Main;
import com.topologi.diffx.config.DiffXConfig;
import com.topologi.diffx.load.LoadingException;
import com.topologi.diffx.load.SAXRecorder;
import com.topologi.diffx.sequence.EventSequence;
import com.topologi.diffx.test.TestFormatter;
import com.topologi.diffx.test.TestUtils;

/**
 * Test class for the Main class, uses the files that are stored in the config directories.
 * 
 * <p>All use cases are in:
 * <pre>
 *   /data/com/topologi/diffx/Main/source
 * </pre>
 * 
 * <p>To add a new use case:
 * 1. create a directory starting with 'UC-'
 * 2. add the two files to compare as 'a.xml' and 'b.xml'   
 * 
 * @author Christophe Lauret
 * @version 27 March 2010
 */
public final class MainTest {

  /**
   * Filter for use case directories.
   */
  private static final FileFilter USECASE_FILTER = new FileFilter() {

    /** Accept directories starting with "UC-"  */
    public boolean accept(File f) {
      return f.isDirectory() && f.getName().startsWith("UC-");
    }
  };

// static variables for use by this test case -------------------------------------------

  /**
   * The XML reader.
   */
  private static XMLReader reader;

  /**
   * The config folder.
   */
  private static File data = TestUtils.getDataDirectory(Main.class);

  /**
   * The folder containing the results.
   */
  private static File tmp = TestUtils.getTempDirectory(Main.class);

  /**
   * Folder containing all the use cases.
   */
  private static File source = new File(data, "source");

  /**
   * Folder containing the resulting Diff XML.
   */
  private static File result = new File(tmp, "result");

  /**
   * The diff-X configuration. 
   */
  private static DiffXConfig config = new DiffXConfig();
  static {
    config.setIgnoreWhiteSpace(true);
    config.setPreserveWhiteSpace(false);
    config.setWhiteSpaceProcessing(com.topologi.diffx.config.WhiteSpaceProcessing.IGNORE);
    config.setGranularity(com.topologi.diffx.config.TextGranularity.TEXT);
    System.err.println(config.getWhiteSpaceProcessing());
    System.err.println(config.getGranularity());
  }

  /**
   * Initialises the XML reader.
   *
   * @throws SAXException If one of the features could not be set.
   */
  @BeforeClass public static void setUpXMLReader() throws SAXException {
    reader = XMLReaderFactory.createXMLReader();
    reader.setFeature("http://xml.org/sax/features/validation", false);
    reader.setFeature("http://xml.org/sax/features/namespaces", true);
    reader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
  }

  /**
   * Ensures that all required folders are created.
   *
   * @throws SAXException If one of the features could not be set.
   */
  @BeforeClass public static void setUpFolders() {
    if (!result.exists()) result.mkdirs();
  }

  /**
   * Tests all the use cases located in:
   * <pre>
   *   /data/com/topologi/diffx/Main/source
   * </pre>
   *
   * @throws IOException Should an I/O error occur.
   */
  @Test public void testAll() throws IOException {
    // list all use cases
    File[] usecases = source.listFiles(USECASE_FILTER);
    // iterate over the use cases.
    for (File uc : usecases) {
      File xml1 = new File(uc, "a.xml");
      File xml2 = new File(uc, "b.xml");
      File rc = new File(result, uc.getName());
      if (!rc.exists())   { rc.mkdirs(); }
      if (!xml1.exists()) { System.err.println("missing file a.xml in "+uc.getName()); }
      if (!xml2.exists()) { System.err.println("missing file b.xml in "+uc.getName()); }
      if (xml1.exists() && xml2.exists()) {
        // setup the info print writer
        File infoFile = new File(rc, "info.txt");
        PrintStream info = new PrintStream(new BufferedOutputStream(new FileOutputStream(infoFile)), true);
        // print the sequences
        EventSequence s1 = printSequence(xml1, info);
        EventSequence s2 = printSequence(xml2, info);
        // process the diff
        long ta = processDiffX(xml1, xml2, info);
        long tb = processDiffX(xml2, xml1, info);
        System.out.println("Processed "+uc.getName()+" "+s1.size()+"x"+s2.size()+" in "+ta+"ms | "+tb+"ms ("+xml1.length()+"x"+xml1.length()+")");
      }
    }
  }

// helpers ------------------------------------------------------------------------------------

  /**
   * Processes the diff of the two documents.
   * 
   * @param xml1 The first XML doc.
   * @param xml2 The second XML doc.
   * @param info The print writer where additionnal info goes.
   * 
   * @throws IOException Should an error occur.
   */
  private long processDiffX(File xml1, File xml2, PrintStream info) throws IOException {
    long t = 0;
    info.println("Executing the diff-x as");
    // output
    int x = xml1.getName().compareTo(xml2.getName());
    File rc = new File(result, xml1.getParentFile().getName());
    File out = new File(rc, (x > 0)? "a-b.xml" : "b-a.xml");
    Writer diff = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), "utf-8"));
    info.println("  diff("+xml1.getParent()+","+xml2.getParent()+") -> "+out.getName());
    PrintStream tmp = System.err;
    try {
      long t0 = new Date().getTime();
      System.setErr(info);
//      Main.diff(xmlr1, xmlr2, diff, config);
      Main.diff(toNode(xml1, true), toNode(xml2, true), diff, config);
      t = new Date().getTime() - t0;
      info.println("Processed in "+t+"ms");
      System.setErr(tmp);
    } catch (Exception ex) {
      System.setErr(tmp);
      System.err.println("Error with diff-X of "+xml1.getName()+((x > 0)? " [+]" : " [-]"));
      System.err.println("  -> "+ex.getMessage());
      info.println("Could not be processed.");
      info.println("It generated the following exception:");
      ex.printStackTrace(info);
    } finally {
      diff.close();
      info.flush();
    }
    verifyWellFormed(out, info);
    return t;
  }

  /**
   * Prints the sequence of the given XML.
   * 
   * @param xml  The XML doc which sequence needs to be printed.;
   * @param info Where the additional information goes.
   * 
   * @throws IOException Should an error occur.
   */
  private EventSequence printSequence(File xml, PrintStream info) throws IOException {
    EventSequence s = new EventSequence(); 
    // report the sequence of tokens
    SAXRecorder recorder = new SAXRecorder();
    if (config != null) recorder.setConfig(config);
    info.println("Printing sequence....");
    info.println("  file = "+xml.getParent()+"\\"+xml.getName());
    info.println("::start__");
    try {
      s = recorder.process(xml);
      TestFormatter tf1 = new TestFormatter();
      tf1.format(s);
      info.println(tf1.getOutput());
    } catch (LoadingException ex) {
      info.println("Could no print the sequence, because of the following error:");
      ex.printStackTrace(info);
    } catch (Exception ex) {
      info.println("Could no print the sequence, because of the following error:");
      ex.printStackTrace(info);
    }
    info.println("::end__");
    info.println();
    return s;
  }

  /**
   * Check the XML file for well-formedness.
   * 
   * @param xml  The file to check.
   * @param info Where the additional info goes.
   * 
   * @throws IOException Should an error occur.
   */
  private void verifyWellFormed(File xml, PrintStream info) throws IOException {
    // check that it is well-formed with SAX
    try {
      reader.parse(new InputSource(xml.getCanonicalPath()));
    } catch (SAXException ex) {
      System.err.println(xml.getName()+" is not well formed.");
      info.println("[!] XML is NOT well-formed.");
      info.println("[!] "+ex.getMessage());
    }
  }

  /**
   * Converts the reader to a node.
   * 
   * @param xml       The reader on the XML.
   * @param isNSAware Whether the factory should be namespace aware.
   * 
   * @return The corresponding node.
   */
  private Node toNode(File xml, boolean isNSAware) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(isNSAware);
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(xml);
      return document;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

}
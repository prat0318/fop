/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELiteTests;

import MDELite.SDB;
import MDELite.Yumlpl;
import MDELite.UMLF;
import MDELite.Yuml;
import MDELite.UMLFpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author dsb
 */
public class Umlf2YumlTest {
    
    public Umlf2YumlTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    public void theWork(String filename) {
        UMLF exml = new UMLF(filename);
        UMLFpl  eumlf = exml.toUMLFpl();
        SDB esdb = eumlf.toSDB();
        Yumlpl y0 = esdb.toYumlpl("Z");
        Yuml yy0 = y0.toYuml();
        RegTest.Utility.validate(filename+"Z.yuml", "Correct/"+filename+"Z.yuml", true);
    }
    
    
    @Test
    public void testStandard1() {
        theWork("TestData/UMLF2YUML/1standard");
    }
    
    @Test
    public void testStandard2() {
        theWork("TestData/UMLF2YUML/2standard");
    }
    
    @Test
    public void testStandard3() {
        theWork("TestData/UMLF2YUML/3standard");
    }
    
    @Test
    public void testStandard4() {
        theWork("TestData/UMLF2YUML/4standard");
    }
    
    @Test
    public void testStandard5() {
        theWork("TestData/UMLF2YUML/5standard");
    }
}

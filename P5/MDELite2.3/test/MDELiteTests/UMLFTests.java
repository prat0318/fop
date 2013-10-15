/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELiteTests;

import MDELite.SDB;
import MDELite.UMLF;
import MDELite.UMLFpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author dsb
 */
public class UMLFTests {
    
    public UMLFTests() {
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
        RegTest.Utility.validate(filename+".umlf.pl", "Correct/"+filename+".umlf.pl", true);
        SDB esdb = eumlf.toSDB();
        RegTest.Utility.validate(filename+".sdb.pl", "Correct/"+filename+".sdb.pl", true);
        UMLFpl eumlf1 = esdb.toUMLFpl("1");
        RegTest.Utility.validate(filename+"1.umlf.pl", "Correct/"+filename+"1.umlf.pl", true);
        UMLF exmla = eumlf1.toUMLF();
        RegTest.Utility.validate(filename+"1g.umlf.xml", "Correct/"+filename+"1g.umlf.xml", true);
    }
    
    
    @Test
    public void testStandard1() {
        theWork("TestData/UMLF/1standard");
    }
    
    @Test
    public void testStandard2() {
        theWork("TestData/UMLF/2standard");
    }
    
    @Test
    public void testStandard3() {
        theWork("TestData/UMLF/3standard");
    }
    
    @Test
    public void testStandard4() {
        theWork("TestData/UMLF/4standard");
    }
    
}

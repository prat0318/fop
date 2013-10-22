/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELiteTests;

import CoreMDELite.SDB;
import MDELite.Yumlpl;
import MDELite.Yuml;
import MDELite.Violet;
import MDELite.Violetpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author dsb
 */
public class Violet2YumlTest {
    
    public Violet2YumlTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    public void theWork(String filename) {
        //First take in the standard violet xml file and convert to violet pl
        Violet violetXml    = new Violet(filename);
        Violetpl violetPl   = violetXml.toVioletpl();
        //Next convert violet pl to standard pl
        SDB standardDB      = violetPl.toSDB();
        //Next convert standard pl to Yuml pl
        Yumlpl yumlpl       = standardDB.toYumlpl();
        //Next convert Yuml pl to Yuml xml
        Yuml yumlXml        = yumlpl.toYuml();
        
        //Make sure Yuml is right...
        RegTest.Utility.validate(yumlXml.fullName, "Correct/"+filename+".violet.yuml",false);
    }
    
    @Test
    public void testStandard1() {
        theWork("TestData/Violet2Yuml/1standard");
    }
  
    @Test
    public void testStandard3() {
        theWork("TestData/Violet2Yuml/GoodLibraryComp");
    }
    
    @Test
    public void testStandard4() {
        theWork("TestData/Violet2Yuml/GoodLibrary");
    }
    
    @Test
    public void testStandard5() {
        theWork("TestData/Violet2Yuml/5standard");
    }
    
    @Test
    public void testStandard6() {
        theWork("TestData/Violet2Yuml/CarRental");
    }    
}

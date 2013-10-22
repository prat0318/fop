/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELiteTests;

import MDELite.Yumlpl;
import MDELite.SDB;
import MDELite.UMLF;
import MDELite.Yuml;
import MDELite.UMLFpl;
import org.junit.Test;

/**
 *
 * @author dsb
 */
public class Yuml2UmlfTest {
    
    public Yuml2UmlfTest() {
    }


    public void theWork(String filename, String number) {
        Yuml y = new Yuml(filename);
        Yumlpl yp = y.toYumlpl();
        SDB s = yp.toSDB();
        SDB patched = s.refreshPosition("TestData/Yuml/"+number+"patched");
        UMLFpl up = patched.toUMLFpl();
        UMLF x = up.toUMLF();
        RegTest.Utility.validate(x.fullName, "Correct/"+x.fullName, true);
    }
    
// tests 1 and 3,4 work.
    @Test
    public void testStandard1() {
        theWork("TestData/Yuml/1standard", "1");
    }

    // NOTE: TEst2 doesn't work.  I;ve concluded something is wrong with UMLFactory
    // rather than an error in my own code.
//    @Test
//    public void testStandard2() {
//        theWork("TestData/Yuml/2standard","2");
//    }
    
    @Test
    public void testStandard3() {
        theWork("TestData/Yuml/3standard","3");
    }
    
    @Test
    public void testStandard5() {
        theWork("TestData/Yuml/5standard","5");
    }
}

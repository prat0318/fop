/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELiteTests;

import MDELite.SDB;
import MDELite.UMLF;
import MDELite.Dot;
import MDELite.UMLFpl;
import org.junit.Test;

/**
 *
 * 
 */
public class KielerTest {
    
    public void theWork(String filename,String number) {
        SDB s1 = new SDB(filename); // start with a correct .sdb file
        Dot d1 = s1.toDot();
        Dot dk = d1.dot2dot("K");
        SDB onlyPosition = dk.toSDB();
        SDB scaledPositions = onlyPosition.scalePosition("SP");
        SDB noPosition = s1.project("TestData/Kieler/"+number+"noPosition", "libpl/removePosition.pl");
        String[] array = {noPosition.fullName,scaledPositions.fullName};
        SDB patched = new SDB("TestData/Kieler/"+number+"patched", array); 
        UMLFpl up = patched.toUMLFpl();
        UMLF result = up.toUMLF();
        RegTest.Utility.validate("TestData/Kieler/"+number+"patchedg.umlf.xml", "Correct/TestData/Kieler/"+number+"patchedg.umlf.xml", true);
    }

    @Test
    public void testMain1() {
        theWork("TestData/Kieler/1standard", "1");
    }

    @Test
    public void testMain2() {
        theWork("TestData/Kieler/2standard", "2");
    }

    @Test
    public void testMain3() {
        theWork("TestData/Kieler/3standard", "3");
    }

   @Test
    public void testMain4() {
        theWork("TestData/Kieler/4standard","4");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELiteTests;

import CoreMDELite.GProlog;
import MDELite.UMLFpl;
import org.junit.Test;

/**
 *
 * @author dsb
 */
public class ConformTest {
    public ConformTest() {
    }

    
    public void theWork(String filename) {
        String outputFile = filename+"Errors.txt";
        // conformance errors placed in file "conform.txt"
        UMLFpl u = new UMLFpl(filename);
        try {
            u.conform();
        } catch (RuntimeException e) {
            assert (e.getMessage().startsWith(GProlog.conformanceFailure));
        }
        RegTest.Utility.validate("conform.txt","Correct/"+outputFile,false);
    }
    
    @Test
    public void test1() {
        theWork("TestData/Error/10standard");
    }
    
    @Test
    public void test2() {
        theWork("TestData/Error/3standard");
    }
}

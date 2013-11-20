/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELiteTests;

import MDELite.Convert;
import org.junit.Test;


/**
 *
 * @author dsb
 */
public class Yuml2VioletTest {

    @Test
    public void test1() {
        theWork("Notepad");
    }

    @Test
    public void test2() {
        theWork("graff");
    }

    @Test
    public void test3() {
        theWork("quark");
    }

    @Test
    public void test4() {
        theWork("yumlparser");
    }
    
    @Test
    public void test5() {
        theWork("GoodLibrary");
    }
    
    
    public void theWork(String name) {
        String folder = "TestData/Yuml2Violet/";
        String[] args = { "yuml", "violet", folder+name };
        Convert.main(args);
        RegTest.Utility.validate(folder+name+"p.violet", "Correct/"+folder+name+".violet",true);
    }

}

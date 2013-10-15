/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELiteTests;

import MDELite.Violet;
import MDELite.Violetpl;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;

/**
 *
 * @author dsb
 */
public class VioletConformityTest {
    boolean testingBad;
    
    public VioletConformityTest() {
        testingBad = false;
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testMain() {
    }
    
    @Test
    public void testMain1() {
        if (!testingBad)
            basic("TestData/VioletConformity/GoodBook.class",false);
        else
            basic("TestData/VioletConformity/BadBook.class",false);
    }
    
    @Test
    public void testMain2() {
        if (!testingBad)
            basic("TestData/VioletConformity/GoodBuilding.class",false);
        else
            basic("TestData/VioletConformity/BadBuilding.class",false);
    }
    
    @Test
    public void testMain3() {
        if (!testingBad)
            basic("TestData/VioletConformity/GoodLibrary.class",false);
        else
            basic("TestData/VioletConformity/BadLibrary.class",false);
    }    
        
    @Test
    public void testMain4() {
        if (!testingBad)
            basic("TestData/VioletConformity/GoodReadable.class",false);
        else
            basic("TestData/VioletConformity/BadReadable.class",false);
    }
    
    @Test
    public void testMain5() {
        if (!testingBad)
            basic("TestData/VioletConformity/GoodLibCompBooks.class",false);
        else
            basic("TestData/VioletConformity/BadLibCompBooks.class",false);
    }    
    
    public void basic(String name, boolean sorted) {
        Violet v        = new Violet(name);
        Violetpl vpl    = v.toVioletpl();
    }
}

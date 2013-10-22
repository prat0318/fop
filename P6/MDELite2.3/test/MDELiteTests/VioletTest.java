/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELiteTests;

import CoreMDELite.SDB;
import MDELite.Violet;
import MDELite.Violetpl;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;

/**
 *
 * @author dsb
 */
public class VioletTest {
    
    public VioletTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

  
    @Test
    public void testMainUnconnected() {
        basic("TestData/Violet/uncon",false);
    }
    
    @Test
    public void testMainCar() {
        basic("TestData/Violet/CarRental",false);
    }    
    
    @Test
    public void testMainComp() {
        basic("TestData/Violet/Comp",false);
    }
    
    @Test
    public void testMain1() {
        basic("TestData/Violet/1standard",false);
    }
   
    @Test
    public void testMain2() {
        basic("TestData/Violet/2standard",true);
    }
    
    @Test
    public void testMain3() {
        basic("TestData/Violet/3standard",true);
    }
    
    @Test
    public void testMain4() {
        basic("TestData/Violet/4standard",true);
    } 
    
    @Test
    public void testMain5() {
        basic("TestData/Violet/5standard",true);
    }
    
    @Test
    public void testMain6() {
        basic("TestData/Violet/6standard",true);
    }
    
    @Test
    public void testMain7() {
        basic("TestData/Violet/7standard",true);
    }    
    
    @Test
    public void testMain10() {
        complex("TestData/Violet/1standard",false);
    }
    
    @Test
    public void testMain20() {
        complex("TestData/Violet/2standard",true);
    }
   
    @Test
    public void testMain30() {
        complex("TestData/Violet/3standard",true);
    }
    
    @Test
    public void testMain40() {
        complex("TestData/Violet/4standard",true);
    }
    
    @Test
    public void testMain50() {
        complex("TestData/Violet/5standard",true);
    }
    
    @Test
    public void testMain60() {
        complex("TestData/Violet/6standard",true);
    }
    
    @Test
    public void testMain70() {
        complex("TestData/Violet/7standard",true);
    }    
        
    public void basic(String name, boolean sorted) {
        Violet v        = new Violet(name);
        Violetpl vpl    = v.toVioletpl();
        RegTest.Utility.validate(vpl.fullName, "Correct/"+vpl.fullName,sorted);
        Violet v1       = vpl.toViolet("V");
        RegTest.Utility.validate(v1.fullName, "Correct/"+name+"V.violet",sorted);
    }
    
    public void complex(String name, boolean sorted) {
        Violet v        = new Violet(name);
        Violetpl vpl    = v.toVioletpl();
        SDB s0          = vpl.toSDB();
        Violetpl vpl2   = s0.toVioletpl("FromSDB");
        Violet v1       = vpl2.toViolet("VV");
        //System.out.println("Comparing " + v1.fullName + " with " + "Correct/"+name);
        RegTest.Utility.validate(v1.fullName, "Correct/"+name+"VV.violet",sorted);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import hashJoin.MainTest;

/**
 *
 * @author bansal
 */
public class Test1 {
    
    public Test1() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
   public void testJoin() {
        RegTest.Utility.redirectStdOut("out.txt");  // redirects standard out to file "out.txt"

        String[] args = null;
        MainTest.readRelation_Hjoin_PrintTupleTest("client.txt","viewing.txt");

        RegTest.Utility.validate("out.txt", "ClientJoinViewing.txt", false); // test passes if files are equal
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import gammaContainers.HJoinWithBloomFilter;
import gammaContainers.MapReduceHJoin;
import hashJoin.Bloom;
import hashJoin.HJoin;
import hashJoin.PrintTuple;
import hashJoin.basicConnector.Connector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hashJoin.MainTest;
import hashJoin.ReadRelation;
import hashJoin.basicConnector.ReadEnd;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
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
    public void testReadRelation_PrintTuple() {
        RegTest.Utility.redirectStdOut("out.txt");  // redirects standard out to file "out.txt"
        System.out.println("Starting ReadRelation _ PrintTuple");
        Connector read_A = new Connector("input1");
        ReadRelation r = new ReadRelation("client.txt","clientDB", read_A.getWriteEnd());
        PrintTuple p = new PrintTuple(read_A.getReadEnd());
        r.start();
        p.start();
        System.out.println("-----------------");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        MainTest.FileSort("out.txt");
        RegTest.Utility.validate("out.txt", "readRelationPrint.txt", false); // test passes if files are equal
    }    

    @Test       
    public void testBloom() throws Exception {
        System.out.println("Starting Bloom Test");
        Connector print_bloom = new Connector("print_bloom");
        ReadRelation r = new ReadRelation("client.txt","clientDB", print_bloom.getWriteEnd());
        Connector bloom_join = new Connector("bloom_join");
        PrintTuple p1 = new PrintTuple(bloom_join.getReadEnd());
        Connector bloom_bmap = new Connector("bloom_bmap");
        ReadEnd readEnd = bloom_bmap.getReadEnd();
        Bloom bloom = new Bloom(print_bloom.getReadEnd(), bloom_join.getWriteEnd(), bloom_bmap.getWriteEnd(), 0);
        r.start();
        bloom.start();
        p1.start();    
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assert("fftffftfffffffftfffffftfffff".equals(readEnd.getNextString())); 
    }
    
    @Test
    public void testJoin() {
        RegTest.Utility.redirectStdOut("out.txt");  // redirects standard out to file "out.txt"

        String[] args = null;
        Connector read_A = new Connector("input1");
        ReadRelation r1 = new ReadRelation("client.txt", "client.txt", read_A.getWriteEnd());
        Connector read_B = new Connector("input2");
        ReadRelation r2 = new ReadRelation("viewing.txt", "viewing.txt", read_B.getWriteEnd());
        Connector out = new Connector("output");
        HJoin h = new HJoin(0, read_A.getReadEnd(),0,read_B.getReadEnd(),out.getWriteEnd());
        PrintTuple p = new PrintTuple(out.getReadEnd());
        r1.start();
        r2.start();
        h.start();
        p.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MainTest.FileSort("out.txt");
        RegTest.Utility.validate("out.txt", "ClientJoinViewing.txt", false); // test passes if files are equal
    }

    @Test
    public void testJoinMapReduce() {
        RegTest.Utility.redirectStdOut("out.txt");  // redirects standard out to file "out.txt"
        Connector out2 = new Connector("output2");
        MapReduceHJoin mapJ = new MapReduceHJoin(0, "client.txt", 0 , "viewing.txt",out2.getWriteEnd() );
        mapJ.start();
        PrintTuple p2 = new PrintTuple(out2.getReadEnd());
        p2.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MainTest.FileSort("out.txt");
        RegTest.Utility.validate("out.txt", "ClientJoinViewing.txt", false); // test passes if files are equal
    }

    @Test
    public void testJoinMapReduceWithBloom() {
        RegTest.Utility.redirectStdOut("out.txt");  // redirects standard out to file "out.txt"
        Connector out2 = new Connector("output2");
        HJoinWithBloomFilter mapJ = new HJoinWithBloomFilter(0, "client.txt", 0 , "viewing.txt",out2.getWriteEnd() );
        mapJ.start();
        PrintTuple p2 = new PrintTuple(out2.getReadEnd());
        p2.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MainTest.FileSort("out.txt");
        RegTest.Utility.validate("out.txt", "ClientJoinViewing.txt", false); // test passes if files are equal
    }


}
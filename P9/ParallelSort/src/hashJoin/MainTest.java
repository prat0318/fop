/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashJoin;

import hashJoin.basicConnector.Connector;
import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;

/**
 *
 * @author bansal
 */
public class MainTest {
    public static void main(String[] args) throws Exception {
        //readRelation_PrintTupleTest();
//        readRelation_Hsplit_PrintTuple();
//       readRelation_Hjoin_PrintTupleTest();
//        readRelation_Hsplit_Merge_PrintTuple();
        bloomTest();
    }

    public static void readRelation_PrintTupleTest() {
        // read --> sort --> print
        System.out.println("Starting ReadRelation _ PrintTuple");
        Connector read_A = new Connector("input1");
        ReadRelation r = new ReadRelation("client.txt", read_A.getWriteEnd());
        PrintTuple p = new PrintTuple(read_A.getReadEnd());
        r.start();
        p.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
    public static void bloomTest() throws Exception {
        System.out.println("Starting Bloom Test");
        Connector print_bloom = new Connector("print_bloom");
        ReadRelation r = new ReadRelation("client.txt", print_bloom.getWriteEnd());
        Connector bloom_join = new Connector("bloom_join");
        PrintTuple p1 = new PrintTuple(bloom_join.getReadEnd());
        Connector bloom_bmap = new Connector("bloom_bmap");
        ReadEnd readEnd = bloom_bmap.getReadEnd();
//        PrintTuple p2 = new PrintTuple(bloom_bmap.getReadEnd());
        Bloom bloom = new Bloom(print_bloom.getReadEnd(), bloom_join.getWriteEnd(), bloom_bmap.getWriteEnd(), 0);
        r.start();
        bloom.start();
        p1.start();       
        System.out.println(readEnd.getNextString());
    }

    public static void readRelation_Hsplit_PrintTuple() {
        // read --> sort --> print
        System.out.println("Starting ReadRelation _ Hsplit _  PrintTuple");
        Connector read_A = new Connector("input1");
        ReadRelation r = new ReadRelation("client.txt", read_A.getWriteEnd());
        Connector hash_1 = new Connector("pipe1");
        Connector hash_2 = new Connector("pipe2");
        Connector hash_3 = new Connector("pipe3");
        Connector hash_4 = new Connector("pipe4");
        HSplit h = new HSplit(0,read_A.getReadEnd(),hash_1.getWriteEnd(),hash_2.getWriteEnd(),hash_3.getWriteEnd(),hash_4.getWriteEnd());
        PrintTuple p1 = new PrintTuple(hash_1.getReadEnd());
        PrintTuple p2 = new PrintTuple(hash_2.getReadEnd());
        PrintTuple p3 = new PrintTuple(hash_3.getReadEnd());
        PrintTuple p4 = new PrintTuple(hash_4.getReadEnd());
        r.start();
        h.start();
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        System.out.println("-----------------");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
    }
    }

    public static void readRelation_Hjoin_PrintTupleTest() {
        System.out.println("Starting ReadRelation _ HJoin _ PrintTuple");
        Connector read_A = new Connector("input1");
        ReadRelation r1 = new ReadRelation("client.txt", read_A.getWriteEnd());
        Connector read_B = new Connector("input2");
        ReadRelation r2 = new ReadRelation("viewing.txt", read_B.getWriteEnd());
        Connector out = new Connector("output");
        HJoin h = new HJoin(0, read_A.getReadEnd(),0,read_B.getReadEnd(),out.getWriteEnd());
        PrintTuple p = new PrintTuple(out.getReadEnd());
        r1.start();
        r2.start();
        h.start();
        p.start();
        System.out.println("-----------------");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void readRelation_Hsplit_Merge_PrintTuple() {
        System.out.println("Starting ReadRelation _ Hsplit _ Merge _ PrintTuple");
        Connector read_A = new Connector("input1");
        ReadRelation r = new ReadRelation("client.txt", read_A.getWriteEnd());
        Connector hash_1 = new Connector("pipe1");
        Connector hash_2 = new Connector("pipe2");
        Connector hash_3 = new Connector("pipe3");
        Connector hash_4 = new Connector("pipe4");
        Connector out = new Connector("output");
        HSplit h = new HSplit(0,read_A.getReadEnd(),hash_1.getWriteEnd(),hash_2.getWriteEnd(),hash_3.getWriteEnd(),hash_4.getWriteEnd());
        Merge m = new Merge(out.getWriteEnd(),hash_1.getReadEnd(),hash_2.getReadEnd(),hash_3.getReadEnd(),hash_4.getReadEnd() );
        PrintTuple p = new PrintTuple(out.getReadEnd());
        r.start();
        h.start();
        m.start();
        p.start();
        System.out.println("-----------------");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void testBFilterAndPrint() {
        Connector bloomConnector = new Connector("bloomConnector");
        Connector hsplitConnector = new Connector("hsplitConnector");
        Connector hJoinConnector = new Connector("hJoinConnector");
        int fieldNumber = 0;
        BFilter filter = new BFilter(bloomConnector.getReadEnd(), 
                hsplitConnector.getReadEnd(), hJoinConnector.getWriteEnd(), fieldNumber);
        PrintTuple p = new PrintTuple(hJoinConnector.getReadEnd());
        filter.start();
        p.start();
        System.out.println("-----------------");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

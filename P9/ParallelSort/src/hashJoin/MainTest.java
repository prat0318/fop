/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashJoin;

import hashJoin.basicConnector.Connector;

/**
 *
 * @author bansal
 */
public class MainTest {
    public static void main(String[] args) {
        //readRelation_PrintTupleTest();
        readRelation_Hsplit_PrintTuple();
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

    public static void readRelation_Hsplit_PrintTuple() {
        // read --> sort --> print
        System.out.println("Starting ReadRelation _ Hsplit _  PrintTuple");
        Connector read_A = new Connector("input1");
        ReadRelation r = new ReadRelation("client.txt", read_A.getWriteEnd());
        Connector hash_1 = new Connector("pipe1");
        Connector hash_2 = new Connector("pipe2");
        Connector hash_3 = new Connector("pipe3");
        Connector hash_4 = new Connector("pipe4");
        HSplit h = new HSplit(read_A.getReadEnd(),hash_1.getWriteEnd(),hash_2.getWriteEnd(),hash_3.getWriteEnd(),hash_4.getWriteEnd());
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
    }
    
}

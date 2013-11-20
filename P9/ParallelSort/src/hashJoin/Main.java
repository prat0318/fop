/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashJoin;

import hashJoin.basicConnector.Connector;
import hashJoin.ReadRelation;

/**
 *
 * @author bansal
 */
public class Main {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        // read --> sort --> print
        System.out.println("Starting Test1{)");
        Connector read_A = new Connector("input1");
        Connector read_B = new Connector("input2");
        ReadRelation r = new ReadRelation("client.txt", read_A);
        PrintTuple p = new PrintTuple(read_A);
        r.start();
        p.start();
        System.out.println("-----------------");
    }
    
}

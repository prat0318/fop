/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author dsb
 */
abstract class Node0 {

    String data1;
    int data2, data3;
    Node left, right;

    Node0(String d1, int d2, int d3) {
        data1 = d1;
        data2 = d2;
        data3 = d3;
        left = null;
        right = null;
    }
    protected final String tab = "\t ";
    protected final String comma = ",";

    @Override
    public String toString() {
        return (data1 + tab + data2 + tab + data3 + tab + extra());
    }

    public String extra() {
        return "";
    }
}

abstract class NodeDebug extends Node0 {
    NodeDebug(String d1, int d2, int d3) {
        super(d1, d2, d3);
        if (Container.debug) {
            System.out.println("new node (" + d1 + comma + d2 + comma + d3 + ")");
        }
    }
}

abstract class NodeSizeOf extends NodeDebug {

    NodeSizeOf(String d1, int d2, int d3) {
        super(d1, d2, d3);
    }
}

abstract class NodeCntr extends NodeSizeOf {

    int creation_time;

    NodeCntr(String d1, int d2, int d3) {
        super(d1, d2, d3);
        creation_time = 0;
    }

    public String extra() {
        return super.extra() + creation_time;
    }
}

public class Node extends NodeCntr {

    Node(String d1, int d2, int d3) {
        super(d1, d2, d3);
    }
}

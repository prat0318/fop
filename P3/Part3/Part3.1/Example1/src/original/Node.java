/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author banaka
 */


class NodeBasic {
    public String data1;
    public int data2, data3;
    
    NodeBasic(String d1, int d2, int d3) {
        data1 = d1;
        data2 = d2;
        data3 = d3;
    }
}

 class NodeDoublyLinkList extends NodeBasic {
        public NodeDoublyLinkList left;
        public NodeDoublyLinkList right;
    
        NodeDoublyLinkList(String d1, int d2, int d3) {
            super(d1, d2, d3);
            left = null;
            right = null;
        }
 
        public String toString() {
            return "";
        }
    }

 class NodeTimeStamp extends NodeDoublyLinkList {
    public int creation_time;

    NodeTimeStamp(String d1, int d2, int d3) {
        super(d1, d2, d3);
        creation_time = 0;
    }
    
    @Override
    public String toString() {
        return (creation_time + super.toString());
    } 
}

class NodeSizeOf extends NodeTimeStamp {
    NodeSizeOf(String d1, int d2, int d3) {
        super(d1,d2,d3);
    }
}

class NodePrint extends NodeSizeOf {
    NodePrint(String d1, int d2, int d3) {
        super(d1, d2, d3);
        if (ContainerPrint.debug) {
            System.out.println("new node (" + d1 + comma + d2 + comma + d3 + ")");
        }
    }
     
    private final String tab = "\t ";
    private final String comma = ",";

    @Override
    public String toString() {
        return (data1 + tab + data2 + tab + data3 + tab + super.toString());
    }
}

public class Node extends NodePrint{
    Node(String d1, int d2, int d3){
        super(d1, d2,d3);
    }
}

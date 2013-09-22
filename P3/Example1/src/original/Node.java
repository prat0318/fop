/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author banaka
 */
public class Node extends NodePrint{
    Node(String d1, int d2, int d3){
        super(d1, d2,d3);
    }
    }

 class NodeBasic {
    
    String data1;
    int data2, data3;
    NodeBasic(String d1, int d2, int d3) {
        data1 = d1;
        data2 = d2;
        data3 = d3;
    }
}

 class NodeDoublyLinkList extends NodeBasic{
        NodeDoublyLinkList left;
    NodeDoublyLinkList  right;
    
        NodeDoublyLinkList(String d1, int d2, int d3) {
        super(d1, d2, d3);
        left = null;
        right = null;
        }
    }

 
 class NodeTimeStamp extends NodeDoublyLinkList{
int creation_time;

    NodeTimeStamp(String d1, int d2, int d3) {
        super(d1, d2, d3);
        creation_time = 0;
    }
    
}

class NodePrint extends NodeTimeStamp{
    
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
        return (data1 + tab + data2 + tab + data3 + tab + creation_time);
    }
    
}

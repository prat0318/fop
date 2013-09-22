/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author dsb
 */
public class Node {

    String data1;
    int data2, data3;
    Node left, right;
    int creation_time;

    Node(String d1, int d2, int d3) {
        data1 = d1;
        data2 = d2;
        data3 = d3;
        left = null;
        right = null;
        creation_time = 0;
        if (Container.debug) {
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

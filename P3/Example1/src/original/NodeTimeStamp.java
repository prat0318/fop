/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author bansal
 */
public class NodeTimeStamp extends NodeDoublyLinkList{
int creation_time;

    NodeTimeStamp(String d1, int d2, int d3) {
        super(d1, d2, d3);
        creation_time = 0;
    }
    
}

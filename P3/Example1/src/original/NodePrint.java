/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author bansal
 */
public class NodePrint extends NodeTimeStamp{
    
     NodePrint(String d1, int d2, int d3) {
          super(d1, d2, d3);
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

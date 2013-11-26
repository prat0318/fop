/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashJoin;

import hashJoin.basicConnector.Connector;
import hashJoin.basicConnector.ReadEnd;
import hashJoin.gammaSupport.Tuple;

import hashJoin.gammaSupport.ReportError;

/**
 * @author bansal
 */
public class PrintTuple extends Thread {
    ReadEnd in;

    public PrintTuple(ReadEnd in) {
        this.in = in;
    }

    public void run() {
        try {
            Tuple input;
            while (true) {
                input = in.getNextTuple();
                if (input == null) {
                    break;
                }
                System.out.println(input);
            }
            System.out.println(in.getRelation());
            System.out.flush();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}

package hashJoin;

import hashJoin.basicConnector.ReadEnd;
import hashJoin.gammaSupport.ReportError;
import hashJoin.gammaSupport.Tuple;

/**
 * Created with IntelliJ IDEA.
 * User: bansal
 * Date: 20/11/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sink extends Thread {
    ReadEnd in;

    public Sink( ReadEnd in ) {
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
                // do nothing
            }
            System.out.flush();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}

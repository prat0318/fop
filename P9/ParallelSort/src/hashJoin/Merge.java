package hashJoin;

import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.GammaConstants;
import hashJoin.gammaSupport.ReportError;
import hashJoin.gammaSupport.Tuple;

/**
 * Created with IntelliJ IDEA.
 * User: bansal
 */
public class Merge extends Thread {
    ReadEnd in[];
    WriteEnd out;
    
    public Merge(WriteEnd out, ReadEnd in0,
           ReadEnd in1, ReadEnd in2, ReadEnd in3) {
        this.out = out;
        this.in = new ReadEnd[]{in0, in1, in2, in3};
    }

    public void run() {
        try {
            Tuple input;
            for (int i = 0; i < GammaConstants.splitLen; i++) {
                while (true) {
                    input = in[i].getNextTuple();
                    if (input == null) {
                        break;
                    }
                    out.putNextTuple(input);
                    //System.out.println("Merged Output " + input);
                }
            }
            out.close();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}

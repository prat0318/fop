package hashJoin;

import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.GammaConstants;
import hashJoin.gammaSupport.ReportError;

/**
 * Created with IntelliJ IDEA.
 * User: bansal
 * Date: 20/11/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class MergeM extends Thread {
    ReadEnd in[];
    WriteEnd out;

    MergeM(WriteEnd out, ReadEnd in0,
          ReadEnd in1, ReadEnd in2, ReadEnd in3) {
        this.out = out;
        this.in = new ReadEnd[]{in0, in1, in2, in3};
    }

    public void run() {
        try {
            String input;
            for (int i = 0; i < GammaConstants.splitLen; i++) {
                while (true) {
                    input = in[i].getNextString();
                    if (input == null) {
                        break;
                    }
                    out.putNextString(input);
                    System.out.println("Merged Output " + input);
                }
            }
            out.close();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}

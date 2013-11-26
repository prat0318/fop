package hashJoin;

import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.BMap;
import hashJoin.gammaSupport.GammaConstants;
import hashJoin.gammaSupport.ReportError;

/**
 * Created with IntelliJ IDEA.
 * User: bansal
 */
public class MSplit extends Thread {
    ReadEnd in;
    WriteEnd out[];

    public MSplit(ReadEnd in, WriteEnd out0,
           WriteEnd out1, WriteEnd out2, WriteEnd out3) {
        this.in = in;
        this.out = new WriteEnd[] {out0, out1, out2, out3};
    }

    public void run() {
        try {
            String input = in.getNextString();
            for (int i=0; i < GammaConstants.splitLen; i++) {
                out[i].putNextString(BMap.mask(input, i));
                out[i].close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}

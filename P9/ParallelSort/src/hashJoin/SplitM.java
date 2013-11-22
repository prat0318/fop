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
public class SplitM extends Thread {
    ReadEnd in;
    WriteEnd out[];
    //int hashKey=0;

    public SplitM(ReadEnd in, WriteEnd out0,
           WriteEnd out1, WriteEnd out2, WriteEnd out3) {
        //this.hashKey=hashKey;
        this.in = in;
        this.out = new WriteEnd[]{out0, out1, out2, out3};
    }

    public void run() {
        try {
            String input;
            while (true) {
                input = in.getNextString();
                if (input == null) {
                    break;
                }
                int hash = BMap.myhash(input);
                out[hash].putNextString(input);
                System.out.println("Hashed " + hash + " " + input);
            }
            for (int i = 0; i < GammaConstants.splitLen; i++) {
                out[i].close();
            }
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}

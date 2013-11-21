package hashJoin;

import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.GammaConstants;
import hashJoin.gammaSupport.Tuple;
import hashJoin.gammaSupport.ReportError;

/**
 * @author bansal
 */
public class HSplit extends Thread {
    ReadEnd in;
    WriteEnd out[];
    int hashKey=0;

    HSplit(int hashKey, ReadEnd in, WriteEnd out0,
           WriteEnd out1, WriteEnd out2, WriteEnd out3) {
        this.hashKey=hashKey;
        this.in = in;
        this.out = new WriteEnd[]{out0, out1, out2, out3};
    }

    public void run() {
        try {
            Tuple input;
            while (true) {
                input = in.getNextTuple();
                if (input == null) {
                    break;
                }
                int hash = myhash(input);
                out[hash].putNextTuple(input);
                System.out.println( " Hashed "+hash + " " + input );
            }
            for (int i = 0; i < GammaConstants.splitLen; i++) {
                out[i].close();
            }
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

    int myhash(Tuple s) {
        return (Math.abs(s.get(hashKey).hashCode()) % GammaConstants.splitLen);
    }


}

package hashJoin;

import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.GammaConstants;
import hashJoin.gammaSupport.ReportError;
import hashJoin.gammaSupport.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * User: bansal
 */
public class HJoin extends Thread {
    ReadEnd in1;
    ReadEnd in2;
    WriteEnd out;
    int joinKey1 = 0;
    int joinKey2 = 0;

    HJoin(int joinKey1, ReadEnd in1, int joinKey2, ReadEnd in2, WriteEnd out) {
        this.joinKey1 = joinKey1;
        this.joinKey2 = joinKey2;
        this.in1 = in1;
        this.in2 = in2;
        this.out = out;
    }

    public void run() {
        try {
            Map<String, Tuple> input_1 = new HashMap<String, Tuple>();
            while (true) {
                Tuple input = in1.getNextTuple();
                if (input == null) {
                    break;
                }
                input_1.put(input.get(joinKey1), input);
                System.out.println("Added to input map  " + input);
            }

            while (true) {
                Tuple input = in2.getNextTuple();
                if (input == null) {
                    break;
                }
                System.out.println("parsing input   " + input);
                Tuple output = Tuple.join(input_1.get(input.get(joinKey1)), input, joinKey1, joinKey2);
                out.putNextTuple(output);
                System.out.println("Added to output End  " + output);
            }

            out.close();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}

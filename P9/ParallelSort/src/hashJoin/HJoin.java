package hashJoin;

import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.ReportError;
import hashJoin.gammaSupport.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public HJoin(int joinKey1, ReadEnd in1, int joinKey2, ReadEnd in2, WriteEnd out) {
        this.joinKey1 = joinKey1;
        this.joinKey2 = joinKey2;
        this.in1 = in1;
        this.in2 = in2;
        this.out = out;
    }

    public void run() {
        try {
            Map<String, List<Tuple>> input_1 = new HashMap<String, List<Tuple>>();
            while (true) {
                Tuple input = in1.getNextTuple();
                if (input == null) {
                    break;
                }
                if(input_1.containsKey(input.get(joinKey1)) ){
                    List<Tuple> t = input_1.get(input.get(joinKey1));
                    t.add(input);
                }else{
                    List<Tuple> t = new ArrayList<Tuple>();
                    t.add(input);
                    input_1.put(input.get(joinKey1),t);
                }
                //System.out.println("Added to input map  " + input);
            }

            while (true) {
                Tuple input = in2.getNextTuple();
                if (input == null) {
                    break;
                }
                //System.out.println("parsing input   " + input);
                if(input_1.containsKey(input.get(joinKey2)) ){
                    List<Tuple> t = input_1.get(input.get(joinKey2));
                    for(Tuple i : t){
                        Tuple output = Tuple.join(i, input, joinKey1, joinKey2);
                        out.putNextTuple(output);
                    }
                }
                //System.out.println("Added to output End  " + output);
            }

            out.close();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}

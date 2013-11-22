/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashJoin;

import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.BMap;
import hashJoin.gammaSupport.Tuple;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prat0318
 */
public class Bloom extends Thread {

    ReadEnd inputReadEnd;
    WriteEnd outputWriteEnd, outputBmap;
    int joinKey;

    public Bloom(ReadEnd inputReadEnd, WriteEnd outputWriteEnd, WriteEnd outputBmap, int joinKey) {
        this.inputReadEnd = inputReadEnd;
        this.outputWriteEnd = outputWriteEnd;
        this.outputBmap = outputBmap;
        this.joinKey = joinKey;
    }

    public void run() {
        Tuple t;
        BMap bmap = BMap.makeBMap();
        try {
            while ((t = inputReadEnd.getNextTuple()) != null) {
                outputWriteEnd.putNextTuple(t);
                String fieldKey = t.get(joinKey);
                bmap.setValue(fieldKey, true);
            }
            outputBmap.putNextString(bmap.getBloomFilter());
            //System.out.println("Closing.");
            outputWriteEnd.close();
        } catch (Exception ex) {
            Logger.getLogger(Bloom.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

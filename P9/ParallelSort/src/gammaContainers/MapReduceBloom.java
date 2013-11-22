/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gammaContainers;

import hashJoin.Bloom;
import hashJoin.HJoin;
import hashJoin.HSplit;
import hashJoin.Merge;
import hashJoin.MMerge;
import hashJoin.ReadRelation;
import hashJoin.basicConnector.Connector;
import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.ArrayConnectors;
import hashJoin.gammaSupport.GammaConstants;
import hashJoin.gammaSupport.ReportError;

/**
 *
 * @author prat0318
 */
public class MapReduceBloom extends ArrayConnectors {
    
    ReadEnd inputReadEnd;
    WriteEnd outputWriteEnd, outputBmap;
    int joinKey;

    public MapReduceBloom(ReadEnd inputReadEnd, WriteEnd outputWriteEnd, WriteEnd outputBmap, int joinKey) {
        this.inputReadEnd = inputReadEnd;
        this.outputWriteEnd = outputWriteEnd;
        this.outputBmap = outputBmap;
        this.joinKey = joinKey;
    }
    
    public void start() {
       try {

           Connector[] arr1r = ArrayConnectors.initConnectorArray("read_Split");
           HSplit hs1 = new HSplit(joinKey, inputReadEnd, arr1r[0].getWriteEnd(), arr1r[1].getWriteEnd(), arr1r[2].getWriteEnd(), arr1r[3].getWriteEnd());
           hs1.start();

            Connector[] arr1write = ArrayConnectors.initConnectorArray("write_Split");
            Connector[] arr2bmap = ArrayConnectors.initConnectorArray("bmap_Split");

            for (int i = 0; i < GammaConstants.splitLen; i++) {
                Bloom hj = new Bloom(arr1r[i].getReadEnd(), arr1write[i].getWriteEnd(), arr2bmap[i].getWriteEnd(), i);
                hj.start();
            }
            
            Merge m = new Merge(outputWriteEnd, arr1write[0].getReadEnd(), arr1write[1].getReadEnd(), arr1write[2].getReadEnd(),
                    arr1write[3].getReadEnd());
            m.start();

            MMerge m1 = new MMerge(outputBmap, arr2bmap[0].getReadEnd(), arr2bmap[1].getReadEnd(), arr2bmap[2].getReadEnd(),
                    arr2bmap[3].getReadEnd());
            m1.start();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
        
    }
    
}

package gammaContainers;

import hashJoin.*;
import hashJoin.basicConnector.Connector;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.ArrayConnectors;
import hashJoin.gammaSupport.GammaConstants;
import hashJoin.gammaSupport.ReportError;

/**
 * Created with IntelliJ IDEA.
 * User: bansal
 */
public class MapReduceHJoin extends ArrayConnectors {
    String fileName1;
    String fileName2;
    WriteEnd out;
    int joinKey1 = 0;
    int joinKey2 = 0;

    public MapReduceHJoin(int joinKey1, String in1, int joinKey2, String in2, WriteEnd out) {
        this.joinKey1 = joinKey1;
        this.joinKey2 = joinKey2;
        this.fileName1 = in1;
        this.fileName2 = in2;
        this.out = out;
    }

    public void start() {
        try {
            Connector c1 = new Connector("input1");
            ReadRelation r1 = new ReadRelation(fileName1, c1.getWriteEnd());
            r1.start();
            Connector c2 = new Connector("input2");
            ReadRelation r2 = new ReadRelation(fileName2, c2.getWriteEnd());
            r2.start();
            Connector[] arr1c = ArrayConnectors.initConnectorArray("input1_Split");
            HSplit hs1 = new HSplit(joinKey1, c1.getReadEnd(), arr1c[0].getWriteEnd(), arr1c[1].getWriteEnd(), arr1c[2].getWriteEnd(), arr1c[3].getWriteEnd());
            hs1.start();
            Connector[] arr2c = ArrayConnectors.initConnectorArray("input2_Split");
            HSplit hs2 = new HSplit(joinKey2, c2.getReadEnd(), arr2c[0].getWriteEnd(), arr2c[1].getWriteEnd(), arr2c[2].getWriteEnd(), arr2c[3].getWriteEnd());
            hs2.start();
            Connector[] o = ArrayConnectors.initConnectorArray("output");

            for (int i = 0; i < GammaConstants.splitLen; i++) {
                HJoin hj = new HJoin(joinKey1, arr1c[i].getReadEnd(), joinKey2, arr2c[i].getReadEnd(), o[i].getWriteEnd());
                hj.start();
            }
            Merge m = new Merge(out, o[0].getReadEnd(), o[1].getReadEnd(), o[2].getReadEnd(), o[3].getReadEnd());
            m.start();

        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }
}

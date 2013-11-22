package gammaContainers;

import hashJoin.*;
import hashJoin.basicConnector.Connector;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.ArrayConnectors;
import hashJoin.gammaSupport.ReportError;

/**
 * Created with IntelliJ IDEA.
 * User: bansal
 * Date: 21/11/13
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class HJoinWithBloomFilter extends ArrayConnectors {
    String fileName1;
    String fileName2;
    WriteEnd out;
    int joinKey1 = 0;
    int joinKey2 = 0;

    public HJoinWithBloomFilter(int joinKey1, String in1, int joinKey2, String in2, WriteEnd out) {
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
            Connector bmap = new Connector("bloom");
            Connector input_1 = new Connector("input_1");
            Connector input_2 = new Connector("input_2");
            Bloom bloom = new Bloom(c1.getReadEnd(), input_1.getWriteEnd(), bmap.getWriteEnd(), 0);
            bloom.start();
            BFilter bfliter = new BFilter(bmap.getReadEnd(),c2.getReadEnd(), input_2.getWriteEnd(), 0);
            bfliter.start();
            HJoin hj = new HJoin(joinKey1, input_1.getReadEnd(), joinKey2, input_2.getReadEnd(), out);
            hj.start();
        } catch ( Exception e )
        {
            ReportError.msg(this.getClass().getName() + e);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gammaContainers;

import hashJoin.BFilter;
import hashJoin.Bloom;
import hashJoin.HJoin;
import hashJoin.HSplit;
import hashJoin.Merge;
import hashJoin.ReadRelation;
import hashJoin.basicConnector.Connector;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.ArrayConnectors;
import hashJoin.gammaSupport.GammaConstants;
import hashJoin.gammaSupport.ReportError;

/**
 *
 * @author prat0318
 */
public class MapReduceGamma extends ArrayConnectors {
    String fileName1;
    String fileName2;
    WriteEnd out;
    int joinKey1 = 0;
    int joinKey2 = 0;

    public MapReduceGamma(int joinKey1, String in1, int joinKey2, String in2, WriteEnd out) {
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
 
            Connector[] split_bloom = ArrayConnectors.initConnectorArray("split_bloom");
            HSplit hs1 = new HSplit(joinKey1, c1.getReadEnd(), split_bloom[0].getWriteEnd(), split_bloom[1].getWriteEnd(), split_bloom[2].getWriteEnd(), split_bloom[3].getWriteEnd());
            hs1.start();

            Connector[] bloom_hjoin = ArrayConnectors.initConnectorArray("bloom_hjoin");
            Connector[] bloom_filter = ArrayConnectors.initConnectorArray("bloom_filter");

            for (int i = 0; i < GammaConstants.splitLen; i++) {
                Bloom hj = new Bloom(split_bloom[i].getReadEnd(), bloom_hjoin[i].getWriteEnd(), bloom_filter[i].getWriteEnd(), joinKey1);
                hj.start();
            }

            Connector c2 = new Connector("input2");
            ReadRelation r2 = new ReadRelation(fileName2, c2.getWriteEnd());
            r2.start();

            Connector[] split_filter = ArrayConnectors.initConnectorArray("split_filter");
            HSplit hs2 = new HSplit(joinKey2, c2.getReadEnd(), split_filter[0].getWriteEnd(), split_filter[1].getWriteEnd(), split_filter[2].getWriteEnd(), split_filter[3].getWriteEnd());
            hs2.start();
            
            Connector[] filter_hjoin = ArrayConnectors.initConnectorArray("filter_hjoin");
            for (int i = 0; i < GammaConstants.splitLen; i++) {
                BFilter bfilter = new BFilter(bloom_filter[i].getReadEnd(), split_filter[i].getReadEnd(), filter_hjoin[i].getWriteEnd(), joinKey2);
                bfilter.start();
            }            

            Connector[] o = ArrayConnectors.initConnectorArray("output");
            for (int i = 0; i < GammaConstants.splitLen; i++) {
                HJoin hj = new HJoin(joinKey1, bloom_hjoin[i].getReadEnd(), joinKey2, filter_hjoin[i].getReadEnd(), o[i].getWriteEnd());
                hj.start();
            }
            
            Merge m = new Merge(out, o[0].getReadEnd(), o[1].getReadEnd(), o[2].getReadEnd(), o[3].getReadEnd());
            m.start();

        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }
}

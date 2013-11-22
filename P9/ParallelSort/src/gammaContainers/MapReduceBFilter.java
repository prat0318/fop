/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gammaContainers;

import hashJoin.BFilter;
import hashJoin.HSplit;
import hashJoin.Merge;
import hashJoin.SplitM;
import hashJoin.basicConnector.Connector;
import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.ArrayConnectors;
import parallelsort.HashSplit;

/**
 *
 * @author gnanda
 */
public class MapReduceBFilter extends ArrayConnectors {
    ReadEnd in_bloom;
    ReadEnd in_split;
    WriteEnd out;
    int fieldNumber;
    
    public void MapReduceBFilter(ReadEnd in_bloom, ReadEnd in_split, WriteEnd out, int fieldNumber) {
        this.in_bloom = in_bloom;
        this.in_split = in_split;
        this.out = out; 
        this.fieldNumber = fieldNumber;
    }
    
    public void start() {
        Connector mb1 = new Connector("mb1");
        Connector mb2 = new Connector("mb2");
        Connector mb3 = new Connector("mb3");
        Connector mb4 = new Connector("mb4");
        SplitM m1 = new SplitM(this.in_bloom, mb1.getWriteEnd(), mb2.getWriteEnd(), 
                mb3.getWriteEnd(), mb4.getWriteEnd());
        
        Connector hb1 = new Connector("hb1");
        Connector hb2 = new Connector("hb2");
        Connector hb3 = new Connector("hb3");
        Connector hb4 = new Connector("hb4");
        HSplit h1 = new HSplit(this.fieldNumber, in_split, hb1.getWriteEnd(), 
                hb2.getWriteEnd(), hb3.getWriteEnd(), hb4.getWriteEnd());
        
        Connector bmerge1 = new Connector("bmerge1");
        Connector bmerge2 = new Connector("bmerge2");
        Connector bmerge3 = new Connector("bmerge3");
        Connector bmerge4 = new Connector("bmerge4");
        BFilter bf1 = new BFilter(mb1.getReadEnd(), hb1.getReadEnd(),
            bmerge1.getWriteEnd(), this.fieldNumber);
        BFilter bf2 = new BFilter(mb2.getReadEnd(), hb2.getReadEnd(),
            bmerge2.getWriteEnd(), this.fieldNumber);
        BFilter bf3 = new BFilter(mb3.getReadEnd(), hb3.getReadEnd(),
            bmerge3.getWriteEnd(), this.fieldNumber);
        BFilter bf4 = new BFilter(mb4.getReadEnd(), hb4.getReadEnd(),
            bmerge4.getWriteEnd(), this.fieldNumber);

        Merge m = new Merge(this.out, bmerge1.getReadEnd(),
                bmerge2.getReadEnd(), bmerge3.getReadEnd(),
                bmerge4.getReadEnd());
    }
}

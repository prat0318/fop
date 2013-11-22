/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashJoin;

import hashJoin.basicConnector.Connector;
import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;

/**
 *
 * @author prat0318
 */
public class BloomSimulator extends Thread{
    WriteEnd writeEnd;
    String fileName;
    int keyPosition;
    
    public BloomSimulator(WriteEnd writeEnd, String filename) {
        this.writeEnd = writeEnd;
        this.fileName = filename;
        this.keyPosition = 0;
    }
    
    public void run() {
        Connector read_split = new Connector("read_split");
        ReadRelation r = new ReadRelation(fileName, read_split.getWriteEnd());
        Connector hash_1 = new Connector("pipe1");
        Connector hash_2 = new Connector("pipe2");
        Connector hash_3 = new Connector("pipe3");
        Connector hash_4 = new Connector("pipe4");
        HSplit h = new HSplit(keyPosition, read_split.getReadEnd(), hash_1.getWriteEnd(),hash_2.getWriteEnd(),hash_3.getWriteEnd(),hash_4.getWriteEnd());
        Sink s2 = new Sink(hash_2.getReadEnd());
        Sink s3 = new Sink(hash_3.getReadEnd());
        Sink s4 = new Sink(hash_4.getReadEnd());
        Connector bloomOut_sink = new Connector("bloomOut_sink");
        Bloom bloom = new Bloom(hash_1.getReadEnd(), bloomOut_sink.getWriteEnd(), writeEnd, keyPosition);
        r.start();
        s2.start(); s3.start(); s4.start();        
        bloom.start();
        h.start();      
        
    }
    
}

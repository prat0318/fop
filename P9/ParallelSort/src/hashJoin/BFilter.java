/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashJoin;

import hashJoin.basicConnector.ReadEnd;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.BMap;
import hashJoin.gammaSupport.Tuple;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gnanda
 */
public class BFilter extends Thread{
    ReadEnd in_bloom;
    ReadEnd in_split;
    WriteEnd out;
    int fieldNumber;
    
    public BFilter(ReadEnd in_bloom, ReadEnd in_split, WriteEnd out, int fieldNumber) {
        this.in_bloom = in_bloom;
        this.in_split = in_split;
        this.out = out; 
        this.fieldNumber = fieldNumber;
    }
    
    public void run() {
        try {
            //System.out.println(in_bloom.getNextString());
            BMap bm = BMap.makeBMap(in_bloom.getNextString());
            
            while(true) {
                Tuple t = in_split.getNextTuple();
                if (t == null) {
                    break;
                }
                String input = t.get(fieldNumber);
                if (bm.getValue(input)) {
                    try {
                        out.putNextTuple(t);
                    } catch (Exception ex) {
                        Logger.getLogger(BFilter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(BFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

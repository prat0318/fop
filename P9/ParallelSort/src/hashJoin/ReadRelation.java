/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashJoin;

import hashJoin.basicConnector.Connector;
import hashJoin.basicConnector.WriteEnd;
import hashJoin.gammaSupport.Relation;
import hashJoin.gammaSupport.Tuple;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import hashJoin.gammaSupport.ReportError;

/**
 * @author bansal
 */
public class ReadRelation extends Thread {

    String dbName;
    BufferedReader in;
    WriteEnd out;

    public ReadRelation(String fileName, WriteEnd writeEnd) {
        try {
            this.dbName = fileName;
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (Exception e) {
            ReportError.msg(e.getMessage());
        }
        this.out = writeEnd;

    }

    public ReadRelation(String fileName, String dbName, WriteEnd writeEnd) {
        try {
            this.dbName = dbName;
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (Exception e) {
            ReportError.msg(e.getMessage());
        }
        this.out = writeEnd;

    }

    public void run() {
        try {
            String input;
            int linesRead = 0;
            while (true) {
                input = in.readLine();
                linesRead++;
                if (input == null) {
                    break;
                }
                String[] input_arr = input.split("\\s+");
                if (linesRead == 1) {
                    Relation relation = new Relation(dbName, input_arr.length);
                    for (int i = 0; i < input_arr.length; i++) {
                        relation.addField(input_arr[i]);
                    }
                    out.setRelation(relation);
                } else if (linesRead == 2) { //For the line with -------------
                    continue;
                } else {
                    Tuple t = new Tuple(input_arr.length);
                    for (int i = 0; i < input_arr.length; i++) {
                        t.set(i, input_arr[i]);
                    }
                    out.putNextTuple(t);
                }
                //System.out.println(input);
            }
            out.close();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }


}

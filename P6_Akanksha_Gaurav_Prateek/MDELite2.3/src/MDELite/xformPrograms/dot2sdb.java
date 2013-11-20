/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELite.xformPrograms;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.StringTokenizer;

/**
 *
 * @author dsb
 */
public class dot2sdb extends CoreMDELite.PCommon {

    static String infile = null;
    static String outfile = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("dot2p.Main <name-of-dot-file-without-.dot>");
            return;
        }
        infile = args[0] + ".dot";
        outfile = args[0] + ".sdb.pl";
        try {
            ps = new PrintStream(outfile);
        } catch (FileNotFoundException ex) {
            System.err.println("cannot open file " + outfile + " for writing");
            return;
        }
        readFile(infile);
    }

    static void readFile(String filename) {
        LineNumberReader r = null;
        try {
            r = new LineNumberReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.append("unable to open file: " + filename);
            return;
        }
        ps.println("table(position,[id,x,y]).");

        while (true) {
            int number = r.getLineNumber();
            String line = null;
            try {
                line = r.readLine();
            } catch (IOException ex) {
                System.err.println("unable to read line " + number + " in file: " + filename);
                return;
            }
            if (line == null) {
                break;
            }
            //System.out.println(number + ": " + line);
            if (line.startsWith("diagraph")) {
                continue;
            }
            if (line.startsWith("graph")) {
                continue;
            }
            if (line.startsWith("}")) {
                continue;
            }
            if (line.contains("--")) {
                continue;
            }
            if (line.contains("->")) {
                continue;
            }
            if (!line.contains("[")) {
                continue;
            }
            translateNodeLine(line);
        }
        try {
            r.close();
        } catch (IOException ex) {
            System.err.println("unable to close file: " + filename);
            return;
        }
    }

    static void translateNodeLine(String line) {
        StringTokenizer st = new StringTokenizer(line, ". \",");
        String nodeid = st.nextToken().trim();
        String poseq = st.nextToken();
        String x = st.nextToken();
        String xdec = st.nextToken();
        String y = st.nextToken();
        String ydec = st.nextToken();
        ps.println("position(" + nodeid + comma + x + comma + y + ").");
    }
}

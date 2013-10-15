/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CoreMDELite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

/**
 *
 * @author dsb
 */
public abstract class GProlog extends MDELiteObject {
    public static final String conformanceFailure = "Conformance Failure";

    public GProlog(String filename, String[] files) {
        super(filename);
        mergeFiles(files);
    }

    // these empty constructors are needed because obscure run-time
    // exceptions are raised otherwise that take 1/2 to track down
    public GProlog(String filename) {
        super(filename);
    }

    private GProlog() {
    }  // shouldn't be called.

    // the result of merged files is the new value of the given object
    protected void mergeFiles(String[] filenames) {
        try {
            PrintStream ps = getPrintStream();
            for (String f : filenames) {
                stuff(f, ps);
            }
            ps.flush();
            ps.close();
        } catch (Exception e) {
            done(e);
        }
    }

    protected static void stuff(String inFileName, PrintStream ps) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inFileName));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                ps.println(line);
            }
        } catch (Exception e) {
            done(e);
        }
    }

    // executes a prolog object.  The execution produces a file, whose object is created prior to the call
    // I know this is strange but this is a general problem of run-time objects being paired with files
    // at some point, one will exist before the other
    
    static String scriptFile = "script.txt";
    
    public void executeProlog(MDELiteObject out) {
        
        try {
            PrintStream ps;
            ps = new PrintStream(scriptFile);
            ps.print(":-['" + fullName + "'],tell('" + out.fullName + "'),run,told,halt.");
            ps.flush();
            ps.close();
        } catch (Exception e) {
            done(e);
        }
        String[] cmdarray = {MDELiteObject.configFile.getProperty("SWI_PROLOG_LOCATION"), "--quiet", "-f", scriptFile};
        try {
            execute(cmdarray);
        } catch (Exception e) {
            System.err.println("MDELite halts -- SWI Prolog Errors detected");
            System.err.println("consult Error.txt");
            System.err.println("debug this prolog file:  " + fullName);
            System.exit(1);
        }
        File s = new File(scriptFile);
        s.delete();
    }

    // this method is used to test conformance of a database 
    // with its constraints.  it is coded only for this purpose
    // as it outputs nothing.
    public void executeProlog() throws RuntimeException {
        try {
            PrintStream ps;
            ps = new PrintStream(scriptFile);
            ps.print(":-['" + fullName + "'],tell('conform.txt'),run,told,halt.");
            ps.flush();
            ps.close();
        } catch (Exception e) {
            done(e);
        }
        String[] cmdarray = {MDELiteObject.configFile.getProperty("SWI_PROLOG_LOCATION"), "--quiet", "-f", scriptFile};
        try {
            execute(cmdarray);
        } catch (Exception e) {
            System.err.println("MDELite halts -- SWI Prolog Errors detected");
            System.err.println("consult Error.txt");
            System.err.println("debug this prolog file:   " + fullName);
            System.err.println(conformanceFailure);
            throw new RuntimeException();
        }
        File c = new File("conform.txt");
        if (c.length()!= 0) {
            String msg = conformanceFailure + "\n" + "look at script.txt and conformance errors in conform.txt";
            throw new RuntimeException(msg);
        }
        c.delete();
        File s = new File(scriptFile);
        s.delete();
    }
    
    public abstract void conform();
}

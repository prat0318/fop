/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CoreMDELite;

import static CoreMDELite.MDELiteObject.done;
import static CoreMDELite.MDELiteObject.execute;
import java.io.*;

/**
 *
 * @author dsb
 */
public class SwiplInstalled  {
    
    public static void  main(String[] args) {
        HomePath.setHomePath(true);
        String swipl = MDELiteObject.configFile.getProperty("SWI_PROLOG_LOCATION");
        String filename = HomePath.homePath+"script.txt";
        
        try {
            PrintStream ps;
            ps = new PrintStream(filename);
            ps.print(":-['" + HomePath.homePath + "libpl/swiplInstalled'],run,halt.");
            ps.flush();
            ps.close();
        } catch (Exception e) {
            MDELiteObject.done(e);
        }
        String[] cmdarray = {MDELiteObject.configFile.getProperty("SWI_PROLOG_LOCATION"), "--quiet", "-f", HomePath.homePath+"script.txt"};
        try {
            execute(cmdarray);
        } catch (Exception e) {
            System.err.println("MDELite halts -- SWI Prolog Errors detected");
            System.err.println("debug this prolog file:  " + filename);
            System.exit(1);
        }
        System.out.println("MDELite Ready to Use!");
    }
}

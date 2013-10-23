/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FSMLite;

import CoreMDELite.SDB;

/**
 *
 * @author dsb
 */
public class Convert extends Common {

    public static void marquee() {
        System.out.println("Usage: Convert <filename(not including dot extension)>");
        System.out.println("       <filename(not including dot extension)>");
        System.exit(1);
    }
    
    public static void main(String args[]) {
        if (args.length!=1)
            marquee();
        toSDB(args[0]);
    }
    
    public static void toSDB(String f) {
                Violet v = new Violet(f);
                Violetpl p = v.toVioletpl();
                p.toViolet();
                System.out.println("Execution complete.");
    }
    
}

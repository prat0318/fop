package catlite;

import CoreMDELite.HomePath;
import CoreMDELite.GProlog;

public class Convert {

    public static void main(String[] args) {
        toViolet(args[0]);
        toJava(args[0]);
    }
        
    public static Violet toViolet(String filename) {
        Violet result = new Violet(filename);
        System.out.println("Parsing the violet file and creating prolog file...");
        catlite.violetParsers.main.Main.main(new String[]{filename});
        System.out.println("Checking whether the prolog file conforms with the rules..");
        result.conform();
        return result;
    }   
    
    static public Violet toJava(String filename) {
        Violet result = new Violet(filename);
        result.toJava();
        return result;
    }

}
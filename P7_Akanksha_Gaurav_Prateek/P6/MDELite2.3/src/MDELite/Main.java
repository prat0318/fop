/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELite;

import java.net.URL;

/**
 *
 * @author eric
 */
public class Main extends Common {
    public static String versionName = "MDELite2.1";
    
    public static void marquee() {
        System.out.println("version "+ versionName);
        System.out.println("Usage: java -jar \"MDELite.jar\" <action> <type> [<convert2Type>] <filename(not including dot extension)>");
        System.out.println("       <action> := convert | conform ");
        System.out.println("       <type>,<convert2Type> := umlf | yuml | violet ");
        System.exit(1);
    }
    
    public static void main (String[] args) {

        // check # of arguments
        if (!legalArgs(args))
            marquee();
        
        if (args[0].equals("conform"))
            Conform.main(new String[]{args[1], args[2]});
        else if (args[0].equals("convert"))
            Convert.main(new String[]{args[1], args[2], args[3]});
        else
            marquee();
    }
    
    public static boolean legalArgs(String[] args) {
        if (args.length<3)
            return false;
        if (!args[0].equals("convert") && !args[0].equals("conform"))
            return false;
        if (!properType(args[1]))
            return false;
        if (args[0].equals("conform") && !(args.length==3))
            return false;        
        if (args[0].equals("convert") && !(args.length==4 && properType(args[2])))
            return false;
        return true;
    }
     
    public static boolean properType(String type) {
        return type.equals("umlf") || type.equals("yuml") || type.equals("violet");
    }
}

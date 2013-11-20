/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELite;

/**
 *
 * @author dsb
 */
public class Common {
    
    public static String typeString(int t) {
        switch(t) {
            case 1: return ".umlf";
            case 2: return ".yuml";
            case 3: return ".violet";
            case 4: return  ".state";
            default: System.err.println("unrecognizable type "+t+" input to Common.typeString()");
                System.exit(1);
                return null; // pacify whiny compiler
        }
    }

    public static int getType(String t) {
        if (t.equals("umlf")) {
            return 1;
        }
        if (t.equals("yuml")) {
            return 2;
        }
        if (t.equals("violet")) {
            return 3;
        }
        if (t.equals("yumlpl")) {
            return 4;
        }
        if (t.equals("state")) {
            return 5;
        }
        System.err.println("unrecognizable type "+t+" to Common.getType()");
        System.exit(1);
        return 0; // pacify whiney compiler
    }
    
}

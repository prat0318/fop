/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CoreMDELite;

import MDELite.Main;

/**
 *
 * @author dsb
 */
public class HomePath {
    public static String homePath = "";

    public static void setHomePath(boolean printMe) {
        int pos;
        Class me = Main.class;
        String tmp = me.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (tmp.startsWith("/") && tmp.contains(":")) {
            tmp = tmp.substring(1);
        }
        if (tmp.contains("build/classes/")) {
            pos = tmp.lastIndexOf("build/classes/");
        } else {
            pos = tmp.lastIndexOf("/")+1;
        }
        homePath = tmp.substring(0, pos);
        if (!homePath.contains(":")) {
            homePath = "/" + homePath;
        }
        if (printMe) {
            System.out.println(homePath);
        }
    }

    public static void main(String[] args) {
        setHomePath(true);
    }
    
}

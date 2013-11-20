/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELite;

import CoreMDELite.MDELiteObject;
import java.io.File;
import java.io.PrintStream;

/**
 *
 * @author dsb
 */
public class Yuml extends MDELiteObject {

    @Override
    public String fileType() {
        return ".yuml";
    }

    @Override
    public String partialFileType() {
        return "";
    }

    public Yuml(String filename) {
        super(filename);
    }

    /* the following are transformations */
    public Yumlpl toYumlpl() {
        String[] args = {filename};
        MDELite.yumlparser.Main.main(args);
        Yumlpl result = new Yumlpl(filename);
        result.conform();
        return result;
    }
}








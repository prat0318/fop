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
public class Violet extends MDELiteObject {

    @Override
    public String fileType() {
        return ".violet";
    }

    @Override
    public String partialFileType() {
        return ".violet";
    }

    public Violet(String filename) {
        super(filename);
    }

    /* the following are transformations */
    public Violetpl toVioletpl() { 
        Violetpl result = new Violetpl(filename);
        String[] args = {filename+fileType()};
        MDELite.violetParser.main.Main.main(args);
        result.conform();
        return result;
    }
}

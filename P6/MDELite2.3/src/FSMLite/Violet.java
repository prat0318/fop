package FSMLite;

import FSMLite.Violetpl;
import CoreMDELite.MDELiteObject;

/**
 * Created with IntelliJ IDEA.
 * User: bansal
 * Date: 21/10/13
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Violet extends MDELiteObject {

    @Override
    public String fileType() {
        return ".state.violet";
    }

    @Override
    public String partialFileType() {
        return ".state.violet";
    }

    public Violet(String filename) {
        super(filename);
    }

    /* the following are transformations */
    public Violetpl toVioletpl() {
        Violetpl result = new Violetpl(filename);
        String[] args = {filename+fileType()};
        System.out.println("Parsing the violet file and creating prolog file...");
        FSMLite.violetParsers.main.Main.main(args);
        System.out.println("Checking whether the prolog file conforms with the rules..");
        result.conform();
        return result;
    }
}


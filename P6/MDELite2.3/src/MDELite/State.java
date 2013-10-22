package MDELite;

import CoreMDELite.MDELiteObject;
import MDELite.Violetpl;

/**
 * Created with IntelliJ IDEA.
 * User: bansal
 * Date: 21/10/13
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class State extends MDELiteObject {

    @Override
    public String fileType() {
        return ".state.violet";
    }

    @Override
    public String partialFileType() {
        return ".state.violet";
    }

    public State(String filename) {
        super(filename);
    }

    /* the following are transformations */
    public Statepl toStatepl() {
        Statepl result = new Statepl(filename);
        String[] args = {filename+fileType()};
        MDELite.stateParsers.main.Main.main(args);
        result.conform();
        return result;
    }
}


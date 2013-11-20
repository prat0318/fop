package FSMLite;

import CoreMDELite.GProlog;
import CoreMDELite.HomePath;
import CoreMDELite.SDB;
/**
 * Created with IntelliJ IDEA.
 * User: bansal
 * Date: 21/10/13
 * Time: 8:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Violetpl extends GProlog {

    @Override
    public String fileType() {
        return ".state.violet.pl";
    }

    @Override
    public String partialFileType() {
        return ".state.violet";
    }

    public Violetpl(String filename) {
        super(filename);
    }

    public Violetpl(String filename, String[] array) {
        super(filename, array);
    }

   //  the following are transformation
    public SDB toSDB() {
        return toSDB("");
    }

    public SDB toSDB(String extra) {
        String[] array = {this.fullName, HomePath.homePath + "libpl/violet2sdb.pl", HomePath.homePath + "libpl/sdb.schema.pl", HomePath.homePath + "libpl/print.pl", HomePath.homePath + "libpl/sdb.run.pl"};
        SDB tmp = new SDB("tmp", array);
        SDB result = new SDB(filename + extra);
        tmp.executeProlog(result);
        tmp.delete();
        return result;
    }

    public Violet toViolet(String extra) {
        Violet result = new Violet(filename + extra);
        System.out.println("Invoking Vm2t to create JAVA files.");
        invokeVm2t(result, HomePath.homePath + "libvm/convertToJava.vm");
        return result;
    }

    public Violet toViolet() {
        return toViolet("");
    }

    @Override
    public void conform(){
        conform(HomePath.homePath + "libpl/conform.pl");
    }

    public void conform(String conformFile) {
        String[] list = {HomePath.homePath+"libpl/discontiguous.pl",
                fullName,conformFile,HomePath.homePath + "libpl/state.conform.pl"};
        SDB tmpconform = new SDB("tmpconform", list);
        tmpconform.executeProlog();
        tmpconform.delete();
    }
}


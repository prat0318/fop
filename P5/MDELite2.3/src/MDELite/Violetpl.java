/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELite;

import CoreMDELite.HomePath;
import CoreMDELite.GProlog;

/**
 *
 * @author dsb
 */
public class Violetpl extends GProlog {

    @Override
    public String fileType() {
        return ".violet.pl";
    }

    @Override
    public String partialFileType() {
        return ".violet";
    }

    public Violetpl(String filename) {
        super(filename);
    }

    public Violetpl(String filename, String[] array) {
        super(filename, array);
    }

    // the following are transformation
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
        invokeVm2t(result, HomePath.homePath + "libvm/violetXml.vm");
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
            fullName,conformFile,HomePath.homePath + "libpl/violet.conform.pl"};
        SDB tmpconform = new SDB("tmpconform", list);
        tmpconform.executeProlog();
        tmpconform.delete();     
    }
}

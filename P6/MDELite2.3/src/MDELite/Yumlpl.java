/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELite;

import CoreMDELite.SDB;
import CoreMDELite.HomePath;
import CoreMDELite.GProlog;

/**
 *
 * @author dsb
 */
public class Yumlpl extends GProlog {

    @Override
    public String fileType() {
        return ".yuml.pl";
    }

    @Override
    public String partialFileType() {
        return ".yuml";
    }

    public Yumlpl(String filename) {
        super(filename);
    }

    public Yumlpl(String filename, String[] array) {
        super(filename, array);
    }

    // the following are transformation
    public SDB toSDB() {
        return toSDB("");
    }

    public SDB toSDB(String extra) {
        String[] array = {HomePath.homePath + "libpl/discontiguous.pl",
            this.fullName, HomePath.homePath + "libpl/yuml2sdb.pl", HomePath.homePath + "libpl/sdb.schema.pl", HomePath.homePath + "libpl/print.pl", HomePath.homePath + "libpl/sdb.run.pl"};
        SDB tmp = new SDB("tmp", array);
        SDB result = new SDB(filename + extra);
        tmp.executeProlog(result);
        result.conform();
        tmp.delete();
        return result;
    }

    public Yuml toYuml(String extra) {
        Yuml result = new Yuml(filename + extra);
        invokeVm2t(result, HomePath.homePath + "libvm/yumlpl2yuml.vm");
        return result;
    }

    public Yuml toYuml() {
        return toYuml("");
    }

    @Override
    public void conform() {
        conform(HomePath.homePath + "libpl/conform.pl");
    }

    public void conform(String filename) {
        String[] list = {HomePath.homePath + "libpl/discontiguous.pl",
            fullName, filename, HomePath.homePath + "libpl/yuml.conform.pl"};
        SDB tmpconform = new SDB("tmpconform", list);
        tmpconform.executeProlog();
        tmpconform.delete();
    }
}

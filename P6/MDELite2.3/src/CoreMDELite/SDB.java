/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CoreMDELite;

import CoreMDELite.HomePath;
import CoreMDELite.GProlog;
import MDELite.Dot;
import MDELite.UMLFpl;
import MDELite.Violetpl;
import MDELite.Yumlpl;

/**
 *
 * @author dsb
 */
public class SDB extends GProlog {

    @Override
    public String fileType() {
        return ".sdb.pl";
    }

    @Override
    public String partialFileType() {
        return ".sdb";
    }

    public SDB(String filename) {
        super(filename);
    }

    public SDB(String filename, String[] files) {
        super(filename, files);
    }

    /**
     * **************** methods/transformations of SDB objects ***********
     */
    public Dot toDot() {
        return toDot("");
    }

    public Dot toDot(String extra) {
        Dot result = new Dot(filename + extra);
        invokeVm2t(result, HomePath.homePath + "libvm/p2dot.vm");
        return result;
    }

    public SDB project(String outputFilename, String plfile) {
        String[] list = {HomePath.homePath + "libpl/discontiguous.pl",
            this.fullName, HomePath.homePath + "libpl/sdb.schema.pl", plfile, HomePath.homePath + "libpl/print.pl"};
        SDB tmp = new SDB("tmp", list);
        SDB result = new SDB(outputFilename);
        tmp.executeProlog(result);
        tmp.delete();
        // remember: don't test conformance as it won't pass
        // reason: the result of this operatin is some part of an SDB database
        return result;
    }

    public Yumlpl toYumlpl() {
        return toYumlpl("");
    }

    public Yumlpl toYumlpl(String extra) {
        String[] list = {HomePath.homePath + "libpl/discontiguous.pl", HomePath.homePath + "libpl/sdb2yuml.pl",
            HomePath.homePath + "libpl/print.pl",
            HomePath.homePath + "libpl/yuml.schema.pl",
            HomePath.homePath + "libpl/yuml.run.pl",
            this.fullName};
        SDB pmerge = new SDB("pmerge", list);
        Yumlpl result = new Yumlpl(filename + extra);
        pmerge.executeProlog(result);
        result.conform();
        pmerge.delete();
        return result;
    }

    public SDB scalePosition(String extra) {
        String[] list = {HomePath.homePath + "libpl/discontiguous.pl",
                         HomePath.homePath + "libpl/print.pl", this.fullName, HomePath.homePath + "libpl/scalePosition.pl"};
        SDB tmp = new SDB("tmp", list);
        SDB result = new SDB(filename + extra);
        tmp.executeProlog(result);
        tmp.delete();
        // don't test conformance as nothing will have changed
        // remember: only tuples of the position table are updated
        // so no logical constraints have been violated
        return result;
    }
    
    public SDB refreshPosition(String newName) {
        Dot d1 = toDot();
        Dot d2 = d1.dot2dot("K");
        SDB onlyPositions = d2.toSDB();
        SDB scaledPositions = onlyPositions.scalePosition("SP");
        SDB noPosition = project(filename+"noPosition",HomePath.homePath + "libpl/removePosition.pl");
        String[] array = {noPosition.fullName,scaledPositions.fullName};
        SDB patched = new SDB(newName, array); 
        return patched;
    }

    public UMLFpl toUMLFpl() {
        return toUMLFpl("");
    }

    public UMLFpl toUMLFpl(String extra) {
        String[] list = {HomePath.homePath + "libpl/discontiguous.pl",
               this.fullName, HomePath.homePath + "libpl/umlf.schema.pl", HomePath.homePath + "libpl/sdb2umlf.pl", HomePath.homePath + "libpl/print.pl", HomePath.homePath + "libpl/umlf.run.pl"};
        UMLFpl tmp = new UMLFpl("tmp", list);
        UMLFpl result = new UMLFpl(filename + extra);
        tmp.executeProlog(result);
        result.conform();  // make sure that the db conforms to umlfpl schema
        tmp.delete();
        return result;
    }
    
    public Violetpl toVioletpl() {
        return toVioletpl("");
    }

    public Violetpl toVioletpl(String extra) {
        String[] list = {HomePath.homePath + "libpl/discontiguous.pl",
            this.fullName, HomePath.homePath + "libpl/violet.schema.pl", HomePath.homePath + "libpl/sdb2violet.pl", HomePath.homePath + "libpl/print.pl", HomePath.homePath + "libpl/violet.run.pl"};
        Violetpl tmp = new Violetpl("tmp", list);
        Violetpl result = new Violetpl(filename + extra);
        tmp.executeProlog(result);
        result.conform();  // make sure that the db conforms to umlfpl schema
        tmp.delete();
        return result;
    }    
    
    public FSMLite.Violetpl toFSMVioletpl(String extra) {
        String[] list = {HomePath.homePath + "libpl/discontiguous.pl",
            this.fullName, HomePath.homePath + "libpl/violet.schema.pl", HomePath.homePath + "libpl/sdb2violet.pl", HomePath.homePath + "libpl/print.pl", HomePath.homePath + "libpl/violet.run.pl"};
        FSMLite.Violetpl tmp = new FSMLite.Violetpl("tmp", list);
        FSMLite.Violetpl result = new FSMLite.Violetpl(filename + extra);
        tmp.executeProlog(result);
        result.conform();  // make sure that the db conforms to umlfpl schema
        tmp.delete();
        return result;
    } 
    
    @Override
    public void conform(){
        String[] list = {HomePath.homePath+"libpl/discontiguous.pl",
             fullName,HomePath.homePath + "libpl/conform.pl",HomePath.homePath + "libpl/sdb.conform.pl"};
        SDB tmpconform = new SDB("tmpconform", list);
        tmpconform.executeProlog();
        tmpconform.delete();     
    }
}

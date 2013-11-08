/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catlite;

import CoreMDELite.GProlog;
import CoreMDELite.HomePath;

/**
 *
 * @author prat0318
 */
public class Violet extends GProlog {

    Violet(String filename){
       super(filename);
    }

    Violet(String filename, String[] array) {
        super(filename, array);
    }
    
    @Override
    public void conform(){
        conform(HomePath.homePath + "libpl/conform.pl");
    }

    public void conform(String conformFile) {
        String[] list = {HomePath.homePath+"libpl/discontiguous.pl",
                fullName ,conformFile,HomePath.homePath + "libpl/state.conform.pl"};
        SDB tmpconform = new SDB("tmpconform", list);
        tmpconform.executeProlog();
        tmpconform.delete();
    }

    @Override
    public String fileType() {
        return ".pl";
    }

    @Override
    public String partialFileType() {
        return ".state.violet";
    }
    
}

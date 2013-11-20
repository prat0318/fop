package FSMLite;

import CoreMDELite.GProlog;
import CoreMDELite.HomePath;

public class Violet extends GProlog {

    Violet(String filename){
       super(filename);
    }

    Violet(String filename, String[] array) {
        super(filename, array);
    }

    @Override
    public String fileType() {
        return ".pl";
    }

    @Override
    public String partialFileType() {
        return ".state.violet";
    }
    
    @Override
    public void conform() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

    public void toSDB(){
        toSDB(HomePath.homePath + "libpl/conform.pl");
    }

    public void toSDB(String conformFile) {
        String[] list = {HomePath.homePath+"libpl/discontiguous.pl",
                fullName ,conformFile,HomePath.homePath + "libpl/state.conform.pl"};
        SDB tmpconform = new SDB("tmpconform", list);
        tmpconform.executeProlog();
        tmpconform.delete();
    }
    
    
     public Violet toJAVA(String extra) {
        Violet result = new Violet(filename + extra);
        invokeVm2t(result, HomePath.homePath + "libvm/convertToJava.vm");
        return result;
    }

    public Violet toJAVA() {
        return toJAVA("");
    }
    
}

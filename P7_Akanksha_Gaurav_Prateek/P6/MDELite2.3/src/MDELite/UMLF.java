/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELite;

import CoreMDELite.MDELiteObject;

/**
 *
 * @author dsb
 * This defines the domain (category object) of Chrome's UMLFactory
 * 
 */
public class UMLF extends MDELiteObject {

    @Override
    public String fileType() {
        return ".umlf.xml";
    }

    @Override
    public String partialFileType() {
        return ".umlf";
    }

    public UMLF(String filename) {
        super(filename);
    }

    /****************** methods/transformations of ChromeUMLFactory objects ************/
    public UMLFpl toUMLFpl() {
        UMLFpl result = new UMLFpl(filename);
        String[] args = {filename};
        MDELite.xformPrograms.umlf_xml2umlf_pl.main(args);
        result.conform();
        return result;
    }
}

package MDELite.stateParsers.main;

import MDELite.stateParsers.writer.DBWriter;
import MDELite.stateParsers.importer.Importer;
import java.io.File;

public class Main {

    public static String VIOLET_XML_DIR = "TestData/testDataViolet";
    static String inputFile;

    /**
     * Main
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: voiletXML-file");
        }
        inputFile = args[0];
        //inputFile = VIOLET_XML_DIR+"/agg.class.violet";    

        //Import XML data into Java objects
        Importer importer = new Importer(inputFile);
        importer.importNodesAndEdges();
        //importer.assignParents();

        //Write our Violet DB file
        DBWriter dbWriter = new DBWriter(inputFile, importer.getStateNodes(), importer.getStateTransitions(),
                 importer.getVioletData(), importer.getNumNodes());
        dbWriter.generateVioletDB();

        //Write our Standard DB file
        //SDBWriter sdbWriter = new SDBWriter(inputFile+DBWriter.DB_FILE_APPENSION);
        //sdbWriter.generateVioletSDB();

        //Convert Violet DB file back to Violet XML
        //String convertedFile = generateVioletXML();

        //Diff Original Violet XML with Converted (back) Violet XML
        //validator.Validator.main(new String[]{inputFile, convertedFile});
    }

//    private static String generateVioletXML() {
//        //simp.class.violet.txt violetXml.vm -o simp2.class.violet
//        String convertedFile = "vm2toutput.txt";
//        //String args[]={inputFile+"DB","violetXml.vm", "-o", convertedFile};
//        String args[] = {inputFile + DBWriter.DB_FILE_APPENSION, "libvm/violetXml.vm"};
//        CoreMDELite.vm2t.Main.main(args);
//
//        return convertedFile;
//    }
}

package org.argouml.refactoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Facade;
import org.argouml.model.UmlException;
import org.argouml.model.XmiReader;
import org.argouml.persistence.PersistenceManager;
import org.argouml.persistence.ProjectFilePersister;
import org.argouml.persistence.SaveException;
import org.argouml.ui.SaveSwingWorker;
import org.argouml.model.Model;
import org.xml.sax.InputSource;

public class CheckConstraints {
    static String FILE_NAME = "/tmp/constraints.zargo";

    static String DIR_NAME = "/tmp/constraints";
    
    static String XMI_NAME = "constraints.xmi";

    static String PL_NAME = "constraints.pl";

    public static boolean validateUML() {
        boolean status = saveFile();
        try {
            ZipFile zipFile = new ZipFile(FILE_NAME);
            zipFile.extractAll(DIR_NAME);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        
        try {
			createPL();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UmlException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
        }

        return status;
    }

    private static void runSwipl() {
//        HomePath.setHomePath(true);
//        String swipl = MDELiteObject.configFile.getProperty("SWI_PROLOG_LOCATION");
//        String filename = HomePath.homePath+"script.txt";
        
//        try {
//            PrintStream ps;
//            ps = new PrintStream(filename);
//            ps.print(":-['" + HomePath.homePath + "libpl/swiplInstalled'],run,halt.");
//            ps.flush();
//            ps.close();
//        } catch (Exception e) {
//            MDELiteObject.done(e);
//        }
        String[] cmdarray = {"/usr/bin/swipl", "--quiet", "-f", DIR_NAME+"/"+PL_NAME};
        try {
            execute(cmdarray);
        } catch (Exception e) {
            System.err.println("MDELite halts -- SWI Prolog Errors detected");
            System.err.println("debug this prolog file:  " );
            System.exit(1);
        }
        System.out.println("MDELite Ready to Use!");    	
    }
    
	private static void createPL() throws FileNotFoundException, UmlException {
		InputSource source = new InputSource(new FileInputStream(new File(DIR_NAME+"/"+XMI_NAME)));        
        XmiReader reader = null;
        Model.initialise("org.argouml.model.mdr.MDRModelImplementation");
        reader = Model.getXmiReader();
            List<String> searchPath = reader.getSearchPath();
        String pathList =
            System.getProperty("org.argouml.model.modules_search_path");
        if (pathList != null) {
            String[] paths = pathList.split(",");
            for (String path : paths) {
                if (!searchPath.contains(path)) {
                    reader.addSearchPath(path);
                }
            }
        }
        reader.addSearchPath(source.getSystemId());

       Collection elementsRead = reader.parse(source, false);
       RegTest.Utility.redirectStdOut(DIR_NAME+"/"+PL_NAME);
        if (elementsRead != null && !elementsRead.isEmpty()) {
            Facade facade = Model.getFacade();
            Object current;
            Iterator elements = elementsRead.iterator();
            while (elements.hasNext()) {
                current = elements.next();
                List contents = facade.getModelElementContents(current);
                System.out.print("dbname("+facade.getName(current).toLowerCase() + ", [");
                Map<String, String> classMapping = new HashMap<String, String>();
                int classId = 0; int index = 0;
                for(Object item: contents){
                    if(facade.isAClass(item)){
                    	if(index != 0) System.out.print(", ");
                    	index++;
                        System.out.print(facade.getName(item).toLowerCase());
                        classMapping.put(facade.getName(item), "class_"+(++classId));
                    }                    
                }
                System.out.println("]).");
                int attrIndex = 0;
                for(Object item: contents){
                    if(facade.isAClass(item)){
                        String className = facade.getName(item);
                    	String parent = "null";
                        Collection parents = facade.getGeneralizations(item);
                        for(Object pp : parents) {
                        	parent = classMapping.get(facade.getName(facade.getGeneral(pp)));
                        }

                        System.out.println("class("+classMapping.get(className)+", "+className.toLowerCase()+", "+facade.getVisibility(item)+", "+parent+").");
                        List items1 = facade.getModelElementContents(item);
                        for(Object item1: items1){
                            System.out.println("attribute("+"attr_"+(++attrIndex)+", "+facade.getName(item1)+", "+facade.getVisibility(item1)+").");
                        }
                        System.out.println();
                    }
                }
                int ass_index = 0;
                int ass_end_index = 0;
                for(Object item: contents){
                    if(facade.isAAssociation(item)){
                        String assoc_id = "assoc_"+(++ass_index);
                        String attr_name = (facade.getName(item).toLowerCase()).equals("") ? "null" : facade.getName(item).toLowerCase();
                        System.out.println("association("+ assoc_id +", "+ attr_name +").");
                        Collection items1 = facade.getModelElementContents(item);
                        for(Object item1: items1){
                            Object classifier = facade.getClassifier(item1);
                            String lower = (Integer)facade.getLower(item1) == -1 ? "inf" : ((Integer)(facade.getLower(item1))).toString();
                            String upper = (Integer)facade.getUpper(item1) == -1 ? "inf" : ((Integer)(facade.getUpper(item1))).toString();
                            System.out.println("association_end("+"assoc_end_"+
                            (++ass_end_index)+", "+assoc_id+ ", "+classMapping.get(facade.getName(classifier).toLowerCase())+
                            ", \""+lower+".."+upper+"\").");
                        }
                        System.out.println();
                    }
                }
            }
        }
        System.setOut(System.out);
	}

    public static boolean saveFile() {

        File file = new File(FILE_NAME);
        Project project = ProjectManager.getManager().getCurrentProject();

        PersistenceManager pm = PersistenceManager.getInstance();
        ProjectFilePersister persister = null;

        persister = pm.getSavePersister();
        pm.setSavePersister(null);
        if (persister == null) {
            persister = pm.getPersisterFromFileName(file.getName());
        }
        if (persister == null) {
            throw new IllegalStateException("Filename " + project.getName()
                    + " is not of a known file type");
        }

        // Repair any errors in the project
        String report = project.repair();

        project.preSave();
        try {
            persister.save(project, file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        project.postSave();

        return true;
    }

// original design of execute
public static void execute(String[] cmdarray) throws Exception {
    String line;
    String errorLine = "";
    boolean error = false;
    // Runtime rt = Runtime.getRuntime();
    //Process p = rt.exec(cmdarray);
    Process p = new ProcessBuilder(cmdarray).start();
    // assume input, output, error stream is standard input, output, error
    BufferedReader er = new BufferedReader(new InputStreamReader(p.getErrorStream()));
    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
    while ((line = er.readLine()) != null) {
        error = true;
        errorLine = errorLine + line + "\n";
    }
    while ((line = in.readLine()) != null) {
        System.out.println("SWIProlog Stdout : " + line);
    }
    p.waitFor();
    p.destroy();
    if (error) {
        PrintStream ps;
        ps = new PrintStream("Error.txt");
        ps.print(errorLine);
        ps.flush();
        ps.close();
        throw new Exception("Consult Error.txt");
    }
}

}

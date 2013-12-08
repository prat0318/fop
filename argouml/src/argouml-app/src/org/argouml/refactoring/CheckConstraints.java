package org.argouml.refactoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;
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
    static String CONSTRAINTS_TO_CHECK_PL = "constraintsCheck.pl";
    
    static final String OS = System.getProperty("os.name");
    
    static public boolean is_active = false;
    
    static String error_message = "";
    static Map<String, String> swiplPathMap = new HashMap<String, String>();
    
    static {
    	swiplPathMap.put("Linux", "/usr/bin/swipl");
    	swiplPathMap.put("Windows", "C:/Program Files/swipl/bin/swipl");
    	swiplPathMap.put("MacOS", "/opt/local/bin/swipl");
    	swiplPathMap.put("MAC OS X", "/opt/local/bin/swipl");
    	swiplPathMap.put("Mac OS X", "/opt/local/bin/swipl");
    	swiplPathMap.put("Other", "/usr/bin/swipl");   //ADD YOUR ENTRY TO HASH MAP if new OS
    }

    public boolean validateUML() throws Exception {
        boolean status = saveFile();
        try {
            ZipFile zipFile = new ZipFile(FILE_NAME);
            zipFile.extractAll(DIR_NAME);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        
        try {
    		is_active = true;
			createPL();
		} catch (Exception e1) {
			status = false;
			e1.printStackTrace();
		} finally {
			is_active = false;
		}

		return runSwipl();
    }

    private boolean runSwipl() throws Exception {
    	System.out.println(this.getClass().getClassLoader().getResource("").getPath());
    	String swiplPath = swiplPathMap.get(OS);
    	if(swiplPath == null) throw new Exception("ADD ENTRY OF " + OS + " to swiplPathMap");

    	String[] filesToConcat = new String[4];
    	filesToConcat[0] = "discontiguous.pl";
    	filesToConcat[1] = "metaargo.pl";
    	filesToConcat[2] = DIR_NAME + "/" + PL_NAME;
    	filesToConcat[3] = CONSTRAINTS_TO_CHECK_PL;
    	GProlog concater = new GProlog("combined", filesToConcat);
    	String combinedFile = "combined.pl";

    	String script = "script.txt";        
        try {
            PrintStream ps; 
            ps = new PrintStream(script);
            ps.println(":-['" + combinedFile + "'],tell('" + "conform.txt" + "'),run,told,halt.");
            ps.println(":-halt.");
            ps.flush();
            ps.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        String[] cmdarray = {swiplPath, "--quiet", "-f", script};
        try {
            execute(cmdarray);
        } catch (Exception e) {
            System.err.println("MDELite halts -- SWI Prolog Errors detected");
            System.err.println("debug this prolog file:  " );
        	System.out.println(System.getProperty("user.dir"));
        	throw new Exception("Could not run prolog constraints. Check your swipl path. ");
        }
        Scanner s = new Scanner(new File("conform.txt"));
        
        error_message = "";
        int count = 0;
        while(s.hasNextLine()) { error_message += ++count + ") " + s.nextLine() + "\n"; } 
        System.out.println(error_message);
        if (!error_message.equals("")) {
        	throw new Exception(error_message);
        }
        return true;
    }
    
	private void createPL() throws Exception {
        String url = DIR_NAME+"/"+XMI_NAME; File file = new File(url);
		InputSource source = new InputSource(new FileInputStream(file)); 
        source.setSystemId(file.toURI().toURL().toExternalForm());
        XmiReader reader = null;
//        Model.initialise("org.argouml.model.mdr.MDRModelImplementation");
        reader = Model.getXmiReader();
        reader.setIgnoredElements(null);
        //reader.
//            List<String> searchPath = reader.getSearchPath();
//        String pathList =
//            System.getProperty("org.argouml.model.modules_search_path");
//        if (pathList != null) {
//            String[] paths = pathList.split(",");
//            for (String path : paths) {
//                if (!searchPath.contains(path)) {
//                    reader.addSearchPath(path);
//                }
//            }
//        }
//        reader.addSearchPath(source.getSystemId());
//        System.out.println("--->"+source.getSystemId());
       Collection elementsRead = reader.parse(source, true);
       PrintWriter writer = new PrintWriter(DIR_NAME+"/" + PL_NAME, "UTF-8");
        if (elementsRead != null && !elementsRead.isEmpty()) {
            Facade facade = Model.getFacade();
            Object current;
            Iterator elements = elementsRead.iterator();
            while (elements.hasNext()) {
                current = elements.next();
                if(!(facade.isAModel(current))) continue;
//                writer.print("dbname("+facade.getName(current).toLowerCase() + ", [");
                List contents = facade.getModelElementContents(current);
                Map<String, String> classMapping = new HashMap<String, String>();
                int classId = 0; int index = 0;
                for(Object item: contents){
                    if(facade.isAClass(item)){
//                    	if(index != 0) writer.print(", ");
                    	index++;
//                    	writer.print(facade.getName(item).toLowerCase());
                        classMapping.put(facade.getUUID(item), "class_"+(++classId));
//                        System.out.println(facade.getName(item)+" "+facade.getUUID(item));
                    }                    
                }
//                writer.println("]).");
                int attrIndex = 0;
                for(Object item: contents){
                    if(facade.isAClass(item)){
                        String className = facade.getName(item);
                        String classUUID = facade.getUUID(item);
                    	String parent = "null";
                        Collection parents = facade.getGeneralizations(item);
                        for(Object pp : parents) {
                        	parent = classMapping.get(facade.getUUID(facade.getGeneral(pp)));
                        }

                        writer.println("class("+classMapping.get(classUUID)+", "+className.toLowerCase()+", "+facade.getVisibility(item)+", "+parent+").");
                        List items1 = facade.getModelElementContents(item);
                        for(Object item1: items1){
                        	writer.println("attribute("+"attr_"+(++attrIndex)+", "+facade.getName(item1).toLowerCase()+", "+classMapping.get(classUUID)+", "+facade.getVisibility(item1)+").");
                        }
                        writer.println();
                    }
                }

                int ass_index = 0;
                int ass_end_index = 0;
                for(Object item: contents){
                    if(facade.isAAssociation(item)){
                        String assoc_id = "assoc_"+(++ass_index);
                        String attr_name = (facade.getName(item).toLowerCase()).equals("") ? "null" : facade.getName(item).toLowerCase();
                        writer.println("association("+ assoc_id +", "+ attr_name +").");
                        Collection items1 = facade.getModelElementContents(item);
                        for(Object item1: items1){
                            Object classifier = facade.getClassifier(item1);
//                            System.out.println(classMapping);
//                            System.out.println(facade.getUUID(classifier) + " " + facade.getName(classifier));
                            String lower = (Integer)facade.getLower(item1) == -1 ? "inf" : ((Integer)(facade.getLower(item1))).toString();
                            String upper = (Integer)facade.getUpper(item1) == -1 ? "inf" : ((Integer)(facade.getUpper(item1))).toString();
                            writer.println("association_end("+"assoc_end_"+
                            (++ass_end_index)+", "+assoc_id+ ", "+classMapping.get(facade.getUUID(classifier))+
                            ", \""+lower+".."+upper+"\").");
                        }
                        writer.println();
                    }
                }
            }
        }
        writer.close(); 
	}

    public boolean saveFile() {

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

        // Repair any errors in the project.
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
public void execute(String[] cmdarray) throws Exception {
    String line;
    String errorLine = "";
    boolean error = false;
    Process p = new ProcessBuilder(cmdarray).start();
    
    // Assume input, output, error stream is standard input, output, error
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

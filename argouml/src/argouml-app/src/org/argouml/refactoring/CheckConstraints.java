package org.argouml.refactoring;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        if (elementsRead != null && !elementsRead.isEmpty()) {
            Facade facade = Model.getFacade();
            Object current;
            Iterator elements = elementsRead.iterator();
            while (elements.hasNext()) {
                current = elements.next();
                List contents = facade.getModelElementContents(current);
                System.out.print("dbname("+facade.getName(current)+", [");
                Map<String, String> classMapping = new HashMap<String, String>();
                int classId = 0;
                for(Object item: contents){
                    if(facade.isAClass(item)){
                        System.out.print(facade.getName(item)+", ");
//                        System.out.println(item);
                        classMapping.put(facade.getName(item), "class_"+(++classId));
                    }                    
                }
                System.out.println("]).");
                int attrIndex = 0;
                for(Object item: contents){
                    if(facade.isAClass(item)){
                        String className = facade.getName(item);
                        System.out.println("class("+classMapping.get(className)+", "+className+", "+facade.getVisibility(item)+").");
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
                        System.out.println("composition("+ assoc_id +", "+facade.getName(item)+").");
                        Collection items1 = facade.getModelElementContents(item);
                        for(Object item1: items1){
                            Object classifier = facade.getClassifier(item1);
                            String lower = (Integer)facade.getLower(item1) == -1 ? "inf" : ((Integer)(facade.getLower(item1))).toString();
                            String upper = (Integer)facade.getUpper(item1) == -1 ? "inf" : ((Integer)(facade.getUpper(item1))).toString();
                            System.out.println("association("+"assoc_end_"+
                            (++ass_end_index)+", "+assoc_id+ ", "+classMapping.get(facade.getName(classifier))+
                            ", \""+lower+".."+upper+"\").");
                        }
                        System.out.println();
                    }
                }
            }
        }
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
}

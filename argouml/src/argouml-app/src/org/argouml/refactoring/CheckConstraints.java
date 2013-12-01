package org.argouml.refactoring;

import java.io.File;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.persistence.PersistenceManager;
import org.argouml.persistence.ProjectFilePersister;
import org.argouml.persistence.SaveException;
import org.argouml.ui.SaveSwingWorker;

public class CheckConstraints {
	public static boolean validateUML() {
		boolean status = saveFile();
		
		return status;
	}
	
	public static boolean saveFile() {
		
		File file = new File("/tmp/constraints.zargo");
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

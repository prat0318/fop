package org.argouml.refactoring;

import java.io.File;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.persistence.PersistenceManager;
import org.argouml.persistence.ProjectFilePersister;
import org.argouml.ui.ProjectBrowser;

public class RefactoringUndoManager {
	public static String BACKUP_FILE = "/tmp/constraints/backup.zargo";
	
	public static void backUp() {
		saveFile();
	}
	
	public static void reloadbackUp() {
		 File file = new File(BACKUP_FILE);
	     Project project = ProjectManager.getManager().getCurrentProject();
	     
	     ProjectBrowser.getInstance().loadProjectWithProgressMonitor(
         		file, true);
	}
	
    public static boolean saveFile() {
        File file = new File(BACKUP_FILE);
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
        	//persister.
            persister.save(project, file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        project.postSave();

        return true;
    }
}

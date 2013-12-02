package org.argouml.ui.cmd;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractAction;

import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.i18n.Translator;
import org.argouml.ui.HelpBox;
import org.argouml.ui.ChangeSignatureBox;
import org.argouml.ui.MoveBox;
import org.argouml.ui.targetmanager.TargetManager;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.UmlClass;
import org.omg.uml.foundation.core.Operation;
import org.tigris.gef.presentation.Fig;


/**
 * The action to show the ArgoUML help dialog.
 */
public class ActionMove extends AbstractAction {
    private static final Logger LOG =
            Logger.getLogger(ActionChangeSignature.class.getName());

    /**
     * Constructor.
     */
    public ActionMove() {
        super(Translator.localize("action.move"), null);
    }

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae) {
    	ArgoDiagram diagram = DiagramUtils.getActiveDiagram();
    	List nodes = diagram.getNodes();
    	List<UmlClass> classes = new ArrayList<UmlClass>();
    	Object source = TargetManager.getInstance().getTarget();
    	
    	if (!nodes.isEmpty()) {
    		for(int i=0;i<nodes.size();i++){
    			UmlClass c = (UmlClass)nodes.get(i);  
    			if(source instanceof Operation){
    				if(((Operation) source).getOwner().getName() == c.getName()){
    					continue;
    				}
    				else{
    					classes.add(c);
    				}
    			}
    		}
    		
    		if (source != null && source instanceof Operation) {
	    		MoveBox box = new MoveBox(Translator.localize("action.move"), source, classes);
	    		box.setVisible(true);	
    		}
    	} else {
    		//OptionPane.showMessageDialog(diagram, "here comes the text.");  
    	}
    	
    }

    /**
     * The UID.
     */
    private static final long serialVersionUID = 0L;
} /* end class ActionHelp */

// Gaurav Nanda.
package org.argouml.ui.cmd;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractAction;

import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.i18n.Translator;
import org.argouml.ui.HelpBox;
import org.argouml.ui.ChangeSignatureBox;
import org.argouml.ui.targetmanager.TargetManager;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.omg.uml.foundation.core.UmlClass;
import org.omg.uml.foundation.core.Operation;
import org.tigris.gef.presentation.Fig;


/**
 * The action to show the ArgoUML help dialog.
 */
public class ActionChangeSignature extends AbstractAction {
    private static final Logger LOG =
            Logger.getLogger(ActionChangeSignature.class.getName());

    /**
     * Constructor.
     */
    public ActionChangeSignature() {
        super(Translator.localize("action.change-method-signature"), null);
    }

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae) {
    	ArgoDiagram diagram = DiagramUtils.getActiveDiagram();
    	List nodes = diagram.getNodes();
    	
    	if (!nodes.isEmpty()) {
    		Object target = TargetManager.getInstance().getTarget();
    		if (target != null && target instanceof Operation) {
	    		ChangeSignatureBox box = new ChangeSignatureBox(Translator.localize("action.change-method-signature"), target);
	    		//LOG.info(target.getClass().getName());
	    		
	//    		if (target != null && target instanceof UmlClass) {
	//    			UmlClass ele = (UmlClass) target;
	//    			String name = ele.getName();
	//    			
	//    			box.setRenameNode();
	//    		} else {
	//    			// This is what we are not doing currently. 
	//    			box.addNodes(nodes);
	//    		}
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

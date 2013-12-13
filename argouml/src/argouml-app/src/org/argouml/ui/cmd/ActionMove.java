package org.argouml.ui.cmd;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.omg.uml.foundation.core.AssociationEnd;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.Generalization;
import org.omg.uml.foundation.core.UmlClass;
import org.omg.uml.foundation.core.AssociationClass;
import org.omg.uml.foundation.core.UmlAssociation;
import org.omg.uml.foundation.core.Operation;
import org.omg.uml.foundation.core.Attribute;
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
    	List edges = diagram.getEdges();
    	List<UmlClass> classes = new ArrayList<UmlClass>();
    	Object source = TargetManager.getInstance().getTarget();
    	
    	    	
    	if (!edges.isEmpty()) {
    		for(int i=0;i<edges.size();i++){
    			UmlClass srcClass = null;
    			if(source instanceof Operation){
    				Operation op = (Operation)source;
    				srcClass = (UmlClass)op.getOwner();
    			}
    			else if(source instanceof Attribute){
    				Attribute at = (Attribute)source;
    				srcClass = (UmlClass)at.getOwner();
    			}
    			Map<String,AssociationEnd> aemap = new HashMap<String,AssociationEnd>();
   				UmlAssociation c = (UmlAssociation)edges.get(i);
   				LOG.info("@@@@@@@@##############TEST: " + c.getName());
   				List<AssociationEnd> aend = c.getConnection();
   				for(AssociationEnd a:aend){
   					LOG.info("@@@@@@@@##############TEST: " + a.getParticipant().getName());
   					aemap.put(c.getName()+a.getParticipant().getName(), a);
   				}
    			
   				if(aemap.containsKey(c.getName()+srcClass.getName())){
   					for (Map.Entry<String, AssociationEnd> entry : aemap.entrySet())
   					{
   						if(!entry.getKey().equals(c.getName()+srcClass.getName())){
   							UmlClass targetClass = (UmlClass)entry.getValue().getParticipant();
   							classes.add(targetClass);
   						}
   					}
   				}   			
    		}
    		
    		if (source != null && (source instanceof Operation || source instanceof Attribute)) {
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

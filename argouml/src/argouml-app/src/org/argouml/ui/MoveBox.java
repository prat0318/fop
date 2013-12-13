package org.argouml.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.*;

import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.DataType;
import org.omg.uml.foundation.core.Operation;
import org.omg.uml.foundation.core.Feature;
import org.omg.uml.foundation.core.Attribute;
import org.omg.uml.foundation.core.Parameter;
import org.omg.uml.foundation.core.UmlClass;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.refactoring.CheckConstraints;
import org.argouml.refactoring.RefactoringUndoManager;
import org.argouml.ui.targetmanager.TargetManager;

public class MoveBox  extends JFrame implements ActionListener{
    private static final Logger LOG =
            Logger.getLogger(ChangeSignatureBox.class.getName());
    
    private Feature source;
    private String[] classes;
    private Map<UmlClass,String> destClasses;
    private String dest;
   

	/**
     * Class constructor.
     * @param title      the title of the help window.
     */
	public MoveBox(String title, Object source, List<UmlClass> c) {
		super(title);
		
		if(source instanceof Operation)
			this.source = (Operation) source;
		else if(source instanceof Attribute)
			this.source = (Attribute) source;
		
		this.classes = new String[c.size()];
		this.destClasses = new HashMap<UmlClass,String>();
		
		for(int i=0;i<c.size();i++){
			if(c.get(i).getName() == ""){
				this.classes[i] = c.get(i).refMofId();
				this.destClasses.put(c.get(i),c.get(i).refMofId());
			}
			else{
				this.classes[i] = c.get(i).getName();			
				this.destClasses.put(c.get(i),c.get(i).getName());
			}
		}
		
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(scrSize.width / 2 - 400, scrSize.height / 2 - 300);

		getContentPane().setLayout(new BorderLayout());
		setSize( 400, 200);
		setRenameNode();
	}
	
	public void setRenameNode() {
		
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
               
        String sourceName = "";
        if(source.getOwner().getName() != "")
        	sourceName = source.getOwner().getName();
        else
        	sourceName = source.getOwner().refMofId();
        
        JLabel gui_source = new JLabel("Source Class: " + sourceName);
        
        JLabel gui_destination = new JLabel("Destination Class:");
        final JComboBox gui_destClasses = new JComboBox(classes);
        dest = String.valueOf(gui_destClasses.getSelectedItem());      

        cp.add(gui_source);
        cp.add(gui_destination);
        cp.add(gui_destClasses);
        
        setSize(230, 140);
        
        JButton submitButton = new JButton("Move");
        if(classes.length == 0){
        	submitButton.setEnabled(false);
        }
        cp.add(submitButton); 
                
        gui_destClasses.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	dest = String.valueOf(gui_destClasses.getSelectedItem());
            }
        });                       
        submitButton.addActionListener(this);          
	}
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{			
		
		try {
			// Lets just save the entire project for now and then we would see what happens next.
			RefactoringUndoManager.saveFile();
			
			//Action begin
			Classifier c = null;
			for (Map.Entry<UmlClass, String> entry : destClasses.entrySet()){
				if(entry.getValue() == dest){
					LOG.info("Owner: " + entry.getKey().getName());
					c = (Classifier)entry.getKey();
					break;
				}
			}
							
			if(source instanceof Operation){
				Operation op = (Operation) source;
				Parameter newP = ((org.omg.uml.UmlPackage) source.getOwner().refOutermostPackage())
    					.getCore().getParameter().createParameter();
				newP.setType((org.omg.uml.foundation.core.Classifier)source.getOwner());
				newP.setName(source.getOwner().getName().toLowerCase());
				newP.setBehavioralFeature(op);

				for(int i=0; i < op.getParameter().size();i++){
				Parameter p = op.getParameter().get(i);
				
				if(p.getType()!= null && p.getType().equals(c)){
					((Operation) source).getParameter().remove(i);
					break;
//					Parameter newP = ((org.omg.uml.UmlPackage) source.getOwner().refOutermostPackage())
//	    					.getCore().getParameter().createParameter();
//					newP.setType((org.omg.uml.foundation.core.Classifier)source.getOwner());
//					newP.setName("class");
//					newP.setBehavioralFeature(op);
				}
	       }
	       }

			source.setOwner(c);
	        TargetManager.getInstance().removeTarget(source);

			//Action end

			
			// Save the project. Invoke the swipl module with this path for checking constraints.
			try {
				boolean status = (new CheckConstraints()).validateUML();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage() + "\n\n" + "Going to revert back to the original state.");
				RefactoringUndoManager.reloadbackUp();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dispose();
		}
	}
}

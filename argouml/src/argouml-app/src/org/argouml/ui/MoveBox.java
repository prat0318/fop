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
import org.omg.uml.foundation.core.Parameter;
import org.omg.uml.foundation.core.UmlClass;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.ui.targetmanager.TargetManager;

public class MoveBox  extends JFrame implements ActionListener{
    private static final Logger LOG =
            Logger.getLogger(ChangeSignatureBox.class.getName());
    
    private Operation source;
    private String[] classes;
    private Map<UmlClass,String> destClasses;
    private String dest;
   

	/**
     * Class constructor.
     *
     * @param title      the title of the help window.
     */
	public MoveBox(String title, Object source, List<UmlClass> c) {
		super(title);
		
		this.source = (Operation) source;
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
               
        JLabel gui_source = new JLabel("Source Class: " + source.getOwner().getName());
        
        JLabel gui_destination = new JLabel("Destination Class:");
        final JComboBox gui_destClasses = new JComboBox(classes);
        dest = String.valueOf(gui_destClasses.getSelectedItem());      

        cp.add(gui_source);
        cp.add(gui_destination);
        cp.add(gui_destClasses);
        
        setSize(210, 140);
        
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
		Classifier c = null;
		for (Map.Entry<UmlClass, String> entry : destClasses.entrySet()){
			if(entry.getValue() == dest){
				LOG.info("Owner: " + entry.getKey().getName());
				c = (Classifier)entry.getKey();
				break;
			}
		}
						
        source.setOwner(c);
        TargetManager.getInstance().removeTarget(source);
    
        this.dispose();
	}
}

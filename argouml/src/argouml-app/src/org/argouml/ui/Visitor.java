/**
 * 
 */
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.*;

import org.omg.uml.foundation.core.*;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.omg.uml.foundation.core.ModelElement;

/**
 * @author bansal
 *
 */
public class Visitor extends JFrame implements ActionListener{

    private static final Logger LOG = Logger.getLogger(Visitor.class.getName());

    private List nodes = new ArrayList();
    private Object target;
    private List<UmlClass> selectedClasses;
    private Operation[] visitorMethods;
    private String dest;

    JTextField gui_class_name;

    /**
     * Class constructor.
     *
     * @param title      the title of the help window.
     */
    public Visitor(String title, Object target) {
        super(title);

        Object[] targetsList = (Object[]) target;
        this.selectedClasses = new ArrayList<UmlClass>();
        for(Object t:targetsList){
        	if(t instanceof UmlClass){
        		//Valid 
        		this.selectedClasses.add((UmlClass)t);
        	}else{
        		//Unvalid selection 
        	}
        }
        //Then we should be able to do this  
        //this.selectedClasses = (UmlClass[]) target;

//        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
//        setLocation(scrSize.width / 2 - 400, scrSize.height / 2 - 300);
//
//        getContentPane().setLayout(new BorderLayout());
//        setSize( 400, 200);
        setRenameNode();
    }
    
    
//CoreFactoryMDRImpl Class has the code which is used to create all the objects present in the model 
//Class : ProjectImpl.java contains the fucntion findType used to find the type of the 
//selected node

//  	public UmlClass createClass(org.omg.uml.UmlPackage extent) {
//  		Model.getCoreFactory().buildClass(getCurrentNamespace());
//  		UmlClass myClass = extent.getCore().getUmlClass().createUmlClass();
//  	    super.initialize(myClass);
//  	    return myClass;
//  	}

  	

      //Model.getCoreFactory().buildClass(getCurrentNamespace());




    private void setRenameNode() {
        String method_name = null, return_type = null;
        
        for(UmlClass class1: selectedClasses){
        	Collection<org.omg.uml.foundation.core.ModelElement> elementSet = class1.getOwnedElement();
        	//extract the methods because every operation and attribute implements ModelElement
        }
        
        //For each of the extracted Operation Check which signatures are similar 
        
        //Get the name of the class User wants for the new Visitor Class = gui_class_name 
        
        //        JButton submitButton = new JButton("Create Visitor pattern ");
        //        cp.add(submitButton);
        //        submitButton.addActionListener(this);
    }



    public void createVisitorClass(){

    	Namespace namespace =(Namespace) Model.getFacade().getNamespace(selectedClasses.get(0));
		UmlClass visitorClass = (UmlClass)Model.getCoreFactory().buildClass(dest, namespace);
		//Classifier classifier = Model.getCoreFactory().createClass();
		//Operation o = Model.getCoreFactory().buildOperation(classifier, returnType)
		//o.setOwner(visitorClass);

    }

    @Override
        public void actionPerformed(ActionEvent event) {
    	//From all the elected classes Extract the operations are part of it 
    	Map<UmlClass,List<Operation>> test = new HashMap<UmlClass,List<Operation>>();
    	for(UmlClass class1:selectedClasses){
    		Collection<ModelElement> elementList = class1.getOwnedElement();
    		Iterator<ModelElement> it = elementList.iterator();
    		List<Operation> opList = new ArrayList<Operation>();
    		while(it.hasNext()){
    			ModelElement i = it.next();
    			if(i instanceof Operation){
    				opList.add((Operation)i);
    			}
    		}
    		test.put(class1,opList);
    	
    		}
    	//These methods will be used to create the Dialog Box needed for the user to select the Functions 
        //Get rid of the dialog box.
    	
        this.dispose();
        createVisitorClass();
        }
}

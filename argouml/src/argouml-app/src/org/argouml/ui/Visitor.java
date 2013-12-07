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
import java.awt.Rectangle;
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
import org.omg.uml.foundation.core.*;
import org.argouml.refactoring.CheckConstraints;
import org.argouml.refactoring.RefactoringUndoManager;
import org.argouml.uml.diagram.DiagramElement;
import org.omg.uml.foundation.datatypes.CallConcurrencyKindEnum;
import org.omg.uml.foundation.datatypes.ParameterDirectionKindEnum;
import org.omg.uml.foundation.datatypes.ScopeKindEnum;
import org.omg.uml.foundation.datatypes.VisibilityKindEnum;
import org.argouml.uml.diagram.static_structure.ui.*;

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
    private UMLClassDiagram diagram;
    
    JTextField gui_class_name;

    /**
     * Class constructor.
     *
     * @param title      the title of the help window.
     */
    public Visitor(String title, Object target, Object diagram) {
        super(title);
        this.diagram = (UMLClassDiagram) diagram;
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
    
    
  	

      //Model.getCoreFactory().buildClass(getCurrentNamespace());

    private void setRenameNode() {
        String method_name = null, return_type = null;
        
        for(UmlClass class1: selectedClasses){
        	Collection<org.omg.uml.foundation.core.ModelElement> elementSet = class1.getOwnedElement();
        	//extract the methods because every operation and attribute implements ModelElement
        }
        
        //For each of the extracted Operation Check which signatures are similar 
       
        //Get the name of the class User wants for the new Visitor Class = gui_class_name 
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
    	
		
    	UmlClass klass = selectedClasses.get(0);
		
		// Save the project. Invoke the swipl module with this path for checking constraints.
		UmlClass visitor = null ;
		Operation newOP;
		
		visitor = (UmlClass) Model.getCoreFactory().buildClass("visitor", klass.getNamespace());
		Rectangle bounds = new Rectangle();
		diagram.createDiagramElement(test, bounds);
			
		List<Feature> eleList = klass.getFeature();
		for(Operation op : visitorMethods){
			List<Parameter> pList = op.getParameter();
					newOP = (Operation) Model.getCoreFactory().buildOperation2(klass, pList.get(0).getType() , "visitorOp");
			}
		

    	//These methods will be used to create the Dialog Box needed for the user to select the Functions 
        //Get rid of the dialog box.
//    	createVisitorClass();
//    	ChangeSignatureBox.change_method_signature(Operation operation,
//                String method_name, String method_return_type,
//                String [] s_param_names, String [] s_return_types);
        this.dispose();
        createVisitorClass();
        }
}

//NOTES 

//CoreFactoryMDRImpl Class has the code which is used to create all the objects present in the model 
//Class : ProjectImpl.java contains the fucntion findType used to find the type of the 
//selected node

//	public UmlClass createClass(org.omg.uml.UmlPackage extent) {
//	}

//Parameter param = ((org.omg.uml.UmlPackage) klass.refOutermostPackage())
//.getCore().getParameter().createParameter();
//param.setType((org.omg.uml.foundation.core.Classifier)klass);
//Parameter returnParameter = (Parameter) Model.getCoreFactory().buildParameter( (Classifier) ele, pList.get(0));

//FigNodeModelElement  figNode = new FigClass(modelElement, bounds, settings);
//DiagramElement de = UMLClassDiagram.createDiagramElement(droppedObject, bounds);

//Parameter returnParameter = (Parameter) Model.getCoreFactory().buildParameter( (Classifier) ele,(BehavioralFeature) ((UmlClass)ele).getFeature().get(2));
//op = (Operation) Model.getCoreFactory().buildOperation2(((Operation) f).getOwner(), returnParameter , "testOP");
//op = (Operation) Model.getCoreFactory().buildOperation2(((Operation) i).getOwner(), i, "testOP");

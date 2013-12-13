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

import java.awt.Point;

import org.omg.uml.foundation.core.*;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.omg.uml.foundation.core.*;
import org.argouml.refactoring.CheckConstraints;
import org.argouml.refactoring.RefactoringUndoManager;
//import org.argouml.refactoring.RefactoringUndoManager;
import org.argouml.uml.diagram.DiagramElement;
import org.omg.uml.foundation.datatypes.CallConcurrencyKindEnum;
import org.omg.uml.foundation.datatypes.ParameterDirectionKindEnum;
import org.omg.uml.foundation.datatypes.ScopeKindEnum;
import org.omg.uml.foundation.datatypes.VisibilityKindEnum;
import org.argouml.uml.diagram.static_structure.ui.*;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.LayerPerspective;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigEdge;
/**
 * @author bansal
 *
 */
public class Visitor extends JFrame implements ActionListener{

    private static final Logger LOG = Logger.getLogger(Visitor.class.getName());

    private List nodes = new ArrayList();
    private Object target;
    private List<UmlClass> selectedClasses;
    private UmlClass selectedClass;
    private Operation[] visitorMethods;
    private String commonMethod;
    private UMLClassDiagram diagram;
    private Operation selectedOp;
    
    String gui_class_name ="Visitor";

//    public Visitor(String title, List<Object> target, Object diagram) {
//        super(title);
//        this.diagram = (UMLClassDiagram) diagram;
//        this.selectedClasses = new ArrayList<UmlClass>();
//        for(Object t:target){
//        	if(t instanceof UmlClass){ 
//        		this.selectedClasses.add((UmlClass)t);
//        		
//        	}else{
//        		//Invalid selection 
//        	}
//        }
//        
//        LOG.info("@@@@@@@@@@@@@@@" + selectedClasses.size());
//        
//		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
//		setLocation(scrSize.width / 2 - 400, scrSize.height / 2 - 300);
//
//		getContentPane().setLayout(new BorderLayout());
//		setSize( 400, 200);
//        
//        setRenameNode();
//    }
    
    public Visitor(String title, List<Object> target, Object diagram) {
        super(title);
        this.diagram = (UMLClassDiagram) diagram;
        for(Object t:target) {
        	if (t instanceof UmlClass) {
        		this.selectedClass = (UmlClass) t;
        		break;
        	}
        }
        
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(scrSize.width / 2 - 400, scrSize.height / 2 - 300);

		getContentPane().setLayout(new BorderLayout());
		setSize( 400, 200);
        
        constructVisitorBox();
    }

    private void constructVisitorBox() {
        String method_name = null, return_type = null;                      
        
        // We might now actually require a map here. But I thought for all the child classes
        // also, I might store mapping here.
        //
        // Initially this map was there for storing multiple classes we used to select
        // for the visitor pattern.
        Map<UmlClass, List<Operation>> mapClassListOp = new HashMap<UmlClass,List<Operation>>();  

        // Get the list of child classes.
    	// I know, I know !! This function should be present somewhere else. But lets live for the moment.
    	selectedClasses = ChangeSignatureBox.get_child_classes(selectedClass);
    	//System.out.print("List of child classes found: " + selectedClasses.toString());        
        
        // Add parent also here.
    	selectedClasses.add(selectedClass);
        
        // A List of common operations.
        List<Operation> lstCommonOp = new ArrayList<Operation>();
        String[] methods;

        for (UmlClass cl : selectedClasses) {
	        // Get the list of operations for the selected class and its children.
	    	List<Feature> lstFeatures = cl.getFeature();  
	    	List<Operation> lstClassOp = new ArrayList<Operation>();
	    	for(Feature ft:lstFeatures){
	    		if(ft instanceof Operation){
	    			Operation op = (Operation)ft;       			
	    			lstClassOp.add(op);
	    		}
	    		mapClassListOp.put(cl, lstClassOp);
	    	}
        }

    	// Brute force to find the list of functions which are common among the classes.
        for (Map.Entry<UmlClass, List<Operation>> entry : mapClassListOp.entrySet()) {
        	List<Operation> lstOuterOp = entry.getValue();
        	for(Operation _opOuter:lstOuterOp){
        		int ct = 0;
            	for(UmlClass _class:selectedClasses){
            		if(!_class.equals(entry.getKey())){
            			List<Operation> lstInnerOp = mapClassListOp.get(_class); 
            			if(lstInnerOp != null){
            				for(Operation _opInner:lstInnerOp){
            					//check if function names are the same
            					if(_opOuter.getName().equals(_opInner.getName())){
            						List<Parameter> lstOuterPar = _opOuter.getParameter();
            						List<Parameter> lstInnerPar = _opInner.getParameter();
            						if(lstOuterPar.size() == lstInnerPar.size()){
            							if(lstOuterPar.size() > 0 && lstInnerPar.size() > 0){
            								Boolean isSame = true;
            								for(int k=0;k<lstOuterPar.size();k++){
            									if(lstOuterPar.get(k).getType() == null){
            										if(lstInnerPar.get(k).getType() != null){
            											isSame = false;
            											break;
            										}
            									}
            									else if(lstOuterPar.get(k).getType() != null){
            										if(lstInnerPar.get(k).getType() == null){
            											isSame = false;
            											break;
            										}
            										else{
            											Parameter p = lstOuterPar.get(k);
            											String s = p.getType().getName();	
            											Parameter p2 = lstInnerPar.get(k);
            											String s2 = p2.getType().getName();                							
            											if(!lstOuterPar.get(k).getType().getName().equals(lstInnerPar.get(k).getType().getName())){
            												isSame = false;
            												break;
            											}                									
            										}
            									}
            								}
            								if(isSame)
            									ct++;                						
            							}
            						}
            					}
            				}
            			}
            		}
            	}
            	if(ct == selectedClasses.size() - 1){
            		lstCommonOp.add(_opOuter);
            	}           
        	}
        	break;
        }
        
        methods = new String[lstCommonOp.size()];
        for(int g=0;g<lstCommonOp.size();g++){
        	LOG.info("Common Operation: " + lstCommonOp.get(g).getName());
        	methods[g] = lstCommonOp.get(g).getName();
        }     	
        
        // UI.
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
               
        JLabel gui_visitor_class = new JLabel("Visitor Class Name:");
        JTextField gui_visitor_class_name = new JTextField(gui_class_name, 15);
        
        JLabel gui_common_methods = new JLabel("Common Methods:");
        final JComboBox gui_common_methods_list = new JComboBox(methods);
        commonMethod = String.valueOf(gui_common_methods_list.getSelectedItem());      

        cp.add(gui_visitor_class);
        cp.add(gui_visitor_class_name);
        cp.add(gui_common_methods);
        cp.add(gui_common_methods_list);
        
        setSize(230, 200);
        
        JButton submitButton = new JButton("Apply Visitor Pattern");
        if(methods.length == 0){
        	submitButton.setEnabled(false);
        }
        cp.add(submitButton); 
                
        gui_common_methods_list.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	commonMethod = String.valueOf(gui_common_methods_list.getSelectedItem());
            }
        });                       
        
        for(Operation _op:lstCommonOp){
        	if(_op.getName().equals(commonMethod)){
        		selectedOp = _op;
        		break;
        	}
        }
        submitButton.addActionListener(this);
      }
    
    public UmlClass createVisitorClass() {
    	Namespace namespace =(Namespace) Model.getFacade().getNamespace(selectedClasses.get(0));
		UmlClass visitorClass = (UmlClass)Model.getCoreFactory().buildClass(gui_class_name, namespace);
		Point p = new Point(550, 160);
		Fig element =  (Fig) diagram.drop(visitorClass, p);
		LayerPerspective layer = diagram.getLayer();
		layer.add(element);
		diagram.add(element);
		diagram.getGraphModel().getNodes().add(visitorClass);

		return visitorClass;
    }
    
    public Interface createVisitorInterface() {
    	Namespace namespace =(Namespace) Model.getFacade().getNamespace(selectedClasses.get(0));
    	Interface visitorInterface = (Interface) Model.getCoreFactory().buildInterface("Visitor", namespace);
		Point p = new Point(550, 10);
		Fig element =  (Fig) diagram.drop(visitorInterface, p);
		LayerPerspective layer = diagram.getLayer();
		layer.add(element);
		diagram.add(element);
		diagram.getGraphModel().getNodes().add(visitorInterface);
		
		return visitorInterface;
    }
  
    public Abstraction createRealization(UmlClass klass, Interface inf){

    	Namespace namespace =(Namespace) Model.getFacade().getNamespace(selectedClasses.get(0));
    	Abstraction realization = (Abstraction) Model.getCoreFactory().buildRealization(inf, klass, namespace);
    	diagram.getGraphModel().getEdges().add(realization);
    	
    	LayerPerspective layer = diagram.getLayer();
    	layer.presentationFor(realization);
    	//FigEdge figEdge = new FigLink(realization, settings);
    	
		//layer.getDiagramElements().add(realization);
    	//Layer lay = editor.getLayerManager().getActiveLayer();
        //FigEdge fe = (FigEdge) lay.presentationFor(getNewEdge());
        
		return realization;
    }

    
    @Override
        public void actionPerformed(ActionEvent event) {
    	//From all the elected classes Extract the operations are part of it 
		try {
			RefactoringUndoManager.saveFile();

    	UmlClass visitor = createVisitorClass();   	
    	Interface visitorInterface = createVisitorInterface();

    	for(UmlClass klass: selectedClasses){
    		List<Feature> elementList = klass.getFeature();
    		for(Feature f : elementList){
    			if(f instanceof Operation){
    				Operation op = (Operation) f;
    				if(op.getName().equals(selectedOp.getName())){
    					Operation newOP = (Operation) Model.getCoreFactory().buildOperation2(visitor, selectedOp.getParameter().get(0).getType() , "visit");
    					Operation newInfOP = (Operation) Model.getCoreFactory().buildOperation2(visitorInterface, selectedOp.getParameter().get(0).getType() , "visit");
    					
    					String[] arguments = new String[op.getParameter().size()-1];
    					String[] argumenttypes = new String[op.getParameter().size()-1];
    					
    					for(int i=0; i < op.getParameter().size();i++){
    						Parameter p = op.getParameter().get(i);;
    						if (i == 0) {
    							Parameter newP = ((org.omg.uml.UmlPackage) klass.refOutermostPackage())
    	    	    					.getCore().getParameter().createParameter();
    							newP.setType((org.omg.uml.foundation.core.Classifier)klass);
    							newP.setName("class");
    							newP.setBehavioralFeature(newOP);

    							Parameter newInfP = ((org.omg.uml.UmlPackage) klass.refOutermostPackage())
    	    	    					.getCore().getParameter().createParameter();
    							newInfP.setType((org.omg.uml.foundation.core.Classifier)klass);
    							newInfP.setName("class");
    							newInfP.setBehavioralFeature(newInfOP);

    						} else {
    							Parameter newP = ((org.omg.uml.UmlPackage) p.refOutermostPackage())
    	    	    					.getCore().getParameter().createParameter();
    							if(p.getType()!=null)
    	    						newP.setType((org.omg.uml.foundation.core.Classifier)p.getType());
    							newP.setName(p.getName());
    							newP.setBehavioralFeature(newOP);
    							
    							Parameter newInfP = ((org.omg.uml.UmlPackage) p.refOutermostPackage())
    	    	    					.getCore().getParameter().createParameter();
    							if(p.getType()!=null)
    								newInfP.setType((org.omg.uml.foundation.core.Classifier)p.getType());
    							newInfP.setName(p.getName());
    							newInfP.setBehavioralFeature(newInfOP);
						
        						arguments[i-1] = p.getName();
        						
        						if(p.getType()!=null)
        							argumenttypes[i-1] = p.getType().getName();
    						}
    					}
    					// Change method signature accept string as return type, which messes it up.
    					// Therefore, retain your return type and YOO !!!	
    					Classifier returnType = op.getParameter().get(0).getType();
    					boolean sigChange = ChangeSignatureBox.change_method_signature(op, "accept", "", arguments, argumenttypes);
    					op.getParameter().get(0).setType(returnType);
    					
    					System.out.println("Return type: " + op.getParameter().get(0).getName());
    					System.out.println("Signature " + sigChange);
    				}
    			}
    		}
    	}
    	
		try {
			boolean status = (new CheckConstraints()).validateUML();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage() + "\n\n" + "Going to revert back to the original state.");
			RefactoringUndoManager.reloadbackUp();
		}
		// Project it back.
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		this.dispose();
	}

		// Save the project. Invoke the swipl module with this path for checking constraints.
//		visitor = (UmlClass) Model.getCoreFactory().buildClass("visitor", klass.getNamespace());
//		Point p = new Point(240, 90);
//		Fig element =  (Fig) diagram.drop(visitor, p);
//		LayerPerspective layer = diagram.getLayer();
//		layer.add(element);
			
//		List<Feature> eleList = klass.getFeature();
//		for(Operation op : visitorMethods){
//			List<Parameter> pList = op.getParameter();
//					newOP = (Operation) Model.getCoreFactory().buildOperation2(klass, pList.get(0).getType() , "visitorOp");
//			}
		

    	//These methods will be used to create the Dialog Box needed for the user to select the Functions 
        //Get rid of the dialog box.
//    	createVisitorClass();
//    	ChangeSignatureBox.change_method_signature(Operation operation,
//                String method_name, String method_return_type,
//                String [] s_param_names, String [] s_return_types);
        this.dispose();
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

// createRealization(visitor,visitorInterface);

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
//import org.argouml.refactoring.RefactoringUndoManager;
import org.argouml.uml.diagram.DiagramElement;
import org.omg.uml.foundation.datatypes.CallConcurrencyKindEnum;
import org.omg.uml.foundation.datatypes.ParameterDirectionKindEnum;
import org.omg.uml.foundation.datatypes.ScopeKindEnum;
import org.omg.uml.foundation.datatypes.VisibilityKindEnum;
import org.argouml.uml.diagram.static_structure.ui.*;
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
    private Operation[] visitorMethods;
    private String commonMethod;
    private UMLClassDiagram diagram;
    
    JTextField gui_class_name;

    public Visitor(String title, List<Object> target, Object diagram) {
        super(title);
        this.diagram = (UMLClassDiagram) diagram;
        this.selectedClasses = new ArrayList<UmlClass>();
        for(Object t:target){
        	if(t instanceof UmlClass){ 
        		
        		this.selectedClasses.add((UmlClass)t);
        		
        	}else{
        		//Invalid selection 
        	}
        }
        
        LOG.info("@@@@@@@@@@@@@@@" + selectedClasses.size());
        
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(scrSize.width / 2 - 400, scrSize.height / 2 - 300);

		getContentPane().setLayout(new BorderLayout());
		setSize( 400, 200);
        
        setRenameNode();
    }

    private void setRenameNode() {
        String method_name = null, return_type = null;                      
        Map<UmlClass, List<Operation>> mapClassListOp = new HashMap<UmlClass,List<Operation>>();        
        List<Operation> lstCommonOp = new ArrayList<Operation>();
        String[] methods;
        Operation selectedOp;
                       
        for(UmlClass cl: selectedClasses){
        	List<Feature> lstFeatures = cl.getFeature();  
        	List<Operation> lstClassOp = new ArrayList<Operation>();
        	for(Feature ft:lstFeatures){
        		if(ft instanceof Operation){
        			Operation op = (Operation)ft;
        			LOG.info("$$$$$$$$ Method Name: " + op.getName());        			
        			lstClassOp.add(op);
        		}
        		mapClassListOp.put(cl, lstClassOp);
        	}
        }
        	
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
            	LOG.info("%%%%%%%%%%%%%%%%%%%% ct size: " + ct);
            	if(ct == selectedClasses.size() - 1){
            		lstCommonOp.add(_opOuter);
            	}           
        	}
        	break;
        }
        
        LOG.info("!!!!!!!!!!!!!!!!!!!!Common Size: " + lstCommonOp.size());
        
        methods = new String[lstCommonOp.size()];
        for(int g=0;g<lstCommonOp.size();g++){
        	LOG.info("Common Operation: " + lstCommonOp.get(g).getName());
        	methods[g] = lstCommonOp.get(g).getName();
        }
        	
        
        //UI
        String gui_class_name = "";
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
                
        //For each of the extracted Operation Check which signatures are similar 
       
        //Get the name of the class User wants for the new Visitor Class = gui_class_name
        
        //selectedClasses -- classes selected by the user -- List<UmlClass>
        //gui_class_name -- destination visitor class name -- String
        //selectedOp -- selected common method -- Operation
         
      }



    public void createVisitorClass(){

    	Namespace namespace =(Namespace) Model.getFacade().getNamespace(selectedClasses.get(0));
		UmlClass visitorClass = (UmlClass)Model.getCoreFactory().buildClass(commonMethod, namespace);
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
		Point p = new Point(240, 90);
		Fig element =  (Fig) diagram.drop(visitor, p);
		LayerPerspective layer = diagram.getLayer();
		layer.add(element);
			
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

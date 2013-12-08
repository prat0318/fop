package org.argouml.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;

import java.awt.Point;

import org.argouml.model.Model;
import org.omg.uml.foundation.core.Feature;
import org.omg.uml.foundation.core.Operation;
import org.omg.uml.foundation.core.Method;
import org.omg.uml.foundation.core.Parameter;
import org.argouml.refactoring.CheckConstraints;
import org.argouml.refactoring.RefactoringUndoManager;
import org.argouml.uml.diagram.DiagramElement;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.UmlClass;
import org.tigris.gef.base.LayerPerspective;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigEdge;


// Gaurav Nanda
public class RenameBox  extends JFrame implements ActionListener{
    private static final Logger LOG =
            Logger.getLogger(RenameBox.class.getName());
    
    private List nodes = new ArrayList();
    private Object target;
    private UMLClassDiagram diagram;
    
	JLabel label;
	JLabel renameLabel;
	JTextField textField;
	
	/**
     * Class constructor.
     *
     * @param title      the title of the help window.
     */
	public RenameBox(String title, Object target, Object diagram) {
		super(title);
		
		this.target = target;
		this.diagram = (UMLClassDiagram) diagram;
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(scrSize.width / 2 - 400, scrSize.height / 2 - 300);

		getContentPane().setLayout(new BorderLayout());
		setSize( 400, 200);
		setRenameNode();
	}
	
	public void setRenameNode() {
		org.omg.uml.foundation.core.ModelElement ele = (org.omg.uml.foundation.core.ModelElement) target;
		String name = ele.getName();
		
		label = new JLabel("Target: " + name);
		renameLabel = new JLabel("Rename To: ");
		textField = new JTextField(50);
		
		//Lay out the text controls and the labels.
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        
        c.anchor = GridBagConstraints.EAST;
        textControlsPane.setLayout(gridbag);
        
        c.gridwidth = GridBagConstraints.REMAINDER; 
        c.fill = GridBagConstraints.HORIZONTAL;
        textControlsPane.add(label, c);
        
        c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
        c.fill = GridBagConstraints.NONE;      //reset to default
        c.weightx = 0.0;
        textControlsPane.add(renameLabel, c);
        
        c.gridwidth = GridBagConstraints.REMAINDER;     //end row
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        textControlsPane.add(textField, c);
        
        JButton submitButton = new JButton("Rename");
        c.gridwidth = GridBagConstraints.REMAINDER;     //end row
        c.fill = GridBagConstraints.NONE;
        c.weightx = 1.0;
        textControlsPane.add(submitButton, c);
        submitButton.addActionListener(this);
        
        textControlsPane.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Rename refactornig."),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
        
        this.add(textControlsPane);
	}
	
	public void addNodes(List nodes) {
		this.nodes.addAll(nodes);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String newName = this.textField.getText();
		
		try {
			// Lets just save the entire project for now and then we would see what happens next.
			RefactoringUndoManager.saveFile();
			
			org.omg.uml.foundation.core.ModelElement ele = (org.omg.uml.foundation.core.ModelElement) target;
			ele.setName(newName);
			
			// Save the project. Invoke the swipl module with this path for checking constraints.

			UmlClass visitor = null ;
			Operation newOP;
			
			UmlClass klass = (UmlClass) ele;
			
			
			visitor = (UmlClass) Model.getCoreFactory().buildClass("visitor", klass.getNamespace());
			//Rectangle bounds = new Rectangle(240, 90 );
			//diagram.createDiagramElement(visitor, bounds);
			Point p = new Point(240, 90);
			Fig element =  (Fig) diagram.drop(visitor, p);
			LayerPerspective layer = diagram.getLayer();
			layer.add(element);
				
			List<Feature> eleList = klass.getFeature();
			Operation op = (Operation)klass.getFeature().get(0);
			{
				List<Parameter> pList = op.getParameter();
				newOP = (Operation) Model.getCoreFactory().buildOperation2(klass, pList.get(0).getType() , "newOp");
				newOP = (Operation) Model.getCoreFactory().buildOperation2(visitor, pList.get(0).getType() , "visitorOp");
			}
			

			
			boolean status = (new CheckConstraints()).validateUML();
			
			if (!status) {
				JOptionPane.showMessageDialog(this, "This refactoring is not possible. Reverting back.");
				RefactoringUndoManager.reloadbackUp();
				// We would be throwing some exception actually and catching here to display any error.
			}
			// Project it back.
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dispose();
		}
	}
}


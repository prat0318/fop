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
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;

import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.DataType;
import org.omg.uml.foundation.core.Operation;
import org.omg.uml.foundation.core.Parameter;
import org.omg.uml.foundation.core.UmlClass;

// Gaurav Nanda
public class ChangeSignatureBox  extends JFrame implements ActionListener{
    private static final Logger LOG =
            Logger.getLogger(ChangeSignatureBox.class.getName());
    
    private List nodes = new ArrayList();
    private Operation target;
    
    JTextField gui_method_return_type, gui_method_name;
    JTextField return_types[], param_names[];

    // Temporary class to hold required elements of the operation.
    class __param {
        Parameter param;
        String type, name;

        __param(Parameter param, String type, String name) {
            this.param = param;
            this.type = type;
            this.name = name;
        }
    }

    List<__param> params;

	/**
     * Class constructor.
     *
     * @param title      the title of the help window.
     */
	public ChangeSignatureBox(String title, Object target) {
		super(title);
		
		this.target = (Operation) target;
		
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(scrSize.width / 2 - 400, scrSize.height / 2 - 300);

		getContentPane().setLayout(new BorderLayout());
		setSize( 400, 200);
		setRenameNode();
	}
	
	public void setRenameNode() {
        String method_name = null, return_type = null;
        List<Parameter> param_list = target.getParameter();
        Parameter return_param = param_list.get(0);

        method_name = target.getName();
        return_type = return_param.getType() != null ?
            return_param.getType().getName() : "void";

        // Iterate over params and save them in our "struct".
        params = new ArrayList<__param>();
        for (int i = 1; i < param_list.size(); i++) {
            Parameter param = param_list.get(i);
            Classifier type = param.getType();
            String type_name = type != null ? type.getName() : "void";
            params.add(new __param(param, type_name, param.getName()));
        }

        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());

        JLabel gui_return_type = new JLabel("Return type:");
        gui_method_return_type = new JTextField(return_type, 15);

        JLabel gui_name = new JLabel("Method name:");
        gui_method_name = new JTextField(method_name, 15);

        cp.add(gui_return_type);
        cp.add(gui_method_return_type);
        cp.add(gui_name);
        cp.add(gui_method_name);

        return_types = new JTextField[param_list.size()];
        param_names = new JTextField[param_list.size()];

        int i = 0;
        for (__param param : params) {
            return_types[i] = new JTextField(param.type, 15);
            param_names[i] = new JTextField(param.name, 15);

            cp.add(return_types[i]);
            cp.add(param_names[i]);

            i += 1;
        }

        setSize(350, 400);

        JButton submitButton = new JButton("Change method signature");
        cp.add(submitButton);
        submitButton.addActionListener(this);
	}

    public boolean is_input_valid() {
        if (this.gui_method_name.getText().length() == 0) return false;

    /* 
        FIXME: Until we can create new types, don't validate return type.
        if (this.gui_method_return_type.getText().length() == 0)  return false;
    */

        for (__param param : params) {
            if (param.name.length() == 0) return false;
            if (param.type.length() == 0) return false;
        }

        return true;
    }
	
	@Override
	public void actionPerformed(ActionEvent event) {
        if (is_input_valid()) {
            String method_name = this.gui_method_name.getText();
            String method_return_type = this.gui_method_return_type.getText();

            target.setName(method_name);
    
            List<Parameter> param_list = target.getParameter();
            Parameter return_param = param_list.get(0);
            if (return_param.getType() == null) {
                // FIXME: Create a new type.
            } else {
                return_param.getType().setName(method_return_type);
            }

            int i = 0;
            for (__param param : params) {
                param.param.setName(param_names[i].getText());

                if (param.param.getType() == null) {
                    // FIXME: Create a new type.
                } else {
                    param.param.getType().setName(return_types[i].getText());
                }

                i += 1;
            }

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input!",
                    "Input validator", JOptionPane.INFORMATION_MESSAGE);
        }
	}
}

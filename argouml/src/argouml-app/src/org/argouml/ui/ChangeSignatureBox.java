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
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.swing.*;

import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.DataType;
import org.omg.uml.foundation.core.Feature;
import org.omg.uml.foundation.core.Generalization;
import org.omg.uml.foundation.core.Operation;
import org.omg.uml.foundation.core.Parameter;
import org.omg.uml.foundation.core.UmlClass;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;

import org.argouml.model.Model;

import org.argouml.refactoring.CheckConstraints;

// Gaurav Nanda
public class ChangeSignatureBox  extends JFrame implements ActionListener{
    private static final Logger LOG =
        Logger.getLogger(ChangeSignatureBox.class.getName());

    private List nodes = new ArrayList();
    private Operation target;

    JTextField gui_method_return_type, gui_method_name;
    JTextField return_types[], param_names[];

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

    private static List<Operation> get_matching_operations(UmlClass klass, Operation
            op_gold) {
        List<Feature> operation_list = klass.getFeature();
        List<Operation> return_list = new ArrayList<Operation>();
        List<Parameter> param_list_gold = op_gold.getParameter();

        for (Feature _op_silv : operation_list) {
            boolean match = true;
            Operation op_silv = (Operation) _op_silv;
            if(op_silv.getName().equals(op_gold.getName())) {
                List<Parameter> param_list_silv = op_silv.getParameter();
                if (param_list_silv.size() == param_list_gold.size()) {
                    // Return type is not used in matching method signatures.
                    for (int i=1; i<param_list_gold.size(); i++) {
                        Parameter param_gold = param_list_gold.get(i);
                        String type_gold = param_gold.getType().getName();

                        Parameter param_silv = param_list_silv.get(i);
                        String type_silv = param_silv.getType().getName();

                        if (!type_gold.equals(type_silv)) {
                            LOG.log(Level.FINE, "Failed because param types" +
                                    " differ: " + type_silv + ", " + type_gold);
                            match = false;
                            break;
                        }
                    }
                } else {
                    LOG.log(Level.FINE, "Failed because param list sizes " +
                            "differ" + param_list_silv.size() + ", " +
                            param_list_gold.size());
                    match = false;
                }
            } else {
                LOG.log(Level.FINE, "Failed because function names differ: "
                        + op_silv.getName() + ", " + op_gold.getName());
                match = false;
            }

            if (match) {
                return_list.add(op_silv);
            }
        }

        return return_list;
    }

    private static List<UmlClass> get_child_classes(UmlClass klass) {
        ArrayList<UmlClass> child_list = new ArrayList<UmlClass>();
        Collection<Generalization> coll = Model.getFacade().getSpecializations(
                (Object) klass);

        for (Generalization gen : coll) {
            UmlClass child = (UmlClass) gen.getChild();
            child_list.add(child);
        }

        return child_list;
    }

    private void setRenameNode() {
        String method_name = null, return_type = null;
        List<Parameter> param_list = target.getParameter();
        Parameter return_param = param_list.get(0);

        method_name = target.getName();
        return_type = return_param.getType() != null ?
            return_param.getType().getName() : "void";

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

        return_types = new JTextField[param_list.size()-1];
        param_names = new JTextField[param_list.size()-1];

        for (int i = 1; i < param_list.size(); i++) {
            Parameter param = param_list.get(i);
            String type = param.getType() != null ? param.getType().getName() :
                "void";
            return_types[i-1] = new JTextField(type, 15);
            param_names[i-1] = new JTextField(param.getName(), 15);

            cp.add(return_types[i-1]);
            cp.add(param_names[i-1]);
        }

        setSize(350, 400);

        JButton submitButton = new JButton("Change method signature");
        cp.add(submitButton);
        submitButton.addActionListener(this);
    }

    public static boolean is_input_valid(Operation operation, String method_name,
            String method_return_type, String [] s_param_names,
            String [] s_return_types) {

        List<Parameter> param_list = operation.getParameter();
        if (param_list.size() - 1 != s_param_names.length)    return false;
        if (param_list.size() - 1 != s_return_types.length)    return false;

        if (method_name.length() == 0) return false;
        if (method_return_type.length() == 0)  return false;

        for (int i = 1; i < param_list.size(); i++) {
            if (s_param_names[i-1].length() == 0) return false;
            if (s_return_types[i-1].length() == 0) return false;
        }

        return true;
    }

    private static void update_method_signature(Operation operation,
            String method_name, String method_return_type,
            String [] s_param_names, String [] s_return_types) {
        Project project = ProjectManager.getManager().getCurrentProject();
        List<Parameter> param_list = operation.getParameter();

        operation.setName(method_name);
        Object data_type = project.findType(method_return_type, false);
        if (data_type == null) {
            data_type = project.findType(method_return_type, true);
        }

        Parameter return_param = param_list.get(0);
        return_param.setType((Classifier) data_type);

        for (int i = 1; i < param_list.size(); i++) {
            Parameter param = param_list.get(i);
            param.setName(s_param_names[i-1]);

            data_type = project.findType(s_return_types[i-1], false);
            if (data_type == null) {
                data_type = project.findType(s_return_types[i-1], true);
            }

            param.setType((Classifier) data_type);
        }
    }

    private static void propagate_change(UmlClass klass, Operation op_gold,
            String method_name, String method_return_type,
            String [] s_param_names, String [] s_return_types) {
        List<UmlClass> child_list = get_child_classes(klass);
        for (UmlClass child : child_list) {
            List<Operation> operation_list = get_matching_operations(child,
                    op_gold);
            for (Operation operation : operation_list) {
                update_method_signature(operation, method_name,
                        method_return_type, s_param_names, s_return_types);
            }

            propagate_change(child, op_gold, method_name, method_return_type,
                    s_param_names, s_return_types);
        }
    }

    public static boolean change_method_signature(Operation operation,
            String method_name, String method_return_type,
            String [] s_param_names, String [] s_return_types) {
        if (is_input_valid(operation, method_name, method_return_type,
                    s_param_names, s_return_types)) {
            // Propagate changes to child classes.
            UmlClass owner = (UmlClass) operation.getOwner();
            propagate_change(owner, operation, method_name, method_return_type,
                    s_param_names, s_return_types);

            // Update this method.
            update_method_signature(operation, method_name, method_return_type,
                    s_param_names, s_return_types);

            // FIXME: Validate UML.
            if (/* CheckConstraints.validateUML() */ true == false) {
                // TODO: Undo change.
                LOG.log(Level.INFO, "Validation failed, but cannot undo.");
            } else {
                LOG.log(Level.INFO, "Validation succeeded!");
            }

            return true;
        }

        return false;
    }

    @Override
        public void actionPerformed(ActionEvent event) {
            // Rearrange the data structures.
            String [] s_param_names = new String[param_names.length];
            String [] s_return_types = new String[return_types.length];

            for (int i=0; i<param_names.length; i++)
                s_param_names[i] = param_names[i].getText();

            for (int i=0; i<return_types.length; i++)
                s_return_types[i] = return_types[i].getText();

            String method_name = this.gui_method_name.getText();
            String method_return_type = this.gui_method_return_type.getText();

            Operation method_target = this.target;

            if (change_method_signature(method_target, method_name,
                        method_return_type, s_param_names, s_return_types)) {
                // Get rid of the dialog box.
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input!",
                        "Input validator", JOptionPane.INFORMATION_MESSAGE);
            }
        }
}

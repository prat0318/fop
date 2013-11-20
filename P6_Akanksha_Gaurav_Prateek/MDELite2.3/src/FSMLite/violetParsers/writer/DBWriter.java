package FSMLite.violetParsers.writer;

import FSMLite.violetParsers.importer.StateViolet;
import FSMLite.violetParsers.importer.StateNode;
import FSMLite.violetParsers.importer.StateEdge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DBWriter {

    public static String DB_FILE_APPENSION = ".pl";
    String inputFile;
    ArrayList<StateNode> stateNodes;
    ArrayList<StateEdge> stateEdges;
    StateViolet violetData;
    int numNodes;
    boolean printedVioletInterfaceExtends;
    boolean printedVioletClassImplements;

    public DBWriter(String inputFile, ArrayList<StateNode> stateNodes,
            ArrayList<StateEdge> stateEdges,
            StateViolet violetData, int numNodes) {
        this.inputFile = inputFile;
        this.stateNodes = stateNodes;
        this.stateEdges = stateEdges;
        this.violetData = violetData;
        this.numNodes = numNodes;
        this.printedVioletInterfaceExtends = false;
        this.printedVioletClassImplements = false;
    }

    public void generateVioletDB() {
        try {
            FileWriter fstream = new FileWriter(inputFile + DB_FILE_APPENSION);
            BufferedWriter out = new BufferedWriter(fstream);

            //Output special pl settings
            out.write(":- style_check(-discontiguous).\n");
            out.write(":- style_check(-singleton).\n");
            //Output header data

            //Ouput the classes
            out.write("\ntable(node_violet,[id,name,nodeType,x,y]).\n");
            if (stateNodes.size() > 0) {
                for (StateNode node : stateNodes) {
                    formatAndWriteNodeFacts(node, out);
                }
            } else {
                out.write(":- dynamic violetClass/7.\n");
            }

            //Ouput the interfaces
//            out.write("\ntable(violetInterface,[id,\"name\",\"methods\",x,y]).\n");
//            if (classInterfaces.size() > 0) {
//                for (ClassInterface node : classInterfaces) {
//                    formatAndWriteInterfaceFacts(node, out);
//                }
//            } else {
//                out.write(":- dynamic violetInterface/5.\n");
//            }

            //Output the associations
            out.write("\ntable(transition,[transid,startsAt,endsAt]).\n");
            if (stateEdges.size() > 0) {
                for (StateEdge edge : stateEdges) {
                    formatAndWriteEdgeFacts(edge, out);
                }
            } else {
                out.write(":- dynamic violetAssociation/9.\n");
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static void formatAndWriteInterfaceFacts(ClassInterface node, BufferedWriter out) throws IOException {
//        String id = node.getId();
//        String name = formatTextVariable(node.getName());
//        // Violet automatically places <<inteface>> as part of the name;
//        // this and any embedded blanks should be stripped.
//        name = name.replace("«interface»", "").replace("'", "").trim();  // Added by *DSB*
//        name = "'" + name + "'";  // Added by *DSB*
//        String methods = formatAttrsAndMeths(node.getMethods().toString());
//
//        out.write("violetInterface(" + id + "," + name + "," + methods + ","
//                + node.getXPos() + "," + node.getYPos() + ").\n");
//    }

    private static void formatAndWriteNodeFacts(StateNode node, BufferedWriter out) throws IOException {
        String id = node.getId();
        String name = node.getName();
        String parentId = "null"; // formatTextVariable(node.getParentId());
        if("".equals(name) || name == null)
            name = id;
        out.write("node_violet(" + id + "," + name + "," +
                 node.getNodeType() + "," + node.getXPos() + "," + node.getYPos() + ").\n");
    }

    private void formatAndWriteEdgeFacts(StateEdge edge, BufferedWriter out) throws IOException {
        String fromId = edge.getStartsAt();
        String toId = edge.getEndsAt();
        String label = edge.getLabel();
        
        //Always need to write an association, regardless of edge type, for Violet purposes
        out.write("transition(" + label + "," + fromId + "," + toId +").\n");
    }

    private static String formatId(String input) {
        return input != null ? input.replaceAll("[^0-9]", "") : "''";
    }

    private static String formatAttrsAndMeths(String input) {
        if (input == null || input.equals("[]")) {
            return "''";
        }
        String output = input.replace("[", "'");
        output = output.replace("]", "'");
        output = output.replace(", ", ";");

        return output;
    }

    private static String formatTextVariable(String input) {
        if (input == null) {
            return "''";
        }
        return "'" + input + "'";
    }
}

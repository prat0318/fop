package MDELite.violetParser.writer;

import MDELite.violetParser.importer.ClassInterface;
import MDELite.violetParser.importer.ClassViolet;
import MDELite.violetParser.importer.ClassNode;
import MDELite.violetParser.importer.ClassEdge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DBWriter {

    public static String DB_FILE_APPENSION = ".pl";
    String inputFile;
    ArrayList<ClassNode> classNodes;
    ArrayList<ClassEdge> classEdges;
    ArrayList<ClassInterface> classInterfaces;
    ClassViolet violetData;
    int numNodes;
    boolean printedVioletInterfaceExtends;
    boolean printedVioletClassImplements;

    public DBWriter(String inputFile, ArrayList<ClassNode> classNodes,
            ArrayList<ClassEdge> classEdges, ArrayList<ClassInterface> classInterfaces,
            ClassViolet violetData, int numNodes) {
        this.inputFile = inputFile;
        this.classNodes = classNodes;
        this.classEdges = classEdges;
        this.classInterfaces = classInterfaces;
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

            //Output header data

            //Ouput the classes
            out.write("\ntable(violetClass,[id,\"name\",\"fields\",\"methods\",superid,x,y]).\n");
            if (classNodes.size() > 0) {
                for (ClassNode node : classNodes) {
                    formatAndWriteNodeFacts(node, out);
                }
            } else {
                out.write(":- dynamic violetClass/7.\n");
            }

            //Ouput the interfaces
            out.write("\ntable(violetInterface,[id,\"name\",\"methods\",x,y]).\n");
            if (classInterfaces.size() > 0) {
                for (ClassInterface node : classInterfaces) {
                    formatAndWriteInterfaceFacts(node, out);
                }
            } else {
                out.write(":- dynamic violetInterface/5.\n");
            }

            //Output the associations
            out.write("\ntable(violetAssociation,[cid1,\"role1\",arrow1,type1,cid2,\"role2\",arrow2,type2,lineStyle]).\n");
            if (classEdges.size() > 0) {
                for (ClassEdge edge : classEdges) {
                    formatAndWriteEdgeFacts(edge, out);
                }
            } else {
                out.write(":- dynamic violetAssociation/9.\n");
            }

            out.write("\ntable(violetInterfaceExtends,[id,idx]).\n");
            if (!printedVioletInterfaceExtends) {
                out.write(":- dynamic violetInterfaceExtends/2.\n");
            }
            out.write("\ntable(violetClassImplements,[cid,iid]).\n");
            if (!printedVioletClassImplements) {
                out.write(":- dynamic violetClassImplements/2.\n");
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void formatAndWriteInterfaceFacts(ClassInterface node, BufferedWriter out) throws IOException {
        String id = node.getId();
        String name = formatTextVariable(node.getName());
        // Violet automatically places <<inteface>> as part of the name;
        // this and any embedded blanks should be stripped.
        name = name.replace("«interface»","").replace("'","").trim();  // Added by *DSB*
        name = "'"+name+"'";  // Added by *DSB*
        String methods = formatAttrsAndMeths(node.getMethods().toString());

        out.write("violetInterface(" + id + "," + name + "," + methods + ","
                + node.getXPos() + "," + node.getYPos() + ").\n");
    }

    private static void formatAndWriteNodeFacts(ClassNode node, BufferedWriter out) throws IOException {
        String id = node.getId();
        String name = formatTextVariable(node.getName());
        String attrs = formatAttrsAndMeths(node.getAttributes().toString());
        String methods = formatAttrsAndMeths(node.getMethods().toString());
        String parentId = "null"; // formatTextVariable(node.getParentId());

        out.write("violetClass(" + id + "," + name + "," + attrs + ","
                + methods + "," + parentId + "," + node.getXPos() + "," + node.getYPos() + ").\n");
    }

    private void formatAndWriteEdgeFacts(ClassEdge edge, BufferedWriter out) throws IOException {
        String fromId = edge.getStartsAtClass();
        String toId = edge.getEndsAtClass();
        String startArrow = formatTextVariable(edge.getStartArrowHead());
        String endArrow = formatTextVariable(edge.getEndArrowHead());
        String startLabel = formatTextVariable(edge.getStartLabel());
        String endLabel = formatTextVariable(edge.getEndLabel());
        String lineStyle = formatTextVariable(edge.getLineStyle());
        String fromType = formatTextVariable(edge.getType1());
        String toType = formatTextVariable(edge.getType2());

        //TODO: Ask Batory about interfaceExtends
        if (edge.getType1().equals("interfacenode") && edge.getType2().equals("interfacenode")) {
            out.write("violetInterfaceExtends(" + fromId + "," + toId + ").\n");
            printedVioletInterfaceExtends = true;
        } else if (edge.getEndsAtClass().contains("interfacenode")) {
            out.write("violetClassImplements(" + fromId + "," + toId + ").\n");
            printedVioletClassImplements = true;
        }

        //Always need to write an association, regardless of edge type, for Violet purposes
        out.write("violetAssociation(" + fromId + "," + startLabel + "," + startArrow + "," + fromType + "," + toId + "," + endLabel + "," + endArrow + "," + toType + "," + lineStyle + ").\n");
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

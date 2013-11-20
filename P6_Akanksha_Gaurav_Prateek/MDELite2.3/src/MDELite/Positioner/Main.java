/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELite.Positioner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author eric
 */
public class Main {
    static LinkedList<Node> nodes;
    
    public static void main (String[] args) {
        double absX = 20;
        double absY = 20;
        nodes = new LinkedList<Node>();
        String inputFileName = null;
        String outputFileName = null;
       
        for (int i = 0; i < args.length; i++) {
            String[] argSplit = args[i].split("=");
            if (argSplit[0].trim().equals("infile"))
                inputFileName = argSplit[1].trim();
            else if (argSplit[0].trim().equals("outfile"))
                outputFileName = argSplit[1].trim();    
        }
      
        Scanner scan = null;
        try {
            scan = new Scanner(new File(inputFileName));
        } catch (FileNotFoundException ex) {
            System.err.append("unable to open file: " + inputFileName);
            return;
        }
        
        File outputFile = new File(outputFileName);
        PrintStream ps = null;
        try {
            ps = new PrintStream(outputFile);
        } catch (FileNotFoundException ex) {
            System.err.println("cannot open file " + outputFile + " for writing");
            return;
        }
        
        boolean foundAssociations = false;

        while (scan.hasNextLine()) {
            String line = scan.nextLine().trim();
            
            if (line.startsWith("// associations")) {
                foundAssociations = true;
                continue;
            }
            else if (!foundAssociations)
                continue;
            
            if (line.startsWith("//") || line.startsWith("}"))
                break;
            
            String[] splitLine = line.split("->");
            String nodeNameL = splitLine[0];
            String nodeNameR = splitLine[1].replace(";","");
            
            Node nodeL;
            Node nodeR;
            
            if ((nodeL = findNode(nodeNameL)) == null) {
                nodeL = new Node(nodeNameL,absX,absY,absX+20,absY);
                absX+=50;
                //absY+=50;
                nodes.add(nodeL);
            }
            
            if ((nodeR = findNode(nodeNameR)) == null) {
                nodeR = new Node(nodeNameR,nodeL.getNextClassX()+10,
                        nodeL.getNextClassY()-10,nodeL.getNextClassX(),
                            nodeL.getNextClassY());
                nodes.add(nodeR);
            }
        }
        
        // Done with associations, now we need to add any standalone nodes
        try {
            scan = new Scanner(new File(inputFileName));
        } catch (FileNotFoundException ex) {
            System.err.append("unable to open file: " + inputFileName);
            return;
        }
        boolean foundClasses = false;
        boolean foundInterfaces = false;
        
        while (scan.hasNextLine()) {
            String line = scan.nextLine().trim();
            if (line.isEmpty())
                continue;
            if (line.startsWith("}"))
                break;            
            if (line.startsWith("// classes")) {
                foundClasses = true;
                foundInterfaces = false;
                continue;
            }
            else if (line.startsWith("// interfaces")) {
                foundClasses = false;
                foundInterfaces = true;
                continue;
            }
            
            if (!foundClasses && !foundInterfaces)
                continue;
          
            if (line.startsWith("//")) {
                foundClasses = false;
                foundInterfaces = false;
                continue;
            }

            // We know we are looking at a line with a class or node
            String nodeName = line.replace(";", "").trim();
            Node node;
            
            if ((node = findNode(nodeName)) == null) {
                node = new Node(nodeName,absX,absY,absX+20,absY);
                absX+=50;
                //absY+=50;
                nodes.add(node);
            }
        }
                 
        ps.println("digraph {");
        ps.println("\t// classes");
        
        for (Node node : nodes) {
            //c1 [pos="50.0,20.0", width="0.1388889", height="0.1388889"];
            ps.println("\t"+node.getName()+" [pos=\""+node.getX()+","+
                node.getY()+"\", width=\"0.1388889\", height=\"0.1388889\"];");
        }
        
        //ps.println("bb=\"0,0,160.0,160.0\";");
        ps.println("}");
        
        ps.close();
    }

    private static Node findNode(String nodeName2Add) {
        for (Node node : nodes) {
            if (node.getName().equals(nodeName2Add))
                return node;
        }
        
        return null;
    }
}

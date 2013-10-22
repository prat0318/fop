package FSMLite.violetParsers.importer;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Importer {

    String inputFile;
    ArrayList<StateNode> stateNodes;
    ArrayList<StateEdge> stateTransitions;
    StateViolet violetData;
    int numNodes;
    int numUnconnectedNodes;
    
    public Importer(String inputFile) {
        this.inputFile = inputFile;
        numNodes = 0;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public ArrayList<StateNode> getStateNodes() {
        return stateNodes;
    }

    public ArrayList<StateEdge> getStateTransitions() {
        return stateTransitions;
    }

    public StateViolet getVioletData() {
        return violetData;
    }

    private static String formatType(String input) {
        return input != null ? input.replaceAll("[0-9]", "") : "null";
    }

//    public void assignParents() {
//        for (StateEdge node : stateTransitions) {
//            if (node.getEndArrowHead() != null && node.getEndArrowHead().equals("TRIANGLE")) {
//                String fromClass = node.getStartsAt();
//                String toClass = node.getEndsAt();
//                int index;
//                int indexToClass;
//                if ((index = findNode(fromClass, "node")) != -1) {
//                    if ((indexToClass = findNode(toClass, "node")) != -1) {
//                        //System.out.println("adding parent: "+toClass);
//                        //System.out.println("adding index: "+index);
//                        stateNodes.get(index).setParentId(toClass);
//                    }
//                }
//            }
//        }
//    }

    private int findNode(String id, String type) {
        if (type.equals("node")) {
            for (int i = 0; i < stateNodes.size(); i++) {
                if (stateNodes.get(i).getId().equals(id)) {
                    return i;
                }
            }
//        } else {
//            for (int i = 0; i < classInterfaces.size(); i++) {
//                if (classInterfaces.get(i).getId().equals(id)) {
//                    return i;
//                }
//            }
        }

        return -1;
    }

    public boolean importNodesAndEdges() {
        stateNodes = new ArrayList<StateNode>();
        stateTransitions = new ArrayList<StateEdge>();
        int curAddingNodeNum = -1;
        int curAddingEdgeNum = -1;
        int curAddingFaceNum = -1;
        boolean addANode = false;

        try {
            //Init parser
            File fXmlFile = new File(inputFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            //Get and store header/meta data
            Element rootElement = (Element) doc.getDocumentElement();
            String javaVersion = rootElement.getAttribute("version");
            String javaClass = rootElement.getAttribute("class");
            Element mainObj = (Element) doc.getElementsByTagName("object").item(0);
            String mainObjCls = mainObj.getAttribute("class");
            violetData = new StateViolet(javaVersion, javaClass, mainObjCls);
            //System.out.println("Violet Data: " + violetData);

            //Loop through all the Voids
            NodeList voidNodes = doc.getElementsByTagName("void");
            for (int i = 0; i < voidNodes.getLength(); i++) {
                Element voidElement = (Element) voidNodes.item(i);
                String voidMethod = voidElement.getAttribute("method");
                //System.out.println("Void Method: " + voidMethod);

                //We only want to deal with addNodes for now
                if (voidMethod.equals("addNode")) {
                    //Loop through the objects within this AddNode
                    //First save the "header" object for this AddNode					  
                    NodeList addNodeObjs = voidElement.getElementsByTagName("object");
                    for (int o = 0; o < addNodeObjs.getLength(); o++) {
                        Element objElement = (Element) addNodeObjs.item(o);
                        String objElementClass = objElement.getAttribute("class");
                        String objElementId = objElement.getAttribute("id").toLowerCase();
                        if (!objElementClass.equals("") && objElementId.equals("")) {
                            objElementId = "unconnected"+numUnconnectedNodes++;
                        }
                       // System.out.println("\tObject (addNode) Class : " + objElementClass + " ID: " + objElementId);

                        //Create new node for this addNode
                        if (objElementClass.contains("CircularStateNode")||objElementClass.contains("StateNode")||objElementClass.contains(".CircularFinalStateNode")) {
                            stateNodes.add(new StateNode(objElementId, objElementClass));
                            curAddingNodeNum += 1;
                            addANode = true;

                            //Get the remaining voids in this addNode and loop em
                            NodeList objVoids = objElement.getElementsByTagName("void");
                            for (int j = 0; j < objVoids.getLength(); j++) {
                                //Init parsing of this particular void within addNode
                                Element addNodeObjVoidE = (Element) objVoids.item(j);
                                String addNodeObjVoidEProperty = addNodeObjVoidE.getAttribute("property");

                                //Get next void (text)
                                NodeList texts = addNodeObjVoidE.getElementsByTagName("void");
                                Element firstText = (Element) texts.item(0);

                                //If the first void denotes attributes, methods, or name, we need to get that data
                                if (addNodeObjVoidEProperty.equals("name")) {

                                    String property = getTagValue("string", firstText, 0);

                                    //Get name for this AddNode
                                    if (addNodeObjVoidEProperty.equals("name")) {
                                        //System.out.println("\t\t\tName: " + property);

                                        stateNodes.get(curAddingNodeNum).setName(property);
                                    }
                                }
                            }
                            numNodes++;
                        } else if (objElementClass.contains("Point2D$Double")) {
                            //Get the remaining voids in this addNode and loop em
                            NodeList objVoids = objElement.getElementsByTagName("void");
                            for (int j = 0; j < objVoids.getLength(); j++) {
                                //Init parsing of this particular void within addNode
                                Element addNodeObjVoidE = (Element) objVoids.item(j);
                                String addNodeObjVoidEMethod = addNodeObjVoidE.getAttribute("method");

                                if (addNodeObjVoidEMethod.equals("setLocation")) {
                                    String xPos = getTagValue("double", addNodeObjVoidE, 0);
                                    String yPos = getTagValue("double", addNodeObjVoidE, 1);

                                    if (addANode) {
                                        stateNodes.get(curAddingNodeNum).setXPos(Double.valueOf(xPos));
                                        stateNodes.get(curAddingNodeNum).setYPos(Double.valueOf(yPos));
                                    } 
                                }
                            }

//							if (addingAClass)
//								System.out.println("\t\t\t\tAdded Class #" +curAddingNodeNum + ": " + stateNodes.get(curAddingNodeNum));
//							else
//								System.out.println("\t\t\t\tAdded Interface #" +curAddingFaceNum + ": " + classInterfaces.get(curAddingFaceNum));
                        }
                    }
                } //Do new connections
                else if (voidMethod.equals("connect")) {
                    curAddingEdgeNum += 1;

                    //First save the "header" object for this connect
                    NodeList addNodeObjs = voidElement.getElementsByTagName("object");
                    Element firstObjElement = (Element) addNodeObjs.item(0);
                    String firstObjElementClass = firstObjElement.getAttribute("class");
                    stateTransitions.add(new StateEdge());

                    //Create new connection for this connect
                    //System.out.println("\tObject (connect): " + firstObjElementClass);

                    //Capture to and from classes on this connection
                    boolean seenFromClass = false;
                    String start = "", stop = "";
                    for (int k = 0; k < addNodeObjs.getLength(); k++) {
                        Element nextObj = (Element) addNodeObjs.item(k);
                        String nextObjIdref = nextObj.getAttribute("idref");
                        if (!nextObjIdref.equals("")) {
                            if (!seenFromClass) {
                                start = nextObjIdref.toLowerCase();
                                seenFromClass = true;
                            } else {
                                //System.out.println(nextObjIdref);
                                stop = nextObjIdref.toLowerCase();
                                stateTransitions.get(curAddingEdgeNum).setStartsAt(start);
                                stateTransitions.get(curAddingEdgeNum).setEndsAt(stop);
                                stateTransitions.get(curAddingEdgeNum).setLabel(start+"_"+stop);
                            }
                        }
                    }

                    //Get the remaining voids in this connect and loop em
//                    NodeList objVoids = firstObjElement.getElementsByTagName("void");
//                    for (int j = 0; j < objVoids.getLength(); j++) {
//                        //Init parsing of this particular void within addNode
//                        Element objVoidE = (Element) objVoids.item(j);
//                        String addNodeObjVoidEProperty = objVoidE.getAttribute("property");
//
//                        if (addNodeObjVoidEProperty.equals("bentStyle") || addNodeObjVoidEProperty.equals("startArrowHead")
//                                || addNodeObjVoidEProperty.equals("endArrowHead") || addNodeObjVoidEProperty.equals("lineStyle")) {
//                            NodeList objVoidObjs = objVoidE.getElementsByTagName("object");
//                            Element objVoidObj = (Element) objVoidObjs.item(0);
//                            String objVoidObjField = objVoidObj.getAttribute("field");
//
//                            if (addNodeObjVoidEProperty.equals("bentStyle")) {
//                                stateTransitions.get(curAddingEdgeNum).setBentStyle(objVoidObjField);
//                            } else if (addNodeObjVoidEProperty.equals("startArrowHead")) {
//                                stateTransitions.get(curAddingEdgeNum).setStartArrowHead(objVoidObjField);
//                            } else if (addNodeObjVoidEProperty.equals("endArrowHead")) {
//                                stateTransitions.get(curAddingEdgeNum).setEndArrowHead(objVoidObjField);
//                            } else if (addNodeObjVoidEProperty.equals("lineStyle")) {
//                                stateTransitions.get(curAddingEdgeNum).setLineStyle(objVoidObjField);
//                            }
//                        } else if (addNodeObjVoidEProperty.equals("startLabel") || addNodeObjVoidEProperty.equals("endLabel")) {
//                            NodeList strings = objVoidE.getElementsByTagName("string").item(0).getChildNodes();
//                            Node firstString = (Node) strings.item(0);
//                            String firstStringStr = standardizeWhiteSpace(firstString.getNodeValue());
//                            if (addNodeObjVoidEProperty.equals("startLabel")) {
//                                stateTransitions.get(curAddingEdgeNum).setStartLabel(firstStringStr);
//                            } else if (addNodeObjVoidEProperty.equals("endLabel")) {
//                                stateTransitions.get(curAddingEdgeNum).setEndLabel(firstStringStr);
//                            }
//                        }
//                    }

                    //System.out.println("\t\t\t\tAdded Edge #" +curAddingEdgeNum + ": " + stateTransitions.get(curAddingEdgeNum));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static String firstCharToLower(String str) {
        String converted = null;
        if (str != null && str.length() > 0) {
            converted = str.substring(0, 1).toLowerCase() + str.substring(1);
        }
        return converted;
    }

    private static String getTagValue(String sTag, Element eElement, int index) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(index).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return standardizeWhiteSpace(nValue.getNodeValue());
    }

    private static String standardizeWhiteSpace(String in) {
        String out = in.replaceAll("\n", " ");
        return out;
    }
}

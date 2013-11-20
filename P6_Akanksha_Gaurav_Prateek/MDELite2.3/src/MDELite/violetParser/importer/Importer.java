package MDELite.violetParser.importer;

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
    ArrayList<ClassNode> classNodes;
    ArrayList<ClassEdge> classEdges;
    ArrayList<ClassInterface> classInterfaces;
    ClassViolet violetData;
    int numNodes;
    int numUnconnectedNodes;
    
    public Importer(String inputFile) {
        this.inputFile = inputFile;
        numNodes = 0;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public ArrayList<ClassNode> getClassNodes() {
        return classNodes;
    }

    public ArrayList<ClassEdge> getClassEdges() {
        return classEdges;
    }

    public ArrayList<ClassInterface> getClassInterfaces() {
        return classInterfaces;
    }

    public ClassViolet getVioletData() {
        return violetData;
    }

    private static String formatType(String input) {
        return input != null ? input.replaceAll("[0-9]", "") : "null";
    }

    public void assignParents() {
        for (ClassEdge node : classEdges) {
            if (node.getEndArrowHead() != null && node.getEndArrowHead().equals("TRIANGLE")) {
                String fromClass = node.getStartsAtClass();
                String toClass = node.getEndsAtClass();
                int index;
                int indexToClass;
                if ((index = findNode(fromClass, "node")) != -1) {
                    if ((indexToClass = findNode(toClass, "node")) != -1) {
                        //System.out.println("adding parent: "+toClass);
                        //System.out.println("adding index: "+index);
                        classNodes.get(index).setParentId(toClass);
                    }
                }
            }
        }
    }

    private int findNode(String id, String type) {
        if (type.equals("node")) {
            for (int i = 0; i < classNodes.size(); i++) {
                if (classNodes.get(i).getId().equals(id)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < classInterfaces.size(); i++) {
                if (classInterfaces.get(i).getId().equals(id)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public boolean importNodesAndEdges() {
        classNodes = new ArrayList<ClassNode>();
        classEdges = new ArrayList<ClassEdge>();
        classInterfaces = new ArrayList<ClassInterface>();
        int curAddingNodeNum = -1;
        int curAddingEdgeNum = -1;
        int curAddingFaceNum = -1;
        boolean addingAClass = false;

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
            violetData = new ClassViolet(javaVersion, javaClass, mainObjCls);
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
                        if (objElementClass.contains("ClassNode")) {
                            classNodes.add(new ClassNode(objElementId, objElementClass));
                            curAddingNodeNum += 1;
                            addingAClass = true;

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
                                if (addNodeObjVoidEProperty.equals("attributes") || addNodeObjVoidEProperty.equals("name")
                                        || addNodeObjVoidEProperty.equals("methods")) {

                                    String property = getTagValue("string", firstText, 0);
                                    //Get attributes for this AddNode
                                    if (addNodeObjVoidEProperty.equals("attributes")) {
                                        //System.out.println("\t\t\tAttributes: " + property);

                                        String[] attributes = property.split(" ");

                                        for (String attribute : attributes) {
                                            classNodes.get(curAddingNodeNum).setAttributes(attribute);
                                        }
                                    }

                                    //Get methods for this AddNode
                                    if (addNodeObjVoidEProperty.equals("methods")) {
                                        //System.out.println("\t\t\tMethods: " + property);

                                        String[] methods = property.split(" ");

                                        for (String method : methods) {
                                            classNodes.get(curAddingNodeNum).setMethods(method);
                                        }
                                    }

                                    //Get name for this AddNode
                                    if (addNodeObjVoidEProperty.equals("name")) {
                                        //System.out.println("\t\t\tName: " + property);

                                        classNodes.get(curAddingNodeNum).setName(property);
                                    }
                                }
                            }
                            numNodes++;
                        } else if (objElementClass.contains("InterfaceNode")) {
                            classInterfaces.add(new ClassInterface(objElementId, objElementClass));
                            curAddingFaceNum += 1;
                            addingAClass = false;

                            //System.out.println("objElementId: " + objElementId + " objElementClass: " + objElementClass);

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
                                if (addNodeObjVoidEProperty.equals("name") || addNodeObjVoidEProperty.equals("methods")) {

                                    String property = getTagValue("string", firstText, 0);
                                    //Get methods for this AddNode
                                    if (addNodeObjVoidEProperty.equals("methods")) {
                                        //System.out.println("\t\t\tMethods: " + property);

                                        String[] methods = property.split(" ");

                                        for (String method : methods) {
                                            classInterfaces.get(curAddingFaceNum).setMethods(method);
                                        }
                                    }

                                    //Get name for this AddNode
                                    if (addNodeObjVoidEProperty.equals("name")) {
                                        //System.out.println("\t\t\tName: " + property);

                                        classInterfaces.get(curAddingFaceNum).setName(property);
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

                                    if (addingAClass) {
                                        classNodes.get(curAddingNodeNum).setXPos(Double.valueOf(xPos));
                                        classNodes.get(curAddingNodeNum).setYPos(Double.valueOf(yPos));
                                    } else {
                                        classInterfaces.get(curAddingFaceNum).setXPos(Double.valueOf(xPos));
                                        classInterfaces.get(curAddingFaceNum).setYPos(Double.valueOf(yPos));
                                    }
                                }
                            }

//							if (addingAClass)
//								System.out.println("\t\t\t\tAdded Class #" +curAddingNodeNum + ": " + classNodes.get(curAddingNodeNum));
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
                    classEdges.add(new ClassEdge(firstObjElementClass));

                    //Create new connection for this connect
                    //System.out.println("\tObject (connect): " + firstObjElementClass);

                    //Capture to and from classes on this connection
                    boolean seenFromClass = false;
                    for (int k = 0; k < addNodeObjs.getLength(); k++) {
                        Element nextObj = (Element) addNodeObjs.item(k);
                        String nextObjIdref = nextObj.getAttribute("idref");
                        if (!nextObjIdref.equals("")) {
                            if (!seenFromClass) {
                                classEdges.get(curAddingEdgeNum).setStartsAtClass(nextObjIdref.toLowerCase());
                                classEdges.get(curAddingEdgeNum).setType1(formatType(nextObjIdref).toLowerCase());
                                seenFromClass = true;
                            } else {
                                //System.out.println(nextObjIdref);
                                classEdges.get(curAddingEdgeNum).setEndsAtClass(nextObjIdref.toLowerCase());
                                classEdges.get(curAddingEdgeNum).setType2(formatType(nextObjIdref).toLowerCase());
                            }
                        }
                    }

                    //Get the remaining voids in this connect and loop em
                    NodeList objVoids = firstObjElement.getElementsByTagName("void");
                    for (int j = 0; j < objVoids.getLength(); j++) {
                        //Init parsing of this particular void within addNode
                        Element objVoidE = (Element) objVoids.item(j);
                        String addNodeObjVoidEProperty = objVoidE.getAttribute("property");

                        if (addNodeObjVoidEProperty.equals("bentStyle") || addNodeObjVoidEProperty.equals("startArrowHead")
                                || addNodeObjVoidEProperty.equals("endArrowHead") || addNodeObjVoidEProperty.equals("lineStyle")) {
                            NodeList objVoidObjs = objVoidE.getElementsByTagName("object");
                            Element objVoidObj = (Element) objVoidObjs.item(0);
                            String objVoidObjField = objVoidObj.getAttribute("field");

                            if (addNodeObjVoidEProperty.equals("bentStyle")) {
                                classEdges.get(curAddingEdgeNum).setBentStyle(objVoidObjField);
                            } else if (addNodeObjVoidEProperty.equals("startArrowHead")) {
                                classEdges.get(curAddingEdgeNum).setStartArrowHead(objVoidObjField);
                            } else if (addNodeObjVoidEProperty.equals("endArrowHead")) {
                                classEdges.get(curAddingEdgeNum).setEndArrowHead(objVoidObjField);
                            } else if (addNodeObjVoidEProperty.equals("lineStyle")) {
                                classEdges.get(curAddingEdgeNum).setLineStyle(objVoidObjField);
                            }
                        } else if (addNodeObjVoidEProperty.equals("startLabel") || addNodeObjVoidEProperty.equals("endLabel")) {
                            NodeList strings = objVoidE.getElementsByTagName("string").item(0).getChildNodes();
                            Node firstString = (Node) strings.item(0);
                            String firstStringStr = standardizeWhiteSpace(firstString.getNodeValue());
                            if (addNodeObjVoidEProperty.equals("startLabel")) {
                                classEdges.get(curAddingEdgeNum).setStartLabel(firstStringStr);
                            } else if (addNodeObjVoidEProperty.equals("endLabel")) {
                                classEdges.get(curAddingEdgeNum).setEndLabel(firstStringStr);
                            }
                        }
                    }

                    //System.out.println("\t\t\t\tAdded Edge #" +curAddingEdgeNum + ": " + classEdges.get(curAddingEdgeNum));
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

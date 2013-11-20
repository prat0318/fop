package FSMLite.violetParsers.importer;

import FSMLite.violetParsers.importer.*;
import java.util.ArrayList;

public class StateNode {

    protected String id;
    protected String name;
    protected String nodeType;
    protected int xPos;
    protected int yPos;

    public StateNode(String id, String className) {
        this.id = id;
        this.nodeType = mapClassToNodeType(className);
    }

    public void setXPos(double xPos) {
        this.xPos = (int) xPos;
    }

    public void setYPos(double yPos) {
        this.yPos = (int) yPos;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        String str = "";
        str += "Id: " + id
                + ", Name: " + name
                + ", NodeType: " + nodeType
                + ", x: " + xPos
                + ", y: " + yPos;
        return str;
    }

    public String getNodeType() {
        return nodeType;
    }

    private String mapClassToNodeType(String className) {
        if("com.horstmann.violet.product.diagram.state.CircularFinalStateNode".equals(className))
            return "stop";
        else if("com.horstmann.violet.CircularStateNode".equals(className))
            return "start";
        else
            return "state";                
    }
}
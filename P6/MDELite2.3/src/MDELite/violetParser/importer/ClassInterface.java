package MDELite.violetParser.importer;

import java.util.ArrayList;

public class ClassInterface {

    protected String id;
    protected String name;
    protected String classType;
    protected ArrayList<String> methods;
    protected int xPos;
    protected int yPos;

    public ClassInterface(String id, String classType) {
        this.id = id;
        this.classType = classType;

        methods = new ArrayList<String>();
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

    public ArrayList<String> getMethods() {
        return methods;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMethods(String method) {
        this.methods.add(method);
    }

    public String toString() {
        String str = "";
        str += "Id: " + id
                + ", Name: " + name
                + ", ClassType: " + classType
                + ", x: " + xPos
                + ", y: " + yPos;

        str += ", Methods:";
        for (String method : methods) {
            str += " " + method;
        }

        return str;
    }

    public String getClassType() {
        return classType;
    }
}
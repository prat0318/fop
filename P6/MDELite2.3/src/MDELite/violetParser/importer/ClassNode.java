package MDELite.violetParser.importer;

import java.util.ArrayList;

public class ClassNode extends ClassInterface {

    private ArrayList<String> attributes;
    protected String parentId;

    public ClassNode(String id, String classType) {
        super(id, classType);

        attributes = new ArrayList<String>();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setAttributes(String attribute) {
        this.attributes.add(attribute);
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public String toString() {
        String str = super.toString();

        str += ", Attributes:";
        for (String attribute : attributes) {
            str += " " + attribute;
        }

        return str;
    }
}

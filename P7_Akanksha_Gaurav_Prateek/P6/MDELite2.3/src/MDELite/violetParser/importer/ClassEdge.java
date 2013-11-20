package MDELite.violetParser.importer;

public class ClassEdge {

    private String classType;
    private String bentStyle;
    private String endArrowHead;
    private String startArrowHead;
    private String startsAtClass;
    private String endsAtClass;
    private String startLabel;
    private String endLabel;
    private String lineStyle;
    private String type1;
    private String type2;

    public ClassEdge(String classType) {
        this.classType = classType;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getType1() {
        return this.type1;
    }

    public String getType2() {
        return this.type2;
    }

    public void setLineStyle(String lineStyle) {
        this.lineStyle = lineStyle;
    }

    public String getLineStyle() {
        return this.lineStyle;
    }

    public void setBentStyle(String bentStyle) {
        this.bentStyle = bentStyle;
    }

    public void setEndLabel(String endLabel) {
        this.endLabel = endLabel;
    }

    public void setStartLabel(String startLabel) {
        this.startLabel = startLabel;
    }

    public void setStartArrowHead(String startArrowHead) {
        this.startArrowHead = startArrowHead;
    }

    public void setEndArrowHead(String endArrowHead) {
        this.endArrowHead = endArrowHead;
    }

    public void setStartsAtClass(String startsAtClass) {
        this.startsAtClass = startsAtClass;
    }

    public void setEndsAtClass(String endsAtClass) {
        this.endsAtClass = endsAtClass;
    }

    public String getStartLabel() {
        return startLabel;
    }

    public String getEndLabel() {
        return endLabel;
    }

    public String getStartArrowHead() {
        return startArrowHead;
    }

    public String getEndArrowHead() {
        return endArrowHead;
    }

    public String getBentStyle() {
        return bentStyle;
    }

    public String getStartsAtClass() {
        return startsAtClass;
    }

    public String getEndsAtClass() {
        return endsAtClass;
    }

    public String toString() {
        String str = "";
        str += "BentStyle: " + bentStyle
                + ", LineStyle: " + lineStyle
                + ", StartArrowHead: " + startArrowHead
                + ", EndArrowHead: " + endArrowHead
                + ", StartLabel: " + startLabel
                + ", EndLabel: " + endLabel
                + ", ClassType: " + classType
                + ", From: " + startsAtClass
                + ", To: " + endsAtClass
                + ", From Type: " + type1
                + ", To Type: " + type2;

        return str;
    }
}

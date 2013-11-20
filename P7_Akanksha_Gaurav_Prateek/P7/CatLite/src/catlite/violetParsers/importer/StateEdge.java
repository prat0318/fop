package catlite.violetParsers.importer;

public class StateEdge {

    private String startsAt;
    private String endsAt;
    private String label;
    
    public StateEdge(){}

    public void setStartLabel(String startLabel) {
        this.label = startLabel;
    }

    public void setStartsAt(String startsAtClass) {
        this.startsAt = startsAtClass;
    }

    public void setEndsAt(String endsAtClass) {
        this.endsAt = endsAtClass;
    }

    public String getLabel() {
        return label;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public String toString() {
        String str = "";
        str +=  ", StartLabel: " + label
                + ", From: " + startsAt
                + ", To: " + endsAt;

        return str;
    }

    void setLabel(String label) {
        this.label = label;
    }
}

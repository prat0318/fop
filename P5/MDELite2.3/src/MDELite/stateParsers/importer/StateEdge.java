package MDELite.stateParsers.importer;

public class StateEdge {

    private String startsAt;
    private String endsAt;
    private String lable;
    
public StateEdge() {
        
    }

    public void setStartLabel(String startLabel) {
        this.lable = startLabel;
    }

    public void setStartsAt(String startsAtClass) {
        this.startsAt = startsAtClass;
    }

    public void setEndsAt(String endsAtClass) {
        this.endsAt = endsAtClass;
    }

    public String getLabel() {
        return lable;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public String toString() {
        String str = "";
        str +=  ", StartLabel: " + lable
                + ", From: " + startsAt
                + ", To: " + endsAt;

        return str;
    }
}

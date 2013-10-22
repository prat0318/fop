package MDELite.violetParser.writer;

public class Position {

    String id;
    String x;
    String y;

    public Position(String id, String x, String y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }
}

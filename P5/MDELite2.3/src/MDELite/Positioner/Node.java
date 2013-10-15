/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELite.Positioner;

/**
 *
 * @author eric
 */
public class Node {
    private String name;
    private double x;
    private double y;
    private double nextClassX;
    private double nextClassY;
    
    Node(String name, double x, double y, double nextClassX, double nextClassY) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.nextClassX = nextClassX;
        this.nextClassY = nextClassY;
    }
    
    public String getName() {
        return name;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getNextClassX() {
        return nextClassX-=5;
    }

    public double getNextClassY() {
        return nextClassY+=10;
    }
}

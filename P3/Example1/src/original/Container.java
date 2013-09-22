/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author dsb
 */
public class Container {

    public static boolean debug = false;  // set to true for debugging
    private String name;
    private int sizeOf;
    private Node head;
    private int counter;

    Container(String name) {
        this.name = name;
        sizeOf = 0;
        head = null;
        counter = 0;
    }

    String getName() {
        return name;
    }

    void delete(Node n) {
        if (n == null) {
            return;
        }
        sizeOf--;
        if (n.left != null) {
            n.left.right = n.right;
            n.left = null;
        }
        if (n.right != null) {
            n.right.left = n.left;
            n.right = null;
        }
    }

    void insert(Node n) {
        sizeOf++;
        n.creation_time = ++counter;
        n.left = null;
        if (head != null) {
            head.left = n;
        }
        n.right = head;
        head = n;
    }

    Node getHead() {
        return head;
    }

    void print() {
        System.out.println(name + "{ // has " + sizeOf + " elements");
        Iterator i = new Iterator(this);
        while (i.hasNext()) {
            Node n = i.getNext();
            System.out.println("   " + n);
        }
        System.out.println("}");
    }
}

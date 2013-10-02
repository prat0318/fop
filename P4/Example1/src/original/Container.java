/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author dsb
 */
abstract class Container0 {

    private String name;
    private Node head;

    Container0(String name) {
        this.name = name;
        head = null;
    }

    String getName() {
        return name;
    }

    void delete(Node n) {
        if (n == null) {
            return;
        }
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
        System.out.println(name +  PrintHook());
        Iterator i = new Iterator((Container) this);
        while (i.hasNext()) {
            Node n = i.getNext();
            System.out.println("   " + n);
        }
        System.out.println("}");
    }

    String PrintHook() {
        return "{ // ";
    }
}

abstract class ContainerDebug extends Container0 {
    public static boolean debug = false;  // set to true for debugging
    
    ContainerDebug(String name) {
        super(name);
    }
}

abstract class ContainerSizeOf extends ContainerDebug {

    private int sizeOf;

    ContainerSizeOf(String name) {
        super(name);
        sizeOf = 0;
    }

    void insert(Node n) {
        super.insert(n);
        sizeOf++;
    }

    void delete(Node n) {
        super.delete(n);
        sizeOf--;
    }

    @Override
    String PrintHook() {
        return super.PrintHook() + "has " + sizeOf + " elements";
    }
}

abstract class ContainerCntr extends ContainerSizeOf {

    private int counter;

    ContainerCntr(String name) {
        super(name);
        counter = 0;
    }

    void insert(Node n) {
        super.insert(n);
        n.creation_time = ++counter;
    }
}

public class Container extends ContainerCntr {

    Container(String name) {
        super(name);
    }
}

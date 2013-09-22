/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author dsb
 */
class IteratorDoublyLinkList {

    public ContainerDoublyLinkList c;
    public NodeDoublyLinkList current;
    public boolean init;

    IteratorDoublyLinkList(ContainerDoublyLinkList c) {
        this.c = c;
        current = c.getHead();
        init = true;
    }

    NodeDoublyLinkList getNext() {
        if (init) {
            init = false;
        } else {
            current = current.right;
        }
        if (Container.debug) {
            System.out.println("get next returns " + current);
        }
        return current;
    }

    boolean hasNext() {
        if (init && current != null) {
            return true;
        }
        return (current != null && current.right != null);
    }

    void delete() {
        NodeDoublyLinkList next = current.right;
        c.delete(current);
        current = next;
        init = true;
    }
}

class IteratorPrint extends IteratorDoublyLinkList {
    public NodePrint current;
    
    IteratorPrint(ContainerPrint c) {
        super(c);
    }
    
    NodePrint getNext() {
        return (NodePrint)super.getNext();
    }
    
}

public class Iterator extends IteratorPrint {
    public Node current;
    
    Iterator(Container c) {
        super(c);
    }
    
    Node getNext() {
        return (Node)super.getNext();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author dsb
 */
public class IteratorDoublyLinkList {

    ContainerDoublyLinkList c;
    NodeDoublyLinkList current;
    boolean init;

    IteratorDoublyLinkList(Container c) {
        this.c = c;
        current = c.getHead();
        init = true;
    }

    Node getNext() {
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
        Node next = current.right;
        c.delete(current);
        current = next;
        init = true;
    }
}

public class Iterator extends IteratorDoublyLinkList {
    Iterator(Container c) {
        super(c);
    }
}

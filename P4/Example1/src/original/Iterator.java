/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

/**
 *
 * @author dsb
 */
abstract class Iterator0 {

    Container c;
    Node current;
    boolean init;

    Iterator0(Container c) {
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

abstract class IteratorDebug extends Iterator0 {

    IteratorDebug(Container c) {
        super(c);
    }

    Node getNext() {
        Node current = super.getNext();
        if (Container.debug) {
            System.out.println("get next returns " + current);
        }
        return current;
    }
}

abstract class IteratorSizeOf extends IteratorDebug {

    IteratorSizeOf(Container c) {
        super(c);
    }
}

abstract class IteratorCntr extends IteratorSizeOf {

    IteratorCntr(Container c) {
        super(c);
    }
}

class Iterator extends IteratorCntr {

    Iterator(Container c) {
        super(c);
    }
}

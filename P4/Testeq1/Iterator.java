package Testeq1;



abstract class Iterator$$root {

    Container c;
    Node current;
    boolean init;

    Iterator$$root( Container c ) {
        this.c = c;
        current = c.getHead();
        init = true;
    }

    Node getNext() {
        if ( init ) {
            init = false;
        }
        else {
            current = current.right;
        }
        return current;
    }

    boolean hasNext() {
        if ( init && current != null ) {
            return true;
        }
        return ( current != null && current.right != null );
    }

    void delete() {
        Node next = current.right;
        c.delete( current );
        current = next;
        init = true;
    }
}



abstract class Iterator$$sizeOf extends  Iterator$$root {
      // inherited constructors



    Iterator$$sizeOf (  Container c ) { super(c); }}



class Iterator extends  Iterator$$sizeOf {
      // inherited constructors



    Iterator (  Container c ) { super(c); }}

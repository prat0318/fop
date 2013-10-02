package conf1;



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

}



abstract class Iterator$$rootDel extends  Iterator$$root {

    void delete() {
        Node next = current.right;
        c.delete( current );
        current = next;
        init = true;
    }
      // inherited constructors



    Iterator$$rootDel (  Container c ) { super(c); }
}



abstract class Iterator$$sizeOfBasic extends  Iterator$$rootDel {
      // inherited constructors



    Iterator$$sizeOfBasic (  Container c ) { super(c); }}



abstract class Iterator$$sizeofDel extends  Iterator$$sizeOfBasic {
      // inherited constructors



    Iterator$$sizeofDel (  Container c ) { super(c); }}



class Iterator extends  Iterator$$sizeofDel {
      // inherited constructors



    Iterator (  Container c ) { super(c); }}

package conf3;



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



abstract class Iterator$$debugBasic extends  Iterator$$root {

    Node getNext() {
        Node current = super.getNext();
        if ( Container.debug ) {
            System.out.println( "get next returns " + current );
        }
        return current;
    }
      // inherited constructors



    Iterator$$debugBasic (  Container c ) { super(c); }
}



abstract class Iterator$$sizeOfBasic extends  Iterator$$debugBasic {
      // inherited constructors



    Iterator$$sizeOfBasic (  Container c ) { super(c); }}



abstract class Iterator$$counterBasic extends  Iterator$$sizeOfBasic {
      // inherited constructors



    Iterator$$counterBasic (  Container c ) { super(c); }}



class Iterator extends  Iterator$$counterBasic {
      // inherited constructors



    Iterator (  Container c ) { super(c); }}

package original;

 //created on: Thu Sep 26 19:16:05 CDT 2013

abstract class Container$$root {

    private String name;
    private Node head;

    Container$$root( String name ) {
        containerConstructor( name );
    }

    void containerConstructor( String containerName ) {
        name = containerName;
        head = null;
    }

    String getName() {
        return name;
    }

    void insert( Node n ) {
        n.left = null;
        if ( head != null ) {
            head.left = n;
        }
        n.right = head;
        head = n;
    }

    Node getHead() {
        return head;
    }

    void print() {
        System.out.println( name +  PrintHook() );
        Iterator i = new Iterator( ( Container ) ((Container) this) );
        while ( i.hasNext() ) {
            Node n = i.getNext();
            System.out.println( "   " + n );
        }
        System.out.println( "}" );
    }

    String PrintHook() {
        return "{ // ";
    }
}

 //created on: Thu Sep 26 19:16:05 CDT 2013

abstract class Container$$rootDel extends  Container$$root {

    void delete( Node n ) {
        if ( n == null ) {
            return;
        }
        if ( n.left != null ) {
            n.left.right = n.right;
            n.left = null;
        }
        if ( n.right != null ) {
            n.right.left = n.left;
            n.right = null;
        }
    }
      // inherited constructors



    Container$$rootDel (  String name ) { super(name); }

}

 //created on: Thu Sep 26 19:20:34 CDT 2013

abstract class Container$$debugBasic extends  Container$$rootDel {

    public static boolean debug = false;
      // inherited constructors



    Container$$debugBasic (  String name ) { super(name); } // set to true for debugging
}

 //created on: Thu Sep 26 19:20:34 CDT 2013

class Container extends  Container$$debugBasic {

    private int counter;

    void containerCosntructor( String name ) {
        super.containerConstructor( name );
        counter = 0;
    }

    void insert( Node n ) {
        super.insert( n );
        n.creation_time = ++counter;
    }
      // inherited constructors



    Container (  String name ) { super(name); }
}

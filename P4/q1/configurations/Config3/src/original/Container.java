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

 //created on: Thu Sep 26 19:20:34 CDT 2013

abstract class Container$$debug extends  Container$$root {

    public static boolean debug = false;
      // inherited constructors



    Container$$debug (  String name ) { super(name); } // set to true for debugging
}

 //created on: Thu Sep 26 19:20:34 CDT 2013

abstract class Container$$sizeOf extends  Container$$debug {

    private int sizeOf;

    void containerConstructor( String name ) {
        super.containerConstructor( name );
        sizeOf = 0;
    }

    void insert( Node n ) {
        super.insert( n );
        sizeOf++;
    }

    void delete( Node n ) {
        super.delete( n );
        sizeOf--;
    }

    @Override
    String PrintHook() {
        return super.PrintHook() + "has " + sizeOf + " elements";
    }
      // inherited constructors



    Container$$sizeOf (  String name ) { super(name); }
}

 //created on: Thu Sep 26 19:20:34 CDT 2013

class Container extends  Container$$sizeOf {
      // inherited constructors



    Container (  String name ) { super(name); }}

package conf3;

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

 //created on: Thu Sep 26 19:20:34 CDT 2013

abstract class Container$$debugBasic extends  Container$$root {

    public static boolean debug = false;
      // inherited constructors



    Container$$debugBasic (  String name ) { super(name); } // set to true for debugging
}

 //created on: Thu Sep 26 19:20:34 CDT 2013

abstract class Container$$sizeOfBasic extends  Container$$debugBasic {

    public int sizeOf;

    void containerConstructor( String name ) {
        super.containerConstructor( name );
        sizeOf = 0;
    }

    void insert( Node n ) {
        super.insert( n );
        sizeOf++;
    }

    @Override
    String PrintHook() {
        return super.PrintHook() + "has " + sizeOf + " elements";
    }
      // inherited constructors



    Container$$sizeOfBasic (  String name ) { super(name); }
}

 //created on: Thu Sep 26 19:20:34 CDT 2013

abstract class Container$$counterBasic extends  Container$$sizeOfBasic {

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



    Container$$counterBasic (  String name ) { super(name); }
}



class Container extends  Container$$counterBasic {
      // inherited constructors



    Container (  String name ) { super(name); }}

package original;



abstract class Node$$root {

    String data1;
    int data2, data3;
    Node left, right;

    Node$$root( String d1, int d2, int d3 ) {
        nodeConstructor( d1,d2,d3 );
    }

    void nodeConstructor( String d1, int d2, int d3 ) {
        ((Node) this).data1 = d1;
        ((Node) this).data2 = d2;
        ((Node) this).data3 = d3;
        ((Node) this).left = null;
        ((Node) this).right = null;
    }

    protected final String tab = "\t ";
    protected final String comma = ",";

    @Override
    public String toString() {
        return ( data1 + tab + data2 + tab + data3 + tab + extra() );
    }

    public String extra() {
        return "";
    }
}



abstract class Node$$sizeOf extends  Node$$root {
      // inherited constructors



    Node$$sizeOf (  String d1, int d2, int d3 ) { super(d1, d2, d3); }}



abstract class Node$$counter extends  Node$$sizeOf {

    int creation_time;

    void nodeConstructor( String d1, int d2, int d3 ) {
        super.nodeConstructor( d1, d2, d3 );
        creation_time = 0;
    }

    public String extra() {
        return super.extra() + creation_time;
    }
      // inherited constructors



    Node$$counter (  String d1, int d2, int d3 ) { super(d1, d2, d3); }
}



class Node extends  Node$$counter {
      // inherited constructors



    Node (  String d1, int d2, int d3 ) { super(d1, d2, d3); }}

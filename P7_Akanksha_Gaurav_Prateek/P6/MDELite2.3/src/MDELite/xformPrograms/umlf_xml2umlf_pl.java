package MDELite.xformPrograms;

import CoreMDELite.PCommon;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author dsb
 */
public class umlf_xml2umlf_pl extends CoreMDELite.PCommon {

    static Document dom;

    static String infile = null;
    static String outfile = null;

    public static void main(String[] args) {
        if (args.length !=1) {
            System.err.println("umlx2p.Main <name-of-xml-file-without-.dot>");
            return;
        }
        infile = args[0]+".umlf.xml";
        outfile=args[0]+".umlf.pl";
        try {
            ps = new PrintStream(outfile); // ps is defined in PCommon
        } catch (FileNotFoundException ex) {
            System.err.println("unable to open file " + outfile + " for writing " + ex.getMessage());
            System.exit(1);
        }
        // print header
        ps.println(":-style_check(-discontiguous).");
        ps.println();
        parseXmlFile(infile);
        ps.close();

    }

    static void parseXmlFile(String filename) {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = db.parse(filename);
            parseDocument();


        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    static void parseDocument() {
        //get the root element
        Element docEle = dom.getDocumentElement();
        parseSimpleClass(docEle);
        parseClass(docEle);
        parseInterface(docEle);
        parseAssociation(docEle);
    }
    

    static String translate(String x) {
        x = x.replace("%28","(").replace("%29",")");
        x = x.replace("%3C", "<").replace("%3E", ">");
        x = x.replace("%20", " ").replace("%0D", ";");
        x = x.replace("%2C", ",");
        return x;
    }

    static void parseSimpleClass(Element docEle) {
        String n = "";

        ps.println("table(umlfSimpleClass,[id,\"name\",x,y]).");
        boolean dynamic=true;
        //get a nodelist of elements
        NodeList nl = docEle.getElementsByTagName("SimpleClass");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                dynamic=false;
                //get the SimpleClass element
                Element sc = (Element) nl.item(i);
                String id = sc.getAttribute("id").toLowerCase();
                //String xs = sc.getAttribute("xs");
                String x = sc.getAttribute("x");
                //String ys = sc.getAttribute("ys");
                String y = sc.getAttribute("y");
                String font = sc.getAttribute("font");
                String fontsize = sc.getAttribute("fontSize");
                NodeList el = sc.getElementsByTagName("Label");
                if (el != null && el.getLength() == 1) {
                    Element e = (Element) el.item(0);
                    //  ps.println(e.getNodeName());
                    n = e.getTextContent();
                }

                ps.print("umlfSimpleClass(");
                ps.print(id + comma + quote(translate(n)));
                ps.print(comma + x + comma + y);
                ps.println(").");
            }
        }
        if (dynamic) ps.println(":- dynamic umlfSimpleClass/4.");
        ps.println();
    }

    static void parseTypeDecl(String type, Element docEle) {
        String n ="";
        String m ="";
        boolean dynamic=true;
        
        NodeList nl = docEle.getElementsByTagName(type);
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                dynamic=false;

                //get the SimpleClass element
                Element sc = (Element) nl.item(i);
                String id = sc.getAttribute("id").toLowerCase();
                //String xs = sc.getAttribute("xs");
                String x = sc.getAttribute("x");
                //String ys = sc.getAttribute("ys");
                String y = sc.getAttribute("y");

                NodeList el = sc.getElementsByTagName("Label");
                if (el != null && el.getLength() == 1) {
                    Element e = (Element) el.item(0);
                    //  ps.println(e.getNodeName());
                    m = e.getTextContent();
                }
                el = sc.getElementsByTagName("Detail");
                if (el != null && el.getLength() == 1) {
                    Element e = (Element) el.item(0);
                    //  ps.println(e.getNodeName());
                    n = e.getTextContent();
                }
                // names for interfaces are string that start with '<< Interface >>;'; remove this
                n = translate(n);
                if (n.startsWith("<< Interface >>;"))
                    n = n.substring(16);
                // now print the tuple
                ps.print("umlf"+type+"(");
                ps.print(id + comma +quote(n)+ comma + quote(translate(m)));
                ps.print(comma + x + comma + y);
                ps.println(").");
            }
        }
        if (dynamic) ps.println(":- dynamic umlf"+type+"/5.");
        ps.println();
    }
    
    static void parseClass(Element docEle) {
        ps.println("table(umlfClass,[id,\"name\",\"members\",x,y]).");
        parseTypeDecl("Class",docEle);
    }
    
    static void parseInterface(Element docEle) {
        ps.println("table(umlfInterface,[id,\"name\",\"members\",x,y]).");
        parseTypeDecl("Interface",docEle);
    }


    

    
//    static void printFactLead(String lead, String name) {
//        ps.print(lead + name + ",")
//    }

    static void parseAssociation(Element docEle) {

        ps.println("table(umlfAssociation,[to,target,dashed,anchor,source,from,type]).");
        boolean dynamic = true;
        //get a nodelist of elements
        NodeList nl = docEle.getElementsByTagName("Association");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                dynamic = false;
                //get the SimpleClass element
                Element a = (Element) nl.item(i);
                String to = a.getAttribute("to").toLowerCase();
                String target = a.getAttribute("target");
                String dashed = a.getAttribute("dashed");
                String anchor = a.getAttribute("anchor");
                String source = a.getAttribute("source");
                String from = a.getAttribute("from").toLowerCase();
                String type = a.getAttribute("type");

                ps.print("umlfAssociation(");
                ps.print(to+comma+target+comma+dashed+comma+anchor+comma+source+comma+from+comma+type);
                ps.println(").");
            }
        }
        if (dynamic) ps.println(":- dynamic umlfAssociation/7");
        ps.println();

    }
}

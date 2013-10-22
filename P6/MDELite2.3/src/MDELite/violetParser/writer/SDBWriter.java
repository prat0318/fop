package MDELite.violetParser.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SDBWriter {

    private String dbFileName;

    public SDBWriter(String dbFileName) {
        this.dbFileName = dbFileName;
    }

    public void generateVioletSDB() {
        try {
            String sdbFileName = dbFileName.substring(0, dbFileName.length() - DBWriter.DB_FILE_APPENSION.length()) + "SDB";
            FileWriter fstream = new FileWriter(sdbFileName);
            BufferedWriter out = new BufferedWriter(fstream);

            ArrayList<Position> positions = new ArrayList<Position>();
            File dbFile = new File(dbFileName);

            //Output special pl settings
            out.write(":- style_check(-discontiguous).\n");

            //Classes
            boolean outputClasses = false;
            out.write("table(class,[id,\"name\",\"fields\",\"methods\",superid]).\n");
            Scanner classScanner = new Scanner(dbFile);
            while (classScanner.hasNextLine()) {
                String line = classScanner.nextLine().trim();
                if (line.startsWith("class(")) {
                    parseAndPrintVDBClass(line, out, positions);
                    outputClasses = true;
                }
            }
            if (!outputClasses) {
                out.write(":- dynamic class/5.\n\n");
            }

            //Interfaces
            boolean outputInterfaces = false;
            out.write("\ntable(interface,[id,\"name\",\"methods\"]).\n");
            Scanner interfaceScanner = new Scanner(dbFile);
            while (interfaceScanner.hasNextLine()) {
                String line = interfaceScanner.nextLine().trim();
                if (line.startsWith("interface(")) {
                    parseAndPrintVDBInterface(line, out, positions);
                    outputInterfaces = true;
                }
            }
            if (!outputInterfaces) {
                out.write(":- dynamic interface/3.\n\n");
            }

            //ClassImplements
            boolean outputClassImplements = false;
            out.write("\ntable(classImplements,[cid,iid]).\n");
            Scanner classImplementsScanner = new Scanner(dbFile);
            while (classImplementsScanner.hasNextLine()) {
                String line = classImplementsScanner.nextLine().trim();
                if (line.startsWith("classImplements(")) {
                    parseAndPrintVDBClassImplements(line, out);
                    outputClassImplements = true;
                }
            }
            if (!outputClassImplements) {
                out.write(":- dynamic classImplements/2.\n\n");
            }

            //InterfaceExtends
            boolean outputInterfaceExtends = false;
            out.write("\ntable(interfaceExtends,[id,idx]).\n");
            Scanner interfaceExtendsScanner = new Scanner(dbFile);
            while (interfaceExtendsScanner.hasNextLine()) {
                String line = interfaceExtendsScanner.nextLine().trim();
                if (line.startsWith("interfaceExtends(")) {
                    parseAndPrintVDBInterfaceExtends(line, out);
                    outputInterfaceExtends = true;
                }
            }
            if (!outputInterfaceExtends) {
                out.write(":- dynamic interfaceExtends/2.\n\n");
            }

            //Associations
            boolean outputAssociations = false;
            out.write("\ntable(association,[cid1,\"role1\",arrow1,cid2,\"role2\",arrow2]).\n");
            Scanner associationScanner = new Scanner(dbFile);
            while (associationScanner.hasNextLine()) {
                String line = associationScanner.nextLine().trim();
                if (line.startsWith("association(")) {
                    parseAndPrintVDBAssociation(line, out);
                    outputAssociations = true;
                }
            }
            if (!outputAssociations) {
                out.write(":- dynamic association/6.\n\n");
            }

            //Positions
            out.write("\ntable(position,[id,x,y]).\n");
            parseAndPrintPositons(out, positions);

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseAndPrintPositons(BufferedWriter out, ArrayList<Position> positions) throws IOException {
        boolean outputPositions = false;
        for (Position p : positions) {
            out.write("position(" + p.getId() + "," + p.getX() + "," + p.getY() + ").\n");
        }
        if (!outputPositions) {
            out.write(":- dynamic position/3.\n\n");
        }
    }

    /*
     * Violet in:
     * table(association,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,bentStyle,lineStyle]).
     * Standard out:
     * table(association,[cid1,"role1",arrow1,cid2,"role2",arrow2]).
     */
    private void parseAndPrintVDBAssociation(String line, BufferedWriter out) throws IOException {
        //		interface(0,'Interface',null,1,555.0,65.0,3).
        String[] tupleData = getTupleData(line);
        String cid1 = tupleData[0];
        String role1 = tupleData[1];
        String arrow1 = tupleData[2];
        String cid2 = tupleData[4];
        String role2 = tupleData[5];
        String arrow2 = tupleData[6];
        String type2 = tupleData[7];
        String lineStyle = tupleData[9];

        arrow1 = getStandardEdgeEndName(arrow1, type2, lineStyle);
        arrow2 = getStandardEdgeEndName(arrow2, type2, lineStyle);

        if (tupleData[3].contains("Class")) {
            cid1 = "class" + cid1;
        } else {
            cid1 = "interface" + cid1;
        }
        if (tupleData[7].contains("Class")) {
            cid2 = "class" + cid2;
        } else {
            cid2 = "interface" + cid2;
        }

        out.write("association(" + cid1 + "," + role1 + "," + arrow1 + "," + cid2 + "," + role2 + "," + arrow2 + ").\n");
    }

    //V 		&& DOTTED 	== Depends On			===	arrow
    //V			&& null		== Is associated with	=== arrow	
    //TRIANGLE 	&& DOTTED	== Implements Interface	=== implem
    //TRIANGLE 	&& null		== Inherits from		=== inherit
    //DIAMOND 	&& null		== Is an aggregate of	=== agg
    //BLACK_DIAMOND && null	== Is Composed Of		=== comp	
    //	rat(arrow,'>').
    //	rat(agg,'<>').
    //	rat(none,'').
    //	rat(comp,'++').
    private String getStandardEdgeEndName(String arrow, String type2, String lineStyle) {
        String out = null;
        if (arrow.equals("'V'")) {
            out = "arrow";
        } else if (arrow.equals("'TRIANGLE'") && type2.equals("'interfaceNode'")) {
            out = "implem";
        } else if (arrow.equals("'TRIANGLE'") && type2.equals("'classNode'")) {
            out = "inherit";
        } else if (arrow.equals("'DIAMOND'")) {
            out = "agg";
        } else if (arrow.equals("'BLACK_DIAMOND'")) {
            out = "comp";
        }
        return out;
    }

    /*
     * Violet in: table(interfaceExtends,[id,idx]). Standard out:
     * table(interfaceExtends,[id,idx]).
     */
    private void parseAndPrintVDBInterfaceExtends(String line, BufferedWriter out) throws IOException {
        String[] tupleData = getTupleData(line);
        String id = "interface" + tupleData[0];
        String idx = "interface" + tupleData[1];

        out.write("interfaceExtends(" + id + "," + idx + ").\n");
    }

    /*
     * Violet in: table(classImplements,[cid,iid]). Standard out:
     * table(classImplements,[cid,iid]).
     */
    private void parseAndPrintVDBClassImplements(String line, BufferedWriter out) throws IOException {
        String[] tupleData = getTupleData(line);
        String cid = "class" + tupleData[0];
        String iid = "interface" + tupleData[1];

        out.write("classImplements(" + cid + "," + iid + ").\n");
    }

    /*
     * Violet in: table(interface,[id,"name","methods",superid,x,y,orderId]).
     * Standard out: table(interface,[id,"name","methods"]).
     */
    private void parseAndPrintVDBInterface(String line, BufferedWriter out, ArrayList<Position> positions) throws IOException {
        //		interface(0,'Interface',null,1,555.0,65.0,3).
        String[] tupleData = getTupleData(line);
        String id = "interface" + tupleData[0];
        String name = tupleData[1];
        String methods = tupleData[2];
        String x = tupleData[4];
        String y = tupleData[5];

        out.write("interface(" + id + "," + name + "," + methods + ").\n");
        positions.add(new Position(id, x, y));
    }

    /*
     * Violet in:
     * table(class,[id,"name","fields","methods",superid,x,y,orderId]). Standard
     * out: table(class,[id,"name","fields","methods",superid]).
     */
    private void parseAndPrintVDBClass(String line, BufferedWriter out, ArrayList<Position> positions) throws IOException {
        String[] tupleData = getTupleData(line);
        String id = "class" + tupleData[0];
        String name = tupleData[1];
        String fields = tupleData[2];
        String methods = tupleData[3];
        String superId = tupleData[4].equals("''") ? tupleData[4] : tupleData[4].replace("Node", "").toLowerCase().replace("'", "");
        String x = tupleData[5];
        String y = tupleData[6];

        out.write("class(" + id + "," + name + "," + fields + "," + methods + "," + superId + ").\n");

        positions.add(new Position(id, x, y));
    }

    private String[] getTupleData(String line) {
        return line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).split(",");
    }
}

package CoreMDELite.vm2t;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Model {

    public Map<String, List<Map<String, String>>> tables = new HashMap<String, List<Map<String, String>>>();
    public Map<String, List<String>> schemas = new HashMap<String, List<String>>();

    public Model(File infile) {
        try {
            // 1st pass for tables. Ignore all lines except those that start with '%schema'
            LineNumberReader br = new LineNumberReader(new InputStreamReader(
                    new FileInputStream(infile)));
            String line = null;
            while ((line = br.readLine()) != null) {

                /* START NEW CODE */
                line.trim();
                if (line.startsWith("tuple(")) // ignore tuple facts
                {
                    continue;
                }
//                if (line.startsWith("table(")) // ignore table facts
//                {
//                    continue;
//                }
//                if (line.startsWith("%table(")) {  // parse table facts
                if (line.startsWith("table(")) { // parse table facts
                    StringTokenizer st = new StringTokenizer(line, " \t(),][.\"");
                    try {
                        st.nextToken(); // ignore "table"
                        List<String> columnNames = new ArrayList<String>();
                        while (st.hasMoreTokens()) {
                            String name = st.nextToken().trim();
                            //System.err.println(name);
                            columnNames.add(name);
                        }
                        schemas.put(columnNames.remove(0), columnNames);
                    } catch (Exception e) {
                        System.err.println("unable to parse table on line " + br.getLineNumber() + 
                                " in " + infile.getName());
                    }
                }
            }
            /* END NEW CODE */
            /*
             * START ORIGINAL CODE line = line.trim(); if
             * (line.matches("%\\s*table\\s*:.*")) { String[] names =
             * line.split(":")[1].split(","); List<String> columnNames = new
             * ArrayList<String>(); for (String name: names)
             * columnNames.add(name.trim());
             * tables.put(columnNames.remove(0).trim(), columnNames); } } END
             * ORIGINAL CODE
             */
            // Fill in tables from schema.
            for (String tableName : schemas.keySet()) {
                tables.put(tableName + "S",
                        new ArrayList<Map<String, String>>());
            }
            // 2nd pass for table entries.  Ignore lines that start with 'table(' or 'tuple('
            br = new LineNumberReader(new InputStreamReader(new FileInputStream(
                    infile)));
            line = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // ignore schema and tuple facts and assorted computations
                if (line.startsWith("table(") || line.startsWith("tuple(") 
                    || line.startsWith("%table(") || line.startsWith(":- ")) {
                    continue;
                }
                if (line.matches("\\s*\\w+\\s*(.*)\\.")) {
                    String tableName = line.split("\\(")[0].trim();
                    /*String[] columnNames = line.substring(
                            line.indexOf("(") + 1, line.lastIndexOf(")")).split(",");*/
                    // the following code was added so that commas within a single quote
                    // won't be recognized.  now split on \n characters, not comma.
                    String newline = "";
                    String strippedLine = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
                    boolean inString = false;
                    for (int i=0; i<strippedLine.length(); i++) {
                        char current = strippedLine.charAt(i);
                        if (current == '\'') {
                            inString=!inString;
                        }
                        if (!inString && current == ',')
                            newline = newline + "@@@";
                        else
                            newline = newline + current;
                    }
                    
                    String[] columnNames = newline.split("@@@");
                    
                    if (!schemas.containsKey(tableName)) {
                        continue;
                    }
                    List<Map<String, String>> entries = tables.get(tableName + "S");
                    Map<String, String> newEntry = new HashMap<String, String>();
                    for (int i = 0; i < schemas.get(tableName).size(); i++) {
                        newEntry.put(schemas.get(tableName).get(i),
                                columnNames[i].trim());
                    }
                    entries.add(newEntry);
                    //System.err.println("adding " + tableName + " tuple");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

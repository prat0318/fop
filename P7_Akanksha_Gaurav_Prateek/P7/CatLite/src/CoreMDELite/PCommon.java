package CoreMDELite;

import java.io.PrintStream;

public class PCommon {

    public static PrintStream ps; // output file
    public static String comma = ",";

    // not currently used
    public static void printTable(String name, String[] fields) {
        ps.println(":- dynamic "+ name + "/"+fields.length+".");
        ps.print("table(" + name + ",[");
        String komma = "";
        for (String i : fields) {
            ps.print(komma + i.replace("'","\""));
            komma = ",";
        }
        ps.println("]).");

        ps.print("tuple(" + name + ",L):-" + name + "(");
        komma = "";
        String result = "";
        for (String i : fields) {
            i = i.replace("\"", "").replace("'","");
            String lead = i.substring(0, 1).toUpperCase();
            result += (komma + lead + i.substring(1));
            komma = ",";
        }
        ps.println(result + "),L=[" + result + "].");
        ps.println("%");
    }

    public static String quote(String x) {
        return "'" + x + "'";
    }
}

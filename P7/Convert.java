import CoreMDELite.HomePath;
import CoreMDELite.GProlog;

public class Convert {
    //T2M
    public static void toPL(String filename){
        System.out.println("Parsing the violet file and creating prolog file...");
        Main.main(filename);
    }



    //M2M
    public static void toSDB(String filename){
        Violet result = new Violet(filename);
        result.toSDB();
    }



    //M2T
    static public Violet toJAVA(String filename) {
        Violet result = new Violet(filename);
        result.toJAVA();
        return result;
    }
}



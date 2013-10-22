/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELite;

/**
 *
 * @author dsb
 */
public class Conform extends Common {

    public static void marquee() {
        System.out.println("Usage: Conform <type> <filename(not including dot extension)>");
        System.out.println("       <type> := umlf | yuml | violet | state");
        System.exit(1);
    }

    public static void main(String args[]) {
        if (args.length != 2) {
            marquee();
        }
        int type = getType(args[0]);
        switch (type) {
            case 1: // umlf
                UMLF x = new UMLF(args[1]);
                UMLFpl u = x.toUMLFpl();
                return;
            case 2: // yuml
                Yuml y = new Yuml(args[1]);
                Yumlpl p = y.toYumlpl();
                return;
            case 3: // violet
                Violet v = new Violet(args[1]);
                Violetpl l = v.toVioletpl();
                return;
            case 4:
                return;
            case 5:
                State s = new State(args[1]);
                Statepl sl = s.toStatepl();
                return;
        }
        System.err.println("unrecognizable type " + type);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package original;

import org.junit.Test;

/**
 *
 * @author dsb
 */
public class MainTest {

    @Test
    public void testMainNoArgs() {
        RegTest.Utility.redirectStdOut("out.txt");
        String[] args = null;
        Main.main(args);
        RegTest.Utility.validate("out.txt", "correct/NoArgs.txt", false);
    }

    @Test
    public void testMainWArgs() {
        RegTest.Utility.redirectStdOut("out.txt");
        String[] args = new String[2];
        Main.main(args);
        RegTest.Utility.validate("out.txt", "correct/WArgs.txt", false);
    }
}

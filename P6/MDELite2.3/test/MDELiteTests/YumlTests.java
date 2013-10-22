/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MDELiteTests;

import MDELite.Yumlpl;
import CoreMDELite.SDB;
import MDELite.Yuml;
import org.junit.Test;


/**
 *
 * @author dsb
 */
public class YumlTests {

    @Test
    public void testMain0() {
        basic("TestData/Yuml/emptyClass", true);
    }

    @Test
    public void testMain1() {
        basic("TestData/Yuml/1standard", true);
    }

    @Test
    public void testMain2() {
        basic("TestData/Yuml/2standard", true);
    }

    @Test
    public void testMain3() {
        basic("TestData/Yuml/3standard", true);
    }

    @Test
    public void testMain4() {
        basic("TestData/Yuml/Notepad", true);
    }

    @Test
    public void testMain5() {
        basic("TestData/Yuml/graff", true);
    }

    @Test
    public void testMain6() {
        basic("TestData/Yuml/quark", true);
    }

    @Test
    public void testMain7() {
        basic("TestData/Yuml/yumlparser", true);
    }

    @Test
    public void testMain00() {
        complex("TestData/Yuml/emptyClass", true);
    }

    @Test
    public void testMain10() {
        complex("TestData/Yuml/1standard", false);
    }

    @Test
    public void testMain20() {
        complex("TestData/Yuml/2standard", true);
    }

    @Test
    public void testMain30() {
        complex("TestData/Yuml/3standard", true);
    }

    @Test
    public void testMain40() {
        basic("TestData/Yuml/Notepad", true);
    }

    @Test
    public void testMain50() {
        basic("TestData/Yuml/graff", true);
    }

    @Test
    public void testMain60() {
        basic("TestData/Yuml/quark", true);
    }

    @Test
    public void testMain870() {
        complex("TestData/Yuml/yumlparser", true);
    }

    public void basic(String name, boolean sorted) {
        Yuml y0 = new Yuml(name);
        Yumlpl yp0 = y0.toYumlpl();
        RegTest.Utility.validate(name + ".yuml.pl", "Correct/" + name + ".yuml.pl", sorted);
        Yuml y1 = yp0.toYuml("S");
        RegTest.Utility.validate(name + "S.yuml", "Correct/" + name + "S.yuml", sorted);
    }

    public void complex(String name, boolean sorted) {
        Yuml y0 = new Yuml(name);
        Yumlpl yp0 = y0.toYumlpl();
        SDB s0 = yp0.toSDB("S");
        Yumlpl yp1 = s0.toYumlpl();
        Yuml y1 = yp1.toYuml();
        RegTest.Utility.validate(name + "S.yuml", "Correct/" + name + "S.yuml", sorted);
    }
}

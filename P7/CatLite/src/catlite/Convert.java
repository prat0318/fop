/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catlite;

/**
 *
 * @author prat0318
 */
public class Convert {
    public Violet toViolet(String filename) {
        Violet result = new Violet(filename);
//        String[] args = {filename+fileType()};
        System.out.println("Parsing the violet file and creating prolog file...");
        FSMLite.violetParsers.main.Main.main(args);
        System.out.println("Checking whether the prolog file conforms with the rules..");
        result.conform();
        return result;
    }    
}

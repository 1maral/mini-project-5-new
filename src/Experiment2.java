import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;

import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;

public class Experiment2 {
    public static void main(String[] args) throws KeyNotFoundException, NullKeyException, IOException{
        
        AACMappings mappings = new AACMappings("AACMappings.txt");
        mappings.addItem("p", "pizza toppings");
        System.out.println(mappings.getCategory());
        String[] imglocs = mappings.getImageLocs();
        for (int i = 0; i < imglocs.length; i++){
            System.out.println("Image Locations of Categories: " + imglocs[i]);
        }
        System.out.println("p for " + mappings.select("p"));
        imglocs = mappings.getImageLocs();
        for (int i = 0; i < imglocs.length; i++){
            System.out.println("Image Locations of Categories: " + imglocs[i]);
        }
        mappings.addItem("o", "onion");
        mappings.addItem("p", "pepperoni");
        mappings.addItem("f", "fuzzy fish");
        System.out.println(mappings.select("o"));
        System.out.println(mappings.select("p"));
        System.out.println(mappings.select("f"));

        mappings.writeToFile("Exw.txt");

    }

    // +---------+-----------------------------------------------------
    // | Helpers |
    // +---------+

    /**
     * Determine if an array of strings contains a string.
     */
    static boolean containsString(String[] strings, String str) {
        for (String s : strings) {
        if (s.equals(str)) {
            return true;
        } // if
        } // for
        return false;
    } // containsString(String[], String)
}

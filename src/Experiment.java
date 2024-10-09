import java.io.PrintWriter;

import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;

public class Experiment {
    public static void main(String[] args) throws KeyNotFoundException, NullKeyException{
        PrintWriter pen = new PrintWriter(System.out, true);
        AACCategory category = new AACCategory("Experiments");
        pen.println(category.getCategory());
        pen.println("null category is " + category);

        // Nothing should be in the empty category.
        try {
        category.select("imageA");
        } catch (Exception e) {
            pen.println("Threw Exception successfully! in empty");
        // Nothing to do; it threw an exception as expected.
        } // try/catch
        try {
        category.select("");
        } catch (Exception e) {
            pen.println("Threw Exception successfully! in null");
        // Nothing to do; it threw an exception as expected.
        } // try/catch

        category.addItem("imageA", "Apple");
        category.addItem("imageB", "Banana");
        category.addItem("imageC", "Cherry");
        pen.println(category.select("imageA"));
        pen.println(category.select("imageB"));
        pen.println(category.select("imageC"));

        try {
        category.select("imagea");
        } catch (Exception e) {
            pen.println("Threw Exception successfully! in wrong key");
        // Nothing to do; it threw an exception as expected.
        } // try/catch
    } // main
} // class Experiment

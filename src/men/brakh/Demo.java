package men.brakh;


import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Demo if class RailFence
 *
 * @author  Pankratiew Alexandr
 * @version  1.0
 */
public class Demo {

    public static String readFile(String filename) {
        try {
            return onlyEng(new String(Files.readAllBytes(Paths.get(filename))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void writeFile(String filename, String message) {
        try (PrintWriter out = new PrintWriter(filename)) {
            out.print(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static String onlyEng(String s) {
        return s.replaceAll("[^A-Za-zА]", "").toUpperCase();
    }

    public static void main(String args[]) {
        Cipher railFence = new RailFence();

        String message = onlyEng("CrYpTыфвфывофыллдылвжлoGr23123123яApHy AnD DaTa SecuRiTy афыврфывдоыфлвод л3щ134124 офывлжфдыов лфыо 1");


        System.out.println( railFence.encode(message, 2) );
    }
}

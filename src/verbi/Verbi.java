/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package verbi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Perasso
 */
public class Verbi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainFrame main = new MainFrame();
        main.setLocation(100, 100);
        main.setVisible(true);
    }

    public static void adjust() {
        try {
            FileOutputStream fos = new FileOutputStream("c:\\metrobikers\\metrobikers\\verbi1.txt");
            PrintWriter out = new PrintWriter(fos);
            FileInputStream fis = new FileInputStream("c:\\metrobikers\\metrobikers\\verbi.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            Pattern p1 = Pattern.compile("\\((\\w*)\\)");
            Pattern p2 = Pattern.compile("(\\w+)(,\\s)(.+)");
            int i = 0;
            String line = null;
            while ((line = reader.readLine()) != null) {
                if ((i++ % 96) != 0) {
                    line = replace(p2, line);

                    Matcher matcher = p1.matcher(line);
                    if (matcher.find()) {
                        line = matcher.replaceAll("") + ", " + matcher.replaceAll("$1");
                    }
                }

                out.println(line);

            }
            fis.close();
            fos.close();
            out.close();
        } catch (Exception ex) {
            
        }

    }

    public static String replace(Pattern p2, String line) {
        Matcher matcher = p2.matcher(line);
        if (matcher.find()) {
            String replaceAll = matcher.replaceAll("$1");
            String replaceAll1 = matcher.replaceAll("$3");

            return replaceAll + ", " + replace(p2, replaceAll1);

        }
        return line;


    }
}

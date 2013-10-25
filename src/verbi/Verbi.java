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
        // adjust();
    }

    public static void adjust() {
        try {
            FileOutputStream fos = new FileOutputStream("c:\\metrobikers\\metrobikers\\verbi1.txt");
            PrintWriter out = new PrintWriter(fos);

            FileInputStream fis = new FileInputStream("c:\\metrobikers\\metrobikers\\verbi.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            long i = 0;
            String line = null;
            while ((line = reader.readLine()) != null) {
                if ((i++ % 96) != 0) {
                    line = LineAdjuster.adjustLine(line);
                }
                out.println(line);
                out.flush();

            }
            fis.close();
            fos.close();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

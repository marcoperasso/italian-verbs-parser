/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package verbi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author perasso
 */
public class LineAdjuster {

    static Pattern p1 = Pattern.compile("([^\\s]+)(,\\s)(.+)");
    static Pattern p2 = Pattern.compile("\\(([^\\s]*)\\)");

    public static String replace(Pattern p2, String line) {
        Matcher matcher = p2.matcher(line);
        if (matcher.find()) {
            String replaceAll = matcher.replaceAll("$1");
            String replaceAll1 = matcher.replaceAll("$3");

            return replaceAll + ", " + replace(p2, replaceAll1);

        }
        return line;


    }

    public static String adjustLine(String line) {
        line = replace(p1, line);
        Matcher matcher = p2.matcher(line);
        if (matcher.find()) {
            line = matcher.replaceAll("") + ", " + matcher.replaceAll("$1");
        }
        return line;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package verbi;

import java.util.ArrayList;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

/**
 *
 * @author Marco
 */
class Tempo {

    private final String name;
    private final ArrayList<String> values = new ArrayList<String>();

    Tempo(Element titolo, Element valore) {
        this.name = titolo.text();
        for (TextNode tn : valore.textNodes()) {
            String text = tn.text().replace((char) 160, ' ').replace(';', ' ').trim();
            if (text.isEmpty()) {
                continue;
            }
            if ((int) text.charAt(0) == 151) {
                text = "-";
            }
            values.add(text);


        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            if (sb.length() > 0) {
                sb.append("\r\n");
            }
            sb.append(s);
        }


        return sb.toString();
    }

    public int getCount() {
        int count = 0;
        for (String s : values) {
            count++;
        }
        return count;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value
     */
    void rimuoviPrimaPersona() {
        values.remove(0);
    }
}

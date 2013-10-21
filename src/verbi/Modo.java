/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package verbi;

import java.util.ArrayList;
import org.jsoup.nodes.Element;

/**
 *
 * @author Marco
 */
class Modo {

    private final String name;
    private final Element element;
    private ArrayList<Tempo> tempi = new ArrayList<Tempo>();

    Modo(String text, Element element) {
        this.name = text.trim();
        this.element = element;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append(name);
        for (Tempo t : tempi) {
            sb.append("\r\n");
            sb.append(t.toString());
        }
        return sb.toString();
    }

    public int getCount() {
        int count = 0;
        for (Tempo t : tempi) {
            count += t.getCount();
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
     * @return the element
     */
    public Element getElement() {
        return element;
    }

    void aggiungiTempo(Element titolo, Element valore) {
        tempi.add(new Tempo(titolo, valore));
    }

    void rimuoviPrimaPersona() {
        assert ("IMPERATIVO".equals(name));
        tempi.get(0).rimuoviPrimaPersona();
    }
}

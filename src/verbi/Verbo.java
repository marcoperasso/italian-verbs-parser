/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package verbi;

import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 *
 * @author Marco
 */
class Verbo {

    private String name;
    private String descri;
    private String warning;
    ArrayList<Modo> modi = new ArrayList<>();

    public Verbo() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
       // sb.append("\r\n");
        //sb.append(descri);
        //sb.append("\r\n");
        //sb.append(warning);

        for (Modo modo : modi) {
            //sb.append("\r\n");
            sb.append(modo.toString());
        }
        return sb.toString();
    }

    void parse(Document doc) {
        Elements select = doc.select("span.verbo");
        if (select.size() != 1) {
            return;
        }
        Element title = select.get(0);
        name = title.text();

        Element titleTable = findParent(title, "table");
        if (titleTable == null) {
            return;
        }
        select = titleTable.select("span.descr");
        if (select.size() != 1) {
            return;
        }
        Element descriNode = select.get(0);
        StringBuilder descriText = new StringBuilder();
        for (Node n : descriNode.childNodes()) {
            if (TextNode.class.isAssignableFrom(n.getClass())) {
                descriText.append(((TextNode) n).text());
            }
        }

        descri = descriText.toString();

        warning = "";
        select = descriNode.select("span.warn");
        if (select.size() == 1) {

            Element warnNode = select.get(0);
            warning = warnNode.text();
        }

        Element globalTableContainer = findParent(titleTable, "table");
        if (globalTableContainer == null) {
            return;
        }

        select = globalTableContainer.select("td[bgcolor=#800000] span");
        for (Element modo : select) {
            modi.add(new Modo(modo.text(), findParent(modo, "tr")));
        }

        for (Modo modo : modi) {
            Element trModo = modo.getElement();
            ArrayList<Element> trTempoTitolo = new ArrayList<Element>();
            ArrayList<Element> trTempoValore = new ArrayList<Element>();

            Element row = trModo;
            while (null != (row = row.nextElementSibling())) {
                //mi sono scontrato con il modo sucessivo!                    
                if (modiContains(modi, row)) {
                    break;
                }
                trTempoTitolo.add(row);
                trTempoValore.add(row = row.nextElementSibling());
            }

            assert (trTempoTitolo.size() == trTempoValore.size());

            for (int i = 0; i < trTempoTitolo.size(); i++) {
                Element titolo = trTempoTitolo.get(i);
                Element valore = trTempoValore.get(i);
                Elements titoli = titolo.select("span.tempo");
                Elements valori = valore.select("td");

                assert (titoli.size() == valori.size());

                for (int j = 0; j < titoli.size(); j++) {
                    Element titolo1 = titoli.get(j);
                    Element valore1 = valori.get(j);
                    modo.aggiungiTempo(titolo1, valore1);
                }
            }
        }

        //inverto congiuntivo e condizionale
        Modo m1 = modi.get(1);
        Modo m2 = modi.get(2);
        modi.set(1, m2);
        modi.set(2, m1);
        Modo m3 = modi.get(3);
        m3.rimuoviPrimaPersona();//tolgo la prima persona dall'imperativo
    }

    private Element findParent(Element el, String nodeName) {
        Element parent = el;
        while (null != (parent = parent.parent())) {
            if (parent.nodeName().equals(nodeName)) {
                return parent;
            }
        }
        return null;
    }

    private boolean modiContains(ArrayList<Modo> modi, Element row) {
        for (Modo m : modi) {
            if (m.getElement() == row) {
                return true;
            }
        }
        return false;
    }

    String getName() {
        return name;
    }
}

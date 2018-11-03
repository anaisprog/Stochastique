package controleur;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import modele.Arc;
import modele.Graph;
import modele.Sommet;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;


public class ParserXMLFile {
	private static int nombreVilles;
	private Graph g;

	/* Fonction pour réaliser le parsing, renvoie un graph qui contient la liste des sommets. */
	public Graph parse(String filename) {
		try {
			g = new Graph();
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true); // never forget this!
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("vertex");
			// Nodelist pour vertex
			NodeList nListEdge = doc.getElementsByTagName("edge");

			int villeActuelle = 0;
			nombreVilles = nList.getLength();
			System.out.println("Le nombre de ville de ce probleme est : " + nombreVilles);

			for (int i = 0; i < nombreVilles; i++) {
				Sommet s = new Sommet();
				g.getSommets().add(s);
			}

			int nbedgepervertex = nListEdge.getLength() / nList.getLength();
			int k = 0;
			for (Sommet s : g.getSommets()) {
				
				for (int temp = 0; temp < nbedgepervertex; temp++) {
					villeActuelle += 1;
					// System.out.println("villeActuelle" + villeActuelle);
					Node nNode = nListEdge.item(temp + k);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						int numSommetA = Integer.parseInt(eElement.getTextContent());
						float cout = Float.parseFloat(eElement.getAttribute("cost"));

						Sommet sa = g.getSommetById(numSommetA + 1);
						Arc a = new Arc(s, sa, cout, 0, 0);
						/*System.out.println("Arc entre " + a.getSomD().getid() + " et " + a.getSomA().getid()
								+ " | cout = " + a.getCout());*/
						g.addArc(a);
					}
				}
				k += nbedgepervertex;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return g;
	}
}
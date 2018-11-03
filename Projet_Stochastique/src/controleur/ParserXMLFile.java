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

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class ParserXMLFile {
	private static int nombreVilles;
	private float coutEntreVilles[][][]; // Ville A - Ville B - Cout entre A et
											// B
	private Graph g;

	// TODO : Renvoi un graph qui contient la liste des sommets.
	/* Fonction pour r�aliser le parsing */
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

						// System.out.println("\nCurrent Element :" +
						// eElement.getNodeName());
						// TODO : fait System.out.println("Attributes" +
						// eElement.getTextContent());

						/* Affichage des couts de chaque arrete */
						/*
						 * Affichage de la ville actuelle VilleActuelle =
						 * villeActuelle/(nombreVilles-1))
						 */
						/*
						 * System.out.println("La ville " +
						 * villeActuelle/(nombreVilles-1)); System.out.println(
						 * "L'arrete n°"+ eElement.getTextContent()+
						 * " a un cout de : " + eElement.getAttribute("cost"));
						 */
						coutEntreVilles = new float[villeActuelle / (nombreVilles - 1)][numSommetA][(int) cout];

					}

					// }
				}
				k += nbedgepervertex;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return g;
	}

	/*
	 * Realisation des tests public static void main(String[] args) {
	 * ParserXMLFile px = new ParserXMLFile();
	 */
	/*
	 * Exemple de lecture de fichier XML chemin =
	 * "/home/an/eclipse-workspace/att48.xml"
	 * 
	 * px.parse("/home/an/eclipse-workspace/att48.xml"); }
	 */
}

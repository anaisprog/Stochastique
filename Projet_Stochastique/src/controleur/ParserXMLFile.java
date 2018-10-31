package controleur;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
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
	private float coutEntreVilles[][][]; //Ville A - Ville B - Cout entre A et B

	
	
	/*Fonction pour réaliser le parsing*/
	void parse(String filename) {
		try {
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true); // never forget this!
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			String numeroArrete;	
			NodeList nList = doc.getElementsByTagName("vertex");
			// Nodelist pour vertex
			NodeList nListEdge = doc.getElementsByTagName("edge");


			System.out.println("----------------------------" + nList.getLength());

			//TODO : Pour tous les vertex : 
			int villeActuelle= -1;
			System.out.println("Nombre de villes" + nList.getLength());
			nombreVilles = nList.getLength();
			System.out.println("Le nombre de ville de ce probleme est :" + nombreVilles);
			
			
				for (int temp = 0; temp < nListEdge.getLength(); temp++) {	
					villeActuelle+=1;
					//System.out.println("villeActuelle" + villeActuelle);
					Node nNode = nListEdge.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;
						//System.out.println("\nCurrent Element :" + eElement.getNodeName());
						//TODO : fait System.out.println("Attributes" + eElement.getTextContent());
						
						/*Affichage des couts de chaque arrete*/
						/*Affichage de la ville actuelle 
						 * VilleActuelle = villeActuelle/(nombreVilles-1))
						 */
						System.out.println("La ville " + villeActuelle/(nombreVilles-1));
						System.out.println("L'arrete n°"+ eElement.getTextContent()+ " a un cout de : " + eElement.getAttribute("cost"));
						int numArrete =  Integer.parseInt(eElement.getTextContent());
						float coutEntreArrete = Float.parseFloat(eElement.getAttribute("cost"));
						coutEntreVilles = new float[villeActuelle/(nombreVilles-1)][numArrete][(int) coutEntreArrete];
					
				}
				
				
				
				//}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/*Realisation des tests*/
	public static void main(String[] args) {
        ParserXMLFile px = new ParserXMLFile();
        /*Exemple de lecture de fichier XML  chemin = "/home/an/eclipse-workspace/att48.xml"
         */
        px.parse("/home/an/eclipse-workspace/att48.xml");
    }
}

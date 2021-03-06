package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import java.util.ArrayList;

import modele.Arc;
import modele.Graph;
import modele.Sommet;

public class VilleInterface extends JPanel implements MouseMotionListener, ComponentListener
{

		private static final long serialVersionUID = 1L;
		private Rectangle.Double tailleFenetre = new Rectangle.Double();
		private ArrayList<Sommet> positionVilles;
		private Graph sonGraphe;
		
		
		public VilleInterface(Graph graphe)
		{
			sonGraphe = graphe;
			positionVilles = new ArrayList<Sommet>(); 

			
			this.setBordsFenetre();
			addComponentListener(this);
			addMouseMotionListener(this);
		}
		
		
		// Fonction permettant de calculer les positions a partir des couts des arcs
		
		public void positionParCouts(Graph g) {
			ArrayList<Arc> lArcs= g.getArcs();
			
			for(Sommet s:g.getSommets()) {
				
			}
			
			
		}
		
		
	
		
		
		public void positionsAccordingScreen() 
		{
			// La liste est initialisee 
			positionVilles.clear();

			//On recupere la taille maximale du panel
			float tailleMax = (this.getWidth() > this.getHeight()) ? this.getHeight() : this.getWidth();
			//On calcule ce coefficient qui nous permettra d'ajuster l'affichage des villes en fonction de la fenetre
			double coef = tailleMax / (tailleFenetre.height > tailleFenetre.width ? tailleFenetre.height : tailleFenetre.width);
			//On adapte les positions de chaque ville afin de les rendre modulable a la fenetre
			//Pour ca on recupere les villes
			//On parcourt donc la liste d'arcs et recupere la ville de depart et d'arrivee
			
			
			
			int index = 0;
			for (Arc a : sonGraphe.getArcs()) 
			{
				//si c'est le premier arc : on ajoute les deux villes
				if(index == 0)
				{
					positionVilles.add(new Sommet(((float)((a.getSomD().getx() - tailleFenetre.x) * coef)), ((float) ((a.getSomD().gety() - tailleFenetre.y) * coef))));
					positionVilles.add(new Sommet(((float)((a.getSomA().getx() - tailleFenetre.x) * coef)), ((float) ((a.getSomA().gety() - tailleFenetre.y) * coef))));
				}
				//Sinon on prend que la ville d'arrivee, celle de depart ayant deja ete enregistree
				else
				{
					positionVilles.add(new Sommet(((float)((a.getSomA().getx() - tailleFenetre.x) * coef)), ((float) ((a.getSomA().gety() - tailleFenetre.y) * coef))));
				}
				
				index++;
			}
			index = 0;
		}
		
		
		public void setBordsFenetre() 
		{
			Sommet villeMin = new Sommet(sonGraphe.getSommets().get(0).getx(), sonGraphe.getSommets().get(0).gety());
			Sommet villeMax = new Sommet(sonGraphe.getSommets().get(0).getx(), sonGraphe.getSommets().get(0).gety());
			
			
			for (Sommet s : sonGraphe.getSommets()) 
			{
				if (s.getx() < villeMin.getx()) 
				{
					villeMin.setx(s.getx());
				} else if (s.getx() > villeMax.getx()) 
				{
					villeMax.setx(s.getx());
				}
				if (s.gety() < villeMin.gety()) 
				{
					villeMin.sety(s.gety());
				} else if (s.gety() > villeMax.gety()) 
				{
					villeMax.sety(s.gety());
				}
			}

			tailleFenetre = new Rectangle.Double(villeMin.getx() - (villeMax.getx() - villeMin.getx()) * 0.02,
											villeMin.gety() - (villeMax.gety() - villeMin.gety()) * 0.02,
											villeMax.getx() - villeMin.getx() + (villeMax.getx() - villeMin.getx()) * 0.2,
											villeMax.gety() - villeMin.gety() + (villeMax.gety() - villeMin.gety()) * 0.2);		
		}
		
		@Override
		public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			Graphics2D g = (Graphics2D) graphics;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			int coef = (this.getWidth() > this.getHeight()) ? this.getHeight() : this.getWidth();

			// Affichage du fond 
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, coef, coef);
			//Couleur des villes
			g.setColor(Color.RED);
			
			// Affichage des villes
			for (Sommet s : positionVilles) {
				g.fillRect((int) s.getx(), (int) s.gety(), 4, 4);
			}
			
			// Affichage des liaisons
			paintPath(g);
		}
		
		public void paintPath(Graphics2D g) 
		{
			g.setColor(Color.BLACK); 
			int nbArc = sonGraphe.getArcs().size();
			for (int i = 0; i < nbArc; i++) 
			{
				Sommet debut = positionVilles.get(i);
				Sommet fin = positionVilles.get(i+1);
				g.drawLine((int)debut.getx() + 2, (int)debut.gety() + 2, (int)fin.getx() + 2, (int)fin.gety() + 2);	
			}

		}
		
		
		public Rectangle.Double getWindowSize() {
			return tailleFenetre;
		}

		public void setWindowSize(Rectangle.Double tailleFenetre) {
			this.tailleFenetre = tailleFenetre;
		}

		@Override
		public void componentResized(ComponentEvent arg0) {
			//on resize, compute the new coordinates with the new window size
			positionsAccordingScreen();
			repaint();
		}

		@Override
		public void componentHidden(ComponentEvent arg0) {

		}

		@Override
		public void componentMoved(ComponentEvent arg0) {

		}

		@Override
		public void componentShown(ComponentEvent arg0) {
			repaint();

		}

		@Override
		public void mouseDragged(MouseEvent arg0) {

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			

		}
	

}
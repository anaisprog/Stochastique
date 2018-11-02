package vue;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.GridLayout;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class Interface implements ActionListener{

	private static JButton charger;
	private static JButton start;
	
	private static JFrame frame;
	private static JPanel panel;
	private static JPanel menu;
	
	
	//radiobuttons
	private static JRadioButton cplex;
	private static JRadioButton recuitSimule;
	private static JRadioButton stochastique;
	private static JRadioButton deterministe;
 	
	public void createJFrame() {

		System.out.println("Creation de l'interface graphique");

		//Creation de la fenetre de resolution
		frame = new JFrame("Resolution du TSP");
	    frame.setPreferredSize(new Dimension(1000, 1000));
	    panel = new JPanel(new BorderLayout());
		
	    //Creation du menu 
	    menu = new JPanel();
		menu.setPreferredSize(new Dimension(400, 200));
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		
	    
		//Realisation du chargement des villes
		JPanel firstBox = new JPanel(new GridLayout(0, 1));
		Border borderDonnees = BorderFactory.createTitledBorder("Donnees");
		firstBox.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0), borderDonnees));
        charger = new JButton("Charger fichier villes");
        charger.addActionListener(this);
		firstBox.add(charger);
		Dimension dimension = new Dimension(250, 150);
		firstBox.setMaximumSize(dimension);

        
		//Radio Buttons pour les differents algo

		JPanel algorithme = new JPanel(new GridLayout(0, 1));
		Border border = BorderFactory.createTitledBorder("Choix Algorithme de résolution");
		algorithme.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), border));


		cplex = new JRadioButton("CPLEX");
		recuitSimule = new JRadioButton("Recuit Simule");

		cplex.addActionListener(this);
		recuitSimule.addActionListener(this);
		
		ButtonGroup algo = new ButtonGroup();
		
		algo.add(cplex);
		algo.add(recuitSimule);

		algorithme.add(cplex);
		algorithme.add(recuitSimule);	
		algorithme.setMaximumSize(dimension);
		
		

		//Radio Buttons pour la résolution 
		
		JPanel resolution = new JPanel(new GridLayout(5, 1));
		Border border2 = BorderFactory.createTitledBorder("Choix type de résolution");
		resolution.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), border2));


		deterministe = new JRadioButton("Deterministe");
		stochastique = new JRadioButton("Stochastique");

		deterministe.addActionListener(this);
		stochastique.addActionListener(this);
		
		ButtonGroup resolutionbutton = new ButtonGroup();
		
		algo.add(deterministe);
		algo.add(stochastique);

		resolution.add(deterministe);
		resolution.add(stochastique);	
		resolution.setMaximumSize(dimension);
		
		/*Ajout des elements au menu */

		menu.add(firstBox);
		menu.add(algorithme);
		menu.add(resolution);
		menu.add(new JSeparator(JSeparator.HORIZONTAL));
		
		start = new JButton("Lancer la résolution ");
        start.addActionListener(this);
        
		JPanel choice = new JPanel();
		choice.setLayout(new BoxLayout(choice, BoxLayout.X_AXIS));
		
		choice.add(start);
		menu.add(choice);
		panel.add(menu, BorderLayout.WEST);
		
	
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		Interface i = new Interface();
		i.createJFrame();
		
	}


}
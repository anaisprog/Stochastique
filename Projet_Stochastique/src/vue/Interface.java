package vue;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.GridLayout;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import controleur.ParserXMLFile;
import controleur.Solveur;
import ilog.concert.IloException;
import modele.Graph;
import modele.ProgrammeLineaire;

public abstract class Interface implements ActionListener {

	private static JButton charger;
	private static JButton start;
	private static JButton stop;
	
	private static JFrame frame;
	private static JPanel panel;	
	private static JPanel menu;
	private static JPanel areaText;
	
	
	//radiobuttons
	private static JRadioButton cplex;
	private static JRadioButton recuitSimule;
	private static JRadioButton stochastique;
	private static JRadioButton deterministe;
 	
	private static JTextArea area_Text;
	private static JScrollPane scroll;
	
	static int algochoice = -1;
	static int nature = -1;
	private static ProgrammeLineaire prog;
	
	public static void createJFrame() {

		System.out.println("Creation de l'interface graphique");

		//Creation de la fenetre de resolution
		frame = new JFrame("Resolution du TSP");
	    frame.setPreferredSize(new Dimension(1000, 600));
	    panel = new JPanel(new BorderLayout());
	    areaText = new JPanel(new BorderLayout());
	    area_Text = new JTextArea(20, 60);
	    scroll = new JScrollPane(area_Text);
	   
		
	    //Creation du menu 
	    menu = new JPanel(new GridLayout(1, 0));
		menu.setPreferredSize(new Dimension(300, 500));
		JPanel second = new JPanel(new GridLayout(0, 1));
		second.add(scroll);

		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		
		
		//Taille de l'areatext
		areaText.setPreferredSize(new Dimension(300, 500));

		
		//Realisation du chargement des villes
		JPanel firstBox = new JPanel(new GridLayout(0, 1));
		Border borderDonnees = BorderFactory.createTitledBorder("Donnees");
		firstBox.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0), borderDonnees));
        charger = new JButton("Charger fichier villes");
        
        charger.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser choix = new JFileChooser();
				
				int retour=choix.showOpenDialog(charger);
				
				if(retour==JFileChooser.APPROVE_OPTION){

				   choix.getSelectedFile().getName();
        
				   String filename = choix.getSelectedFile().
				          getAbsolutePath();
				   
				   ParserXMLFile parser = new ParserXMLFile();
				   Graph graph = parser.parse(filename);
				   if(graph != null){
					   prog = new ProgrammeLineaire();
					   prog.setGraph(graph);
				   } else {
					   JOptionPane.showMessageDialog(panel, "Format de fichier non valide", "Attention",
						        JOptionPane.WARNING_MESSAGE);
				   } 
				}
			}
        	
        });
        
		firstBox.add(charger);
		Dimension dimension = new Dimension(250, 150);
		firstBox.setMaximumSize(dimension);

		
		//Radio Buttons pour les differents algo
		JPanel algorithme = new JPanel(new GridLayout(0, 1));
		Border border = BorderFactory.createTitledBorder("Choix Algorithme de resolution");
		algorithme.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), border));


		cplex = new JRadioButton("CPLEX");
		recuitSimule = new JRadioButton("Recuit Simule");

		cplex.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				algochoice = 1;
			}
		});
		
		recuitSimule.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				algochoice = 0;
			}
		});
		
		ButtonGroup algo = new ButtonGroup();
		
		algo.add(cplex);
		algo.add(recuitSimule);

		algorithme.add(cplex);
		algorithme.add(recuitSimule);	
		algorithme.setMaximumSize(dimension);
		
		//Radio Buttons pour le type du problème 
		JPanel resolution = new JPanel(new GridLayout(5, 1));
		Border border2 = BorderFactory.createTitledBorder("Choix type de resolution");
		resolution.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), border2));

		deterministe = new JRadioButton("Deterministe");
		stochastique = new JRadioButton("Stochastique");

		deterministe.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				nature = 0;
			}
		});
		
		stochastique.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				nature = 1;
			}
		});
		
		ButtonGroup resolutionbutton = new ButtonGroup();
		
		resolutionbutton.add(deterministe);
		resolutionbutton.add(stochastique);

		resolution.add(deterministe);
		resolution.add(stochastique);	
		resolution.setMaximumSize(dimension);

		
		/*Ajout des elements au menu */
		menu.add(firstBox);
		menu.add(algorithme);
		menu.add(resolution);
		menu.add(new JSeparator(JSeparator.HORIZONTAL));
		menu.add(second);
		
		start = new JButton("Lancer");
        start.addActionListener(new ActionListener(){
        
        
			@Override
			public void actionPerformed(ActionEvent e) {
				if(algochoice == -1){
					JOptionPane.showMessageDialog(panel, "Veuillez choisir l'algorithme de résolution", "Attention",
					        JOptionPane.WARNING_MESSAGE);
				}
				
				if(nature == -1){
					JOptionPane.showMessageDialog(panel, "Veuillez choisir la nature du problème", "Attention",
					        JOptionPane.WARNING_MESSAGE);
				}
				
				if(prog == null){
					JOptionPane.showMessageDialog(panel, "Aucun fichier de données chargé", "Attention",
					        JOptionPane.WARNING_MESSAGE);
				}
				
				Solveur solv = new Solveur(algochoice, nature, prog);
				
				try {
					solv.run();
				} catch (IloException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	
        });
        
        stop = new JButton("Arreter");
        stop.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			}
        	
        });

		
		panel.add(menu, BorderLayout.WEST);

		// Pour le panel text 
		panel.add(area_Text, BorderLayout.EAST);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		areaText.setVisible(true);
		JPanel choice = new JPanel();
		choice.setLayout(new BoxLayout(choice, BoxLayout.X_AXIS));
		
		choice.add(start);
		choice.add(stop);
		menu.add(choice);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void majAffichage(String info){
		String txtbase = Interface.area_Text.getText();
		Interface.area_Text.setText(txtbase + "\n" + info);
	}
	
	public static void main(String[] args) {
		Interface.createJFrame();
	}
}
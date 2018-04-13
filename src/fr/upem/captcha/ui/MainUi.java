/*
 *  AUTHORS : NASR DAVID, OEUVRAY JORIS
 *  Description : Gestion et affichage de la fenêtre
 */

package fr.upem.captcha.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MainUi {
	
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	private final static int width = 1024;
	private final static int height = 768;
	
	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Captcha"); // Création de la fenêtre principale
		
		GridLayout layout = createLayout();  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		
		frame.setLayout(layout);  // Affection du layout dans la fenêtre.
		frame.setSize(width, height); // Définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.
		 
		JButton okButton = createOkButton();
		
		frame.add(createLabelImage("centre ville.jpg")); // Ajouter des composants à la fenêtre
		frame.add(createLabelImage("le havre.jpg"));
		frame.add(createLabelImage("panneau 70.jpg"));
		frame.add(createLabelImage("panneaubleu-carre.jpeg"));
		frame.add(createLabelImage("parking.jpg"));
		frame.add(createLabelImage("route panneau.jpg"));
		frame.add(createLabelImage("tour eiffel.jpg"));
		frame.add(createLabelImage("ville espace verts.jpg"));
		frame.add(createLabelImage("voie pieton.jpg"));
		
		
		
		frame.add(new JTextArea("Cliquez n'importe où ... juste pour tester l'interface !"));
		
		
		frame.add(okButton);
		
		frame.setVisible(true);
	}
	
	
	private static GridLayout createLayout(){
		return new GridLayout(4,3);
	}
	
	private static JButton createOkButton(){
		return new JButton(new AbstractAction("Vérifier") { // Ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // Faire des choses dans l'interface donc appeler cela dans la queue des �v�nements
					
					@Override
					public void run() { // c'est un runnable
						System.out.println("J'ai cliqué sur Ok");
					}
				});
			}
		});
	}
	
	private static JLabel createLabelImage(String imageLocation) throws IOException{
		
		final URL url = MainUi.class.getResource(imageLocation); // Aller chercher les images !! IMPORTANT 
		
		System.out.println(url); 
		
		BufferedImage img = ImageIO.read(url); // Lire l'image
		Image sImage = img.getScaledInstance(width/3,height/4, Image.SCALE_SMOOTH); // Redimensionner l'image
		
		final JLabel label = new JLabel(new ImageIcon(sImage)); // Créer le composant pour ajouter l'image dans la fen�tre
		
		label.addMouseListener(new MouseListener() { // Ajouter le listener d'évenement de souris
			private boolean isSelected = false;
			
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
		
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) { // Ce qui nous intéresse c'est lorsqu'on clique sur une image, il y a donc des choses � faire ici
				EventQueue.invokeLater(new Runnable() { 
					
					@Override
					public void run() {
						if(!isSelected){
							label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
							isSelected = true;
							selectedImages.add(url);
						}
						else {
							label.setBorder(BorderFactory.createEmptyBorder());
							isSelected = false;
							selectedImages.remove(url);
						}
						
					}
				});
				
			}
		});
		
		return label;
	}
}

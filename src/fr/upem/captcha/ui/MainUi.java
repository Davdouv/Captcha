/**
 * @authors : David NASR - Joris OEUVRAY
 * @date : 13 avr. 2018
 * @file : MainUI.java
 * @package : fr.upem.captcha.ui
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
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import fr.upem.captacha.grid.Grid;
import fr.upem.captcha.images.Images;
import fr.upem.captcha.images.boisson.Boisson;
import fr.upem.captcha.images.poulet.Poulet;

/**
 * MainUi est une classe qui permet la gestion et l'affichage de la fenêtre
 */
public class MainUi {
	
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	//private static Images correctCategory;
	//private static List<URL> correctImages = new ArrayList<URL>();
	private static Grid grid = new Grid();
	private final static int width = 800;
	private final static int height = 600;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		JFrame frame = new JFrame("Captcha"); // CrÃ©ation de la fenÃªtre principale
		
		GridLayout layout = createLayout();  // CrÃ©ation d'un layout de type Grille avec 4 lignes et 3 colonnes
		
		frame.setLayout(layout);  // Affection du layout dans la fenÃªtre.
		frame.setSize(width, height); // DÃ©finition de la taille
		frame.setResizable(false);  // On dÃ©finit la fenÃªtre comme non redimentionnable
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenÃªtre on quitte le programme.
		 
		JButton okButton = createOkButton();

		for(int i = 0; i < 9; i++) {
			frame.add(createLabelImage(grid.getImages().get(i)));
		}
		
		frame.add(new JTextArea("Cliquez sur les images de " + grid.getCategory().getClass().getSimpleName()));
		
		frame.add(okButton);
		
		frame.setVisible(true);
	}

	private static GridLayout createLayout(){
		return new GridLayout(4,3);
	}
	
	private static JButton createOkButton(){
		return new JButton(new AbstractAction("VÃ©rifier") { // Ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // Faire des choses dans l'interface donc appeler cela dans la queue des ï¿½vï¿½nements
					
					@Override
					public void run() { // c'est un runnable
						if (checkSelectedImages(grid.getCategory(), selectedImages, grid.getCorrectImages())) {
							System.out.println("C'est correct !");
						}
						else {
							System.out.println("Tu es démasqué robot !");
							// Demandé ici -> Relancer un test plus compliqué que le 1er
							// restart();
						}
					}
				});
			}
		});
	}
	
	private static JLabel createLabelImage(URL imageLocation) throws IOException{
		
		//final URL url = MainUi.class.getResource(imageLocation); // Aller chercher les images !! IMPORTANT 
		final URL url = imageLocation;
		
		//System.out.println(url); 
		
		BufferedImage img = ImageIO.read(url); // Lire l'image
		Image sImage = img.getScaledInstance(width/3,height/4, Image.SCALE_SMOOTH); // Redimensionner l'image
		
		final JLabel label = new JLabel(new ImageIcon(sImage)); // CrÃ©er le composant pour ajouter l'image dans la fenï¿½tre
		
		label.addMouseListener(new MouseListener() { // Ajouter le listener d'Ã©venement de souris
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
			public void mouseClicked(MouseEvent arg0) { // Ce qui nous intÃ©resse c'est lorsqu'on clique sur une image, il y a donc des choses ï¿½ faire ici
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
		
	/**
	 * Vérifie si toutes les images correctes ont été sélectionnés (et seulement elles)
	 * 
	 * @param 
	 * 		category - La bonne catégorie
	 * 		selectedImages - La liste des URL des images sélectionnées
	 * 		correctImages - La liste des URL des images correctes
	 * @return boolean - Retourne true si toutes les images sont correctes, sinon false
	 */
	private static boolean checkSelectedImages(Images category, ArrayList<URL> selectedImages, List<URL> correctImages) {
		// D'abord on vérifie si la liste contient toutes les images correct
		if (selectedImages.containsAll(correctImages)) {
			System.out.println("OKAY");
			// Ensuite on veut vérifier qu'il n'y ait pas de mauvaises images sélectionnées
			for (URL imageURL : selectedImages) {
				if(!category.isPhotoCorrect(imageURL)) return false;	// Si une seule image est fausse, alors le test est échoué
			}
			
			return true;			
		}
		else {	// Si on n'a pas sélectionné toutes les images correctes, alors c'est forcément faux
			return false;
		}
		
		// Une autre méthode serait de parcourir toutes les images et d'utiliser la méthode isPhotoCorrect sur les images sélectionnées
		// Ainsi, pas besoin de stocker les images correctes
	}
	
	/**
	 * Restart...
	 * 
	 */
	private static void restart() {
		selectedImages.clear();
		//frame.removeAll();
		grid.restart();
	}
}

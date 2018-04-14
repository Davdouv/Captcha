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

import fr.upem.captcha.images.Images;
import fr.upem.captcha.images.boisson.Boisson;
import fr.upem.captcha.images.poulet.Poulet;

/**
 * MainUi est une classe qui permet la gestion et l'affichage de la fen�tre
 */
public class MainUi {
	
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	private final static int width = 800;
	private final static int height = 600;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		JFrame frame = new JFrame("Captcha"); // Création de la fenêtre principale
		
		GridLayout layout = createLayout();  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		
		frame.setLayout(layout);  // Affection du layout dans la fenêtre.
		frame.setSize(width, height); // Définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.
		 
		JButton okButton = createOkButton();
		
		/*
		frame.add(createLabelImage("centre ville.jpg")); // Ajouter des composants à la fenêtre
		frame.add(createLabelImage("le havre.jpg"));
		frame.add(createLabelImage("panneau 70.jpg"));
		frame.add(createLabelImage("panneaubleu-carre.jpeg"));
		frame.add(createLabelImage("parking.jpg"));
		frame.add(createLabelImage("route panneau.jpg"));
		frame.add(createLabelImage("tour eiffel.jpg"));
		frame.add(createLabelImage("ville espace verts.jpg"));
		frame.add(createLabelImage("voie pieton.jpg"));
		*/
		
		ArrayList<Images> categories = getCategories();
		
		Images category = getRandomCategory(categories);	// R�cup�re une cat�gorie au hasard
		
		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(4)+1;	// Renvoie un nombre entre 1 et 4
		List<URL> correctImages = category.getRandomPhotosURL(randomNumber);	// Renvoie un nombre al�atoire d'images de la cat�gorie
		
		List<URL> wrongImages = getOtherCategoryPhotos(categories, category, randomNumber);
		
		ArrayList<URL> imagesList = new ArrayList<URL>();
		imagesList.addAll(correctImages);
		imagesList.addAll(wrongImages);
		Collections.shuffle(imagesList);

		for(int i = 0; i < 9; i++) {
			System.out.println(imagesList.get(i).toString());
			frame.add(createLabelImage(imagesList.get(i)));
		}
		
		frame.add(new JTextArea("Cliquez sur les images de " + category.getClass().getSimpleName()));
		
		
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
	
	private static JLabel createLabelImage(URL imageLocation) throws IOException{
		
		//final URL url = MainUi.class.getResource(imageLocation); // Aller chercher les images !! IMPORTANT 
		final URL url = imageLocation;
		
		//System.out.println(url); 
		
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
	
	/**
	 * Retourne la liste des cat�gories existantes
	 * 
	 * @return ArrayList<Images> - La liste des classes existantes impl�mentant l'interface Images
	 */
	public static ArrayList<Images> getCategories() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>(); 		// une liste de toutes nos classes
		classes.add(Poulet.class);	// on rajoute manuellement toutes nos classes
		classes.add(Boisson.class);
		
		ArrayList<Images> categories = new ArrayList<Images>();
		for (Class clazz : classes) {
			categories.add(instantiateImages(clazz));	// On instance chaque classe en objet de type Images qu'on rajoute dans notre liste
		}
		
		return categories;
	}

	/**
	 * Retourne une cat�gorie al�atoire
	 * 
	 * @param categories - La liste des categories existantes, � r�cup�rer avec getCategories()
	 * @return Images - Un objet de la cat�gorie qui impl�mente l'interface Images
	 */
	public static Images getRandomCategory(ArrayList<Images> categories) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Random randomGenerator = new Random();
		Images category = categories.get(randomGenerator.nextInt(categories.size()));	// on choisit al�atoirement une classe dans la liste

		return category;	// on renvoit la categorie
	}
	
	/**
	 * Retourne une cat�gorie al�atoire
	 * 
	 * @param category - Instancie un objet de type Images � partir de la class de category
	 * @return Images - L'objet de type Images instanci�
	 */
	public static Images instantiateImages(Class<?> category) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> cls = Class.forName(category.getTypeName());	// On r�cup�re le type de la classe
		Object clsInstance = cls.newInstance();	// On instancie un objet du type de la classe
		return (Images)clsInstance;	// On le cast en Images pour pouvoir utiliser les m�thodes de l'interface
	}
	
	/**
	 * Retourne une liste d'url d'images qui ne font pas partie de la bonne cat�gorie
	 * 
	 * @param 
	 * 		categories - La liste de toutes les cat�gories
	 * 		category - La bonne cat�gorie
	 * 		randomNumber - Le nombre d'images correctes (on fera 9 - ce nombre pour connaitre combien d'images il nous manque)
	 * @return List<URL> - La liste d'url des mauvaises images
	 */
	private static List<URL> getOtherCategoryPhotos(ArrayList<Images> categories, Images category, int randomNumber) {
		categories.remove(category);	// on enl�ve la bonne cat�gorie de la liste
		List<URL> wrongImages = new ArrayList<URL>();	// on va stocker toutes les images dans une liste
		
		Random randomGenerator = new Random();
		for (int i = randomNumber; i < 10; i++) {
			URL url;
			do {
				int rand = randomGenerator.nextInt(categories.size());	// On choisit une categorie al�atoire
				url = categories.get(rand).getRandomPhotoURL();
			} while (wrongImages.contains(url));	// on v�rifie qu'on n'a pas d�j� s�lectionn� cette image, sinon on en choisit une autre

			wrongImages.add(url);	// On rajoute une photo al�atoire de cette cat�gorie
		}
		
		return wrongImages;
	}
}

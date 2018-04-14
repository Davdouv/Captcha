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
 * MainUi est une classe qui permet la gestion et l'affichage de la fenêtre
 */
public class MainUi {
	
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
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
		
		/*
		frame.add(createLabelImage("centre ville.jpg")); // Ajouter des composants Ã  la fenÃªtre
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
		
		Images category = getRandomCategory(categories);	// Récupére une catégorie au hasard
		
		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(4)+1;	// Renvoie un nombre entre 1 et 4
		List<URL> correctImages = category.getRandomPhotosURL(randomNumber);	// Renvoie un nombre aléatoire d'images de la catégorie
		
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
		return new JButton(new AbstractAction("VÃ©rifier") { // Ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // Faire des choses dans l'interface donc appeler cela dans la queue des ï¿½vï¿½nements
					
					@Override
					public void run() { // c'est un runnable
						System.out.println("J'ai cliquÃ© sur Ok");
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
	 * Retourne la liste des catégories existantes
	 * 
	 * @return ArrayList<Images> - La liste des classes existantes implémentant l'interface Images
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
	 * Retourne une catégorie aléatoire
	 * 
	 * @param categories - La liste des categories existantes, à récupérer avec getCategories()
	 * @return Images - Un objet de la catégorie qui implémente l'interface Images
	 */
	public static Images getRandomCategory(ArrayList<Images> categories) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Random randomGenerator = new Random();
		Images category = categories.get(randomGenerator.nextInt(categories.size()));	// on choisit aléatoirement une classe dans la liste

		return category;	// on renvoit la categorie
	}
	
	/**
	 * Retourne une catégorie aléatoire
	 * 
	 * @param category - Instancie un objet de type Images à partir de la class de category
	 * @return Images - L'objet de type Images instancié
	 */
	public static Images instantiateImages(Class<?> category) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> cls = Class.forName(category.getTypeName());	// On récupére le type de la classe
		Object clsInstance = cls.newInstance();	// On instancie un objet du type de la classe
		return (Images)clsInstance;	// On le cast en Images pour pouvoir utiliser les méthodes de l'interface
	}
	
	/**
	 * Retourne une liste d'url d'images qui ne font pas partie de la bonne catégorie
	 * 
	 * @param 
	 * 		categories - La liste de toutes les catégories
	 * 		category - La bonne catégorie
	 * 		randomNumber - Le nombre d'images correctes (on fera 9 - ce nombre pour connaitre combien d'images il nous manque)
	 * @return List<URL> - La liste d'url des mauvaises images
	 */
	private static List<URL> getOtherCategoryPhotos(ArrayList<Images> categories, Images category, int randomNumber) {
		categories.remove(category);	// on enléve la bonne catégorie de la liste
		List<URL> wrongImages = new ArrayList<URL>();	// on va stocker toutes les images dans une liste
		
		Random randomGenerator = new Random();
		for (int i = randomNumber; i < 10; i++) {
			URL url;
			do {
				int rand = randomGenerator.nextInt(categories.size());	// On choisit une categorie aléatoire
				url = categories.get(rand).getRandomPhotoURL();
			} while (wrongImages.contains(url));	// on vérifie qu'on n'a pas déjà sélectionné cette image, sinon on en choisit une autre

			wrongImages.add(url);	// On rajoute une photo aléatoire de cette catégorie
		}
		
		return wrongImages;
	}
}

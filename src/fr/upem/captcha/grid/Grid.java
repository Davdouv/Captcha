/**
 * @authors : David NASR - Joris OEUVRAY
 * @date : 15 avr. 2018
 * @file : Grid.java
 * @package : fr.upem.captcha.grid
 */

package fr.upem.captcha.grid;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import fr.upem.captcha.images.Images;
import fr.upem.captcha.images.boisson.Boisson;
import fr.upem.captcha.images.poulet.Poulet;

/**
 * Grid est une classe qui va contenir les informations à afficher dans le Captcha :
 * 	- correctCategory : La bonne categorie
 * 	- correctImages : Les images de la catégorie (de 1 à 4 images)
 * 	- allImages : correctImages + des images qui ne font pas partie de la catégorie
 */
public class Grid {
	private Images correctCategory;
	private List<URL> correctImages = new ArrayList<URL>();
	private ArrayList<URL> allImages = new ArrayList<URL>();
	
	/**
	 * Constructeur par défaut de Grid.
	 * Fais appel à ses méthodes pour initialiser ses variables (catégorie et photos aléatoires)
	 */
	public Grid() {
		ArrayList<Images> categories = null;
		try {
			categories = getCategories();	// On récupére les différentes classes
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		try {
			correctCategory = getRandomCategory(categories);	// On récupére une classe au hasard
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		// Récupére une catégorie au hasard
		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(4)+1;	// Renvoie un nombre entre 1 et 4
		
		correctImages = correctCategory.getRandomPhotosURL(randomNumber);	// Renvoie un nombre aléatoire d'images de la catégorie
		List<URL> wrongImages = getOtherCategoryPhotos(categories, correctCategory, randomNumber);

		allImages.addAll(correctImages);
		allImages.addAll(wrongImages);
		Collections.shuffle(allImages);
	}
	
	public Images getCategory() {
		return correctCategory;
	}
	public List<URL> getCorrectImages() {
		return correctImages;
	}
	public ArrayList<URL> getImages() {
		return allImages;
	}
	
	
	// Piste d'amélioration -> Trouver un moyen pour récupérer toutes les classes qui implémentent Images
	/**
	 * Retourne la liste des catégories existantes
	 * 
	 * @return ArrayList<Images> - La liste des classes existantes implémentant l'interface Images
	 */
	public static ArrayList<Images> getCategories() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>(); 		// une liste de toutes nos classes
		classes.add(Poulet.class);	// on rajoute manuellement toutes nos classes
		classes.add(Boisson.class);		

		// TEST Méthode, ne fonctionne pas car les classes ne sont pas chargées, mais on ne veut pas les charger manuellement
	/*
		Images.class.getClassLoader().loadClass("fr.upem.captcha.images.poulet.Poulet");
		Images.class.getClassLoader().loadClass("fr.upem.captcha.images.boisson.Boisson");
		
	    for(Package p : Package.getPackages()) {
	    	String packageName = p.getName();
	    	if (packageName.startsWith("fr.upem.captcha.images.")) {
	    		String className = packageName.substring(packageName.lastIndexOf(".")+1);
	    		String upClassName = className.substring(0, 1).toUpperCase() + className.substring(1);
	    		String fullName = packageName+"."+upClassName;
	    		Class clazz = Class.forName(fullName);
	    		classes.add(clazz);
	         }
	    }
	*/
		// On instancie chaque classe en objet de type Images qu'on rajoute dans notre liste
		ArrayList<Images> categories = new ArrayList<Images>();
		for (Class clazz : classes) {
			categories.add(instantiateImages(clazz));
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
		for (int i = randomNumber; i < 9; i++) {
			URL url;
			do {
				int rand = randomGenerator.nextInt(categories.size());	// On choisit une categorie aléatoire
				url = categories.get(rand).getRandomPhotoURL();
			} while (wrongImages.contains(url));	// on vérifie qu'on n'a pas déjà sélectionné cette image, sinon on en choisit une autre

			wrongImages.add(url);	// On rajoute une photo aléatoire de cette catégorie
		}
		
		return wrongImages;
	}
	
	/**
	 * Restart ...
	 * 
	 */
	public static void restart() {
		
	}
}

/**
 * @authors : David NASR - Joris OEUVRAY
 * @date : 4 mai 2018
 * @file : Categorie.java
 * @package : fr.upem.captcha.images
 */

package fr.upem.captcha.images;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class Categorie implements Images {
	List<URL> photos = new ArrayList<URL>();

	/* (non-Javadoc)
	 * @see fr.upem.captcha.images.Images#getPhotos()
	 */
	@Override
	public List<URL> getPhotos() {
		List<URL> photos = new ArrayList<URL>();
		
		//String packageName = "src/"+this.getClass().getPackage().getName();
		String packageName = "../src/"+this.getClass().getPackage().getName();
		String currentPath = packageName.replace('.', '/');
		System.out.println(currentPath);
		
		Path currentRelativePath = Paths.get(currentPath);
		
		// On récupére les sous dossiers (c'est à dire les categories)
		List<String> directories = null;
		try {
			directories = Files.walk(currentRelativePath, 1)
			        .map(Path::getFileName)
			        .map(Path::toString)
			        .filter(n -> !n.contains("."))
			        .collect(Collectors.toList());
			directories.remove(0);	// On enléve le 0 car c'est le nom du dossier courant
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(directories);
		
		// Pour chaque sous-dossier, on récupére les images
		for (String directorie : directories) {
			Path childPath = Paths.get(currentPath + "/" + directorie);
			
			// On récupére les images
			List<String> images = null;
			try {
				images = Files.walk(childPath, 1)
				        .map(Path::getFileName)
				        .map(Path::toString)
				        .filter(n -> n.contains(".jpg") || n.contains(".png"))
				        .collect(Collectors.toList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (String image : images) {
				photos.add(this.getClass().getResource(directorie+"/"+image));
			}
		}
		
		// S'il n'y a pas de sous dossier, on récupére les images directement dans le dossier actuel
		if(directories.isEmpty()) {
			List<String> images = null;
			try {
				images = Files.walk(currentRelativePath, 1)
				        .map(Path::getFileName)
				        .map(Path::toString)
				        .filter(n -> n.contains(".jpg") || n.contains(".png"))
				        .collect(Collectors.toList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (String image : images) {
				photos.add(this.getClass().getResource(image));
			}
		}
		
		this.photos = photos;
		return photos;
	}

	/* (non-Javadoc)
	 * @see fr.upem.captcha.images.Images#getRandomPhotosURL(int)
	 */
	@Override
	public List<URL> getRandomPhotosURL(int value) throws IllegalArgumentException {
		if (this.photos.isEmpty()) getPhotos();
		List<URL> photos = this.photos;
		List<URL> randomPhotos = new ArrayList<URL>();
		Random randomGenerator = new Random();
		List<Integer> randomNumbers = new ArrayList<Integer>();
		
		if (photos.size() == 0) {
			throw new IllegalArgumentException("Il n'y a aucune photo pour cette classe");
		}
		else if (value > photos.size()) {
			throw new IllegalArgumentException("La valeur doit être inférieure à " + photos.size());
		}
		
		int randomNumber;
		for (int i = 0; i < value; i++) {
			do {
				randomNumber = randomGenerator.nextInt(photos.size());
			} while(randomNumbers.contains(randomNumber));
			
			randomNumbers.add(randomNumber);
			randomPhotos.add(photos.get(randomNumber));
		}
		
		return randomPhotos;
	}

	/* (non-Javadoc)
	 * @see fr.upem.captcha.images.Images#getRandomPhotoURL()
	 */
	@Override
	public URL getRandomPhotoURL() {
		return getRandomPhotosURL(1).get(0);
	}

	/* (non-Javadoc)
	 * @see fr.upem.captcha.images.Images#isPhotoCorrect(java.net.URL)
	 */
	@Override
	public boolean isPhotoCorrect(URL url) {
		String packageName = this.getClass().getPackage().getName();
		return url.toString().replace('/', '.').contains(packageName);
	}
}

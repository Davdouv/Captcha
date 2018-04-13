/**
 * @authors : David NASR - Joris OEUVRAY
 * @date : 13 avr. 2018
 * @file : Poulet.java
 * @package : fr.upem.captcha.images
 */

package fr.upem.captcha.images.poulet;

import java.net.URL;
import java.util.List;
import java.util.Random;

import fr.upem.captcha.images.Images;

import java.util.ArrayList;

public class Poulet implements Images {

	public static void main(String[] args) {
		Poulet poulet = new Poulet();
		//System.out.println(poulet.getRandomPhotosURL(1));
		System.out.println(poulet.isPhotoCorrect(poulet.getRandomPhotoURL()));
	}
	
	@Override
	public List<URL> getPhotos() {
		List<URL> photos = new ArrayList<URL>();
		photos.add(this.getClass().getResource("poule.jpg"));
		photos.add(this.getClass().getResource("poulet.jpg"));

		return photos;
	}

	@Override
	public List<URL> getRandomPhotosURL(int value) throws IllegalArgumentException {
		List<URL> photos = getPhotos();
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

	@Override
	public URL getRandomPhotoURL() {
		return getRandomPhotosURL(1).get(0);
	}

	@Override
	public boolean isPhotoCorrect(URL url) {
		String packageName = this.getClass().getPackage().getName();
		return url.toString().replace('/', '.').contains(packageName);
	}

}

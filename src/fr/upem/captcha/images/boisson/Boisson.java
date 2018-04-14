/**
 * @authors : David NASR - Joris OEUVRAY
 * @date : 14 avr. 2018
 * @file : Boisson.java
 * @package : fr.upem.captcha.images.boisson
 */

package fr.upem.captcha.images.boisson;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.upem.captcha.images.Images;

public class Boisson implements Images {

	/* (non-Javadoc)
	 * @see fr.upem.captcha.images.Images#getPhotos()
	 */
	@Override
	public List<URL> getPhotos() {
		List<URL> photos = new ArrayList<URL>();
		photos.add(this.getClass().getResource("cafe.jpg"));
		photos.add(this.getClass().getResource("biere.jpg"));
		photos.add(this.getClass().getResource("eau.jpg"));
		photos.add(this.getClass().getResource("jusOrange.jpg"));
		photos.add(this.getClass().getResource("lait.jpg"));
		photos.add(this.getClass().getResource("oasis.jpg"));
		photos.add(this.getClass().getResource("cocktail.jpg"));
		photos.add(this.getClass().getResource("tea.jpg"));

		return photos;
	}

	/* (non-Javadoc)
	 * @see fr.upem.captcha.images.Images#getRandomPhotosURL(int)
	 */
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

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

import fr.upem.captcha.images.Categorie;

public class Boisson extends Categorie {

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
}

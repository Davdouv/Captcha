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
		
		for(int i=1; i<8; i++) {
			String s = "0" + i + ".jpg";
			photos.add(this.getClass().getResource(s));
		}

		return photos;
	}
}

/**
 * @authors : David NASR - Joris OEUVRAY
 * @date : 13 avr. 2018
 * @file : Images.java
 * @package : fr.upem.captcha.images
 * @description : [INSERT FILE DESCRIPTION]
 */

package fr.upem.captcha.images;

import java.net.URL;
import java.util.List;

public interface Images {
	public static List<URL> getPhotos() {
		return null;
	}
	
	public static List<URL> getRandomPhotosURL(int value) {
		return null;
	}
	
	public static URL getRandomPhotoURL() {
		return null;
	}
	
	public static boolean isPhotoCorrect(URL url) {
		return false;
	}

}

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
	public List<URL> getPhotos();
	
	public List<URL> getRandomPhotosURL(int value);
	
	public URL getRandomPhotoURL();
	
	public boolean isPhotoCorrect(URL url);

}

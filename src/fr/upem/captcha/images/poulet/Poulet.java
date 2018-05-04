/**
 * @authors : David NASR - Joris OEUVRAY
 * @date : 13 avr. 2018
 * @file : Poulet.java
 * @package : fr.upem.captcha.images
 */

package fr.upem.captcha.images.poulet;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.upem.captcha.images.Images;

import java.util.ArrayList;

// Piste d'am�lioration -> Trouver un moyen pour factoriser le code (Si Images �tait une classe abstraite �a aurait �t� cool...) car c'est le m�me dans chaque classe
public class Poulet implements Images {
	
	// Piste d'am�lioration -> Trouver un moyen de r�cup�rer tous les fichiers (.jpg, .png ...) du dossier et de ses sous-dossiers
	@Override
	public List<URL> getPhotos() {
		Path currentRelativePath = Paths.get("src/fr/upem/captcha/images/poulet");
		String path = currentRelativePath.toAbsolutePath().toString();
		//System.out.println(path);
		List<String> jpgFileNames = null;
		try {
			jpgFileNames = Files.walk(Paths.get(path))
			        .map(Path::getFileName)
			        .map(Path::toString)
			        .filter(n -> n.endsWith(".jpg"))
			        .collect(Collectors.toList());
			
			System.out.println(Files.walk(Paths.get(path))
			        .map(Path::getFileName)
			        .map(Path::toString)
			        .filter(n -> !n.contains("."))
			        .collect(Collectors.toList()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(jpgFileNames);
		
		List<URL> photos = new ArrayList<URL>();
		for (String file : jpgFileNames) {
			//System.out.println(file);
			//photos.add(this.getClass().getResource(file));
		}
		
		for(int i=1; i<8; i++) {
			String s = "0" + i + ".jpg";
			photos.add(this.getClass().getResource(s));
		}
		
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
			throw new IllegalArgumentException("La valeur doit �tre inf�rieure � " + photos.size());
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

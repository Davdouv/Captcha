/**
 * @authors : David NASR - Joris OEUVRAY
 * @date : 13 avr. 2018
 * @file : Poulet.java
 * @package : fr.upem.captcha.images
 */

package fr.upem.captcha.images.poulet;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.upem.captcha.images.Categorie;

import java.util.ArrayList;
import java.util.Enumeration;

<<<<<<< HEAD
// Piste d'amélioration -> Trouver un moyen pour factoriser le code (Si Images était une classe abstraite ça aurait été cool...) car c'est le même dans chaque classe
public class Poulet extends Categorie {
=======
// Piste d'amï¿½lioration -> Trouver un moyen pour factoriser le code (Si Images ï¿½tait une classe abstraite ï¿½a aurait ï¿½tï¿½ cool...) car c'est le mï¿½me dans chaque classe
public class Poulet implements Images {
>>>>>>> f3772409dba4760c28da8914dde22637ff7283ae
	
	// Piste d'amï¿½lioration -> Trouver un moyen de rï¿½cupï¿½rer tous les fichiers (.jpg, .png ...) du dossier et de ses sous-dossiers
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
		
		System.out.println("ok : " + getClass().getResource("poule.jpg"));
		
<<<<<<< HEAD
		try {
			Enumeration<URL> en=getClass().getClassLoader().getResources("src/fr/upem/captcha/images/poulet");
			System.out.println(en);
			if (en.hasMoreElements()) {
			    System.err.println("okokok");
			}
			else {
				System.err.println("nononono");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
=======
		if (photos.size() == 0) {
			throw new IllegalArgumentException("Il n'y a aucune photo pour cette classe");
		}
		else if (value > photos.size()) {
			throw new IllegalArgumentException("La valeur doit ï¿½tre infï¿½rieure ï¿½ " + photos.size());
		}
		
		int randomNumber;
		for (int i = 0; i < value; i++) {
			do {
				randomNumber = randomGenerator.nextInt(photos.size());
			} while(randomNumbers.contains(randomNumber));
			
			randomNumbers.add(randomNumber);
			randomPhotos.add(photos.get(randomNumber));
>>>>>>> f3772409dba4760c28da8914dde22637ff7283ae
		}
	
		return photos;
	}

}

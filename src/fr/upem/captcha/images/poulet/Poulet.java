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

public class Poulet extends Categorie {
	
	@Override
	public List<URL> getPhotos() {
		List<URL> photos = new ArrayList<URL>();
		
		for(int i=1; i<8; i++) {
			String s = "0" + i + ".jpg";
			photos.add(this.getClass().getResource(s));
		}

		return photos;
		
		/*
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

		for (String file : jpgFileNames) {
			//System.out.println(file);
			//photos.add(this.getClass().getResource(file));
		}
		
		System.out.println("ok : " + getClass().getResource("poule.jpg"));
		
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
		}
		*/
	}

}

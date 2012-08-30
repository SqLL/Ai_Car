package generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import seller.Administration;
import seller.Automatique;
import seller.PneusNeige;
import seller.PointOfSale;
import seller.PrestigeAndFun;
import seller.Tourisme;
import seller.ToutTerrain;
import seller.Turbo;
import seller.Utilitaire;
import seller.Voiture;

/**
 * 
 * @author squall
 * 
 *         This is a class to generate random car with or without option
 */

public class GenerateurClasse {

	/**
	 * Number of class from voiture
	 */
	public static final int NUMBERTYPEVOITURE = 5;

	public static final List<String> MARQUES = Arrays.asList("Ford",
			"Mazzeratti", "Renault", "BMW", "Audi", "Toyota");
	/**
	 * Decorator pattern option @see DecorateurVoiture
	 */
	public static final int NUMBEROPTION = 2;

	/**
	 * Without Option or with option
	 */
	public static final int NUMBERCHOIX = 2;

	public static Voiture genererVoiture() {

		int possibilite = (int) (Math.random() * (NUMBERCHOIX - 0) + 0);
		Voiture result;

		// System.out.println("possibilite = "+possibilite);
		if (possibilite == 0) // without option
		{
			result = GenererVoitureSansOption();
		} else // with option(s)
		{
			result = GenererOption(((int) (Math.random() * NUMBEROPTION + 1)),
					GenererVoitureSansOption());
		}
		return result;
	}

	/**
	 * 
	 * @return Random Car without option
	 */
	public static Voiture GenererVoitureSansOption() {
		// Random Rent
		Random random = new Random();

		// Random type
		int voitureType = (int) (Math.random() * NUMBERTYPEVOITURE + 1);
		// Random Marque
		String voitureMarque = MARQUES.get((int) (Math.random()
				* (MARQUES.size() - 0) + 0));

		// System.out.println("voitureType = "+voitureType);
		switch (voitureType) {
		case 1:
			return new Utilitaire(random.nextBoolean(), voitureMarque);

		case 2:
			return new Tourisme(random.nextBoolean(), voitureMarque);

		case 3:
			return new Automatique(random.nextBoolean(), voitureMarque);

		case 4:
			return new ToutTerrain(random.nextBoolean(), voitureMarque);

		default:
			return new PrestigeAndFun(random.nextBoolean(), voitureMarque);

		}
	}

	/*
	 * public static Voiture GenererOption(Voiture v) { int optionType = (int)
	 * (Math.random() * NUMBEROPTION + 1); System.out.println("optionType=" +
	 * optionType); switch (optionType) { case 1: return new Turbo(v); case 2:
	 * return new PneusNeige(v); } return v; }
	 */
	/**
	 * 
	 * @param numberOption
	 *            The number of option
	 * @param v
	 *            Car Without option
	 * @return Car v with Random Option(s)
	 */
	public static Voiture GenererOption(int numberOption, Voiture v) {
		Voiture result = v;
		boolean optionAlready;
		int optionType;
		List<Integer> optionOnVoiture = new ArrayList<Integer>();

		while (numberOption > 0) {
			optionType = (int) (Math.random() * NUMBEROPTION + 1);
			optionAlready = false;

			// System.out.println("optionType = "+optionType);
			// To know if we have already this option on the car
			for (Integer number : optionOnVoiture) {
				if (number.intValue() == optionType)
					optionAlready = true;
			}

			if (!optionAlready) {
				// if we have'nt
				switch (optionType) {
				case 1:
					result = new Turbo(v);
					// this option is now on the list to know that we used
					optionOnVoiture.add(new Integer(optionType));
					numberOption--;
				case 2:
					result = new PneusNeige(v);
					// this option is now on the list to know that we used
					optionOnVoiture.add(new Integer(optionType));
					numberOption--;
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * 
	 * @param tobereturn
	 *            is a random pointOfSale generate with conf
	 * @see PointOfSale
	 * @param conf
	 *            is a configuration to generate the PointOfSale and cars
	 * @param local the name of the localisation       
	 *            
	 * @return PointOfSale
	 */
	public static PointOfSale genererPointOfSale(PointOfSale tobereturn,
			Administration conf, String _local,int masterkey) {

		tobereturn.setlVoiture(new ArrayList<Voiture>());
		int nombreVoiture = conf.getSizeParkPointOfSale(); // Use the capacity
															// of conf
		System.out.println("[INFO] Generation de "
				+ nombreVoiture +" voitures");
		for (int i = 0; i < nombreVoiture; i++) {
			tobereturn.getlVoiture().add(GenerateurClasse.genererVoiture(conf,masterkey));
		}
		tobereturn.setLocal(_local);
		return tobereturn;
	}

	/**
	 * 
	 * 
	 * @param tobereturn
	 *            is a random pointOfSale generate with conf
	 * @see PointOfSale
	 * @param conf
	 *            is a configuration to generate the PointOfSale and cars
	 * @return PointOfSale
	 */
	public static PointOfSale genererPointOfSale(PointOfSale tobereturn,
			Administration conf,int masterkey) {

		tobereturn.setlVoiture(new ArrayList<Voiture>());
		int nombreVoiture = conf.getSizeParkPointOfSale(); // Use the capacity
															// of conf
		System.out.println("[INFO] Generation de "
				+ nombreVoiture +" voitures");
		for (int i = 0; i < nombreVoiture; i++) {
			tobereturn.getlVoiture().add(GenerateurClasse.genererVoiture(conf,masterkey));
		}
	
		return tobereturn;
	}
	/**
	 * 
	 * @param conf
	 *            is a parameter which configure the probability of the
	 *            attributes from the car
	 * @return Car artifact
	 */
	private static Voiture genererVoiture(Administration conf,int masterkey) {

		int possibilite = (int) (Math.random() * (NUMBERCHOIX - 0) + 0);
		Voiture result;

		// System.out.println("possibilite = "+possibilite);
		if (possibilite == 0) // without option
		{
			result = GenererVoitureSansOption(conf,masterkey);
		} else // with option(s)
		{
			result = GenererOption(((int) (Math.random() * NUMBEROPTION + 1)),
					GenererVoitureSansOption(conf,masterkey));
		}
		return result;

	}

	private static Voiture GenererVoitureSansOption(Administration conf,int masterkey) {
		// Random Rent
		Random random = new Random();
		Double rndm = random.nextDouble();
		Double probability = new Double(conf.getProbRent());
		//System.out.println(probability.toString() + " <=> " + rndm.toString());
		boolean pis_rent;
		if (probability == 0.0) {
			pis_rent=false;
		} else if (probability == 1.0) {
			pis_rent=true;
		} else {
			int answer = probability.compareTo(rndm);
			if (answer < 0 || answer == 0) {
				pis_rent = true;
			} else {
				pis_rent = false;
			}
		}

		// Random type
		int voitureType = (int) (Math.random() * NUMBERTYPEVOITURE + 1);
		// Random Marque
		String voitureMarque = MARQUES.get((int) (Math.random()
				* (MARQUES.size() - 0) + 0));

		// System.out.println("voitureType = "+voitureType);
		switch (voitureType) {
		case 1:
			return new Utilitaire(pis_rent, voitureMarque,masterkey);

		case 2:
			return new Tourisme(pis_rent, voitureMarque,masterkey);

		case 3:
			return new Automatique(pis_rent, voitureMarque,masterkey);

		case 4:
			return new ToutTerrain(pis_rent, voitureMarque,masterkey);

		default:
			return new PrestigeAndFun(pis_rent, voitureMarque,masterkey);

		}
	}
}

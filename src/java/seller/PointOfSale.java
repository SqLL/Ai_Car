package seller;

import generator.GenerateurClasse;

import java.util.ArrayList;
import java.util.List;

import customer.Contrainte;

import cartago.*;


/**
 * 
 * @author squall
 *
 *	Sale Point is a place where you can try to rent a car
 */
public class PointOfSale extends Artifact {

	private List<Voiture> lVoiture=new ArrayList<Voiture>();
	private String local;
	
	void init() {
		this.setlVoiture(new ArrayList<Voiture>());
		int nombreVoiture = (int) (Math.random() * 10 + 3);
		System.out.println("Generation d'une company avec pour voiture : "
				+ nombreVoiture);
		for (int i = 0; i < nombreVoiture; i++) {
			getlVoiture().add(GenerateurClasse.genererVoiture());
		}
	}
	
	

	@OPERATION
	void displayContent() {
		System.out.println(this.toString());
	}

	/**
	 * 
	 * @return if one or more car is available to rent
	 */
	@OPERATION
	boolean carAvailable() {
		boolean result = false;
		for (Voiture car : getlVoiture()) {
			result = (result || !car.is_Rent());
		}
		return result;
	}

	@OPERATION
	List<Voiture> getCarAvailable() {
		List<Voiture> lResult = new ArrayList<Voiture>();
		for (Voiture car : getlVoiture()) {
			if (!car.is_Rent())
				lResult.add(car);
		}
		return lResult;
	}

	@OPERATION
	List<Voiture> getCarAvailable(Contrainte Constraint,
			OpFeedbackParam<List<Voiture>> list) {
		List<Voiture> lResult = new ArrayList<Voiture>();
		for (Voiture car : getlVoiture()) {
			if ((!car.is_Rent())
					&& (car.getType().equals(Constraint.getTypeVoiture()))) {
				lResult.add(car);

			}
		}
		list.set(lResult);
		return lResult;
	}

	public String toString() {
		StringBuffer result = new StringBuffer("Localisation : "+this.local+
				System.getProperty("line.separator"));
		for (Voiture voiture : getlVoiture()) {
			result.append(" Voiture : "
					+ voiture.toString()
					+ System.getProperty("line.separator")
					+ " Indice : "
					+ voiture.getIndice()
					+ System.getProperty("line.separator")
					+ (voiture.is_Rent() ? " Voiture loué"
							: " Voiture non loué")
					+ System.getProperty("line.separator"));
			result.append(System.getProperty("line.separator"));
		}
		return result.toString();
	}

	@OPERATION
	public PointOfSale him(OpFeedbackParam<PointOfSale> point){
		point.set(this);
		return this;
	}
	
	public void addVoiture(Voiture v) {
		getlVoiture().add(v);
	}

	public List<Voiture> getlVoiture() {
		return lVoiture;
	}

	public void setlVoiture(List<Voiture> lVoiture) {
		this.lVoiture = lVoiture;
	}
	
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
}

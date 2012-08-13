package seller;

import cartago.Artifact;

/**
 * This class is to configure seller and company attribute
 * 
 * @author squall
 */
public class Administration extends Artifact {

	private double probRent; // Probability of the car to being rent
	private int sizeParkPointOfSale; // Capacity of sales points 

	void init() {

	}

	public double getProbRent() {
		return probRent;
	}

	public void setProbRent(double probRent) {
		this.probRent = probRent;
	}

	public int getSizeParkPointOfSale() {
		return sizeParkPointOfSale;
	}

	public void setSizeParkPointOfSale(int sizeParkPointOfSale) {
		this.sizeParkPointOfSale = sizeParkPointOfSale;
	}

}

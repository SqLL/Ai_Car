package seller;

import cartago.Artifact;

/**
 * This class is to configure seller and company attribute
 * 
 * @author squall
 */
public class Administration extends Artifact {

	private double probRent; // Probabilite que la voiture soit lou�
	private int sizeParkPointOfSale; // Capacit� des points de vente

	void Init() {

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

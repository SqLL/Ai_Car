package communication;

import java.util.ArrayList;
import java.util.List;

import customer.Contrainte;

import seller.Voiture;

import cartago.Artifact;
import cartago.GUARD;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

/**
 * 
 * @author squall this class is to do an easy way to communicate between seller
 *         and customer
 * 
 */

public class Dialog extends Artifact {


	private List<Voiture> carAvailable = new ArrayList<Voiture>();

	/**
	 * Request from the client to the rent for the seller
	 */
	private Contrainte request;
	private boolean answerRequest;

	void Init() {
		request = new Contrainte();
		answerRequest = false;
	}

	/**
	 * This Operation is for the client he have to wait some informations
	 */
	@OPERATION
	public void waitingInformations() {

		/*
		 * Attente du client
		 */
		await("waitingAnswer");

		System.out.println("[INFO] [CUSTOMER] Attente terminée ");
		//System.out.println(this.carAvailable.size());
		boolean result = (this.carAvailable.size() != 0);
		//System.out.println("result= " + result);
		signal("answerAvailable", result);
		//System.out.println(" Signal envoyé");
	}


	@GUARD
	public boolean waitingAnswer() {
		return answerRequest;
	}

	@OPERATION
	public void setRequest(Contrainte _request) {
		request = _request;
	}

	@OPERATION
	public Contrainte getRequest(OpFeedbackParam<Contrainte> Request) {
		Request.set(this.request);
		return this.request;
	}

	@OPERATION
	public List<Voiture> getCarAvailable() {
		return carAvailable;
	}

	@OPERATION
	public void setCarAvailable(List<Voiture> _carAvailable) {
		// System.out.println(this.carAvailable.isEmpty());
		for (Voiture v : _carAvailable) {
			this.carAvailable.add(v);
		}
		// System.out.println(this.carAvailable.size());
	}

	@OPERATION
	public void setAnswer(boolean _answerRequest) {
		answerRequest = _answerRequest;
	}

	@OPERATION
	public void display() {

		StringBuffer result = new StringBuffer();
		for (Voiture voiture : this.carAvailable) {
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
		System.out.println(result.toString());
	}

}

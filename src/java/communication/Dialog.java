package communication;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
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
	private Voiture carChoose;
	private List<Float> lPrices = new ArrayList<Float>();
	private List<File> lFiles=new ArrayList<File>();
	
	private List<String> lActionCustomer = new ArrayList<String>();
	private List<String> lActionSeller = new ArrayList<String>();



	/**
	 * Request from the client to the rent for the seller
	 */
	private Contrainte request;
	private boolean answerRequest;
	

	void init() {
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
		for (int i=0;i<this.carAvailable.size();i++) {
			result.append(" Voiture : "
					+ this.carAvailable.get(i).toString()
					+ System.getProperty("line.separator")
					+ " Indice : "
					+ this.carAvailable.get(i).getIndice()
					+ System.getProperty("line.separator")
					+ (this.carAvailable.get(i).is_Rent() ? " Voiture loué"
							: " Voiture non loué")
					+ System.getProperty("line.separator")
					+ this.lFiles.get(i).getName().toString()
					+ System.getProperty("line.separator")
					+ this.lPrices.get(i)+""
					+ System.getProperty("line.separator"));
			result.append(System.getProperty("line.separator"));
			
		
		}
		System.out.println(result.toString());
		
		
	}

	
	@OPERATION 
	public Voiture quickChoice(){
		if(!this.carAvailable.isEmpty())
		{
			carChoose=this.carAvailable.get(0);
		}
		return carChoose;
	}
	
	@OPERATION
	public void getCarChoose(OpFeedbackParam<Voiture> _car)
	{
		_car.set(this.carChoose);
	}
	
	@OPERATION
	public void getTypeCarChoose(OpFeedbackParam<String> result){
		result.set("seller."+carChoose.getType());
	}
	
	@OPERATION
	public void setlPrices(List<Float> _lPrices) {
		for (Float v : _lPrices) {
			this.lPrices.add(v);
		}
	}

	@OPERATION
	public void setlFiles(List<File> lFiles) {
		for (File v : lFiles) {
			this.lFiles.add(v);
		}
	}

	@OPERATION
	public List<String> getlActionCustomer(OpFeedbackParam<List<String>> result) {
		result.set(lActionCustomer);
		return lActionCustomer;
	}

	public void setlActionCustomer(List<String> lActionCustomer) {
		this.lActionCustomer = lActionCustomer;
	}

	@OPERATION
	public List<String> getlActionSeller(OpFeedbackParam<List<String>> result) {
		result.set(lActionSeller);
		return lActionSeller;
	}

	public void setlActionSeller(List<String> lActionSeller) {
		this.lActionSeller = lActionSeller;
	}
	
	@OPERATION
	public void addActionCustomer(String action)
	{
		this.lActionCustomer.add(action);
	}
	
	@OPERATION
	public void addActionSeller(String action)
	{
		this.lActionSeller.add(action);
	}
}

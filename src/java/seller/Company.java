package seller;

import generator.GenerateurClasse;

import java.util.ArrayList;
import java.util.List;

import customer.Contrainte;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;


/**
 * 
 * @author squall
 *
 *	This class Represent Company which containt sales Points and car in each of them
 */

public class Company extends Artifact {

	
	private List<PointOfSale> lPoint=new ArrayList<PointOfSale>();

	private PointOfSale pointFound;
	private Administration config;

	void init()
	{

		lPoint = new ArrayList<PointOfSale>();
		//lPoint.add(GenerateurClasse.genererCompany(new PointOfSale()));
	}

	void init(Administration conf)
	{
		lPoint = new ArrayList<PointOfSale>();
		lPoint.add(GenerateurClasse.genererPointOfSale(new PointOfSale(),conf));

	}

	
	@OPERATION
	void displayContent() {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		StringBuffer result= new StringBuffer();
		System.out.println("DEBUG");
		for(PointOfSale point : lPoint)
		{
			if(point==null)
				System.out.println("NULL");
			result.append(point.toString());
		}
		return result.toString();
	}
	
	@OPERATION
	void carAvailable() {
		int ite=0;
		System.out.println(lPoint.size());
		while(pointFound == null && ite < lPoint.size())
		{
			if(lPoint.get(ite).carAvailable())
				pointFound=lPoint.get(ite);
			
			ite++;
		}
		if(pointFound != null)
			signal("decisionAvailable", true);
		else
			signal("decisionAvailable", false);
	}
	
	@OPERATION
	List<Voiture> getCarAvailable(Contrainte Constraint,
			OpFeedbackParam<List<Voiture>> list) {
		List<Voiture> lResult = new ArrayList<Voiture>();
		lResult=pointFound.getCarAvailable(Constraint, list);
		list.set(lResult);
		return lResult;
	}

	public Administration getConfig() {
		return config;
	}

	@OPERATION
	public void setConfig(Administration config) {
		this.config = config;
	}
	
	@OPERATION
	public void addPointOfSale(PointOfSale n){
		lPoint.add(n);
	}
	
}

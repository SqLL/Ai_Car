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

	/**
	 * List of sales point of a company
	 */
	private List<PointOfSale> lPoint=new ArrayList<PointOfSale>();
	private PointOfSale pointFound;
	private Administration config;
	private String name;



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
	
	void init(Administration conf, Contrainte request)
	{
		name=request.getNameCompany();
		lPoint = new ArrayList<PointOfSale>();
		lPoint.add(GenerateurClasse.genererPointOfSale(new PointOfSale(),conf,request.getNamePointsOfSales()));
	}

	
	@OPERATION
	void displayContent() {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		StringBuffer result= new StringBuffer("Company : "+this.name+ System.getProperty("line.separator"));
		for(PointOfSale point : lPoint)
		{
			result.append(point.toString());
		}
		return result.toString();
	}
	
	@OPERATION
	void carAvailable() {
		int ite=0;
		//System.out.println(lPoint.size());
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
	
	public List<PointOfSale> getlPoint() {
		return lPoint;
	}

	public void setlPoint(List<PointOfSale> lPoint) {
		this.lPoint = lPoint;
	}

	public PointOfSale getPointFound() {
		return pointFound;
	}

	public void setPointFound(PointOfSale pointFound) {
		this.pointFound = pointFound;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OPERATION
	public void getCompany(OpFeedbackParam<Company> _company)
	{
		_company.set(this);
	}
	
}

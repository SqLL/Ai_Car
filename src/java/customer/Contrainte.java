package customer;

import java.util.Date;



import cartago.Artifact;




/**
 * 
 * @author squall
 *
 * This class represent a request of the client
 *  What he want and what you need when you try to rent a car
 *
 */
public class Contrainte extends Artifact {
	
	/*
	 * Here you can add variable if you want to more specify the request
	 */
	Date date_depart = new Date();
	Date date_arriver = new Date();
	String point_depart;
	String point_arriver;
	String typeVoiture=new String();
	
	void Init() {

	}

	/*
	 * Getter and Setter
	 */
	
	public Date getDate_depart() {
		return date_depart;
	}

	public void setDate_depart(Date date_depart) {
		this.date_depart = date_depart;
	}

	public Date getDate_arriver() {
		return date_arriver;
	}

	public void setDate_arriver(Date date_arriver) {
		this.date_arriver = date_arriver;
	}

	public String getPoint_depart() {
		return point_depart;
	}

	public void setPoint_depart(String point_depart) {
		this.point_depart = point_depart;
	}

	public String getPoint_arriver() {
		return point_arriver;
	}

	public void setPoint_arriver(String point_arriver) {
		this.point_arriver = point_arriver;
	}

	public String getTypeVoiture() {
		return typeVoiture;
	}

	public void setTypeVoiture(String typeVoiture) {
		this.typeVoiture = typeVoiture;
	}
		
}

package seller;


/**
 * This class is member of Decorator Pattern
 * 
 * @author squall
 * @see Voiture
 */

public class Utilitaire extends Voiture {


	public Utilitaire(boolean is_rent_, String _name) {
		this.is_rent = is_rent_;
		setName(_name);
		setIndice(20);
	}
	
	@Override
	public String getType() {
		return "Utilitaire";
	}	

	@Override
	public String toString() {
		return name.toString()+System.getProperty("line.separator")+" Type : "+getType();
	}

	@Override
	public int getIndice() {
		return indice;
	}

}

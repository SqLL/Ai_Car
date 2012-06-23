package seller;

/**
 * 
 * @author squall
 *
 * This class is member of Decorator Pattern
 * 
 * @see Voiture
 */
public class Tourisme extends Voiture {

	public Tourisme(boolean _is_rent, String _name) {
		this.is_rent = _is_rent;
		setName(_name);
		setIndice(30);
	}
	
	@Override
	public String getType() {
		return "Tourisme";
	}
	
	@Override
	public String toString() {
		return name.toString()+System.getProperty("line.separator")+"Type : "+getType();
	}

	@Override
	public int getIndice() {
		return indice;
	}

}

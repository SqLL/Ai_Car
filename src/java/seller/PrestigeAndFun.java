package seller;


/**
 * 
 * @author squall
 *
 * This class is member of Decorator Pattern
 * 
 * @see Voiture
 */
public class PrestigeAndFun extends Voiture {

	public PrestigeAndFun(boolean _is_rent, String _name) {
		this.is_rent = _is_rent;
		setName(_name);
		setIndice(50);
	}

	@Override
	public String getType() {
		return "Prestige and fun";
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

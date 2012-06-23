package seller;


/**
 * 
 * @author squall
 *
 * This class is member of Decorator Pattern
 * 
 * @see Voiture
 */
public class ToutTerrain extends Voiture {

	public ToutTerrain(boolean _is_rent, String _name)
	{
		this.is_rent=_is_rent;
		setName(_name);
		setIndice(55);
	}
	
	@Override
	public String getType() {
		return "Tout terrain";
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

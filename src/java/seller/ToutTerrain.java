package seller;


/**
 * This class is member of Decorator Pattern
 * 
 * @author squall
 * @see Voiture
 */
public class ToutTerrain extends Voiture {

	/**
	@Override
	public void init(Voiture _car) {
		this.is_rent=_car.is_rent;
		setName(_car.name);
		setIndice(_car.getIndice());
	}**/
	
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
		return name.toString()+System.getProperty("line.separator")+" Type : "+getType();
	}

	@Override
	public int getIndice() {
		return indice;
	}

	@Override
	public int changeKey() {
		this.key = (int)(Math.random() * (Integer.MAX_VALUE-0)) + 0;
		return key;
	}
	
	@Override
	public void drive(int _key) {
		if(_key != this.key)
		{
			System.out.println("Wrong Key");
		}
		else
		{
			System.out.println("Right Key");
		}
		
	}
}

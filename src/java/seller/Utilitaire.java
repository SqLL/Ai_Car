package seller;


/**
 * This class is member of Decorator Pattern
 * 
 * @author squall
 * @see Voiture
 */

public class Utilitaire extends Voiture {

	/**
	void init()
	{
		System.out.println("test");
	}
	
	
	@Override
	public void init(Voiture _car) {
		this.is_rent=_car.is_rent;
		setName(_car.name);
		setIndice(_car.getIndice());
	}
	**/
	
	public Utilitaire(boolean is_rent_, String _name) {
		super();
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

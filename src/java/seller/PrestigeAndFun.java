package seller;


/**
 * This class is member of Decorator Pattern
 * 
 * @author squall
 * @see Voiture
 */

public class PrestigeAndFun extends Voiture {
/**
	@Override
	public void init(Voiture _car) {
		this.is_rent=_car.is_rent;
		setName(_car.name);
		setIndice(_car.getIndice());
	}**/
	
	public PrestigeAndFun(boolean _is_rent, String _name) {
		this.is_rent = _is_rent;
		setName(_name);
		setIndice(50);
		this.counter = (int)(Math.random() * (100000-0)) + 0;
	}
	
	public PrestigeAndFun(boolean is_rent_, String _name,int _masterkey)
	{
		this(is_rent_,_name);
		this.masterkey=_masterkey;			
	}
	
	public int getCounter()
	{
		return this.counter;
	}

	@Override
	public String getType() {
		return "Prestige and Fun";
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
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name.toString();
	}
	
	@Override
	public String getClassCar() {
		if (this.getIndice() <= 100 && this.getIndice() >= 90) {
			return "A";
		} else if (this.getIndice() >= 50 && this.getIndice() <= 89) {
			return "B";
		} else {

			return "C";
		}
	}
	
	@Override
	public int getMasterKey() {
		// TODO Auto-generated method stub
		return this.masterkey;
	}

}

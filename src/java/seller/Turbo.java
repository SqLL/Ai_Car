package seller;
/**
 * This class is member of Decorator Pattern, that is an option decorator of a car
 * @author squall
 * @see Voiture
 */
public class Turbo extends DecorateurVoiture {
/**
	@Override
	public void init(Voiture _car) {
		this.is_rent=_car.is_rent;
		setName(_car.name);
		setIndice(_car.getIndice());
	}**/
	
	public Turbo(Voiture v)
	{
		this.voiture=v;
	}
	
	@Override
	public int getIndice() {
		return this.voiture.getIndice()+10;
	}

	@Override
	public String toString() {
		return this.voiture.toString()+System.getProperty("line.separator")+" Option : Turbo";
	}

	public boolean is_Rent()
	{
		return voiture.is_Rent();
	}

	@Override
	public String getType() {
		return this.voiture.getType();
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

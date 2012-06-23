package seller;
/**
 * 
 * @author squall
 *
 * This class is member of Decorator Pattern, that is an option decorator of a car
 * 
 * 
 * @see Voiture
 */
public class Turbo extends DecorateurVoiture {

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
		return this.voiture.toString()+System.getProperty("line.separator")+"Option : Turbo";
	}

	public boolean is_Rent()
	{
		return voiture.is_Rent();
	}

	@Override
	public String getType() {
		return this.voiture.getType();
	}
}

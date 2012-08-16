package seller;

import cartago.Artifact;


/**
 *	This is the abstract class it's also the high lvl class in Decorator pattern
 * 
 * @author squall
 *
 */
public abstract class Voiture extends Artifact {

	protected boolean is_rent;
	protected int key;// Key electronic
	protected int indice;
	protected String name;
	protected int counter; // Counter kilometers
	
	public void setIndice(int value)
	{
		indice=value;
	}
	public void setName(String nname)
	{
		name=nname;
	}
	
	public boolean is_Rent()
	{
		return is_rent;
	}
	
	
	
	public abstract String getType();
	public abstract String toString();
	public abstract int getIndice();
	public abstract int changeKey();
	public abstract void drive(int _key);
	
}

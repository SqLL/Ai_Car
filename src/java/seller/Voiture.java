package seller;

import cartago.Artifact;


/**
 * 
 * @author squall
 *
 *	This is the abstract class it's also the high lvl class in Decorator pattern
 */
public abstract class Voiture extends Artifact {

	protected boolean is_rent;
	protected int indice;
	protected String name;
	
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
	

}

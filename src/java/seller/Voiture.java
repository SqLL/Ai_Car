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
	protected int masterkey;
	
	
	private long fuel;
	private long mileage;
	private long dirtyness;
	private String currentLocation;
	private int tankCapacity = 100;
	private boolean malfunction=false;
	private boolean damage=false;
	
	
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
	public abstract int getCounter();
	public abstract int changeKey();
	public abstract String getClassCar();
	public abstract void drive(int _key);
	public abstract String getName();
	public abstract int getMasterKey();
	
	public long getFuel() {
		return fuel;
	}
	public void setFuel(long fuel) {
		this.fuel = fuel;
	}
	public long getMileage() {
		return mileage;
	}
	public void setMileage(long mileage) {
		this.mileage = mileage;
	}
	public long getDirtyness() {
		return dirtyness;
	}
	public void setDirtyness(long dirtyness) {
		this.dirtyness = dirtyness;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public int getTankCapacity() {
		return tankCapacity;
	}
	public void setTankCapacity(int tankCapacity) {
		this.tankCapacity = tankCapacity;
	}
	public boolean isMalfunction() {
		return malfunction;
	}
	public void setMalfunction(boolean malfunction) {
		this.malfunction = malfunction;
	}
	public boolean isDamage() {
		return damage;
	}
	public void setDamage(boolean damage) {
		this.damage = damage;
	}
	
}

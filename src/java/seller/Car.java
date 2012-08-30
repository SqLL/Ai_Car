package seller;


import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;
import cartago.OpFeedbackParam;


/**
 * 
 * @author squall
 *
 * This class is born to use Voiture class this is also a part of wrapper design
 */

public class Car extends Artifact{

	
	
	Voiture car;
    private long startTime;
    private int malfunctionality=10;
    private int damage=100;
    
    /**
     * Départ du chronomètre
     */
    public void start(){
        startTime = System.currentTimeMillis();
    }

    /**
     * Arrêt du chronomètre
     * @return long - temps en nanosecondes écoulé depuis le départ du chrono 
     */
    public long stop() {
        return System.currentTimeMillis() - startTime;
    }
    
	void init()
	{
		
	}
	
	void init(Voiture _car,String _location) 
	{
		car=_car;
		//Here it's to define the observable attribute
		this.car.setFuel(100);
		this.car.setMileage(0);
		this.car.setDirtyness(0);
		this.car.setCurrentLocation(_location);
		this.car.setTankCapacity(100);
		this.car.setMalfunction(false);
		this.car.setDamage(false);		
	}
	
	@OPERATION
	public void drive(int _key)
	{
		start();
		car.drive(_key);
		int random = (int)(Math.random() * (100-0)) + 0;
		if(random < this.malfunctionality)
		{
			this.car.setMalfunction(true);
			signal("malfunction");
		}
		random = (int)(Math.random() * (100-0)) + 0;
		if(random < this.damage)
		{
			this.car.setDamage(true);
			signal("damage");
		}
		update(stop());
	}
	
	@OPERATION
	public int changeKey(OpFeedbackParam<Integer> _key,int _masterKey)
	{
		//System.out.println("_masterKey "+_masterKey+" this.car.getMasterKey()="+this.car.getMasterKey());
		if(_masterKey==this.car.getMasterKey())
		{
			_key.set(car.changeKey());
			return _key.get();
		}
		
		return -1;
	}
	
	@OPERATION
	public void setCar(Voiture _car)
	{
		this.car=_car;
	}
	
	public int getIndice()
	{
		return this.car.getIndice();
	}
	
	@OPERATION
	public void refuel()
	{
	    this.car.setFuel(this.car.getTankCapacity());
	}
	
	
	
	@OPERATION
	public void update(long timeStep)
	{

	    this.car.setFuel(this.car.getFuel() - timeStep);
	    this.car.setMileage(this.car.getMileage() - timeStep);
	    this.car.setDirtyness(this.car.getDirtyness()+(timeStep/10));
	  
	}
}

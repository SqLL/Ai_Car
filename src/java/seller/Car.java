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
		defineObsProperty("fuel",100);
		defineObsProperty("mileage",0);
		defineObsProperty("dirtyness",0);
		defineObsProperty("currentLocation",_location);
		defineObsProperty("tankCapacity",100);
		defineObsProperty("malfunction",false);
		defineObsProperty("damage",false);
		
	}
	
	@OPERATION
	public void drive(int _key)
	{
		start();
		car.drive(_key);
		int random = (int)(Math.random() * (100-0)) + 0;
		if(random < this.malfunctionality)
		{
		    ObsProperty prop = getObsProperty("malfunction");
		    prop.updateValue(true);
			signal("malfunction");
		}
		random = (int)(Math.random() * (100-0)) + 0;
		if(random < this.damage)
		{
		    ObsProperty prop = getObsProperty("damage");
		    prop.updateValue(true);
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
		ObsProperty prop = getObsProperty("fuel");
		ObsProperty tnkCapacity = getObsProperty("tankCapacity");
	    prop.updateValue(tnkCapacity.intValue());
	}
	
	
	
	@OPERATION
	public void update(long timeStep)
	{
	    ObsProperty prop = getObsProperty("fuel");
	    prop.updateValue(prop.intValue()-timeStep);
	    prop = getObsProperty("mileage");
	    prop.updateValue(prop.intValue()+timeStep);
	    prop = getObsProperty("dirtyness"); 
	    prop.updateValue(prop.intValue()+(timeStep/10));
	}
}

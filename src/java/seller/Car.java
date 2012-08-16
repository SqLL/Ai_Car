package seller;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;


/**
 * 
 * @author squall
 *
 * This class is born to use Voiture class this is also a part of wrapper design
 */

public class Car extends Artifact{

	Voiture car;
	
	void init()
	{
		
	}
	
	void init(Voiture _car) 
	{
		car=_car;
	}
	
	@OPERATION
	public void drive(int _key)
	{
		car.drive(_key);
	}
	
	@OPERATION
	public int changeKey(OpFeedbackParam<Integer> _key)
	{
		_key.set(car.changeKey());
		return _key.get();
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
}

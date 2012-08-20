package justice;

import java.util.ArrayList;


/**
 * 
 * This class represent the sanction with the different state of violations and her penalty
 * 
 * @author squall
 *
 */
public class Sanction {

	ArrayList<String> state;
	ArrayList<Integer> fee;
	
	Sanction(String _state,int _fee)
	{
		this.fee=new ArrayList<Integer>();
		this.state=new ArrayList<String>();
		this.fee.add(_fee);
		this.state.add(_state);
	}
	
	public void AddOne(String _state,int _fee)
	{
		this.fee.add(_fee);
		this.state.add(_state);
	}
	
}

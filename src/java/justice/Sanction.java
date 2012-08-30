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
	ArrayList<String> fee;
	
	Sanction(String _state,String _fee)
	{
		this.fee=new ArrayList<String>();
		this.state=new ArrayList<String>();
		this.fee.add(_fee);
		this.state.add(_state);
	}
	Sanction()
	{
		this.fee=new ArrayList<String>();
		this.state=new ArrayList<String>();	
	}
	
	public void AddOne(String _state,String _fee)
	{
		this.fee.add(_fee);
		this.state.add(_state);
	}
	
	public String toString()
	{
		StringBuffer content=new StringBuffer();
		for(int i=0,j=0;i<fee.size() && j <state.size()  ;i++,j++)
			content.append(fee.get(i).toString()+" "+this.state.get(j).toString()+System.getProperty("line.separator"));
		return content.toString();
	}
	
	public ArrayList<String> getState() {
		return state;
	}
	public void setState(ArrayList<String> state) {
		this.state = state;
	}
	public ArrayList<String> getFee() {
		return fee;
	}
	public void setFee(ArrayList<String> fee) {
		this.fee = fee;
	}
}

package justice;

import java.util.ArrayList;

/**
 * 
 * This class represent the norms like the database
 * 
 * 
 * @author squall
 *
 */
public class Norms {
/**
 * Checkpoint is a part of norms and that is a point when you have to check the norms.
 * 
 * @author squall
 *
 */
public enum Pertinence {
   A(0),B(1), C(2);

   protected int value;

   /** Constructeur */
   Pertinence(int num) {
      this.value = num;
   }
   
   Pertinence(String pert)
   {
	   if(pert.equals("A"))
	   {
		   this.value=0;
	   }
		   else if(pert.equals("B"))
	   {
		   this.value=1;
	   }
	   else
		   this.value=2;
   }
  
   public String toString()
   {
	   if(this.value==0)
	   return "A";
	   else if(this.value==1)
		   return "B";
	   else
		   return "C";
   }

   public void change(){
	   this.value++;
   }
   
   public int getValue() {
      return this.value;
   }
   
}

	String target;
	String evaluator;
	String content;
	int operator;
	ArrayList<Pertinence> Checkpoints;
	Sanction Sanctions;
	
	/**
	 * First constructor without checkpoint
	 * @param _target
	 * @param _evaluator
	 */
	Norms(String _target,String _evaluator,String _content,String operator)
	{
		this.target=_target;
		this.evaluator=_evaluator;
		this.content=_content;
		if(operator.equals("obl"))
			this.operator=0;
		else
			this.operator=1;
		Checkpoints=new ArrayList<Pertinence>();
		Sanctions=new Sanction();
	}
	
	/**
	 * Second constructor with all informations you need
	 * @param _target
	 * @param _evaluator
	 * @param _s
	 * @param _c
	 */
	Norms(String _target,String _evaluator,Sanction _s,Pertinence _c)
	{
		this.target=_target;
		this.evaluator=_evaluator;
		Checkpoints=new ArrayList<Pertinence>();
		Sanctions=_s;
		Checkpoints.add(_c);
	}
	
	/**
	 * This function is because a norms can have more than one state with fee
	 * @param _state
	 * @param _fee
	 */
	void addSanction(String _state,String _fee)
	{
		Sanctions.AddOne(_state, _fee);
	}
	/**
	 * Some norms can be apply at many checkpoint
	 * @param _state
	 * @param _fee
	 */
	void addCheckpoint(Pertinence c)
	{
		Checkpoints.add(c);
	}
	
	public String toString()
	{
		StringBuffer content = new StringBuffer("Norm : "+this.operator+" "+this.evaluator.toString()+" "+this.target.toString()+" "+this.content.toString());
		for(Pertinence check : Checkpoints)
		{
			content.append(check.toString());
		}
		content.append(System.getProperty("line.separator"));
		content.append("	Sanction : "+Sanctions.toString());
		
		return content.toString();
	}
	
}

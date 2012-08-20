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
public enum Checkpoint {
   A(0),B(1), C(2);

   protected int value;

   /** Constructeur */
   Checkpoint(int num) {
      this.value = num;
   }

   public void change(){
	   this.value++;
   }
   
   public int getValue() {
      return this.value;
   }
}

	String target;
	int evaluator;
	ArrayList<Checkpoint> Checkpoints;
	Sanction Sanctions;
	
	/**
	 * First constructor without checkpoint
	 * @param _target
	 * @param _evaluator
	 */
	Norms(String _target,int _evaluator)
	{
		this.target=_target;
		this.evaluator=_evaluator;
		Checkpoints=new ArrayList<Checkpoint>();
	}
	
	/**
	 * Second constructor with all informations you need
	 * @param _target
	 * @param _evaluator
	 * @param _s
	 * @param _c
	 */
	Norms(String _target,int _evaluator,Sanction _s,Checkpoint _c)
	{
		this.target=_target;
		this.evaluator=_evaluator;
		Checkpoints=new ArrayList<Checkpoint>();
		Sanctions=_s;
		Checkpoints.add(_c);
	}
	
	/**
	 * This function is because a norms can have more than one state with fee
	 * @param _state
	 * @param _fee
	 */
	void addSanction(String _state,int _fee)
	{
		Sanctions.AddOne(_state, _fee);
	}
	/**
	 * Some norms can be apply at many checkpoint
	 * @param _state
	 * @param _fee
	 */
	void addCheckpoint(Checkpoint c)
	{
		Checkpoints.add(c);
	}
	
	
}

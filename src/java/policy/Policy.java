package policy;

/**
 *  this class represent the policy especially the estimation of the price.It is different and it's depend from company
 *  So in this class i use State Pattern
 * 
 * @author squall
 *
 */
public class Policy {

	CompanyState state;

	public CompanyState getState() {
		return state;
	}

	public void setState(String company) {
		if (company.equals("RandomCar")) {
			setState(new RandomCar());
		} else if (company.equals("EuropCar")) {
			setState(new EuropCar());
		} else if (company.equals("RentaCar")) {
			setState(new RentaCar());
		} else {
			setState(new FrogCar());
		}
	}
	
	public void setState(CompanyState _state)
	{
		this.state=_state;
	}
	
	public int calculPrice()
	{
		return this.state.calculPrice();
	}
}

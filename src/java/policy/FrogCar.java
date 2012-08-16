package policy;

public class FrogCar extends CompanyState {

	@Override
	int calculPrice(int indice, int estimationKilometers) {
		// TODO Auto-generated method stub
		return indice*10+estimationKilometers;
	}

}

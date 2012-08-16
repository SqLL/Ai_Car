package policy;


public class EuropCar extends CompanyState {

	@Override
	int calculPrice(int indice, int estimationKilometers) {
		// TODO Auto-generated method stub
		return indice*2+estimationKilometers*1;
	}

}

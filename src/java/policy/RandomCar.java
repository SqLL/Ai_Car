package policy;

public class RandomCar extends CompanyState {

	@Override
	int calculPrice(int indice,int estimationKilometers) {
		// TODO Auto-generated method stub
		return indice+50+estimationKilometers;
	}

}

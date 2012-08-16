package policy;

public class RentaCar extends CompanyState {

	@Override
	int calculPrice(int indice,int estimationKilometers) {
		// TODO Auto-generated method stub
		return indice+200;
	}

}

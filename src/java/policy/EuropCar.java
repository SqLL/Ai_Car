package policy;


public class EuropCar extends CompanyState {

	@Override
	int calculPrice(int indice) {
		// TODO Auto-generated method stub
		return indice*2+200;
	}

}

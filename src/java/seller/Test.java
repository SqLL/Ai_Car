package seller;

public class Test {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * Test Decorateur
	 */
	/**
	public static void testDecorateur()
	{
		Company entreprise = new Company("RentCar");
		entreprise.addVoiture(new Turbo(new Ferrari()));
		entreprise.addVoiture(new PneusNeige(new Twingo()));
		System.out.println(entreprise.toString());
	}

	public static void testGenerateur()
	{
		Company result=GenerateurClasse.genererCompany(4, "Roumania Pro");
		System.out.println(result.toString());
	}
	**/
}

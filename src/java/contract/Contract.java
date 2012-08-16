package contract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import policy.Policy;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

import seller.Company;
import seller.Voiture;

import customer.Contrainte;

/**
 * 
 * @author squall
 *
 * Contract for a client and a seller
 */
public class Contract extends Artifact {

	private Contrainte request;
	private Company company;
	private List<Voiture> lVoiture;
	private Voiture carChoosen;
	private Policy policy;
	private int penality;
	String titre = "Respect Project";

	/**
	 * To make contract we need all the informations name of the company how she
	 * will make the price, the car , the name of the client
	 * 
	 * @param request
	 *            Informations client
	 * @param company
	 *            Informations company and sales points
	 * @param list
	 *            Liste car available and match with the request of the client
	 */
	void init(Contrainte _request, Company _company, List<Voiture> _lVoiture) {
		request = _request;
		company = _company;
		lVoiture = _lVoiture;
		policy = new Policy(company.getName());
	}

	@OPERATION
	void makeContract() {
		File target = new File(
				"/Users/squall/Dropbox/Stage/Documents/tmp/contrat.tex");
		for (int i = lVoiture.size() - 1; i >= 0; i--) {
			File dst = new File(target.getParent() + File.separatorChar + i
					+ target.getName());
			try {
				Parse(target, dst, i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Execute(dst);
		}
	}

	/**
	 * 
	 * 
	 * @param target
	 *            File to modify
	 * @param i
	 *            Iterator of which car i use it's also used to rename the file @see
	 *            makeContract()
	 * @throws IOException
	 *             Exception when opening the file
	 */
	public void Parse(File target, File dst, int i) throws IOException {
		PrintWriter ecri = null;
		BufferedReader lecteurAvecBuffer = null;
		String ligne;

		// Compile regular expression
		String patternStr = ".*newcommand.*";// {\titre}{titre}";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher("r");

		try {
			lecteurAvecBuffer = new BufferedReader(new FileReader(target));
			ecri = new PrintWriter(new FileWriter(dst));
		} catch (FileNotFoundException exc) {
			System.out.println("Error when opening file");
		}
		while ((ligne = lecteurAvecBuffer.readLine()) != null) {
			System.out.println(ligne);
			matcher.reset(ligne);
			if (matcher.matches()) {
				ligne = Replace(ligne, i);
			}
			ecri.print(ligne + System.getProperty("line.separator"));
		}
		lecteurAvecBuffer.close();
		ecri.flush();
		ecri.close();
	}

	/**
	 * this function for example will extract the substring between the first {
	 * } to identify the string and to replace by the value into the last { }
	 * 
	 * @param ligne
	 *            is the string who need to be modify to personalize the
	 *            contract
	 * @return the new string
	 */
	protected String Replace(String ligne, int i) {
		// Here we have to extract the information about the line
		StringBuffer toBeAnalyzed = new StringBuffer(ligne);
		String idRepair = toBeAnalyzed.substring(toBeAnalyzed.indexOf("{") + 2,
				toBeAnalyzed.indexOf("}"));
		String content = new String();

		/*
		 * Here is the if with all possibilities
		 */
		if (idRepair.equals("titre")) {
			content = titre;
		} else if (idRepair.equals("company")) {
			content = company.getName();
		} else if (idRepair.equals("localisation")) {
			content = company.getPointFound().getLocal();
		} else if (idRepair.equals("customername")) {
			content = request.getNameCustomer();
		} else if (idRepair.equals("price")) {
			content = policy.calculPrice(lVoiture.get(i).getIndice(),this.request.getEstimationKilometers()) + " Euro";
		}

		/*
		 * replace the string between the second { }
		 */
		toBeAnalyzed.replace(toBeAnalyzed.lastIndexOf("{") + 1,
				toBeAnalyzed.lastIndexOf("}"), content);

		return toBeAnalyzed.toString();
	}

	/**
	 * Determine the compilation line from your os
	 * 
	 * @param target
	 *            is the name of the new files
	 */
	public void Execute(File target) {

		String latexCmd = new String();
		/*
		 * To determine the correct line to compile the file i need to know with
		 * operating system the program is launch
		 */
		if (System.getProperty("os.name").matches(".*Mac.*")) {
			latexCmd = "/usr/texbin/pdflatex " + target.getName();
		}

		File base = new File(target.getParent());
		String cmd[] = { "bash", "-c", latexCmd };

		try {
			Process proc = Runtime.getRuntime().exec(cmd, null, base);
			InputStream in = proc.getInputStream();

			StringBuilder build = new StringBuilder();
			char c = (char) in.read();

			while (c != (char) -1) {
				build.append(c);
				c = (char) in.read();
			}

			String response = build.toString();

			// on l'affiche
			System.out.println(response);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 
	 * @param finalPrice is the price for the client it's use as out/in parameter for Jason
	 */
	@OPERATION
	public void calculPrice(OpFeedbackParam<Integer> finalPrice)
	{
		//Initialisations
		//First part here can be to check the norms and add some sanctions.
	
		//Second Part for the moment the price is calculated with the estimation parameters
		finalPrice.set(policy.calculPrice(this.carChoosen.getIndice(),this.request.getEstimationKilometers()));
	
		
	}

	public Voiture getCarChoosen() {
		return carChoosen;
	}

	@OPERATION
	public void setCarChoosen(Voiture carChoosen) {
		this.carChoosen = carChoosen;
	}
}

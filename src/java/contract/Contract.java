package contract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import configuration.Configuration;

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
 *         Contract for a client and a seller
 */
public class Contract extends Artifact {

	private Contrainte request;
	private Company company;
	private List<Voiture> lVoiture;
	private List<Float> lPrices;
	private List<File> lFiles;
	private Voiture carChoosen;
	private Policy policy;
	private Long numberday;
	private int numberweekendday;
	private int numberotherday;
	
	// private int penality;
	String titre = "Rental Car Contract";
	String bold = "\textbf{";

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
		
		// Here it's to calcul the number of day total
		long diff =this.request.getDate_arriver().getTime() - this.request.getDate_depart().getTime();
		Long numberday=new Long(diff / (24 * 60 * 60 * 1000));
		Calendar calendar1=Calendar.getInstance();
		calendar1.setTime(this.request.getDate_depart());
		
		numberweekendday=0;
		numberotherday=0;
		for(int i=1;i<=numberday;i++){
            if(calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
            {
            	numberotherday++;
            }
            else
            {
            	numberweekendday++;
            }
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
        }
		
		this.lFiles=new ArrayList<File>();
		this.lPrices=new ArrayList<Float>();
		System.out.println("numberweekendday= "+numberweekendday+" numberotherday="+numberotherday);
	}

	@OPERATION
	void makeContract() {
		File target = new File(Configuration.latexModel);
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
			File dstPdf=new File(dst.getName().replaceFirst(".tex", ".pdf"));
			lFiles.add(dstPdf);
			
			this.lPrices.add(new Float(this.policy.calculPrice(this.lVoiture.get(i).getClassCar(), this.numberotherday, this.numberweekendday)));
			
		}	
			System.out.println(lFiles.toString());
		
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
		 * Translation from the word to the value
		 */

		content=Translation(idRepair, i);
		
		
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
			//System.out.println(response);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * @param finalPrice
	 *            is the price for the client it's use as out/in parameter for
	 *            Jason
	 */
	@OPERATION
	public void calculPrice(OpFeedbackParam<Float> finalPrice) {
		// Initialisations
		// First part here can be to check the norms and add some sanctions.
		
		
		// Second Part for the moment the price is calculated with the
		// estimation parameters
		finalPrice.set(policy.calculPrice(this.carChoosen.getClassCar(),
				this.numberotherday,this.numberweekendday));
	}

	public String putBold(String toPutInBold) {
		return bold + toPutInBold + "}";
	}


	public String Translation(String idRepair, int i) {
		String content = new String();
		if (idRepair.equals("titre")) {
			content = titre;
		}
		// Section General Informations
		else if (idRepair.equals("currentday")) {
			content = Calendar.getInstance().get(Calendar.DATE)+" ";
		} else if (idRepair.equals("currentmonth")) {
			content = Calendar.getInstance().get(Calendar.MONTH)+"";
		} else if (idRepair.equals("currentyear")) {
			content = Calendar.getInstance().get(Calendar.YEAR)+"";
		} else if (idRepair.equals("sellpointname")) {
			content = request.getNameCompany()+request.getNamePointsOfSales();
		} else if (idRepair.equals("companyname")) {
			content = request.getNameCompany();
		} else if (idRepair.equals("sellpointaddresscity")) {
			content=request.getNamePointsOfSales();
		}

		// Section Car
		// Proposition de toString pour completer tout ca
		else if (idRepair.equals("cartype")) {
			System.out.println(this.lVoiture.get(i).toString());
			content=this.lVoiture.get(i).toString();
		} else if (idRepair.equals("carmodel")) {
		} else if (idRepair.equals("carbrand")) {
		} else if (idRepair.equals("otheroptions")) {
			
		}

		// Section informations counter
		else if (idRepair.equals("startkilometers")) {
			content=this.lVoiture.get(i).getCounter()+" ";
		}

		// Section rental period
		else if (idRepair.equals("startlocation")) {
			System.out.println(this.request.getPoint_depart().toString());
			content=this.request.getPoint_depart().toString()+" ";
		} else if (idRepair.equals("startdate")) {
			content=this.request.getDate_depart().toString()+" ";
		} else if (idRepair.equals("locationend")) {
			content=this.request.getPoint_arriver().toString()+" ";
		} else if (idRepair.equals("dateend")) {
			content=this.request.getDate_arriver().toString()+" ";
		} else if (idRepair.equals("numberofdays")) {
			 content = numberday+"";
		}

		// Section Insurance
		else if (idRepair.equals("malfunctionpenalty")) {
			content=this.policy.getMalfunctionPenalty()+"";
		}

		// Section Rental Rate
		else if (idRepair.equals("totalrentingprice")) {
			
		} else if (idRepair.equals("priceperweekday")) {
			if(this.lVoiture.get(i).getClassCar()=="A")
			content= this.policy.getPricePerWeekDayA()+"";
			else if(this.lVoiture.get(i).getClassCar()=="B")
			{
				content= this.policy.getPricePerWeekDayB()+"";
			}
			else
			{
				content= this.policy.getPricePerWeekDayC()+"";
			}
			
		} else if (idRepair.equals("priceperweekendday")) {
			if(this.lVoiture.get(i).getClassCar()=="A")
			content= this.policy.getPricePerWeekendDayA()+"";
			else if(this.lVoiture.get(i).getClassCar()=="B")
			{
				content= this.policy.getPricePerWeekendDayB()+"";
			}
			else
			{
				content= this.policy.getPricePerWeekendDayC()+"";
			}
		} else if (idRepair.equals("compositionrule")) {
			content=this.policy.calculPrice(this.lVoiture.get(i).getClassCar(), this.numberotherday, this.numberweekendday)+"";
		} else if (idRepair.equals("additionalgasprice")) {
			content=policy.getAdditionalGasLiterPrice()+" ";
		}

		// Section Deposit
		else if (idRepair.equals("deposit")) {
			content=policy.getDeposit()+" ";
		}

		// Section Return of Vehicle to Company
		else if (idRepair.equals("destinationpenalty")) {
			content=this.policy.getChangeDestinationPenalty()+"";
		} else if (idRepair.equals("additionalkmprice")) {
			content=policy.getAdditionalKmPrice()+" ";
		} else if (idRepair.equals("theftpenaltyfine")) {
			content=policy.getDamagePenalty()+" ";
		} else if (idRepair.equals("returndatepenalty")) {
			
			
		} else if (idRepair.equals("priceperadditionalweekendday")) {
			if(this.lVoiture.get(i).getClassCar()=="A")
			content= this.policy.getPricePerAdditionalWeekendDayA()+"";
			else if(this.lVoiture.get(i).getClassCar()=="B")
			{
				content= this.policy.getPricePerWeekDayB()+"";
			}
			else
			{
				content= this.policy.getPricePerWeekDayC()+"";
			}
		} else if (idRepair.equals("priceperadditionalweekday")) {
			
		} else if (idRepair.equals("maxdays")) {
			content=policy.getMaxAdditionalDays()+" ";
		}

		// Section General Terms
		else if (idRepair.equals("signatureclientkey")) {
			//TODO
			//STEP 1 Agent name 
			//STEP 2 Encryption of part of the pdf
		} else if (idRepair.equals("signaturecompanykey")) {
		}
		return content;

	}

	public Voiture getCarChoosen() {
		return carChoosen;
	}

	@OPERATION
	public void setCarChoosen(Voiture carChoosen) {
		this.carChoosen = carChoosen;
	}
	
	@OPERATION
	List<File> getContractsList(OpFeedbackParam<List<File>> list) {
		list.set(this.lFiles);
		return this.lFiles;
	}
	
	@OPERATION
	List<Float> getCarPrices(OpFeedbackParam<List<Float>> list) {
		list.set(this.lPrices);
		return this.lPrices;
	}
	
	@OPERATION
	public String getLocationDepart(OpFeedbackParam<String> depart )
	{
		depart.set(this.request.getPoint_depart());
		return depart.toString();
	}
}

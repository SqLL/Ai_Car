package policy;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import configuration.Configuration;

/**
 * this class represent the policy especially the estimation of the price.It is
 * different and it's depend from company So in this class i use State Pattern
 * 
 * @author squall
 * 
 */
public class Policy {

	/**
	 * Variable for the connection to the database
	 */
	private String userName = Configuration.userName; // change it to your
														// username
	private String password = Configuration.password; // change it to your
														// password
	private String url = Configuration.url;
	private Connection dataLink;

	/**
	 * Variable Bussinness rules
	 */
	float additionalKmPrice;
	float additionalGasLiterPrice;
	float pricePerWeekDayA;
	float pricePerWeekendDayA;
	float pricePerWeekDayB;
	float pricePerWeekendDayB;
	float pricePerWeekDayC;
	float pricePerWeekendDayC;
	float deposit;
	float damagePenalty;
	float malfunctionPenalty;
	float repairPenalty;
	int maxAdditionalDays;
	float pricePerAdditionalWeekdayA;
	float pricePerAdditionalWeekendDayA;
	float pricePerAdditionalWeekdayB;
	float pricePerAdditionalWeekendDayB;
	float pricePerAdditionalWeekdayC;
	float pricePerAdditionalWeekendDayC;
	float stealingPenalty;
	float changeDestinationPenalty;
	int minimumExperienceYears;

	public Policy(String company) {
		// Here i load the informations from the data base
		dataLink = EtablishedConnection();
		Statement stmt;
		try {
			stmt = (Statement) dataLink.createStatement();
			String requete = "SELECT * FROM BusinessRules WHERE id_company= (SELECT id FROM Company WHERE UPPER(name) LIKE \""+company.toUpperCase()+"\" )";
			ResultSet rs = (ResultSet) stmt.executeQuery(requete);
			while(rs.next())
			{
				//Une ligne a chercher
				this.additionalKmPrice=Float.valueOf(rs.getString("additionalKmPrice").trim()).floatValue(); 		
				this.additionalGasLiterPrice=Float.valueOf(rs.getString("additionalGasLiterPrice").trim()).floatValue();
				this.pricePerWeekDayA=Float.valueOf(rs.getString("pricePerWeekDayA").trim()).floatValue();
				this.pricePerWeekendDayA=Float.valueOf(rs.getString("pricePerWeekendDayA").trim()).floatValue();
				this.pricePerWeekDayB=Float.valueOf(rs.getString("pricePerWeekDayB").trim()).floatValue();
				this.pricePerWeekendDayB=Float.valueOf(rs.getString("pricePerWeekendDayB").trim()).floatValue();
				this.pricePerWeekDayC=Float.valueOf(rs.getString("pricePerWeekDayC").trim()).floatValue();
				this.pricePerWeekendDayC=Float.valueOf(rs.getString("pricePerWeekendDayC").trim()).floatValue();
				this.deposit=Float.valueOf(rs.getString("deposit").trim()).floatValue();
				this.damagePenalty=Float.valueOf(rs.getString("damagePenalty").trim()).floatValue();
				this.malfunctionPenalty=Float.valueOf(rs.getString("malfunctionPenalty").trim()).floatValue();
				this.repairPenalty=Float.valueOf(rs.getString("repairPenalty").trim()).floatValue();
				this.maxAdditionalDays=Integer.valueOf(rs.getString("maxAdditionalDays").trim()).intValue();
				this.pricePerAdditionalWeekdayA=Float.valueOf(rs.getString("pricePerAdditionalWeekdayA").trim()).floatValue();
				this.pricePerAdditionalWeekendDayA=Float.valueOf(rs.getString("pricePerAdditionalWeekendDayA").trim()).floatValue();
				this.pricePerAdditionalWeekdayB=Float.valueOf(rs.getString("pricePerAdditionalWeekdayB").trim()).floatValue();
				this.pricePerAdditionalWeekendDayB=Float.valueOf(rs.getString("pricePerAdditionalWeekendDayB").trim()).floatValue();
				this.pricePerAdditionalWeekdayC=Float.valueOf(rs.getString("pricePerAdditionalWeekdayC").trim()).floatValue();
				this.pricePerAdditionalWeekendDayC=Float.valueOf(rs.getString("pricePerAdditionalWeekendDayC").trim()).floatValue();
				this.stealingPenalty=Float.valueOf(rs.getString("stealingPenalty").trim()).floatValue();
				this.changeDestinationPenalty=Float.valueOf(rs.getString("changeDestinationPenalty").trim()).floatValue();
				this.minimumExperienceYears=Integer.valueOf(rs.getString("minimumExperienceYears").trim()).intValue();
			}
			
		} catch (SQLException f) {
			f.printStackTrace();
		}

		//System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return 
				", additionalKmPrice=" + additionalKmPrice
				+ ", additionalGasLiterPrice=" + additionalGasLiterPrice
				+ ", pricePerWeekDayA=" + pricePerWeekDayA
				+ ", pricePerWeekendDayA=" + pricePerWeekendDayA
				+ ", pricePerWeekDayB=" + pricePerWeekDayB
				+ ", pricePerWeekendDayB=" + pricePerWeekendDayB
				+ ", pricePerWeekDayC=" + pricePerWeekDayC
				+ ", pricePerWeekendDayC=" + pricePerWeekendDayC + ", deposit="
				+ deposit + ", damagePenalty=" + damagePenalty
				+ ", malfunctionPenalty=" + malfunctionPenalty
				+ ", repairPenalty=" + repairPenalty + ", maxAdditionalDays="
				+ maxAdditionalDays + ", pricePerAdditionalWeekdayA="
				+ pricePerAdditionalWeekdayA
				+ ", pricePerAdditionalWeekendDayA="
				+ pricePerAdditionalWeekendDayA
				+ ", pricePerAdditionalWeekdayB=" + pricePerAdditionalWeekdayB
				+ ", pricePerAdditionalWeekendDayB="
				+ pricePerAdditionalWeekendDayB
				+ ", pricePerAdditionalWeekdayC=" + pricePerAdditionalWeekdayC
				+ ", pricePerAdditionalWeekendDayC="
				+ pricePerAdditionalWeekendDayC + ", stealingPenalty="
				+ stealingPenalty + ", changeDestinationPenalty="
				+ changeDestinationPenalty + ", minimumExperienceYears="
				+ minimumExperienceYears + "]";
	}

	/**
	 * 
	 * @return Connection (Link to the dataBase to make statement and execute
	 *         Query)
	 */
	private Connection EtablishedConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = (Connection) DriverManager.getConnection(url, userName,
					password);
			System.out.println("[INFO] Database connection established");

		} catch (Exception e) {
			System.err.println("[INFO] Cannot connect to database server");
		}
		return conn;
	}

	public float calculPrice(String classe, int numberotherday,int numberweekendday) {
		if(classe=="A")
		{
			return this.pricePerWeekDayA*numberotherday+this.pricePerWeekendDayA*numberweekendday;
		}
		else if(classe=="B")
		{
			return this.pricePerWeekDayB*numberotherday+this.pricePerWeekendDayB*numberweekendday;
		}
		else
		{
			return this.pricePerWeekDayC*numberotherday+this.pricePerWeekendDayC*numberweekendday;
		}
		
	}

	public float getAdditionalKmPrice() {
		return additionalKmPrice;
	}

	public float getAdditionalGasLiterPrice() {
		return additionalGasLiterPrice;
	}

	public float getPricePerWeekDayA() {
		return pricePerWeekDayA;
	}

	public float getPricePerWeekendDayA() {
		return pricePerWeekendDayA;
	}

	public float getPricePerWeekDayB() {
		return pricePerWeekDayB;
	}

	public float getPricePerWeekendDayB() {
		return pricePerWeekendDayB;
	}

	public float getPricePerWeekDayC() {
		return pricePerWeekDayC;
	}

	public float getPricePerWeekendDayC() {
		return pricePerWeekendDayC;
	}

	public float getDeposit() {
		return deposit;
	}

	public float getDamagePenalty() {
		return damagePenalty;
	}

	public float getMalfunctionPenalty() {
		return malfunctionPenalty;
	}

	public float getRepairPenalty() {
		return repairPenalty;
	}

	public int getMaxAdditionalDays() {
		return maxAdditionalDays;
	}

	public float getPricePerAdditionalWeekdayA() {
		return pricePerAdditionalWeekdayA;
	}

	public float getPricePerAdditionalWeekendDayA() {
		return pricePerAdditionalWeekendDayA;
	}

	public float getPricePerAdditionalWeekdayB() {
		return pricePerAdditionalWeekdayB;
	}

	public float getPricePerAdditionalWeekendDayB() {
		return pricePerAdditionalWeekendDayB;
	}

	public float getPricePerAdditionalWeekdayC() {
		return pricePerAdditionalWeekdayC;
	}

	public float getPricePerAdditionalWeekendDayC() {
		return pricePerAdditionalWeekendDayC;
	}

	public float getStealingPenalty() {
		return stealingPenalty;
	}

	public float getChangeDestinationPenalty() {
		return changeDestinationPenalty;
	}

	public int getMinimumExperienceYears() {
		return minimumExperienceYears;
	}
	
	
	
}

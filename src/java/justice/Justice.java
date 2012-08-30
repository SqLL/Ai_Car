package justice;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import policy.Policy;
import seller.Voiture;

import justice.Norms.Pertinence;
import cartago.Artifact;
import cartago.OPERATION;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import configuration.Configuration;

/**
 * This class contains the whole list about norms and the void to check the
 * violations or not of the norms
 * 
 * @author squall
 * 
 */
public class Justice extends Artifact {

	ArrayList<Norms> lNorms;
	Pertinence currentPertinencePoint;
	private List<String> lActionCustomer = new ArrayList<String>();
	private List<String> lActionSeller = new ArrayList<String>();
	private Policy policy;
	private Voiture car;

	/**
	 * Variable pour la connexion à la Base de données
	 */
	private String userName = Configuration.userName; // change it to your
														// username
	private String password = Configuration.password; // change it to your
														// password
	private String url = Configuration.url;
	private Connection dataLink;

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

	void init() {

		dataLink = EtablishedConnection();
		currentPertinencePoint = Pertinence.A;
		lNorms = new ArrayList<Norms>();

		// Load of all the norms
		Statement stmt, stmt_two, stmt_three;
		try {

			stmt = (Statement) dataLink.createStatement();
			stmt_two = (Statement) dataLink.createStatement();
			stmt_three = (Statement) dataLink.createStatement();
			String requete = "SELECT * FROM Norms";

			ResultSet rs = (ResultSet) stmt.executeQuery(requete);

			Integer current_norms = new Integer(0);
			Integer current_id_norms;

			while (rs.next()) {
				// First make the norms
				current_id_norms = new Integer((rs.getString("id")));
				this.lNorms.add(new Norms(rs.getString("target"), rs
						.getString("evaluator"), rs.getString("content"), rs
						.getString("operator")));

				// Sanction field

				String requete_sanctions = "SELECT * FROM Sanction WHERE id_norms="
						+ current_id_norms.intValue();
				ResultSet rssanction = (ResultSet) stmt_two
						.executeQuery(requete_sanctions);
				while (rssanction.next()) {
					lNorms.get(current_norms).addSanction(
							rssanction.getString("state"),
							rssanction.getString("fee"));

				}

				// Checkpoint field

				String requete_pertinence = "SELECT * FROM Pertinence WHERE id_norms="
						+ current_id_norms;
				ResultSet rspertinence = (ResultSet) stmt_three
						.executeQuery(requete_pertinence);
				while (rspertinence.next()) {
					if (rspertinence.getString("pertinence").equals("A")) {
						lNorms.get(current_norms).setPertinence(Pertinence.A);
					} else if (rspertinence.getString("pertinence").equals("B")) {
						lNorms.get(current_norms).setPertinence(Pertinence.B);
					} else
						lNorms.get(current_norms).setPertinence(Pertinence.C);
				}
				current_norms++;
			}
		} catch (SQLException f) {
			f.printStackTrace();
		}
		// System.out.println("CHARGEMENT TERMINER");
		// System.out.println(this.toString());
	}

	@OPERATION
	public void checkPoint() {
		// Here we have to check all the norms with the currentCheckpoint
		System.out.println("CHECKPOINT "
				+ this.currentPertinencePoint.toString());

		for (Norms norm : lNorms) {
			if (this.currentPertinencePoint == Pertinence.A) {
				if (norm.pertinence == Pertinence.A) {
					if (norm.operator == 0) // 0=Obligation
					{
						if (norm.target.equals("client")) // determine le client
						{
							if (!(this.lActionCustomer.contains(norm.content))) {
								Sanction sanction = norm.getSanctions();
								for (int i = 0; i < sanction.getState().size(); i++) {
									if (verification(sanction.getState().get(i)) == true) {
										// Lancer l'application de la norm
										System.out.println("Application Norms");
									}
								}
							}
						} else if (norm.target.equals("company")) {

						}
					}
				}
			} else if (this.currentPertinencePoint == Pertinence.B) {
				if (norm.pertinence == Pertinence.B) {
					if (norm.operator == 0) // 0=Obligation
					{
						if (norm.target.equals("client")) // determine le client
						{
							if (!(this.lActionCustomer.contains(norm.content))) {
								Sanction sanction = norm.getSanctions();
								for (int i = 0; i < sanction.getState().size(); i++) {
									if (verification(sanction.getState().get(i)) == true) {
										// Lancer l'application de la norm
										System.out.println("Application Norms");
									}
								}
							}
						} else if (norm.target.equals("company")) {

						}
					}
				}
			}

			else {// NOT A NOT B
				if (norm.operator == 0) // 0=Obligation
				{
					if (norm.target.equals("client")) // determine le client
					{
						if (!(this.lActionCustomer.contains(norm.content))) {
							Sanction sanction = norm.getSanctions();
							for (int i = 0; i < sanction.getState().size(); i++) {
								if (verification(sanction.getState().get(i)) == true) {
									// Lancer l'application de la norm
									System.out.println("Application Norms");
								}
							}
						}
					} else if (norm.target.equals("company")) {

					}
				}
			}
		}

		this.currentPertinencePoint.change();
	}

	public String toString() {
		StringBuffer content = new StringBuffer();
		for (Norms n : this.lNorms) {
			content.append(n.toString());
		}
		return content.toString();
	}

	@OPERATION
	public void setlActionCustomer(List<String> _lActionCustomer) {
		for (String action : _lActionCustomer) {
			lActionCustomer.add(action);
		}
		System.out.println(lActionCustomer.toString());
	}

	@OPERATION
	public void setlActionSeller(List<String> _lActionSeller) {
		for (String action : _lActionSeller) {
			lActionSeller.add(action);
		}
	}

	public boolean verification(String state) {

		return true;
	}

	public Policy getPolicy() {
		return policy;
	}

	@OPERATION
	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public Voiture getCar() {
		return car;
	}

	@OPERATION
	public void setCar(Voiture car) {
		this.car = car;
		System.out.println("VOITURE COURANTE" + this.car.toString());
	}
}

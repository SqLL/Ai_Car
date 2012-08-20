package justice;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import justice.Norms.Checkpoint;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;


/**
 * This class contains the whole list about norms and the void to check the violations or not of the norms
 * 
 * @author squall
 *
 */
public class Justice {

	ArrayList<Norms> lNorms;
	Checkpoint currentCheckpoint;
	
	
	/**
	 * Variable pour la connexion à la Base de données
	 */
	private String userName = "root"; // change it to your username
	private String password = "root"; // change it to your password
	private String url = "jdbc:mysql://localhost:3306/AI_Data";
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
	
	public Justice()
	{
		EtablishedConnection();
		currentCheckpoint=Checkpoint.A;
		
		//Load of all the norms
		Statement stmt;
		try {
			
			stmt = (Statement) dataLink.createStatement();
			String requete = "SELECT * FROM Norms";
			/**
			 * SELECT * from Norms N
				INNER JOIN Sanction S on S.id_norms=N.id
				INNER JOIN Checkpoint C on C.id_norms=N.id
			 */
			ResultSet rs = (ResultSet) stmt.executeQuery(requete);
			int previous_id_norms;
			int previous_id_sanction;
			int previous_id_checkpoint;
/**
			while (rs.next()) {
				if(previous_id_norms!=rs.getString("id"))
				{
					//You have to build a norms with sanctions and fee
					lNorms.add(new Norms());
					
				}
				else
				{
				//Here you have to add some informations about the last norms on checkpoint sanctions or something
					if(previous_id_norms != rs.getString("id_Checkpoint"))			
					lNorms.last().getSanction().addOne(rs.getString("fee"),rs.getString("state");
					if(previous_id_norms != rs.getString("id_Sanctions"))	
					lNorms.last().getCheckpoint().addOne(rs.getString("Checkpoint");
				}
			}
**/
		} catch (SQLException f) {
			f.printStackTrace();
		}
	}
	
	public void Checkpoint()
	{
		//Here we have to check all the norms with the currentCheckpoint
		
		
	}
}

package customer;


import java.sql.DriverManager;
import java.sql.Statement;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;




public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        Connection conn = null;
        try
        {
            String userName = "root"; //change it to your username
            String password = "root"; //change it to your password
            String url = "jdbc:mysql://localhost:3306/AI_Data";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = (Connection) DriverManager.getConnection(url, userName, password);
            System.out.println("Database connection established");
            Statement stmt = conn.createStatement();
            String requete = "SELECT * FROM Company";
            ResultSet rs = (ResultSet) stmt.executeQuery(requete);
            while(rs.next()){
            System.out.println(rs.getString("Name"));
            }
            
        }
        catch (Exception e)
        {
            System.err.println("Cannot connect to database server");
        }
	}

}

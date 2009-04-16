//Bret Lowrey and Danny Liu
//Done for phase 1 of CS122B, Jacobson
//University of California Irvine, Spring 2009
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DirectorFix
{
	public static void main(String[] arg) throws Exception
	{

		// Incorporate postgreSQL driver
		Class.forName("org.postgresql.Driver").newInstance();

		// Connect to the test database
		Connection connection =
			DriverManager.getConnection("jdbc:postgresql://localhost/fabflixs", "testuser", "testpass");
		
		Statement select = connection.createStatement();
		select.executeUpdate("ALTER TABLE movies ADD director_first_name varchar(50) DEFAULT 'None'");

		Statement select1 = connection.createStatement();
		ResultSet result = select1.executeQuery("SELECT * FROM movies");
		while(result.next())
		{
			String[] split = result.getString("director_last_name").split(" ");	
			if (split.length>1)
			{
				Statement select2 = connection.createStatement();
				select2.executeUpdate("UPDATE movies SET director_last_name = '" + split[1] + "', " + 
						"director_first_name = '" + split[0] + "' WHERE id ='" +	result.getInt(1) +"'");
			}
		}
		
		System.out.println("Conversion of director's names successful");
	}
}

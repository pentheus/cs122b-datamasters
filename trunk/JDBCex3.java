// JDBC Example - updating a DB via SQL template and value groups
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson
// Slightly revised for CS122B Spring 2007, by Norman Jacobson

import java.sql.*;				// Enable SQL processing

public class JDBCex3
{
	public static void main(String[] arg) throws Exception
	{
		// Incorporate postgreSQL driver
		Class.forName("org.postgresql.Driver").newInstance();

		// Connect to the test database
		Connection connection =
			DriverManager.getConnection("jdbc:postgresql://localhost/moviedb", "testuser", "testpass");

		// prepare SQL statement template that's to be repeatedly executed
		String updateString = "update stars set first_name = ? where id = ?";
		PreparedStatement updateStars = connection.prepareStatement(updateString);

		// values for first and second "?" wildcard in statement template
		int [] ids = {1, 2};
		String [] firstNames = {"tom1", "keanu2"};

		// for each record in table, update to new values given
		for(int i = 0; i < ids.length; i++)
		{
			updateStars.setString(1, firstNames[i]);
			updateStars.setInt(2, ids[i]);
			updateStars.executeUpdate();
		}
	}
}

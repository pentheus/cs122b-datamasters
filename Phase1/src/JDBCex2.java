// JDBC Example - deleting a record
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson
// Slightly revised for CS122B Spring 2007, by Norman Jacobson


import java.sql.*;		// Enable SQL processing

public class JDBCex2
{

	public static void main(String[] arg) throws Exception
	{
		// Incorporate postgreSQL driver
		Class.forName("org.postgresql.Driver").newInstance();

		// Connect to the test database
		Connection connection =
			DriverManager.getConnection("jdbc:postgresql://localhost/moviedb", "testuser", "testpass");

		// create update DB statement -- deleting second record of table; return status
		Statement update = connection.createStatement();
		int retID = update.executeUpdate("delete from stars where id = 2");
		System.out.println("retID = " + retID);
	}
}

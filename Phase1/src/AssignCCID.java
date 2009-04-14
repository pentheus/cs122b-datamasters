import java.sql.*;

public class AssignCCID 
{
	public static void main(String[] arg) throws Exception
	{

		// Incorporate postgreSQL driver
		Class.forName("org.postgresql.Driver").newInstance();

		// Connect to the test database
		Connection connection =
			DriverManager.getConnection("jdbc:postgresql://localhost/fabflixs", "testuser", "testpass");
		
		Statement select = connection.createStatement();
		select.executeUpdate("ALTER TABLE creditcards ADD cc_id integer");

		Statement select1 = connection.createStatement();
		ResultSet result = select1.executeQuery("SELECT * FROM creditcards");
		Integer i = 1;
		while(result.next())
		{
			//if the credit card is either 15 or 16 chars long...
			if(!((result.getString(1).length()>16) || (result.getString(1).length()<15)))
			{
				Statement select2 = connection.createStatement();
				select2.executeUpdate("UPDATE creditcards SET cc_id = " + i + " WHERE credit_card_number ='" +
						result.getString(1) + "' AND name_on_card = '" + result.getString(2) + "'");
				Statement select3 = connection.createStatement();
				ResultSet result3 = select3.executeQuery("SELECT * FROM customers WHERE cc_id = '" + result.getString(1) + "'");
				while(result3.next())
				{
					Statement select4 = connection.createStatement();
					select4.executeUpdate("INSERT INTO customers_temp(id, first_name, last_name, cc_id," +
							"address, email, password) VALUES ('" + result3.getString(1) + "', '" +  result3.getString(2) + "', '" 
							+ result3.getString(3) + "', " + i + ", '"+ result3.getString(5) + "', '" + result3.getString(6) + "', '" 
							+ result3.getString(7) + "')");
				}
				i++;
			}
			else
			{
				Statement select2 = connection.createStatement();
				select2.executeUpdate("UPDATE creditcards SET cc_id = " + i + ",  credit_card_number = 'invalid'" +
						" WHERE credit_card_number ='" + result.getString(1) + "' AND name_on_card = '" + result.getString(2) + "'");
				i++;
			}
			
		}
		Statement select5 = connection.createStatement();
		select5.executeUpdate("DROP TABLE customers");
		
		Statement select6 = connection.createStatement();
		select6.executeUpdate("ALTER TABLE customers_temp RENAME TO customers");
		
		Statement select7 = connection.createStatement();
		select7.executeUpdate("ALTER TABLE creditcards ADD PRIMARY KEY (cc_id)");
		
		Statement select8 = connection.createStatement();
		select8.executeUpdate("ALTER TABLE customers ADD CONSTRAINT cc_id_c foreign key(cc_id) REFERENCES creditcards(cc_id)");
		System.out.println("Conversion of customers successful");
	}
}

package info;

import java.sql.*;

//TODO get regular expression that matches an exact but case insensitive
//TODO make password field display ****
public class Login 
{
	private String loginID;
	private String cameFrom = "cart.jsp";
	private int numberOfTries = 0;
	
	public void setLoginID(String info) {
		this.loginID = info;
	}

	public String getLoginID() {
		return loginID;
	}
	
	public boolean loginExists(String email, String password)
	{
		boolean exists = false;
		
		Connection connection;
		try 
		{
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
		
			Statement select = connection.createStatement();
			ResultSet resultCustomers = select.executeQuery("SELECT * FROM customers WHERE email='" + email+ "' AND password='" + password +"'");
			while(resultCustomers.next())
			{
				exists = true;
			}
			Statement select1 = connection.createStatement();
			ResultSet resultUser = select1.executeQuery("SELECT * FROM users WHERE email='" + email+ "' AND password='" + password +"'");
			while(resultUser.next())
			{
				exists = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return exists;
	}
	
	public boolean loginExists(String email)
	{
		boolean exists = false;
		
		Connection connection;
		try 
		{
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
		
			Statement select = connection.createStatement();
			ResultSet resultCustomers = select.executeQuery("SELECT * FROM customers WHERE email ='" + email+ "'");
			while(resultCustomers.next())
			{
				exists = true;
			}
			Statement select1 = connection.createStatement();
			ResultSet resultUser = select1.executeQuery("SELECT * FROM customers WHERE email ='" + email+ "'");
			while(resultUser.next())
			{
				exists = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return exists;
	}
	
	public void createCustomer(String firstName, String lastName, String email, String password)
	{
		Connection connection;
		try 
		{	
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
			Statement insert = connection.createStatement();
			String execute = "INSERT INTO users VALUES('" + firstName + "', '" + lastName + "', '" + email + "', '" + password + "')";
			insert.executeUpdate(execute);
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
	}

	public void setCameFrom(String cameFrom) {
		this.cameFrom = cameFrom;
	}

	public String getCameFrom() {
		return cameFrom;
	}

	public void incNumberOfTries() {
		this.numberOfTries++;
	}
	
	public void resetNumberOfTries() {
		this.numberOfTries = 0;
	}

	public int getNumberOfTries() {
		return numberOfTries;
	}
}
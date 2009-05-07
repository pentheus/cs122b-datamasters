package info;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Cart {
	private ArrayList<Item> items = new ArrayList<Item>();

	public void addItem(Item item) {
		if(itemExists(item))
			getItem(item.getTitle()).incQuantity();
		else
			items.add(item);
	}

	public ArrayList<Item> getItems() {
		return items;
	}
	
	public void resetCart() {
		items = new ArrayList<Item>();
	}
	
	public boolean itemExists(Item item)
	{
		for(Item i : items)
		{
			if (i.getTitle().equals(item.getTitle()))
			{
				return true;
			}
		}
		return false;
	}
	
	public Item getItem(String title)
	{
		for(Item i : items)
		{
			if (i.getTitle().equals(title))
			{
				return i;
			}
		}
		return null;
	}
	
	public void submitOrder(String customerID, String movieID, String time, String quantity, String shippingAddress )
	{
		Connection connection;
		try 
		{
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
		
			Statement select = connection.createStatement();
			select.executeUpdate("INSERT INTO sales VALUES(" + customerID +"," + movieID + ",'" + time + "'," + quantity + ",'" + shippingAddress + "')");
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}

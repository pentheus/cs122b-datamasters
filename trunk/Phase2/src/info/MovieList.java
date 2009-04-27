package info;

import java.sql.*;

public class MovieList 
{
	private int currentPage = 0;
	public int getNumberOfRecordPages()
	{
		int recordCount = 0;
		
		Connection connection;
		try 
		{
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
		
			Statement select = connection.createStatement();
			ResultSet resultMovies = select.executeQuery("select * from movies");
			while(resultMovies.next())
			{
				recordCount++;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		//return the number rounded up
		return ((recordCount + tools.MovieListGenerator.RECORDS_PER_PAGE - 1) / 
				tools.MovieListGenerator.RECORDS_PER_PAGE);
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void incCurrentPage() {
		currentPage++;
	}
	public void decCurrentPage() {
		currentPage--;
	}
}

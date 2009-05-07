package info;

import java.sql.*;

public class MovieList 
{
	private int currentPage = 0;
	private String sort = "title";
	private String direction = "AtoZ";
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
	
	public int getNumberOfRecordPages(String title, String year, String director, String star)
	{
		int recordCount = 0;
		
		Connection connection;
		try 
		{
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
		
			Statement select = connection.createStatement();
			//TODO
			ResultSet resultMovies = select.executeQuery("select * from movies where title ~* '" + title + "'");
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
	/*<tr>
    	<th>
    		<form action='<%= request.getRequestURI() %>' method="POST">
      		  <input type="SUBMIT" value="Prev">
      		  <input type="HIDDEN" name="prev" value="true">
   		    </form>
   	 	</th>
		<th>
			<form action='<%= request.getRequestURI() %>' method="POST">
        		<input type="SUBMIT" value="Next">
        		<input type="HIDDEN" name="next" value="true">
    		</form>  
    	</th>
    </tr>*/
	public String getPrevButton(String currentPage)
	{
		return 
				"<form action='"+ currentPage +"' method='POST'>\n" +
				"<input type='SUBMIT' value='Prev'>\n" +
				"<input type='HIDDEN' name='prev' value='true'>\n" +
   		        "</form>";
	}
	
	public String getNextButton(String currentPage)
	{
		return 
				"<form action='"+ currentPage +"' method='POST'>\n" +
				"<input type='SUBMIT' value='Next'>\n" +
				"<input type='HIDDEN' name='next' value='true'>\n" +
   		        "</form>";
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDirection() {
		return direction;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getSort() {
		return sort;
	}
}

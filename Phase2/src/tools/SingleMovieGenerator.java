//This will create a page to be imbeded about a single movie
package tools;

import java.sql.*;

public class SingleMovieGenerator 
{
	
	public static String getMovie(String id)
	{
		String html = "";
		Connection connection;
		try 
		{
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
		
			Statement select = connection.createStatement();
			ResultSet resultMovie = select.executeQuery("select * from movies where id=" + id + "");
			while(resultMovie.next())
			{
				html += "Title :<BR>" + resultMovie.getString("title") +" <BR> <BR> ";
				html += "Year :<BR>" + resultMovie.getString("year") +" <BR> <BR> ";
				html += "Director :<BR>" + resultMovie.getString("director_first_name") + " " 
				  		+ resultMovie.getString("director_last_name") +" <BR> <BR> ";
				html += "Genres :<BR>" + MovieListGenerator.getGenres(connection,resultMovie.getString("id")) +" <BR> <BR> ";
				html += "Poster :<BR><img src='" + resultMovie.getString("banner_url") +"' alt='Image not found' <BR> <BR> ";
				html += "Stars :<BR>" + MovieListGenerator.getStars(connection,resultMovie.getString("id")) +" <BR> <BR> ";
				html += "<A HREF='" + resultMovie.getString("trailer_url") +"'>Click here for trailer </A> <BR> <BR> ";
				html += "<form  action='cart.jsp' method='GET' ><input TYPE=HIDDEN name='add' value='"+resultMovie.getString("title")+"' />" +
						"<input type='submit' value='Add to Cart'/></form>";
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return html;
	}

}

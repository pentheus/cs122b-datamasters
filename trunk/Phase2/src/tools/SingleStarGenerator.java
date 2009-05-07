//This will create a page to be embedded about a single star
package tools;

import java.sql.*;

public class SingleStarGenerator 
{
	
	public static String getStar(String id)
	{
		String html = "";
		Connection connection;
		try 
		{
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
		
			Statement select = connection.createStatement();
			ResultSet resultMovie = select.executeQuery("select * from stars where id=" + id + "");
			/*star's name, date of birth, picture of the star, and the title of the movies in which the star acted (each again linked to the appropriate single movie page).*/
			while(resultMovie.next())
			{
				html += "Name :<BR>" + resultMovie.getString("first_name") + " " 
		  					+ resultMovie.getString("last_name") +" <BR> <BR> ";
				html += "Date of Birth :<BR>" + resultMovie.getDate("dob").toString() +" <BR> <BR> ";
				html += "Movies :<BR>" + getMovies(connection,id) +" <BR> <BR> ";
				html += "Picture :<BR><img src='" + resultMovie.getString("photo_url") +"' alt='Image not found' <BR> <BR> ";
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return html;
	}
	
	static String getMovies(Connection connection, String id)
	{
		String movies = "";
		Statement select1;
		try 
		{
			select1 = connection.createStatement();
			ResultSet resultStarsInMovies = select1.executeQuery("select * from stars_in_movies" +
								 " where star_id=" + id);
			while(resultStarsInMovies.next())
			{
				Statement select2 = connection.createStatement();
				ResultSet resultMovies = select2.executeQuery("select * from movies" +
									 " where id=" + resultStarsInMovies.getString("movie_id"));
				while(resultMovies.next())
				{
					movies += "<A HREF='single-movie.jsp?id=" + resultStarsInMovies.getString("movie_id") + "'>" +resultMovies.getString("title") + "</A>, " ;
				}
			}
			//cut off the last comma
			if(!movies.equals(""))
				movies = movies.substring(0, movies.length()-2);
		}
		catch (SQLException e) 		{
			e.printStackTrace();
		}
		return movies;
	}

}

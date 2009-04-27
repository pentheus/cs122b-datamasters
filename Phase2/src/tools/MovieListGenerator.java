package tools;

import java.sql.*;
import java.util.ArrayList;

//TODO clickable sort buttons
//TODO clickable Genres, Stars
//TODO Pages, buttons to go between
public class MovieListGenerator 
{
	public static final int RECORDS_PER_PAGE = 20;

	public static String getList(int index)
	{	
		ArrayList<String> resultPages = new ArrayList<String>();
		int records = 0;//used to keep track of each record added to the table
		
		Connection connection;
		try 
		{
			StringBuilder sb = new StringBuilder();
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
		
			Statement select = connection.createStatement();
			ResultSet resultMovies = select.executeQuery("select * from movies");
			while(resultMovies.next())
			{
				sb.append("<tr>\n");
				sb.append("<th>" + resultMovies.getString("title") + "</th>\n");
				sb.append("<th>" + resultMovies.getString("year") + "</th>\n");
				sb.append("<th>" + resultMovies.getString("director_first_name") + " " 
						  + resultMovies.getString("director_last_name")  + "</th>\n");			
				sb.append("<th>" + getGenres(connection,resultMovies.getString("id")) + "</th>\n");			
				sb.append("<th>" + getStars(connection,resultMovies.getString("id")) + "</th>\n");
				//TODO link button
				sb.append("<th>" + "<button type='add'>Add</button>" + "</th>\n");
				records++;
				if((records % RECORDS_PER_PAGE) == 0)
				{
					resultPages.add(sb.toString());
					sb = new StringBuilder();
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return resultPages.get(index);
	}
	
	private static String getGenres(Connection connection, String id)
	{
		String genres = "";
		Statement select1;
		try 
		{
			select1 = connection.createStatement();
		ResultSet resultGenresInMovies = select1.executeQuery("select * from genres_in_movies" +
								 " where movie_id=" + id);
		while(resultGenresInMovies.next())
		{
			Statement select2 = connection.createStatement();
			ResultSet resultGenres = select2.executeQuery("select * from genres" +
									 " where id=" + resultGenresInMovies.getString("genre_id"));
			while(resultGenres.next())
			{
				genres += resultGenres.getString("name") + ", " ;
			}
		}
		//cut off the last comma
		if(!genres.equals(""))
			genres = genres.substring(0, genres.length()-2);
		}
		catch (SQLException e) 		{
			e.printStackTrace();
		}
		return genres;
	}
	
	private static String getStars(Connection connection, String id)
	{
		String stars = "";
		Statement select1;
		try 
		{
			select1 = connection.createStatement();
			ResultSet resultStarsInMovies = select1.executeQuery("select * from stars_in_movies" +
								 " where movie_id=" + id);
			while(resultStarsInMovies.next())
			{
				Statement select2 = connection.createStatement();
				ResultSet resultStars = select2.executeQuery("select * from stars" +
									 " where id=" + resultStarsInMovies.getString("star_id"));
				while(resultStars.next())
				{
					stars += resultStars.getString("first_name") + " " +
							 resultStars.getString("last_name") + ", " ;
				}
			}
			//cut off the last comma
			if(!stars.equals(""))
				stars = stars.substring(0, stars.length()-2);
		}
		catch (SQLException e) 		{
			e.printStackTrace();
		}
		return stars;
	}
}


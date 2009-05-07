package tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

//TODO clickable Genres, Stars
public class MovieListGenerator 
{
	public static final int RECORDS_PER_PAGE = 20;

	public static String getList(int index, String comparator, String compareDirection)
	{	
		
		Connection connection;
		ArrayList<Movie> movies = new ArrayList<Movie>();
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
				sb.append("<th>" + "<form  action='cart.jsp' method='GET' ><input TYPE=HIDDEN name='add' value='"+resultMovies.getString("title")+"' />" +
						"<input type='submit' value='Add to Cart'/></form>" + "</th>\n");
				movies.add(new Movie(resultMovies.getString("title"), resultMovies.getString("year"), resultMovies.getString("director_first_name"),
						resultMovies.getString("director_first_name"),getGenres(connection,resultMovies.getString("id")), 
						getStars(connection,resultMovies.getString("id")), comparator, compareDirection, sb.toString()));
				sb = new StringBuilder();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		Collections.sort(movies);

		return getRecords(index, movies);
	}
	
	public static String getGenreList(int index, String comparator, String compareDirection, String genreName)
	{	
		
		Connection connection;
		ArrayList<Movie> movies = new ArrayList<Movie>();
		try 
		{
			StringBuilder sb = new StringBuilder();
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
		
			Statement select = connection.createStatement();
			ResultSet resultMovies = select.executeQuery("select * from movies");
			while(resultMovies.next())
			{
				String genres = getGenres(connection,resultMovies.getString("id")).toLowerCase();
				if(genres.contains(genreName))
				{
					sb.append("<tr>\n");
					sb.append("<th><A HREF='single-movie.jsp?id="+ resultMovies.getString("id") + "'>" + resultMovies.getString("title") + "</A></th>\n");
					sb.append("<th>" + resultMovies.getString("year") + "</th>\n");
					sb.append("<th>" + resultMovies.getString("director_first_name") + " " 
							  + resultMovies.getString("director_last_name")  + "</th>\n");			
					sb.append("<th>" + getGenres(connection,resultMovies.getString("id")) + "</th>\n");			
					sb.append("<th>" + getStars(connection,resultMovies.getString("id")) + "</th>\n");
					//TODO link button
					sb.append("<th>" + "<form  action='cart.jsp' method='GET' ><input TYPE=HIDDEN name='add' value='"+resultMovies.getString("title")+
							"' /><input type='submit' value='Add to Cart'/></form>" + "</th>\n");
					movies.add(new Movie(resultMovies.getString("title"), resultMovies.getString("year"), resultMovies.getString("director_first_name"),
							resultMovies.getString("director_first_name"),getGenres(connection,resultMovies.getString("id")), 
							getStars(connection,resultMovies.getString("id")), comparator, compareDirection, sb.toString()));
					sb = new StringBuilder();
				}				
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		Collections.sort(movies);

		return getRecords(index, movies);
	}
	
	private static String getRecords(int index, ArrayList<Movie> movies)
	{
		StringBuilder sb = new StringBuilder();
		int records = 0;
		ArrayList<String> resultPages = new ArrayList<String>();
		
		for(Movie m : movies)
		{
			records++;
			sb.append(m.getRecord());
			if(((records % RECORDS_PER_PAGE) == 0)||(movies.size()<=records))
			{
				resultPages.add(sb.toString());
				sb = new StringBuilder();
			}
		}
		return resultPages.get(index).toString();
	}
	
	
	public static String getGenres(Connection connection, String id)
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
		//cuts off the last comma
		if(!genres.equals(""))
			genres = genres.substring(0, genres.length()-2);
		}
		catch (SQLException e) 		{
			e.printStackTrace();
		}
		return genres;
	}
	
	static String getStars(Connection connection, String id)
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
					stars += "<A HREF='single-star.jsp?id=" + resultStarsInMovies.getString("star_id") + "'>" + resultStars.getString("first_name") + " " +
							 resultStars.getString("last_name") + "</A>, " ;
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
	
	public static String getResults(String title, String year, String director, String star, int index, String comparator, String compareDirection)
	{
		System.out.println(title);
		System.out.println(year);
		System.out.println(director);
		System.out.println(star);
		ArrayList<Movie> movies = new ArrayList<Movie>();
		Connection connection;
		try 
		{
			StringBuilder sb = new StringBuilder();
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/fabflixs","testuser", "testpass");
		
			Statement select = connection.createStatement();
			//TODO
			ResultSet resultMovies = select.executeQuery("select * from movies where title ~* '" + title + "'");
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
				sb.append("<th>" + "<form  action='cart.jsp' method='GET' ><input TYPE=HIDDEN name='add' value='"+resultMovies.getString("title")+"' />" +
						"<input type='submit' value='Add to Cart'/></form>" + "</th>\n");
				movies.add(new Movie(resultMovies.getString("title"), resultMovies.getString("year"), resultMovies.getString("director_first_name"),
						resultMovies.getString("director_first_name"),getGenres(connection,resultMovies.getString("id")), 
						getStars(connection,resultMovies.getString("id")), comparator, compareDirection, sb.toString()));
				sb = new StringBuilder();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		if(movies.size() == 0)
		{
			return "No movies found" ;
		}
		Collections.sort(movies);

		return getRecords(index, movies);
	}
}


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BrowseGenre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BrowseGenre() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		ArrayList<String> genres = getGenres();
		// set content type to HTML
	    response.setContentType("text/html");

	    // print formatted information
	    PrintWriter out = response.getWriter();
	      
	    String genreParam = "NULL";//default case
	    if(request.getParameter("genre")!=null)
	 	{
	 		genreParam = request.getParameter("genre").toLowerCase().trim();
	 	}
	    
	    String directionParam = "AtoZ";//default case
	    if(request.getParameter("direction")!=null)
	 	{
	    	directionParam = request.getParameter("direction").trim();
	 	}
	    
	    String sortParam = "title";//default case
	    if(request.getParameter("sort")!=null)
	 	{
	    	sortParam = request.getParameter("sort").trim();
	 	}
	    
	    String currentPage = "0";//default case
	    if(request.getParameter("currentpage")!=null)
	 	{
	    	currentPage = request.getParameter("currentpage").trim();
	 	}
	    int currentPageInt = Integer.parseInt(currentPage);

	    out.println("<html><head>");
	    out.println("</head><body>");
	    out.println("<FONT FACE='Lucida,Verdana,Helvetica,Arial'>");

	    if(!genreParam.equals("NULL"))
	    {
	    	out.println("<table border='1' align='center' WIDTH='80%'><tr>");	
	    		String newDirection = "AtoZ";
	    		if (directionParam.equalsIgnoreCase("AtoZ"))
	    		{
	    			newDirection ="ZtoA";
	    		}
	    		out.println("<th><A HREF='"+ request.getRequestURI() +"?sort=title&direction="+
	    				newDirection+"&currentpage=0&genre="+ genreParam +"'> Title </A></th>");	       	 	
	    		out.println("<th><A HREF='"+ request.getRequestURI() +"?sort=year&direction="
	    					+newDirection+"&currentpage=0&genre="+ genreParam +"'> Year </A></th>");
	    		out.println("<th><A HREF='"+ request.getRequestURI() +"?sort=director&direction="+newDirection+
	    						"&currentpage=0&genre="+ genreParam +"'> Director </A></th>");
	    		out.println("<th>Genres</th>");
	    		out.println("<th>Stars</th>");
	    		out.println("<th>Add to <br> Shopping Cart</th></tr>");
	    	
	    		out.println(tools.MovieListGenerator.getGenreList(currentPageInt, sortParam,
	    															directionParam,genreParam));
	    	
	    	out.println("</table>");
	    }
	    else
	    {
	    	for(String genreName : genres)
	    	{
	    		out.println("<li><A HREF='browse-genre.jsp?genre="+ genreName +"'>"+ genreName +"</A></li> ");
	    	}
	    }	      
	    
	    out.println("<div class='navbar' align='center'>");
	    
	    String parameters = "&genre=" + genreParam + "&direction=" + directionParam + "&sort=" + sortParam;
	    
	    if(!(currentPageInt<= 0))
	    {
	    	currentPageInt--;
	    	out.println("<A HREF='browse-genre.jsp?currentpage="+ currentPageInt + parameters + "'> Prev </A>");
	    }
	    out.println("&nbsp&nbsp&nbsp");
	    currentPageInt = Integer.parseInt(currentPage);
	    if(!(currentPageInt>= (getNumberOfRecordPages(genreParam)-1)))
	    {
	    	currentPageInt++;
	    	out.println("<A HREF='browse-genre.jsp?currentpage="+ currentPageInt + parameters + "'> Next </A>");
	    }
	    
	    out.println("</font></body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doGet(request, response);		
	}

	private ArrayList<String> getGenres()
	{
		ArrayList<String> genres = new ArrayList<String>();
		
		try 
		{
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/fabflixs","testuser", "testpass");

			Statement select = connection.createStatement();
			ResultSet resultGenres = select.executeQuery("select * from genres");
			while(resultGenres.next())
			{
				String genreName = resultGenres.getString("name").toLowerCase();
				if(!genres.contains(genreName))
				{
					genres.add(genreName);
				}
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		Collections.sort(genres);
		return genres;
	}
	
	public int getNumberOfRecordPages(String genreName)
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
				String genres = tools.MovieListGenerator.getGenres(connection,resultMovies.getString("id")).toLowerCase();
				//System.out.println("Genre : " + genreName + "; genres: " + genres);
				if(genres.contains(genreName))
				{
					recordCount++;
				}
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
}

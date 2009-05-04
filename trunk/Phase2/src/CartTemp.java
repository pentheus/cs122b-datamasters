import info.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartTemp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public CartTemp() {
        super();
    }
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		// set content type to HTML
	    response.setContentType("text/html");

	    // print formatted information
	    PrintWriter out = response.getWriter();
	      
	    String genreParam = "NULL";//default case
	    if(request.getParameter("genre")!=null)
	 	{
	 		genreParam = request.getParameter("genre").toLowerCase().trim();
	 	}
	    
	    Cart cart = new Cart();
	    cart.addItem(new Item("Item 1", 1));
	    cart.addItem(new Item("Item 2", 1));

	    out.println("<html><head>");
	    out.println("</head><body>");
	    out.println("<FONT FACE='Lucida,Verdana,Helvetica,Arial'>");

	    
	    /*out.println("<table border='1' align='center' WIDTH='80%'><tr>");
	    	out.println("<th>Title</th>");
	    	out.println("<th>Quanity</th>");
	    	out.println("<th>Add to <br> Shopping Cart</th></tr>");
	    	
	    	for(Item item : cart.getItems())
	    	{
	    		out.println("<tr>");
		    	out.println("<th>" + item.getTitle()  + "</th>");
		    	out.println("<th>" + item.getQuantity()  + "</th>");
		    	out.println("<th>" + "<button type='submit'>Set Quantity</button>"  + "</th>");
		    	out.println("</tr>");
	    	}
	    	
	    	out.println("</table>");*/
	    
	    out.println("</font></body></html>");
	    out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doGet(request, response);		
	}

}

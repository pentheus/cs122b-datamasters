

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Tell browser we have a text web page
	    response.setContentType("text/html");

	    // Set up an output stream to the browser window
	    PrintWriter out = response.getWriter();

	    // Request session info
	    HttpSession session = request.getSession();

	    // Welcome new or returning user; update session count
	    String heading;
	    int accessCount;
	    Object status = session.getAttribute("accessCount");
	    if (status == null)
	    {
	      heading = "Welcome";
	      accessCount = 0;
	    }
	    else
	    {
	      heading = "Welcome Back";
	      accessCount = (Integer)status + 1;
	    }

	    session.setAttribute("accessCount", accessCount);
	    out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" +
			"<H1 ALIGN=\"CENTER\">" + heading + "</H1>\n" +
			"<H2>Information on Your Session:</H2>\n" +
			"<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
			"<TR BGCOLOR=\"#FFAD00\">\n" +
			"  <TH>Info Type<TH>Value\n" +
			"<TR>\n" +
			"  <TD>ID\n" +
			"  <TD>" + session.getId() + "\n" +
			"<TR>\n" +
			"  <TD>Creation Time\n" +
			"  <TD>" + new Date(session.getCreationTime()) + "\n" +
			"<TR>\n" +
			"  <TD>Time of Last Access\n" +
			"  <TD>" + new Date(session.getLastAccessedTime()) + "\n" +
			"<TR>\n" + getDate() +
			"  <TD>Number of Previous Accesses\n" +
			"  <TD>" + accessCount + "\n" +
			"</TR>"+
			"</TABLE>\n");

	    out.println("</BODY></HTML>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public static String getDate()
	{
		return new java.util.Date().toString();
	}

}

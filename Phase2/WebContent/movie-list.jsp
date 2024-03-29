<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- Permission is granted to copy, distribute and/or modify this document -->
<!-- under the terms of the GNU Free Documentation License, Version 1.1 -->
<!-- or any later version published by the Free Software Foundation; -->
<!-- with no invariant sections. -->
<!-- A copy of the license can be found at http://www.gnu.org/copyleft/fdl.html -->

<jsp:useBean id="pageInfo" class="info.MovieList" scope="session"/>
<jsp:setProperty name="pageInfo" property="*"/> 

 <%
        // If process is true, attempt to validate and process the form
        if ("true".equals(request.getParameter("next")))
        {
        	pageInfo.incCurrentPage();
        	response.sendRedirect("movie-list.jsp");
        }
            
 		if ("true".equals(request.getParameter("prev")))
 		{
 			pageInfo.decCurrentPage();
 			response.sendRedirect("movie-list.jsp");
 		}
 		String sort = pageInfo.getSort();//default case
 		if(request.getParameter("sort")!=null)
 		{
 			sort = request.getParameter("sort");
 			pageInfo.setSort(request.getParameter("sort"));
 		}
 		String direction = pageInfo.getDirection();//default case
 		if(request.getParameter("direction")!=null)
 		{
 			direction = request.getParameter("direction");
 			pageInfo.setDirection(request.getParameter("direction"));
 		}
 		if(request.getParameter("resetpage")!=null)
 		{
 			if(request.getParameter("resetpage").equalsIgnoreCase("true"))
 				pageInfo.setCurrentPage(0);
 		}
 		if(request.getParameter("currentpage")!=null)
 		{
 			int pageNumber = Integer.parseInt(request.getParameter("currentpage"));
 				pageInfo.setCurrentPage(pageNumber);
 		}
    %>

<HTML>
<BODY MARGINWIDTH="0" MARGINHEIGHT="0" LEFTMARGIN="0" RIGHTMARGIN="0" TOPMARGIN="0" BGCOLOR="ffec98" TEXT="#000000" LINK="#336699" VLINK="#336699" ALINK="#336699">
	<TR><TD><FONT FACE="Lucida,Verdana,Helvetica,Arial">
	
	<table border="1" align="center" WIDTH="80%">		
	<tr>
		<%
		String newDirection = "AtoZ";
		if (direction.equalsIgnoreCase("AtoZ"))
		{
			newDirection ="ZtoA";
		}
		%>
    	<th>
    	<%= "<A HREF='"+ request.getRequestURI() +"?sort=title&direction="+newDirection+"&resetpage=true'> Title </A>"%>
   	 	</th>
		<th><%= "<A HREF='"+ request.getRequestURI() +"?sort=year&direction="+newDirection+"&resetpage=true'> Year </A>"%></th>
		<th><%= "<A HREF='"+ request.getRequestURI() +"?sort=director&direction="+newDirection+"&resetpage=true'> Director </A>"%></th>
		<th>Genres</th>
		<th>Stars</th>
		<th>Add to <br> Shopping Cart</th> 
	</tr>
	<%=tools.MovieListGenerator.getList(pageInfo.getCurrentPage(), sort,direction)%>
	
	</table> 
	</FONT>
	</TD></TR>
	
	<div class="navbar" align="center">
	<hr>
	<table cellspacing="20" align="center">
	<tr>
		<% if (!(pageInfo.getCurrentPage()<= 0)){%>
    	<th>
    		<%= pageInfo.getPrevButton(request.getRequestURI())%>
    		<%}%>
   	 	</th>
		<% if (!(pageInfo.getCurrentPage()>= (pageInfo.getNumberOfRecordPages()-1))){%>
    	<th>
    		<%= pageInfo.getNextButton(request.getRequestURI()) %>
    		<%}%>
   	 	</th>
    </tr>
    </table>
	</div>


</BODY>
</HTML>

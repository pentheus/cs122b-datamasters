<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- Permission is granted to copy, distribute and/or modify this document -->
<!-- under the terms of the GNU Free Documentation License, Version 1.1 -->
<!-- or any later version published by the Free Software Foundation; -->
<!-- with no invariant sections. -->
<!-- A copy of the license can be found at http://www.gnu.org/copyleft/fdl.html -->
<HTML>
<jsp:useBean id="searchInfo" class="info.MovieList" scope="page"/>
<jsp:setProperty name="searchInfo" property="*"/> 

<%
		String title = "NULL";
		if (request.getParameter("title") != null)
		{
			title = request.getParameter("title");
		}
		
		String action = "NULL";
		if (request.getParameter("action") != null)
		{
			action = request.getParameter("action");
		}
		
        // If process is true, attempt to validate and process the form
        if ("true".equals(request.getParameter("next")))
        {
        	searchInfo.incCurrentPage();
        	response.sendRedirect("search.jsp?title=" + title + "&action=" + action);
        }
            
 		if ("true".equals(request.getParameter("prev")))
 		{
 			searchInfo.decCurrentPage();
 			response.sendRedirect("search.jsp");
 		}
 		String sort = searchInfo.getSort();//default case
 		if(request.getParameter("sort")!=null)
 		{
 			sort = request.getParameter("sort");
 			searchInfo.setSort(request.getParameter("sort"));
 		}
 		String direction = searchInfo.getDirection();//default case
 		if(request.getParameter("direction")!=null)
 		{
 			direction = request.getParameter("direction");
 			searchInfo.setDirection(request.getParameter("direction"));
 		}
 		if(request.getParameter("resetpage")!=null)
 		{
 			if(request.getParameter("resetpage").equalsIgnoreCase("true"))
 				searchInfo.setCurrentPage(0);
 		}
 		if(request.getParameter("currentpage")!=null)
 		{
 			int pageNumber = Integer.parseInt(request.getParameter("currentpage"));
 				searchInfo.setCurrentPage(pageNumber);
 		}
%>
<BODY MARGINWIDTH="0" MARGINHEIGHT="0" LEFTMARGIN="0" RIGHTMARGIN="0" TOPMARGIN="0" BGCOLOR="ffec98" TEXT="#000000" LINK="#336699" VLINK="#336699" ALINK="#336699">

<TABLE BORDER=0 WIDTH="100%" CELLPADDING=1 CELLSPACING=0>
	<TR>
		<TD ALIGN="CENTER" BGCOLOR="a60000"><a href="http://localhost:8080/Phase2/"><img src="./fabflix.png" alt="Fabflix" width="295" height="201" hspace=0 border=0></a></TD>
	</TR>
</TABLE>


<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="2" ALT=""></TD></TR></TABLE>

<TABLE WIDTH="100%" CELLPADDING=0 CELLSPACING=0 BORDER=0>
	<TR>
		<TD ALIGN="CENTER" BGCOLOR="#D3D4C5">
			<TABLE CELLPADDING=0 CELLSPACING=0 BORDER=0>
				<TR>
				<TD           BGCOLOR="#D3D4C5" ALIGN=CENTER NOWRAP>&nbsp;&nbsp;<A CLASS="topnav" HREF="index.jsp">Main</A>&nbsp;&nbsp;</TD>
					<TD BGCOLOR="#D3D4C5" ALIGN=CENTER>|</TD>
					<TD           BGCOLOR="#D3D4C5" ALIGN=CENTER NOWRAP>&nbsp;&nbsp;<A CLASS="topnav" HREF="browse.jsp">Browse</A>&nbsp;&nbsp;</TD>
					<TD BGCOLOR="#D3D4C5" ALIGN=CENTER>|</TD>
					<TD HEIGHT=22 BGCOLOR="#D3D4C5" ALIGN=CENTER NOWRAP>&nbsp;&nbsp;Search&nbsp;&nbsp;</TD>
					<TD BGCOLOR="#D3D4C5" ALIGN=CENTER>|</TD>
					<TD           BGCOLOR="#D3D4C5" ALIGN=CENTER NOWRAP>&nbsp;&nbsp;<A CLASS="topnav" HREF="login.jsp">Login</A>&nbsp;&nbsp;</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>

<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="2" ALT=""></TD></TR></TABLE>


<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="2" ALT=""></TD></TR></TABLE>

<CENTER>

<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="ffec98"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="15" ALT=""></TD></TR></TABLE>

<TABLE cellspacing="0" cellpadding="3" border="0" width="100%" bgcolor="ffec98">
	<TR>
		<TD align="center" valign="top">

<TABLE WIDTH="100%">
<TR><TD>
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="1" ALT=""></TD></TR></TABLE>
	<TABLE CELLSPACING="0" CELLPADDING="3" WIDTH="100%" BORDER="0" BGCOLOR="#D3D4C5">
		<TR>
		  <TD><FONT FACE="Lucida,Verdana,Helvetica,Arial">
		<B><FONT SIZE="+2">Search</FONT></B>
		</FONT></TD>
		</TR>
	</TABLE>
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="1" ALT=""></TD></TR></TABLE>
	<TABLE CELLSPACING="0" CELLPADDING="3" WIDTH="100%" BORDER="0" BGCOLOR="#EEEED4">
	<TR><TD><FONT FACE="Lucida,Verdana,Helvetica,Arial">
	<form  action="search.jsp" method="GET" >
	<table border="1">
		<tr>
			<td>Movie Title:</td>
			<td><input type="text" name="title" size="30"></td>
		</tr>
		<tr>
			<td>Year:</td>
			<td><input  type="text" name="year" size="30"></td>
		</tr>
		<tr>
			<td>Director:</td>
			<td><input type="text" name="director" size="30"></td>
		</tr>
		<tr>
			<td>Star's Name: </td>
			<td><input type="text" name="star" size="30"></td>
		</tr>
	</table>
	<P><button type="submit" name="action" value="search">Search</button></P>
	</form>
	
	<%if(action.equalsIgnoreCase("search")){ %>
		<% String results = tools.MovieListGenerator.getResults(title, "", "", "", searchInfo.getCurrentPage(), sort,direction); %>
		<%if(!results.equalsIgnoreCase("No movies found")){ %>
			<%= "<table border='1' align='center' WIDTH='80%'>"		%>
			<%= "<tr>"%>
				<%
				String newDirection = "AtoZ";
				if (direction.equalsIgnoreCase("AtoZ"))
				{
					newDirection ="ZtoA";
				}
				%>
		    <%="<th>"%>
		    <%= "<A HREF='"+ request.getRequestURI() +"?sort=title&direction="+newDirection+"&resetpage=true'> Title </A>"%>
		   	<%=  "</th>"%>
			<%=	"<th>"+ "<A HREF='"+ request.getRequestURI() +"?sort=year&direction="+newDirection+"&resetpage=true'> Year </A>" + "</th>"%>
			<%=	"<th>"+ "<A HREF='"+ request.getRequestURI() +"?sort=director&direction="+newDirection+"&resetpage=true'> Director </A>" + "</th>"%>
			<%= "<th>Genres</th>"%>
			<%= "<th>Stars</th>"%>
			<%= "<th>Add to <br> Shopping Cart</th>" %>
			<%="</tr>"%>
		<%} %>
		
		<%= results%>
		<%if(action.equalsIgnoreCase("search")){ %>
		<%if(!results.equalsIgnoreCase("No movies found")){ %>
			<%="<div class='navbar' align='center'>"%>
			<%="<hr>"%>
			<%="<table cellspacing='20' align='center'>"%>
			<%="<tr>"%>
				<% if (!(searchInfo.getCurrentPage()<= 0)){%>
			<%="<th>"%>
					<% searchInfo.decCurrentPage();%>
		    		<%= "<A HREF='search.jsp?title=" + title + "&action=" + action +"'>Prev</A>" %>
		    		<%}%>
		   	<%="</th>"%>
				<% if (!(searchInfo.getCurrentPage()>= (searchInfo.getNumberOfRecordPages()-1))){%>
		    <%="<th>"%>
		   			<% searchInfo.incCurrentPage();%>
		   			<% System.out.println(searchInfo.getCurrentPage());%>
		    		<%= "<A HREF='search.jsp?title=" + title + "&action=" + action +"'>Next</A>" %>
		    		<%}%>
		   	 	<%="</th>"%>
	   	 	<%="</tr>"%>
	   	 	<%="</table>"%>
	   	 	<%="</div>"%>
   	 	<%}} %>
	<%} %>
	</table> 
	</TABLE>
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><tr style="ffec98"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="1" ALT=""></TD></TR></TABLE>
</TD></TR>
</TABLE>

</CENTER>

<P>Bret Lowrey, CS122B, 2009 </P>

</BODY>
</HTML>

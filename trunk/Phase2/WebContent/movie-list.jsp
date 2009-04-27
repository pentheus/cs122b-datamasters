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
<HTML>
<BODY MARGINWIDTH="0" MARGINHEIGHT="0" LEFTMARGIN="0" RIGHTMARGIN="0" TOPMARGIN="0" BGCOLOR="#CFE0EB" TEXT="#000000" LINK="#336699" VLINK="#336699" ALINK="#336699">
	<TR><TD><FONT FACE="Lucida,Verdana,Helvetica,Arial">
	<table border="1" align="center">	
	
	<tr>
		<th>Title</th>
		<th>Year</th>
		<th>Director</th>
		<th>Genres</th>
		<th>Stars</th>
		<th>Add to <br> Shopping Cart</th> 
	</tr>
	
	<%=tools.MovieListGenerator.getList(pageInfo.getCurrentPage())%>
	
	</table> 
	</FONT>
	</TD></TR>
	
	<div class="navbar" align="center">
	<hr><a href="MovieList1.html">previous</a> &nbsp; 
	<a href="MovieList2.html">next</a>
	</div>


</BODY>
</HTML>

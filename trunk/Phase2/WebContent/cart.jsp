<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- Permission is granted to copy, distribute and/or modify this document -->
<!-- under the terms of the GNU Free Documentation License, Version 1.1 -->
<!-- or any later version published by the Free Software Foundation; -->
<!-- with no invariant sections. -->
<!-- A copy of the license can be found at http://www.gnu.org/copyleft/fdl.html -->
<HTML>
<BODY MARGINWIDTH="0" MARGINHEIGHT="0" LEFTMARGIN="0" RIGHTMARGIN="0" TOPMARGIN="0" BGCOLOR="ffec98" TEXT="#000000" LINK="#336699" VLINK="#336699" ALINK="#336699">

<%@page import="java.util.*"%>
<jsp:useBean id="cart" scope="session" class="info.Cart"/>

<% 
String action = "NULL";
if (request.getParameter("action") != null)
{
	action = request.getParameter("action");
}
if (request.getParameter("add") != null)
{
	cart.addItem(new info.Item(request.getParameter("add"), 1));
}
ArrayList<info.Item> items = cart.getItems(); 
for(info.Item i : items)
{
	if (request.getParameter(i.getTitle()) != null)
	{
		int quantity = Integer.parseInt(request.getParameter(i.getTitle()));
		if(quantity >= 0)
		{
			i.setQuantity(quantity);
		}
	}
}
%>

<TABLE BORDER=0 WIDTH="100%" CELLPADDING=1 CELLSPACING=0>
	<TR>
		<TD ALIGN="CENTER" BGCOLOR="a60000"><a href="http://localhost:8080/Phase2/"><img src="./fabflix.png" alt="Fabflix" width="295" height="201" hspace=0 border=0></a></TD>
	</TR>
</TABLE>
<TABLE WIDTH="100%" CELLPADDING=0 CELLSPACING=0 BORDER=0>
	<TR>
		<TD ALIGN="CENTER" BGCOLOR="#D3D4C5">
			<TABLE CELLPADDING=0 CELLSPACING=0 BORDER=0>
				<TR>
					<TD           BGCOLOR="#D3D4C5" ALIGN=CENTER NOWRAP>&nbsp;&nbsp;<A CLASS="topnav" HREF="index.jsp">Main</A>&nbsp;&nbsp;</TD>
					<TD BGCOLOR="#D3D4C5" ALIGN=CENTER>|</TD>
					<TD           BGCOLOR="#D3D4C5" ALIGN=CENTER NOWRAP>&nbsp;&nbsp;<A CLASS="topnav" HREF="browse.jsp">Browse</A>&nbsp;&nbsp;</TD>
					<TD BGCOLOR="#D3D4C5" ALIGN=CENTER>|</TD>
					<TD           BGCOLOR="#D3D4C5" ALIGN=CENTER NOWRAP>&nbsp;&nbsp;<A CLASS="topnav" HREF="search.jsp">Search</A>&nbsp;&nbsp;</TD>
					<TD BGCOLOR="#D3D4C5" ALIGN=CENTER>|</TD>
					<TD           BGCOLOR="#D3D4C5" ALIGN=CENTER NOWRAP>&nbsp;&nbsp;<A CLASS="topnav" HREF="login.jsp">Login</A>&nbsp;&nbsp;</TD>
					<TD BGCOLOR="#D3D4C5" ALIGN=CENTER>|</TD>
					<TD           BGCOLOR="#D3D4C5" ALIGN=CENTER NOWRAP>&nbsp;&nbsp;<A CLASS="topnav" HREF="checkout.jsp">Checkout</A>&nbsp;&nbsp;</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="2" ALT=""></TD></TR></TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="2" ALT=""></TD></TR></TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="ffec98"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="15" ALT=""></TD></TR></TABLE>



<TR><TD>
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="1" ALT=""></TD></TR></TABLE>
	<TABLE CELLSPACING="0" CELLPADDING="3" WIDTH="100%" BORDER="0" BGCOLOR="#D3D4C5">
		<TR>
		  <TD><FONT FACE="Lucida,Verdana,Helvetica,Arial">

		<B><FONT SIZE="+2">Shopping Cart</FONT></B>
		</FONT></TD>
		</TR>
	</TABLE>
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="1" ALT=""></TD></TR></TABLE>
	<TABLE CELLSPACING="0" CELLPADDING="3" WIDTH="100%" BORDER="0" BGCOLOR="#EEEED4">
	<TR><TD><FONT FACE="Lucida,Verdana,Helvetica,Arial">&nbsp;

	</FONT>
	<P><font face="Lucida,Verdana,Helvetica,Arial"> 
	<STRONG> Items in the Shopping Cart: </STRONG> </BR> </BR> 

	

	<form  action="cart.jsp" method="GET" >
	<table border='1' align='center' WIDTH='80%'><tr>
		<th>Title</th>
	    <th>Quanity</th>
	    	
	    <% for(info.Item item : cart.getItems()){%>
	    	<%= "<tr>"%>
		    <%= "<th>" + item.getTitle()  + "</th>"%>
		    <%= "<th><input type='text' size='5' name='"+ item.getTitle() + "' value='"+ item.getQuantity()  + "'></th>"%>
		    <%= "</tr>"%>
	    	<%}%>
	    	
	   </table>
	    <div class='navbar' align='right' />
	    <BR>
        <input type="submit" name="action" value="Update Quantity" />
    </form>
	</TD></TR>
	</TABLE>

	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="1" ALT=""></TD></TR></TABLE>
</TD></TR>

<P>Bret Lowrey, CS122B, 2009 </P>

</BODY>
</HTML>

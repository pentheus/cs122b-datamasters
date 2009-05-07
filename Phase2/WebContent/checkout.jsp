<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- Permission is granted to copy, distribute and/or modify this document -->
<!-- under the terms of the GNU Free Documentation License, Version 1.1 -->
<!-- or any later version published by the Free Software Foundation; -->
<!-- with no invariant sections. -->
<!-- A copy of the license can be found at http://www.gnu.org/copyleft/fdl.html -->

<HTML>

<%@page import="java.util.*"%>
<jsp:useBean id="cart" scope="session" class="info.Cart"/>
<jsp:useBean id="login" scope="session" class="info.Login" />
<head>
<script type="text/javascript">
function startup(){
	<% 
	if(cart.getItems().size()==0)
	{
		%>
		<%= "alert('You must have items in your cart first');" %>
		<%= "window.location = 'index.jsp';" %>
	<%
	}
	String action = "NULL";
	if (request.getParameter("action") != null)
	{
		action = request.getParameter("action");
	}

	if(action.equals("Order"))
	{
		if(login.getLoginID().equals("NULL"))
		{%>
			<%= "alert('Please Login First');" %>
			<%= "window.location = 'login.jsp?cameFrom=checkout.jsp';" %>
		<%
		}
		else
		{
			//place order here
			cart.resetCart();
			%>
			<%= "alert('Thank you for your order');" %>
			<%= "window.location = 'index.jsp';" %>
		<%
		}
	}
	%>
}
</script>
</head>

<BODY onload="startup()" MARGINWIDTH="0" MARGINHEIGHT="0" LEFTMARGIN="0" RIGHTMARGIN="0" TOPMARGIN="0" BGCOLOR="ffec98" TEXT="#000000" LINK="#336699" VLINK="#336699" ALINK="#336699">

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

	<form  action="checkout.jsp" method="GET" >
	<table border='1' align='center' WIDTH='80%'><tr>
		<th>Title</th>
	    <th>Quanity</th>
	    
	    <% double totalPrice = 0; %>
	    	
	    <% for(info.Item item : cart.getItems()){%>
	    	<%= "<tr>"%>
		    <%= "<th>" + item.getTitle()  + "</th>"%>
		    <%= "<th>"+item.getQuantity()+"</th>"%>
		    <%= "</tr>"%>
		    <% totalPrice +=  item.getQuantity() * 19.95;%>
	    	<%}%>
	    	<%= "<tr>"%>
		    <%= "<th>" + " "  + "</th>"%>
		    <%= "<th>" + "Total: $" + totalPrice  + "</th>"%>
		    <%= "</tr>"%>
	   </table><BR><BR>
	  
	   Credit Card Holder: <BR>
	   <input type='text' name='cardHolder' value='<%= login.getCreditCardHolder() %>'/> <BR> <BR>
	   
	   Credit Card Number: <BR>
	   <input type='text' name='creditCard' value='<%= login.getCreditCardNumber() %>'/> <BR> <BR>
	   
	   Expiration Date: <BR>
	   <input type='text' name='cardExpir' value='<%= login.getCreditCardExpiration() %>'/> <BR> <BR><BR> <BR>
	   
	   Address on File: <BR>
	   <input type='text' name='onFileAddress' value='<%= login.getAddress() %>'/> <BR> <BR>
	   
	   Shipping Address:<BR>(will be the same as address above if left blank) <BR>
	   <input type='text' name='shippingAddress' /> <BR> <BR>
	   
	    <div class='navbar' align='right' />
        <input type="submit" name="action" value="Order" />
    </form>
	</TD></TR>
	</TABLE>

	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="1" ALT=""></TD></TR></TABLE>
</TD></TR>

<P>Bret Lowrey, CS122B, 2009 </P>

</BODY>
</HTML>

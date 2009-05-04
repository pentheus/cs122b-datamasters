<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="login" scope="session" class="info.Login" />
    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- Permission is granted to copy, distribute and/or modify this document -->
<!-- under the terms of the GNU Free Documentation License, Version 1.1 -->
<!-- or any later version published by the Free Software Foundation; -->
<!-- with no invariant sections. -->
<!-- A copy of the license can be found at http://www.gnu.org/copyleft/fdl.html -->
<HTML>

<% 
if (request.getParameter("cameFrom") != null)
{
	login.setCameFrom(request.getParameter("cameFrom").trim());
}

String existingEmail = "NULL";
if (request.getParameter("existingEmail") != null)
{
	existingEmail = request.getParameter("existingEmail").trim();
}
 
String existingPassword = "NULL";
if (request.getParameter("existingPassword") != null)
{
	existingPassword = request.getParameter("existingPassword").trim();
}

String forgottenPassword = "NULL";
if (request.getParameter("forgottenPassword") != null)
{
	forgottenPassword = request.getParameter("forgottenPassword").trim();
}

String firstName = "NULL";
if (request.getParameter("firstName") != null)
{
	firstName = request.getParameter("firstName").trim();
}
 
String lastName = "NULL";
if (request.getParameter("lastName") != null)
{
	lastName = request.getParameter("lastName").trim();
}

String newEmail = "NULL";
if (request.getParameter("newEmail") != null)
{
	newEmail = request.getParameter("newEmail").trim();
}

String password1 = "NULL";
if (request.getParameter("password1") != null)
{
	password1 = request.getParameter("password1");
}

String password2 = "NULL";
if (request.getParameter("password2") != null)
{
	password2 = request.getParameter("password2");
}
%>

<head>
<script type="text/javascript">
function startup(){
	<% if(login. getNumberOfTries()==3)
	{%>
		<%= "alert('Number of login tries exceeded, please try again later.');" %>
		<%= "window.location = 'index.jsp';" %>
		
	<%}
	if(!existingEmail.equals("NULL")) 
	{  
		if(login.loginExists(existingEmail,existingPassword))
		{
			login.setLoginID("existingEmail");
			login.resetNumberOfTries();%>
    <%= "alert('Login Successful!');" %>
    <%= "window.location = '"+ login.getCameFrom() +"';" %>
    <% } else{  
       login.incNumberOfTries();%>
    <%= "alert('Wrong email and password combination!');" %>
    <% } }%>
    <% if(!forgottenPassword.equals("NULL")) 
	{ %>
    <%= "myRef = window.open('forgotten-password.html','mywin','left=20,top=20,width=500,height=500,toolbar=1,resizable=0'); myRef.focus();" %>
    <% } %>

    <% if((!newEmail.equals("NULL"))&&(!lastName.equals("NULL"))&&(!password1.equals("NULL"))&&(!password2.equals("NULL")))
	{  
    	System.out.println(firstName + " " + lastName + " " + newEmail + " " + password1 + " " + password2);
    	if(firstName.equals("NULL"))
    	{
    		firstName = "none";
    	}
    	if(password1.equals(password2))
    	{
    		if(!login.loginExists(newEmail))
    		{
    			login.createCustomer(firstName,lastName,newEmail,password1);%>
        <%= "alert('User created and logged in!');" %>
        <%= "window.location = '"+ login.getCameFrom() +"';" %>
        <% } else{  %>
        <%= "alert('Email used already exists in database!');" %>
        <% }} else{ %>
        <%= "alert('Passwords do not match!');" %>
        <% }}%>
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
					<TD HEIGHT=22 BGCOLOR="#D3D4C5" ALIGN=CENTER NOWRAP>&nbsp;&nbsp;Login&nbsp;&nbsp;</TD>					
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

		<B><FONT SIZE="+2">Login</FONT></B>
		</FONT></TD>
		</TR>
	</TABLE>
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="1" ALT=""></TD></TR></TABLE>
	<TABLE CELLSPACING="0" CELLPADDING="3" WIDTH="100%" BORDER="0" BGCOLOR="#EEEED4">
	<TR><TD><FONT FACE="Lucida,Verdana,Helvetica,Arial">&nbsp;

	<form action="login.jsp" method="POST" >
      <P><font face="Lucida,Verdana,Helvetica,Arial"> <STRONG> Existing customer? </STRONG> 
		<BR> <BR> Email Address:<BR> <input type="text" name="existingEmail" size="30">
		<BR> <BR> Password: <BR> <input type="password" name="existingPassword" size="30"> <BR> <BR>
		</font></P>
      <p> 
        <input type="submit" name="SubmitExisting" value="Submit" />
      </p>
    </form>
	<A href="login.jsp?forgottenPassword=true">Forgotten Password? </A>
	</FONT>
	
	
	</TD></TR>
</TABLE>
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="1" ALT=""></TD></TR></TABLE>
	<TABLE CELLSPACING="0" CELLPADDING="3" WIDTH="100%" BORDER="0" BGCOLOR="#EEEED4">
	<TR><TD><FONT FACE="Lucida,Verdana,Helvetica,Arial">&nbsp;

	</FONT>
	<form action="login.jsp" method="POST" >
      <P><font face="Lucida,Verdana,Helvetica,Arial"> <STRONG> New Customer? </STRONG> 
        </BR> </BR> First Name:	</BR> <input type="text" name="firstName" size="30">
		</BR> </BR> Last Name*: </BR> <input type="text" name="lastName" size="30"> </BR> 
		</BR> Email Address*: </BR> <input type="text" name="newEmail" size="30">
		 </BR> </BR> Password*: </BR> <input type="password" name="password1" size="30">
		</BR> </BR> Retype Password*: </BR> <input type="password" name="password2" size="30"> </BR> </BR>
		</font></P>
      <p> 
        <input type="submit" name="SubmitExisting" value="Submit" />
      </p>
      <BR><BR>
      * Required Fields
    </form>
	
	
	</TD></TR>
	</TABLE>
	
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0"><TR BGCOLOR="#000000"><TD><IMG SRC="images/1x1.gif" WIDTH="1" HEIGHT="1" ALT=""></TD></TR></TABLE>
</TD></TR>

<P>Bret Lowrey, CS122B, 2009 </P>

</BODY>
</HTML>

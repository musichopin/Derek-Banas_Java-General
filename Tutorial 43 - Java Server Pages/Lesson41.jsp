<!-- A Java Server Page is an html file with Java embedded using tags -->
<!-- This is a JSP Directive that sets options and imports libraries -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Another JSP Directive -->

<%@ page import="java.lang.Math.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<!-- This is a Scriptlet Java code inserted in the html -->
<!-- You can enter any core Java code between these tags -->

<% out.print("Todays Date: "); %>

<!-- This is an Expression code that evaluates to a String -->

<%=new java.util.Date() %>

<!-- You can create fields and methods in Declarations -->

<%! int number1, number2; %>
<br /><br />

<!-- I create a form that will talk to this page -->

<form action="calculatejsp.jsp" method="post">

Enter 2 Numbers:
<input type="text" name="numone" />

<input type="text" name="numtwo" />

<input type="submit" value="Calculate" />

</form>

The Answer:

<!-- JSP expression code that prints the values of the fields -->

<%= request.getParameter("numone") %>
+
<%= request.getParameter("numtwo") %>

<!-- Another Scriplet block of code -->

<% 
// Grab the value of numone & numtwo and convert them to ints

try{
number1 = Integer.parseInt(request.getParameter("numone"));
number2 = Integer.parseInt(request.getParameter("numtwo"));
int sumOfNums = number1 + number2;
out.print(" = " + sumOfNums); 
}
catch (NumberFormatException ex)
{
  // do something with the exception
}
%>


</body>
</html>
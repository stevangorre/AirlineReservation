<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ARS Login</title>
<link href="./css/style.css" rel="stylesheet" />
</head>
<body>
<h1>Airline Reservation System</h1>
<h2>Login Page</h2>
<h3 class='error'>${error}</h3>
<form:form method="post" action="processLogin.obj" modelAttribute="user">
<table>
<tr>
<td>Username  ::</td>
<td><form:input path="userName"/><br/><form:errors path="userName" cssClass="error"/></td>
</tr>
<tr>
<td>Password ::</td>
<td><form:password path="password"/><br/><form:errors path="password" cssClass="error"/></td>
</tr>
<tr>
<td colspan="2"><input type="submit" value="Login">
</table>
</form:form>
<br/>
<br/>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ARS Login</title>
<link href="./css/style.css" rel="stylesheet" />
</head>
<body>
<div id='main'></div>
<h1 class='head'>Airline Reservation System</h1>
<hr width='75%'/>
<h3 class='error'>${error}</h3>
<br/><br/><br/>
<a class='link' href='index.obj'>Login</a>&nbsp;&nbsp;
<a class='link' href='register.obj'>Register</a>
<br/>
<br/>
<c:if test="${not empty user}">
<h2>Login</h2>
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
<td colspan="2"><input class='button' type="submit" value="Login">
</table>
</form:form>
</c:if>
<c:if test="${not empty userBeanForRegister}">
<h2>Register Page</h2>
<form:form method="post" action="processRegister.obj" modelAttribute="userBeanForRegister">
<table>
<tr>
<td>Username    ::</td>
<td><form:input path="userName"/><br/><form:errors path="userName" cssClass="error"/></td>
</tr>
<tr>
<td>Password  ::</td>
<td><form:password path="password"/><br/><form:errors path="password" cssClass="error"/></td>
</tr>
<tr>
<td>Confirm Password  ::</td>
<td><form:password path="confirmPassword"/><form:errors path="confirmPassword" cssClass="error"/></td>
</tr> 
<tr>
<td>Mobile Number ::</td>
<td><form:input path="mobileNo"/><br/><form:errors path="mobileNo" cssClass="error"/></td>
</tr>
<tr>
<td><input class='button' type="submit" value="Register"/></td>
<td><input class='button' type="reset" value="clear"/></td>
</tr>
</table>
</form:form>
</c:if>
<c:if test="${not empty uid}">
<h2 class='success'>Registered Successfully Mr. ${uid}</h2>
</c:if>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ARS Register</title>
<link href="./css/style.css" rel="stylesheet" />
</head>
<body>
<h1>Register Page</h1>
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
<%-- <tr>
<td>Confirm Password  ::</td>
<td><form:password path="confirmPassword"/><form:errors path="confirmPassword" cssClass="error"/></td>
</tr> --%>
<tr>
<td>Mobile Number ::</td>
<td><form:input path="mobileNo"/><br/><form:errors path="mobileNo" cssClass="error"/></td>
</tr>
<tr>
<td><input type="submit" value="Register"/></td>
<td><input type="reset" value="clear"/></td>
</tr>
</table>
</form:form>
<c:if test="${not empty uid}">
<p>Registered Successfully Mr. ${uid}</p>
</c:if>
</body>
</html>
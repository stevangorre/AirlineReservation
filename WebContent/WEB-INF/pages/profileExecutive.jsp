<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<h1>Airline Reservation System - Executive Page</h1><hr/><br/>
<h3>Welcome ${user.userName}</h3>
<h4>${error}</h4>
<h4 class='success'>${message}</h4>
<h2>view occupancy on a flight with in a period</h2>
<form action="processExecOccup.obj" method='post'>
<input type='hidden' name='email' value='${user.userName}' />
<label>Enter start date of desired period</label>
<input type='date' name='startDate' /><br/><br/>
<label>Enter end date of desired period</label>
<input type='date' name='endDate' /><br/><br/>
<input type='submit' value='Fetch' />
</form>
<br/><br/>

<c:if test="${not empty execOccup}">
<h4>Occupancy details for the given period are as follows </h4>
<table>
<tr>
<th>Flight Number</th>
<th>Total seats</th>
<th>Occupied seats</th>
</tr>
<c:forEach items="${execOccup}" var="m">
<tr>
<td>${m.flightNo}</td>
<td>${m.firstSeats}</td>
<td>${m.bussSeats}</td>
</tr>
</c:forEach>
</table>
</c:if>
</body>
</html>
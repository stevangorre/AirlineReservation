<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Executive Page</title>
<link href="./css/style.css" rel="stylesheet" />
</head>
<body>
<div id='main'></div>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<h1 class='head'>Airline Reservation System</h1><hr width='75%'/><br/>
<h3>Logged in as Executive</h3>
<h4 class='error'>${error}</h4>
<h4 class='success'>${message}</h4>
<br/><br/>
<h2>View Occupancy of flights</h2>
<a class='link' href="ViewOccupancy.obj?custEmail=${user.userName }"> between cities</a>&nbsp;&nbsp;
<a class='link' href="ViewOccupancyWithDates.obj?custEmail=${user.userName }">in a given period</a>&nbsp;&nbsp;
<a class='link' href='logout.obj'>Logout</a>
<br/>
<br/><br/><br/>
<c:if test="${not empty bookingObject}">
<form:form method="post" action="processViewOccupancy.obj" modelAttribute="bookingObject">
<input type='hidden' name='email' value='${user.userName}' />
<table>
<tr>
<td>Source City</td>
<td>
<td><form:select path="depCity">
<form:option value="" label="--SELECT--"/>
<form:options items="${airList}"/>
</form:select>
</td>
</tr>
<tr>
<td>Destination City</td>
<td>
<td><form:select path="arrCity">
<form:option value="" label="--SELECT--"/>
<form:options items="${airList}"/>
</form:select>
</td>
</tr>
<tr>
<td><input class='button' type="submit" value="view occupancy"/></td>
<td><input class='button' type="reset" value="clear"/></td>
</tr>
</table>
</form:form>
</c:if>
<br>
<br>
<c:if test="${not empty occupList}">
<table>
<c:forEach items="${occupList}" var="m">
<tr>
<td>${m.flightNo}</td>
<td>${m.firstSeats}</td>
<td>${m.bussSeats}</td>
</tr>
</c:forEach>
</table>
</c:if>
<c:if test="${not empty show}">
<form action="processExecOccup.obj" method='post'>
<input type='hidden' name='email' value='${user.userName}' />
<label>Enter start date of desired period</label>
<input type='date' name='startDate' /><br/><br/>
<label>Enter end date of desired period</label>
<input type='date' name='endDate' /><br/><br/>
<input class='button' type='submit' value='Fetch' />
</form>
</c:if>
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
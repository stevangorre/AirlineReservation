<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Page</title>

<link href="./css/style.css" rel="stylesheet" />
</head>
<body>
<div id='main'></div>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<h1 class='head'>Airline Reservation System</h1><hr width='75%'/><br/>
<h3>Logged in as  Admin <%-- ${user.role} --%></h3>
<h4 class='error'>${error}</h4>
<h4 class='success'>${message}</h4>
<br/><br/>
<a class='link' href="ChangeSchedule.obj">Change Schedule</a>&nbsp;&nbsp;
<a class='link' href="AddAirport.obj">Add destination</a>&nbsp;&nbsp;
<a class='link'  href="getAddFlight.obj">Add Flight</a>&nbsp;&nbsp;
<a class='link' href="showReport.obj">Generate reports</a>&nbsp;&nbsp;
<a class='link' href='logout.obj'>Logout</a>
<br/><br/><br/>
<a class='link' href="adminOccup.obj" >Occupancy based on airline &amp; class</a>&nbsp;&nbsp;
<a class='link' href="viewDests.obj" >View Destinations</a>&nbsp;&nbsp;
<a class='link' href="viewAllFlights.obj" >View Flights</a>&nbsp;&nbsp;
<br/><br/><br/>
<c:if test="${not empty show}">
<a class='link' href="FlightstoDest.obj">Flights to destination</a>&nbsp;&nbsp;
<a class='link' href="FlightsOnDate.obj">Flights on date</a>&nbsp;&nbsp;
</c:if>
<br/>
<br/><br/><br/>

<c:if test="${not empty airport}">
<form:form method="post" action="processAddDestination.obj" modelAttribute="airport">
<table>
<tr>
<td>Airport Code  ::</td>
<td><form:input path="abbr"/><br/><form:errors path="abbr" cssClass="error" /></td>
</tr>
<tr>
<td>Airport Name   ::</td>
<td><form:input path="airportName"/><br/><form:errors path="airportName" cssClass="error" /></td>
</tr>
<tr>
<td>Airport Location</td>
<td><form:input path="location"/><br/><form:errors path="location" cssClass="error" /></td>
</tr>
<tr>
<td><input class='button' type="submit" value="Add"></td>
<td><input class='button' type="reset" value="clear"></td>
</tr>
</table>
</form:form>
</c:if>

<c:if test="${not empty flight}">
<form:form method="post" action="processAddFlight.obj" modelAttribute="flight">
<table>
<tr>
<td>Flight Number</td>
<td><form:input path="flightNo" required='true' /><form:errors path="flightNo"/></td>
</tr>
<tr>
<td>Airline  ::</td>
<td><form:input path="airLine" required='true' /><form:errors path="airLine"/></td>
</tr>
<tr>
<td>Source city</td>
<td><form:select path="depCity" required='true' >
<form:option value="" label="--SELECT--"/>
<form:options items="${destList}"/>
</form:select>
<form:errors path="depCity"/></td>
</tr>
<tr>
<td>Destination city</td>
<td><form:select path="arrCity" required='true' >
<form:option value="" label="--SELECT--"/>
<form:options items="${destList}"/>
</form:select>
<form:errors path="arrCity"/></td>
</tr>
<tr>
<td>Departure date</td>
<td><form:input path="depDate" type="date" required='true' /><form:errors path="depDate"/></td>
</tr>
<tr>
<td>Arrival date</td>
<td><form:input path="arrDate" type="date" required='true' /><form:errors path="arrDate"/></td>
</tr>
<tr>
<td>Departure Time</td>
<td><form:input path="depTime" required='true' /><form:errors path="depTime"/></td>
</tr>
<tr>
<td>Arrival Time</td>
<td><form:input path="arrTime" required='true' /><form:errors path="arrTime"/></td>
</tr>
<tr>
<td>Number of firstclass seats</td>
<td><form:input path="firstSeats" required='true' /><form:errors path="firstSeats"/></td>
</tr>
<tr>
<td>Firstclass seats fare</td>
<td><form:input path="firstSeatFare" required='true' /><form:errors path="firstSeatFare"/></td>
</tr>
<tr>
<td>Number of business class seats</td>
<td><form:input path="bussSeats" required='true' /><form:errors path="bussSeats"/></td>
</tr>
<tr>
<td>Business class fare</td>
<td><form:input path="bussSeatsFare" required='true' /><form:errors path="bussSeatsFare"/></td>
</tr>
<tr>
<td><input class='button' type="submit" value="add flight"/></td>
<td><input class='button' type="reset" value="clear"/></td>
</tr>
</table>
</form:form>
</c:if>

<c:if test="${not empty destsList}">
<form action="processFlightsTodest.obj" method="post">
Select Destination to view  ::
<br/>
<select name="destin" required>
<option value="">--SELECT--</option>
<c:forEach items="${destsList}" var="m">
<option value="${m}">${m}</option>
</c:forEach>
</select>
<br/>
<br/>
<input class='button' type="submit" value="get Flights"/>
</form>
</c:if>

<c:if test="${not empty filtered}">
<table>
<tr>
<th>Flight Number</th>
<th>Airline</th>
<th>Departure City</th>
<th>Arrival city</th>
<th>Departure date</th>
<th>Arrival date</th>
<th>departure time</th>
<th>Arrival time</th>
<th>FirstClass seats</th>
<th>Firstclass fare</th>
<th>Business seats</th>
<th>Business seats fare</th>
</tr>
<c:forEach items="${filtered}" var="m">
<tr>
<td>${m.flightNo}</td>
<td>${m.airLine}</td>
<td>${m.depCity}</td>
<td>${m.arrCity}</td>
<td>${m.depDate}</td>
<td>${m.arrDate}</td>
<td>${m.depTime}</td>
<td>${m.arrTime}</td>
<td>${m.firstSeats}</td>
<td>${m.firstSeatFare}</td>
<td>${m.bussSeats}</td>
<td>${m.bussSeatsFare}</td>
</tr>
</c:forEach>
</table>

</c:if>

<c:if test="${not empty flights}">
<form action="processPassangersOfFlights.obj" method="post">
Select Flight No of which passengers list is to be displayed::
<br/>
<select name="flightno" required>
<option value="">--SELECT--</option>
<c:forEach items="${flights}" var="m">
<option value="${m}">${m}</option>
</c:forEach>
</select><br/><br/>
<input class='button' type="submit" value="get Passangers List"/>
</form>
</c:if>

<c:if test="${not empty passangers}">
<table>
<tr><th>List of Passengers</th></tr>
<c:forEach items="${passangers}" var="m">
<tr><td>${m}</td></tr>
</c:forEach>

</table>
</c:if>

<c:if test="${not empty forDate}">

<form:form method="post" action="processFLightsOnDate.obj" modelAttribute="forDate">
Enter the date on which flights are present::
<br/><form:input type="date" path="depDate"/><br/><br/>
<input class='button' type="submit" value="get FLights"/>
</form:form>
<br/>
<c:if test="${not empty flightondate}">
<h3>Flights on ${dater} are </h3>
<table>
<tr>
<th>Flight Number</th>
<th>Airline</th>
<th>Departure City</th>
<th>Arrival city</th>
<th>Departure date</th>
<th>Arrival date</th>
<th>departure time</th>
<th>Arrival time</th>
<th>FirstClass seats</th>
<th>Firstclass fare</th>
<th>Business seats</th>
<th>Business seats fare</th>
</tr>
<c:forEach items="${flightondate}" var="m">
<tr>
<td>${m.flightNo}</td>
<td>${m.airLine}</td>
<td>${m.depCity}</td>
<td>${m.arrCity}</td>
<td>${m.depDate}</td>
<td>${m.arrDate}</td>
<td>${m.depTime}</td>
<td>${m.arrTime}</td>
<td>${m.firstSeats}</td>
<td>${m.firstSeatFare}</td>
<td>${m.bussSeats}</td>
<td>${m.bussSeatsFare}</td>
</tr>
</c:forEach>
</table>
</c:if>
</c:if>

<c:if test="${not empty flightList}">

<table>
<thead>
<tr><th>Flight No</th><th>Airline</th>
<th>Departure City</th>
<th>Arrival city</th>
<th>Departure date</th>
<th>Arrival date</th>
<th>departure time</th>
<th>Arrival time</th>
<th>Remove</th>
<th>Passengers</th>
<th>Bookings</th>
</tr>
</thead>
<tbody>
<c:forEach items="${flightList}" var="m">
<tr><td>${m.flightNo}</td>
<td>${m.airLine}</td>
<td>${m.depCity}</td>
<td>${m.arrCity}</td>
<td>${m.depDate}</td>
<td>${m.arrDate}</td>
<td>${m.depTime}</td>
<td>${m.arrTime}</td>
<td>
<a class='change' href='processRemoveFlight.obj?flightNo=${m.flightNo}'>Remove</a>
</td>
<td>
<a class='change' href='processPassangersOfFlights.obj?flightNo=${m.flightNo}'>Passengers</a>
</td>
<td>
<a class='change' href='displayBookings.obj?flightNo=${m.flightNo}'>Bookings</a>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</c:if>
<c:if test="${not empty destinsList}">
<h3>Destinations</h3>
<table>
<c:forEach items="${destinsList}" var="m">
<tr><td>${m}</td></tr></c:forEach>
</table>
</c:if>

<!-- display bookings -->

<c:if test="${not empty booklist}">
<table>
<thead>
	<tr>
		<th>Booking ID</th>
		<th>Number of Passengers </th>
		<th>Class Type </th>
		<th>Total Fare </th>
		<th>Seat Number</th>
		<th>Card Info </th>	
	</tr>
</thead>
<tbody>
<c:forEach items="${booklist}" var="booking">
	<tr>
		<td>${booking.bookingId}</td>
		<td>${booking.numOfPass}</td>
		<td>${booking.classType}</td>
		<td>${booking.totalFare}</td>
		<td>${booking.seatNumbers}</td>
		<td>${booking.cardInfo}</td>
	</tr>
</c:forEach>	
</tbody>
</table>
</c:if>

<!--  admin occupancy -->

<c:if test="${not empty airlines}">
<form method="post" action="processadminoccupancy.obj">
Select airline<br/>
<select name="airline" required>
<option value="">--SELECT--</option>
<c:forEach items="${airlines}" var="m">
<option value="${m}">${m}</option>
</c:forEach>
</select>
<br><br/>
Select class ::<br/>
<input type="radio" value="firstClass" name="clas" required>First Class
<input type="radio" value="Business" name="clas">Business Class
<br/><br/>
<input class='button' type="submit" value="get occupancy">
</form>
</c:if>

<c:if test="${not empty adminOccup}">
<h4>Occupancy details of ${airline} ${clas} are as follows </h4>
<table>
<tr>
<th>Flight Number</th>
<th>Total seats</th>
<th>Occupied seats</th>
</tr>
<c:forEach items="${adminOccup}" var="m">
<tr>
<td>${m.flightNo}</td>
<td>${m.firstSeats}</td>
<td>${m.bussSeats}</td>
</tr>
</c:forEach>
</table>
</c:if>

<c:if test="${not empty fList}">
<table>
<thead>
<tr><th>Flight No</th>
<th>Airline</th>
<th>Departure date</th>
<th>Arrival date</th>
<th>departure time</th>
<th>Arrival time</th>
<th>Change</th>
</tr>
</thead>
<tbody>
<c:forEach items="${fList}" var="m">
<tr><td>${m.flightNo}</td>
<td>${m.airLine}</td>
<td>${m.depDate}</td>
<td>${m.arrDate}</td>
<td>${m.depTime}</td>
<td>${m.arrTime}</td>
<td>
<a class='change' href='procesChangeSchedule.obj?flightNo=${m.flightNo}'>Change</a>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</c:if>

<c:if test="${not empty flightForChange}">
<form:form method="post" action="processFinalChangeSchedule.obj" modelAttribute="flightForChange">
<table>
<tr>
<td>Flight Number</td>
<td><form:input path="flightNo" readonly="true"/><form:errors path="flightNo"/></td>
</tr>
<tr>
<td>Airline  ::</td>
<td><form:input path="airLine" readonly="true"/><form:errors path="airLine"/></td>
</tr>
<tr>
<td>Departure date</td>
<td><form:input path="depDate" class='future' min='' type="date"/><form:errors path="depDate"/></td>
</tr>
<tr>
<td>Arrival date</td>
<td><form:input path="arrDate" class='future' min='' type="date"/><form:errors path="arrDate"/></td>
</tr>
<tr>
<td>Departure Time</td>
<td><form:input path="depTime"/><form:errors path="depTime"/></td>
</tr>
<tr>
<td>Arrival Time</td>
<td><form:input path="arrTime"/><form:errors path="arrTime"/></td>
</tr>
<tr>
<td><input class='button' type="submit" value="Change Schedule"/></td>
<td><input class='button' type="reset" value="clear"/></td>
</tr>
</table>
</form:form>
</c:if>
<script>
var d=new Date();
var days=d.getDate();
var month=d.getMonth()+1;
var year=d.getFullYear();
var str=""+year+"-"+""+month+"-"+days;
var depDate1=document.getElementsByClassName('future')[0];
var depDate2=document.getElementsByClassName('future')[1];
depDate1.setAttribute("min",str);
depDate2.setAttribute("min",str);
</script>
</body>
</html>
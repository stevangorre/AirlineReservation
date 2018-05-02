<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<link href="./css/style.css" rel="stylesheet" />
</head>
<body>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<h1>Airline Reservation System</h1><hr/><br/>
<h3>Welcome ${user.userName}</h3>
<h4>${error}</h4>
<h4 class='success'>${message}</h4>
<ul>
<li><a href='book.obj'>Book a flight</a></li>
<li><a href='view.obj?custEmail=${user.userName}'>View/Update Bookings</a></li>
<li><a href='update.obj?custEmail=${user.userName}'>Edit Profile</a></li>
<li><a href='logout.obj'>Logout</a></li>
</ul>
<br/>
<br/>
<c:if test="${not empty userUpdate}">
<form:form action="processUpdateProfileForm.obj" method="post" modelAttribute="userUpdate">
<table>
<tr><td>Name :</td>
<td><form:hidden path="userName" />${userUpdate.userName}</td></tr>
<tr><td>Password :</td>
<td><form:input type='password' path="password" /><br/><form:errors path="password" cssClass="error"/></td></tr>

<tr><td>Mobile No. :</td>
<td><form:input path="mobileNo" /><br/><form:errors path="mobileNo" cssClass="error"/></td></tr>
<tr><td colspan="2"><input type="submit" value="Update Profile"> </td></tr>

</table>
</form:form>
</c:if>
<c:if test="${not empty bookings}">
<table>
<thead>
	<tr>
		<th>Flight No</th>
		<th>Booking ID</th>
		<th>Number of Passangers </th>
		<th>Class Type </th>
		<th>Total Fare </th>
		<th>Seat Number</th>
		<th>Card Info </th>
		<th>Departure city </th>
		<th>Arrival City</th>
		<th>Departure Date </th>	
		<th> </th>
		<th> </th>
		<th> </th>	
	</tr>
</thead>
<tbody>
<c:forEach items="${bookings}" var="booking">
	<tr>
		<td>${booking.flightNo}</td>
		<td>${booking.bookingId}</td>
		<td>${booking.numOfPass}</td>
		<td>${booking.classType}</td>
		<td>${booking.totalFare}</td>
		<td>${booking.seatNumbers}</td>
		<td>${booking.cardInfo}</td>
		<td>${booking.depCity}</td>
		<td>${booking.arrCity}</td>
		<td>${booking.depDate}</td>
		<td><a href="">Update Booking</a></td>
		<td><a href="cancelBooking.obj?bookingId=${booking.bookingId}">Cancel Booking</a></td>
		<td><a href="">Reschedule Booking</a></td>
	</tr>
</c:forEach>	
</tbody>
</table>
</c:if>
</body>
</html>
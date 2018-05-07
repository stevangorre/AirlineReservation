<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ARS</title>
<link href="./css/style.css" rel="stylesheet" />
</head>
<body>
<div id='main'></div>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%-- <c:if test="${not empty user}"><c:redirect url="home.jsp"></c:redirect></c:if> --%>
<h1 class='head'>Airline Reservation System</h1><hr width='75%'/><br/>
<h3>Welcome ${user.userName}</h3>
<h4 class='error'>${error}</h4>
<h4 class='success'>${message}</h4>
<br/><br/>
<a class='link' href='viewFlights.obj?custEmail=${user.userName}'>Book a flight</a>&nbsp;&nbsp;
<a class='link' href='view.obj?custEmail=${user.userName}'>View/Update Bookings</a>&nbsp;&nbsp;
<a class='link' href='update.obj?custEmail=${user.userName}'>Edit Profile</a>&nbsp;&nbsp;
<a class='link' href='logout.obj'>Logout</a>
<br/>
<br/><br/><br/>
<c:if test="${not empty userUpdate}">
<form:form action="processUpdateProfileForm.obj" method="post" modelAttribute="userUpdate">
<table>
<tr><td>Name :</td>
<td><form:hidden path="userName" />${userUpdate.userName}</td></tr>
<tr><td>Password :</td>
<td><form:input type='password' path="password" /><br/><form:errors path="password" cssClass="error"/></td></tr>

<tr><td>Mobile No. :</td>
<td><form:input path="mobileNo" /><br/><form:errors path="mobileNo" cssClass="error"/></td></tr>
<tr><td colspan="2"><input class='button' type="submit" value="Update Profile"> </td></tr>

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
		<th>Update</th>
		<th>Cancel</th>
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
		<td><a class='change' href="updateBooking.obj?bookingId=${booking.bookingId}">Update</a></td>
		<td><a class='change' href="cancelBooking.obj?bookingId=${booking.bookingId}">Cancel</a></td>
	</tr>
</c:forEach>	
</tbody>
</table>
</c:if>
<c:if test="${not empty booking}">
<form:form action="processUpdateUpdateBooking.obj" method="post" modelAttribute="booking">
<table>
<tr><td>Booking Id :</td>
<td><form:hidden path="bookingId" />${booking.bookingId}</td></tr>
<tr><td>Number of Passengers</td>
<td><form:input path="numOfPass" type="number" min="1" max="5"/>
<form:errors path="numOfPass" cssClass="error"/>
</td></tr>

<tr><td>Preferred class</td>
<td>
<form:radiobutton label="FirstClass" value="firstClass" path="classType"/>
<form:radiobutton label="Business Class" value="Business" path="classType"/>
<form:errors path="classType" cssClass="error"/>
</td></tr>
<tr><td colspan="2"><input class='button' type="submit" value="Update Booking"> </td></tr>

</table>
</form:form>
</c:if>

<c:if test="${not empty flightObj}">
<form:form  action="processViewFlights.obj"  method="post" modelAttribute="flightObj">
<table>
<tr>
<td>Source</td>
<td>
<form:select path="depCity"><form:option value="" label="Please Select" />
<form:options items="${destList}"/></form:select><br/><form:errors path="depCity" cssClass="error"/>
</td>
</tr>
<tr>
<td>Destination</td>
<td>
<form:select path="arrCity"><form:option value="" label="Please Select" />
<form:options items="${destList}"/></form:select><br/><form:errors path="arrCity" cssClass="error"/>
</td>
</tr>
 <tr>
<td>Departure date</td>
<%--<td>
<form:input path="depdate" min="" id="depdate" required="true" />
<form:errors path="depdate" cssClass="error"/></td>--%>
<td>
<input type="date" name="depdate" min="" id="depdate" value='' required /></td>
</tr> 
<tr>
<td><input class='button' type="submit" value="View Flights"/></td>
<td><input class='button' type="reset" value="clear"></td>
</tr>
</table>
</form:form>
</c:if>
<br>
<br>
<c:if test="${not empty flist}">
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
<th>Book</th>
</tr>
<c:forEach items="${flist}" var="m">
<tr>
<td>${m.flightNo}</td>
<td>${m.airLine}</td>
<td>${m.depCity}</td>
<td>${m.arrCity}</td>
<td>${m.depDate}</td>
<td>${m.arrDate}</td>
<td>${m.depTime }</td>
<td>${m.arrTime}</td>
<td>${m.firstSeats}</td>
<td>${m.firstSeatFare}</td>
<td>${m.bussSeats}</td>
<td>${m.bussSeatsFare}</td>
<td>
<c:if test="${m.firstSeats>0 || m.bussSeats>0}">
<a class='change' href="BookFlight.obj?fnum=${m.flightNo}&custEmail=${user.userName}">Book</a>
</c:if>
</td>
</tr>
</c:forEach>
</table>
</c:if>

<c:if test="${not empty bookingObj}">
<h1>Book Fight</h1>
<form:form action="processBookFlight.obj" method="post" modelAttribute="bookingObj">
<table>
<tr><td>User Name :</td>
<td><form:hidden path="custEmail"  value='${user.userName}'/>${user.userName}
<form:hidden path="depCity"  value='${bookingObj.depCity}'/>
<form:hidden path="depDate"  value='${bookingObj.depDate}'/>
<form:hidden path="arrCity"  value='${bookingObj.arrCity}'/></td></tr>
<tr>
<td>Flight Number</td>
<td><form:input path="flightNo" value="${fno}"  readonly="true"/>
<form:errors path="flightNo"/>
</td>
</tr>
<tr>
<td>Preferred class</td>
<td>
<form:radiobutton label="First Class" value="firstClass" path="classType"/>
<form:radiobutton label="Business Class" value="Business" path="classType"/>
<form:errors path="classType" cssClass="error"/>
</td>
</tr>
<tr>
<td>Number of Passengers</td>
<td><form:input path="numOfPass" type="number" min="1" max="5"/>
<form:errors path="numOfPass" cssClass="error"/>
</td>
</tr>
<tr>
<td>Credit card number</td>
<td><form:input path="cardInfo"/>
<form:errors path="cardInfo" cssClass="error"/>
</td>
</tr>
<tr>
<td><input class='button' type="submit" value="Book"/></td>
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
var depDate=document.getElementById('depdate');
depDate.setAttribute("min",str);
depDate.value=str;
</script>
</body>
</html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>My jobs :: FreeAgents.eu</title>
		<link rel="stylesheet" href="css.css" /> 
		<link rel="stylesheet" href="signup.css"/>
		<link rel="stylesheet" href="post-job.css"/>
		<link href="https://fonts.googleapis.com/css?family=Oxygen:300,400,700" rel="stylesheet">
</head>
<body>
		<header>
			<div class="flowe-header">
				<a href="index.jsp">
					<div class="flowe-logo"></div>
				</a>
				<div class="flowe-header-menu"></div>
				<div class="header-menu">
					<a href="postjob">Post Job</a>	
					<a href="browsejobs">Browse Job Offers</a>
					<a href="logout">Log Out</a>
					<div class="flowe-blue-button">
						<p class="flowe-signup-button"><a href="profile.jsp"><c:out value="${sessionScope.name}" /></a></p>
					</div>
				</div>
			</div>
		</header>
		<div class="flowe-footer-menu profile">
				<p><a href="mymessages">My Messages</a></p>
				<p><a href="myjobs">My Jobs</a></p>
				<p><a href="myfeedbacks?id=${user.id}">My Feedbacks</a></p>
 		</div>	
		<div id="post-job">
			<h2 id="search-offers">Offers for job</h2>
			<div class="post-job search-job">
				<c:if test="${not empty offers}">
					<c:forEach var="offer" items="${offers}">
						<div class="search-results">
							<div class="result-description">
								<p>Description</p>
								<p>${offer.content}</p>
							</div>
							<div class="result-budjet">
								<p>Budjet</p>
								<p>${offer.price}</p>
							</div>
							<div class="result-title">
								<p>From</p>
								<p><a href="viewprofile?id=${offer.senderUser.id}">${offer.senderUser.firstName} ${offer.senderUser.lastName}</a></p>
							</div>
							<form method="GET" action="acceptoffer">
								<input type="hidden" value="${offer.id}" name="id"/>
								<input type="submit" id="post-job-btn\" value="Accept offer"/>
							</form>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty offers}">
					<h2>There are no offers made for this job!</h2>
				</c:if>
			</div>
			<div class="post-job-account">
			</div>
		</div>
		<footer>
			<div class="flowe-footer-menu">
					<p>Terms Of Service</p>	
					<p>Privacy Policy</p>
					<p class="flowe-blog">Blog</p>
					<p>Careers</p>
					<p class="flowe-faq">FAQ</p>
			</div>
			<div class="flowe-footer">
				<div class="flowe-logo-footer"> 
					<a href="index.jsp">					
						<img src="logo.png">
					</a>
				</div>
				<p>&copy; 2017 Free Agents LTD. All rights reserved.</p>
			</div>
		</footer>
</body>
</html>
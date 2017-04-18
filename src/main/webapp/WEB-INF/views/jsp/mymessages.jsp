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
						<p class="flowe-signup-button"><a href="profile">${sessionScope['name']}</a></p>
					</div>
				</div>
			</div>
		</header>
		<div class="flowe-footer-menu profile">
				<p><a href="mymessages">My Messages</a></p>
				<p><a href="myjobs">My Jobs</a></p>
				<p><a href="myfeedbacks">My Feedbacks</a></p>
 			</div>	
		<div id="post-job">
			<h2 id="search-offers">My Messages</h2>
			<div class="post-job search-job">
				<table style="border: 1px solid black;">
					  <tr>
					    <th>Sender name</th>
					    <th>Title</th> 
					    <th>Date & Time</th>
					  </tr>
					<c:forEach var="message" items="${messages}">
						<tr>
						<td>${message.sender.firstName}&nbsp${message.sender.lastName}</td>
						<td>${message.title}</td>
						<td>${message.date}</td>
						</tr>
					</c:forEach>
				</table>
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
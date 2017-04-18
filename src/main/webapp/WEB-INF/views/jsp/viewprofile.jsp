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
		<div id="signup">
				<label for="first-name">First name</label>
				<p>${userprofile.firstName}</p>
				<label for="last-name">Last name</label>
				<p>${userprofile.lastName}</p>
				<label for="last-name">Location</label>
				<p><c:out value="${country}"/></p>
				<label for="job-title">Job title</label>
				<p>${userprofile.jobTitle}</p>
				<label for="phone">Phone number</label>
				<p>${userprofile.phone}</p>
				<label for="per-hour-rate">Per hour rate ($)</label>
				<p>${userprofile.perHourRate}</p>
				<label for="aboutme">About me</label>
				<p>${userprofile.aboutMe}</p>
				<label for="portfolio">Portfolio</label>
				<p>${userprofile.portfolio}</p>
		</div>
		<form method="POST" action="sendmessage">
			<input type="hidden" value="${userprofile.id}" name="id">
			<input type="submit" id="post-job-btn" value="Send Message" />
		</form>
		
		<form method="POST" action="sendfeedback">
			<input type="hidden" value="${userprofile.id}" name="id">
			<input type="submit" id="post-job-btn" value="Send Feedback" />
		</form>
		
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
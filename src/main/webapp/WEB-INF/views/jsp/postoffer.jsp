<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%boolean logged = false;
if (session.getAttribute("logged") != null || session.getAttribute("user") != null) {
	logged = true;
}%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Post Job :: FreeAgents.eu</title>
		<link rel="stylesheet" href="post-job.css"/>
		<link rel="stylesheet" type="text/css" href="signup.css">
		<link href="https://fonts.googleapis.com/css?family=Oxygen:300,400,700" rel="stylesheet">
	</head>
	<body>
		<header>
			<div class="flowe-header">
				<a href="index.jsp"><div class="flowe-logo"></div></a>
				<div class="flowe-header-menu"></div>
				<div class="header-menu">
					<a href="postjob">Post Job</a>
					<a href="browsejobs">Browse Job Offers</a>
					<a href="logout">Log Out</a>
					<div class="flowe-blue-button">
						<p class="flowe-signup-button"><a href="profile.jsp">${user.getFirstName()}</a></p>
					</div>
				</div>
			</div>
		</header>
		<div id="post-job">
			<h2>Post offer</h2>
			<div class="post-job">
				<form method="POST" action="postoffer">
					<textarea rows="4" cols="50" name="content" placeholder="Enter content here..."></textarea>
					<input type="hidden" value="${id}" name="id">
					<input type="number" name="price" placeholder="Price" required="required"/>
					<input type="submit" id="post-job-btn" value="Post Offer" />
				</form>
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
				<img src="logo.png">
			</div>
			<p>&copy; 2017 Free Agents LTD. All rights reserved.</p>
		</div>
	</footer>
	</body>
</html>
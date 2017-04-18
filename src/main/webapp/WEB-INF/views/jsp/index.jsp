<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="logged" value="false"/>
<c:if test="${not empty sessionScope['logged'] || not empty sessionScope['user']}">
<c:set var="logged" value="true"/>
</c:if>
<!DOCTYPE html>
	<html>
	<head>
		<title>FreeAgents.eu :: online platform for freelancers</title>
		<link rel="stylesheet" href="post-job.css"/>
		<link rel="stylesheet" type="text/css" href="signup.css">
		<link href="https://fonts.googleapis.com/css?family=Oxygen:300,400,700" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	</head>
	<body>
		<!-- header -->
		<header>
			<div class="flowe-header">
				<div class="flowe-logo"></div>
				<div class="flowe-header-menu"></div>
				<div class="header-menu">
					<c:if test="${logged eq true}">
					<a href="postjob">Post Job</a>	
					<a href="browsejobs">Browse All Jobs</a>
					<a href="logout">Log Out</a>
					<div class="flowe-blue-button">
					<p class="flowe-signup-button"><a href="profile">${sessionScope['name']}</a></p>
					</c:if>
					<c:if test="${logged eq false}">
					<a href="LogIn.html">Post Job</a>	
					<a href="LogIn.html">Browse All Jobs</a>
					<a href="LogIn.html">Log In</a>
					<div class="flowe-blue-button">
					<p class="flowe-signup-button"><a href="SignUp.html">Sign Up</a></p>
					</c:if>
					
					</div>
				</div>
			</div>
		</div>
		</header>
		<section>
		<div class="main-description">
			<div class="main-header">
				<p>Get your job done today!</p>
				<p>Post your Job for free and get started within minutes.</p>
			</div>
			<div class="tips">
				<div>
					<span><img src="check.png"></span>
					<p>Hire on demand</p>
					<p>Find global talent when you need it. Stay flexible.</p>
				</div>
				<div>
					<span><img src="check.png"></span>
					<p>Choose from 150k+ skills</p>
					<p>Seamlessly access any skill or expertise at any time.</p>
				</div>
				<div>
					<span><img src="check.png"></span>
					<p>Work with 50k+ qualified Freelancers</p>
					<p>Tap into UK's most trusted freelancing community.</p>
				</div>
			</div>
			
		</div>
		</section>
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
				<img src="Images/flowe-header-logo.png">
			</div>
			<p>&copy; 2017 Free Agents LTD. All rights reserved.</p>
		</div>
	</footer>
</body>
</html>
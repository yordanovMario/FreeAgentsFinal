<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="logged" value="false"/>
<c:if test="${sessionScope['logged'] eq true && not empty sessionScope['user']}">
<c:set var="logged" value="true"/>
</c:if>
<!DOCTYPE html>
	<html>
	<head>
		<title>FreeAgents.eu :: online platform for freelancers</title>
		<link rel="stylesheet" href="css/post-job.css"/>
		<link rel="stylesheet" type="text/css" href="css/signup.css">
		<link rel="stylesheet" type="text/css" href="css/css.css">
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
					<a href="login">Post Job</a>	
					<a href="login">Browse All Jobs</a>
					<a href="login">Log In</a>
					<div class="flowe-blue-button">
					<p class="flowe-signup-button"><a href="signup">Sign Up</a></p>
					</c:if>
					
					</div>
				</div>
			</div>
		</div>
		</header>
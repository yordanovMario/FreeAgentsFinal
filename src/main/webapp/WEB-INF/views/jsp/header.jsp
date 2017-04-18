<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
						<p class="flowe-signup-button"><a href="profile">${user.getFirstName()}</a></p>
					</div>
				</div>
			</div>
		</header>
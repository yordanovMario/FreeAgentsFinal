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
		<div id="post-job">
			<h2>Post Job</h2>
			<div class="post-job">
				<form method="POST" action="postjob">
					<input type="text" name="title" placeholder="Title" required="required"/>
					<!--<input type="textarea" name="description" placeholder="Description" required="required"/> -->
					<textarea rows="4" cols="50" name="description" placeholder="Enter description here..."></textarea>
					<input type="number" name="budget" placeholder="Budget (US $)" required="required"/>
					<select name="expire" class="categories">
						<option value="">Validity of the ad</option>
						<option value="1">7 days</option>
						<option value="2">14 days</option>
						<option value="3">21 days</option>
					</select>
					<select name="category" class="categories">
						<option value="">Select Category</option>
						<c:forEach var="category" items="${categories}">
							<option value="${category.key}">${category.value}</option>
						</c:forEach>
					</select>
					<select name="reqExp" class="categories">
					  <option value="">Select Experience Level</option>
					  <option value="1">Beginner</option>
					  <option value="2">Advanced</option>
					  <option value="3">Expert</option>
					</select>
					
					<input type="submit" id="post-job-btn" value="Post Job" />
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
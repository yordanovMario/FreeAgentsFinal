<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<html>
	<head>
		<title>${user.getFirstName()}'s profile in FreeAgents.eu</title>
	 	<link rel="stylesheet" href="post-job.css"/>
	 	<link rel="stylesheet" href="css.css"/>
		<link rel="stylesheet" type="text/css" href="signup.css">
		<link href="https://fonts.googleapis.com/css?family=Oxygen:300,400,700" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	</head>
	<body>
		<!-- header -->
		<header>
			<div class="flowe-header">
				<a href="index.jsp"><div class="flowe-logo"></div></a>
				<div class="flowe-header-menu"></div>
				<div class="header-menu">
					<a href="postjob">Post Job</a>	
					<a href="browsejobs">Browse Job Offers</a>
					<a href="logout">Log Out</a>
					<div class="flowe-blue-button">
						<p class="flowe-signup-button">${user.getFirstName()}</p>
					</div>
				</div>
			</div>
		</header>
		<section>
		<div class="main-description">
		<div class="main-header">
		<p>${user.getFirstName()}&nbsp${user.getLastName()}</p>
		</div>
		<div class="tips">
		<div>
			<div class="flowe-footer-menu profile">
				<p><a href="mymessages">My Messages</a></p>
				<p><a href="myjobs">My Jobs</a></p>
				<p><a href="myfeedbacks?id=${user.id}">My Feedbacks</a></p>
 			</div>				
			<div id="signup">
				<form method="POST" action="editdata">
				<label for="first-name">First name</label>
				<input type="text" id="first-name" name="firstname" value="${user.firstName}"/>
				<label for="last-name">Last name</label>
				<input type="text" id="last-name" name="lastname" value="${user.lastName}">
				<label for="country">Location</label>
				<select name="country" class="categories">
					<option value="">Select Country</option>
					<c:forEach var="country" items="${countries}">
						<c:if test="${user.country == country.key}">
							<option selected="selected" value="${country.key}">${country.value}</option>
						</c:if>
						<c:if test="${user.country != country.key}">
							<option value="${country.key}">${country.value}</option>
						</c:if>
					</c:forEach>
				</select>
				<label for="job-title">Job title</label>
				<input type="text" id="job-title" name="jobtitle" value="${user.jobTitle}"/><br>
				<label for="phone">Phone number</label>
				<input type="text" id="phone" name="phone" value="${user.phone}"/><br>
				<label for="per-hour-rate">Per hour rate ($)</label>
				<input type="number" id="per-hour-rate" name="perhourrate" value="${user.perHourRate}"/>
				<label for="aboutme">About me</label>
				<textarea rows="4" cols="50" id="aboutme" name="aboutme">${user.aboutMe}</textarea>
				<label for="portfolio">Portfolio</label>
				<textarea rows="4" cols="50" id="portfolio" name="portfolio">${user.portfolio}</textarea>
				<input type="submit" id="post-job-btn" value="Edit my data" />
				</form>
			</div>
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
				<img src="logo.png">
			</div>
			<p>&copy; 2017 Free Agents LTD. All rights reserved.</p>
		</div>
	</footer>
</body>
</html>

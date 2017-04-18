<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Browse all jobs :: FreeAgents.eu</title>
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
					<a href="#">Browse Job Offers</a>
					<a href="logout">Log Out</a>
					<div class="flowe-blue-button">
						<p class="flowe-signup-button"><a href="profile">${user.firstName}</a></p>
					</div>
				</div>
			</div>
		</header>
		<div id="post-job">
			<h2 id="search-offers">Search offers</h2>
			<div class="post-job search-job">
				<form method="GET" action="browsejobs">
					<select name="category" class="categories">
						<option value="">Select Category</option>
						<c:forEach var="category" items="${categories}">
							<c:if test="${category.key == categoryID}">
								<option selected="selected" value="${category.key}">${category.value}</option>
							</c:if>
							<c:if test="${category.key != categoryID}">
								<option value="${category.key}">${category.value}</option>
							</c:if>
						</c:forEach>
					</select>
					<select name="sort" class="categories">
					  <option value="">Order by</option>
					  <option value="2">Price Ascending</option>
					  <option value="3">Price Descending</option>
					  
					</select>
					
					<input type="submit" id="post-job-btn" value="Show Results" />
				</form>
			</div>
			
			<c:forEach var="job" items="${jobs}">
				<div class="search-results">
					<div class="result-title">
						<p>Title</p>
						<p>${job.title}</p>
					</div>
					<div class="result-budjet">
						<p>Budjet</p>
						<p>${job.budget}</p>
					</div>
					<div class="result-description">
						<p>Description</p>
						<p>${job.description}</p>
					</div>
					<div class="result-title">
						<p>From</p>
						<p><a href="viewprofile?id=${job.employer.id}">${job.employer.firstName} ${job.employer.lastName}</a></p>
					</div>
					<form method="POST" action="postoffer">
						<input type="hidden" value="${job.id}"name="id">
						<input type="submit" id="post-job-btn" value="Send Offer" />
					</form>
				</div>
			</c:forEach>
	
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
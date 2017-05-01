<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div class="flowe-footer-menu profile">
			<p><a href="mymessages">My Messages</a></p>
			<p><a href="myjobs">My Jobs</a></p>
			<p><a href="myfeedbacks">My Feedbacks</a></p>
			<p><a href="jobsIwork">Jobs I'm working</a></p>
		</div>	
		<div id="post-job">
			<h2 id="search-offers">Viewing job ${job.title}</h2>
			<div class="post-job search-job view-from">
				<div class="search-results">
					<div class="result-budjet">
						<p>Budget</p>
						<p>${job.budget}</p>
					</div>
					<div class="result-description">
						<p>Description</p>
						<p>${job.description}</p>
					</div>
					<div class="result-description">
						<p>Category</p>
						<p>${categories.get(job.category)}</p>
					</div>
					<div class="result-description">
						<p>Required Experience</p>
						<c:if test="${job.requiredExp == 1}">
						<p>Beginner</p>
						</c:if>
						<c:if test="${job.requiredExp == 2}">
						<p>Intermediate</p>
						</c:if>
						<c:if test="${job.requiredExp == 3}">
						<p>Expert</p>
						</c:if>
					</div>
					<div class="result-title">
						<p>Date posted</p>
						<p>${job.date}</p>
					</div>
					<c:if test="${job.employer.id != sessionScope.user.id}">
					<form method="GET" action="postoffer">
						<input type="hidden" value="${job.id}"name="id">
						<input type="submit" id="post-job-btn" value="Send Offer" />
					</form>
					</c:if>
					
				</div>
		<div class="post-job-account">
		</div>
	</div>
<jsp:include page="footer.jsp" />
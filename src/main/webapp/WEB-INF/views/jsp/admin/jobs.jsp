<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<section>
<div class="main-description">
	<div class="main-header">
		<p>${user.getFirstName()}&nbsp${user.getLastName()}</p>
	</div>
	<div class="tips">
		<div class="flowe-footer-menu profile">
			<p><a href="jobs">Jobs</a></p>
			<p><a href="offers">Offers</a></p>
			<p><a href="messages">Messages</a></p>
			<p><a href="feedbacks">Feedbacks</a></p>
			<p><a href="users">Users</a></p>
		</div>
		<div id="post-job">
			<h2 id="search-offers">All Jobs</h2>
			<div class="post-job search-job">
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
							<p>Status</p>
							<p>${statuses.get(job.status)}</p>
						</div>
						<div class="result-description">
							<p>Date posted</p>
							<p>${job.date}</p>
						</div>
		
						<form method="GET" action="viewjob">
							<input type="hidden" value="${job.id}"name="id">
							<input type="submit" id="post-job-btn" value="View job details" />
						</form>
					</div>
				</c:forEach>
				</div>
			<div class="post-job-account">
		</div>
	</div>
</div>
</div>
</section>
		
<jsp:include page="footer.jsp" />				
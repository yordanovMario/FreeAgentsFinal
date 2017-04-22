<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div class="flowe-footer-menu profile">
				<p><a href="mymessages">My Messages</a></p>
				<p><a href="myjobs">My Jobs</a></p>
				<p><a href="myfeedbacks?id=${user.id}">My Feedbacks</a></p>
 			</div>	
		<div id="post-job">
			<h2 id="search-offers">My Jobs</h2>
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
							<p>Description</p>
							<p>${job.description}</p>
						</div>
						<div class="result-description">
							<p>Status</p>
							<p>${statuses.get(job.status)}</p>
						</div>
						<div class="result-description">
							<p>Date posted</p>
							<p>${job.date}</p>
						</div>
						<c:if test="${job.status eq 2}">
							<form method="GET" action="viewoffers">
								<input type="hidden" value="${job.id}"name="id">
								<input type="submit" id="post-job-btn" value="View offers" />
							</form>
						</c:if>
						<c:if test="${job.status eq 1}">
							<p>There are no offers yet for this job</p>
						</c:if>
						<c:if test="${job.status eq 3}">
						<div class="result-description">
							<p>Offer accepted from</p>
							<a href="viewprofile?id=${job.employer.id}">${job.employer.firstName} ${job.employer.lastName}</a>
							<a href="sendmessage?id=${job.employer.id}">Send message</a>
						</div>
							<form method="GET" action="viewjob">
								<input type="hidden" value="${job.id}"name="id">
								<input type="submit" id="post-job-btn" value="View job details" />
							</form>
						</c:if>
					</div>
				</c:forEach>
				</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
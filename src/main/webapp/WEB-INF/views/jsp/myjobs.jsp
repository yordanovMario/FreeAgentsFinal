<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div class="flowe-footer-menu profile">
			<p><a href="mymessages">My Messages</a></p>
			<p><a href="myjobs">My Jobs</a></p>
			<p><a href="myfeedbacks?id=${user.id}">My Feedbacks</a></p>
			<p><a href="jobsIwork">Jobs I'm working</a></p>
		</div>	
		<div id="post-job">
			<h2 id="search-offers">My Jobs</h2>
			<div class="post-job search-job">
				<c:if test="${not empty jobs}">
					<c:forEach var="job" items="${jobs}">
						<div class="search-results">
							<div class="result-title">
								<p>Title</p>
								<p>${job.title}</p>
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
								<a href="viewprofile?id=${job.worker.id}">${job.worker.firstName} ${job.worker.lastName}</a>
								<a href="sendmessage?id=${job.worker.id}">Contact</a>
							</div>
							</c:if>
							<c:if test="${job.status eq 5}">
								
							</c:if>
							<form method="GET" action="viewjob">
									<input type="hidden" value="${job.id}"name="id">
									<input type="submit" id="post-job-btn" value="View job details" />
							</form>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty jobs}">
					<h2>You haven't posted any jobs yet!</h2>
				</c:if>
				</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
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
			<div class="post-job search-job view-job">
				<div class="search-results">
					<div class="result-title">
						<p>Title</p>
						<p>${job.title}</p>
					</div>
					<div class="result-description">
						<p>Category</p>
						<p>${categories.get(job.category)}</p>
					</div>
					<div class="result-title">
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
					<div class="result-description">
						<p>Offers</p>
						<p>There are no offers yet for this job</p>
					</div>
					</c:if>
					<c:if test="${job.status eq 3}">
					<div class="result-description">
						<p>Offer accepted from</p>
						<a href="viewprofile?id=${job.worker.id}">${job.worker.firstName} ${job.worker.lastName}</a>
						<a href="sendmessage?id=${job.worker.id}">Contact</a>
					</div>
					<form method="POST" action="finishjob">
							<input type="hidden" value="${job.id}"name="id">
							<input type="submit" id="post-job-btn" value="Finish Job" />
					</form>
					</c:if>
					<c:if test="${job.status eq 5}">
					<div class="result-description">
						<p>Job done by</p>
						<p><a href="viewprofile?id=${job.worker.id}">${job.worker.firstName} ${job.worker.lastName}</a></p>
						<p><a href="sendmessage?id=${job.worker.id}">Contact</a></p>
					</div>
						<c:if test="${job.fbFromEmployer eq false}">
						<div class="result-description">
							<p>Feedback</p>
							<p>You have not left feedback for the job</p>
							<form method="GET" action="sendfeedback">
								<input type="hidden" value="${job.worker.id}" name="id">
								<input type="hidden" value="${job.id}" name="jobid">
								<input type="hidden" value="true" name="who">
								<input type="submit" id="post-job-btn" value="Leave feedback" />
							</form>
						</div>
						</c:if>
						<c:if test="${job.fbFromEmployer eq true}">
						<div class="result-description">
							<p>Feedback</p>
							<p>You have left feedback for the job</p>
						</div>
						</c:if>
					</c:if>
					
				</div>
				</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
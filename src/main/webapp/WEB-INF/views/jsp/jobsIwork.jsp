<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div class="flowe-footer-menu profile">
				<p><a href="mymessages">My Messages</a></p>
				<p><a href="myjobs">My Jobs</a></p>
				<p><a href="myfeedbacks?id=${user.id}">My Feedbacks</a></p>
				<p><a href="jobsIwork" style="color: #FFA500; font-weight: 600;">Jobs I'm working</a></p>
 			</div>	
		<div id="post-job">
			<!-- <h2 id="search-offers">Jobs I'm working</h2> -->
			<div class="post-job search-job working-jobs">
				<c:if test="${(jobsIwork != '') && (jobsIwork != 'error')}">
					<c:forEach var="job" items="${jobsIwork}">
						<div class="search-results">
							<div class="result-title">
								<p>Title</p>
								<p>${job.title}</p>
							</div>
							<div class="result-budjet">
								<p>Budget</p>
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
								<p>Employer</p>
								<a href="viewprofile?id=${job.employer.id}">${job.employer.firstName} ${job.employer.lastName}</a>
							<a href="sendmessage?id=${job.employer.id}">Contact</a>
							</div>
							<div class="result-description">
								<p>Date posted</p>
								<p>${job.date}</p>
							</div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty jobsIwork}">
					<h2>There are no jobs that you're working at the moment!</h2>
				</c:if>
				</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
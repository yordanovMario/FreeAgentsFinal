<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div id="post-job">
			<!-- <h2 id="search-offers">Jobs I'm working</h2> -->
			<div class="post-job search-job working-jobs">
				<c:if test="${(jobsuser != '') && (jobsuser != 'error')}">
					<c:forEach var="job" items="${jobsuser}">
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
								<p>Status</p>
								<p>${statuses.get(job.status)}</p>
							</div>
							<div class="result-description">
								<p>Date posted</p>
								<p>${job.date}</p>
							</div>
							<c:if test="${job.employer.id != sessionScope.user.id}">
								<c:if test="${job.status < 3}">
									<form method="GET" action="viewjobfrombrowsejobs">
										<input type="hidden" value="${job.id}"name="id">
										<input type="submit" id="post-job-btn" value="View Job Details" />
									</form>
								</c:if>
							</c:if>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty jobsuser}">
					<h2>There are no jobs that you're working at the moment!</h2>
				</c:if>
				</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
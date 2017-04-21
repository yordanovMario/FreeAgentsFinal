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
						<form method="GET" action="viewoffers">
							<input type="hidden" value="${job.id}"name="id">
							<input type="submit" id="post-job-btn" value="View offers" />
						</form>
					</div>
				</c:forEach>
				</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
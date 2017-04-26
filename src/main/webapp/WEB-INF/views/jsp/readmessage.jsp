<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div class="flowe-footer-menu profile">
			<p><a href="mymessages">My Messages</a></p>
			<p><a href="myjobs">My Jobs</a></p>
			<p><a href="myfeedbacks?id=${user.id}">My Feedbacks</a></p>
			<p><a href="jobsIwork">Jobs I'm working</a></p>
		</div>	
		<div id="post-job">
			<h2 id="search-offers">Reading message from ${message.sender.firstName}</h2>
			<div class="post-job search-job">
						<div class="search-results">
							<div class="result-title">
								<p>Title</p>
								<p>${message.title}</p>
							</div>
							<div class="result-budjet">
								<p>Content</p>
								<p>${message.content}</p>
							</div>
							<div class="result-description">
								<p>Date sent</p>
								<p>${message.date}</p>
							</div>
						</div>
				</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
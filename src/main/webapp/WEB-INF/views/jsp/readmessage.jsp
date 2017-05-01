<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div class="flowe-footer-menu profile">
				<p><a href="mymessages" style="color: #FFA500; font-weight: 600;">My Messages</a></p>
				<p><a href="myjobs">My Jobs</a></p>
				<p><a href="myfeedbacks">My Feedbacks</a></p>
				<p><a href="jobsIwork">Jobs I'm working</a></p>
		</div>
		<div class="flowe-footer-menu profile">
			<p><a href="mymessages">Inbox</a></p>
			<p><a href="sentmessages">Sent</a></p>
			<p><a href="sendmessage">New message</a></p>
			<p><a href="#">&nbsp;</a></p>
		</div>
		<div id="post-job">
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
							<c:if test="${message.sender.id != sessionScope.user.id}">
								<form method="GET" action="sendmessage" class="view-send-message">
									<input type="hidden" value="${message.sender.id}" name="id">
									<input type="hidden" value="Re: ${message.title}" name="title">
									<input type="hidden" value="1" name="type">
									<input type="submit" id="post-job-btn" value="Reply" class="view-message-button"/>
								</form>
							</c:if>
						</div>
				</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
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
			<p><a href="sendmessage" style="color: #FFA500; font-weight: 600;">New message</a></p>
			<p><a href="#">&nbsp;</a></p>
		</div>			
		<div id="post-job">
			<c:if test="${type == 1 || type == 2}">
			<h2>Send Message to ${receiver.firstName}</h2>
			<div class="post-job">
				<form method="POST" action="sendmessage">
					<input type="text" id="job-title" name="title" required="required" placeholder="Enter title here..." value="${title}"/>
					<textarea rows="4" cols="50" name="content" required="required" placeholder="Enter content here..."></textarea>
					<input type="hidden" value="${id}" name="id">
					<input type="submit" id="post-job-btn" value="Send Message" />
				</form>
			</div>
			</c:if>
			<c:if test="${type == 3}">
			<h2>New message</h2>
			<div class="post-job">
				<form method="POST" action="sendmessage">
					<input type="text" id="job-title" name="username" required="required" placeholder="Receiver's username..."/>
					<input type="text" id="job-title" name="title" required="required" placeholder="Enter title here..."/>
					<textarea rows="4" cols="50" name="content" required="required" placeholder="Enter content here..."></textarea>
					<input type="hidden" value="${id}" name="id">
					<input type="submit" id="post-job-btn" value="Send Message" />
				</form>
			</div>
			</c:if>
		</div>
<jsp:include page="footer.jsp" />
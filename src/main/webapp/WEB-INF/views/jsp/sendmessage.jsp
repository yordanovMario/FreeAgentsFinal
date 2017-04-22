<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div id="post-job">
			<h2>Send Message to ${receiver.firstName}</h2>
			<div class="post-job">
				<form method="POST" action="sendmessage">
					<input type="text" id="job-title" name="title" required="required" placeholder="Enter title here..." />
					<textarea rows="4" cols="50" name="content" required="required" placeholder="Enter content here..."></textarea>
					<input type="hidden" value="${id}" name="id">
					<input type="submit" id="post-job-btn" value="Send Message" />
				</form>
			</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
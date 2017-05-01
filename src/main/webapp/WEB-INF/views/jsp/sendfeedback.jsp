<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div id="post-job">
			<h2>Leave Feedback for ${receiver.firstName}</h2>
			<div class="post-job">
				<form method="POST" action="sendfeedback">
					<select name="rating" class="categories" required="required">
						<option value="3">Rating</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
					<textarea rows="4" cols="50" name="content" required="required" placeholder="Enter your comment here..."></textarea>
					<input type="hidden" value="${id}" name="id">
					<input type="hidden" value="${jobid}" name="jobid">
					<input type="hidden" value="${who}" name="who">
					<input type="submit" id="post-job-btn" value="Send Feedback" />
				</form>
			</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div class="flowe-footer-menu profile">
				<p><a href="mymessages">My Messages</a></p>
				<p><a href="myjobs">My Jobs</a></p>
				<p><a href="myfeedbacks">My Feedbacks</a></p>
 			</div>	
		<div id="post-job">
			<h2 id="search-offers">My Feedbacks</h2>
			<div class="post-job search-job">
				<table style="border: 1px solid black;">
					  <tr>
					    <th>Sender name</th>
					    <th>Rating</th>
					    <th>Content</th>  
					    <th>Date & Time</th>
					  </tr>
					<c:forEach var="feedback" items="${feedbacks}">
						<tr>
						<td>${feedback.sender.firstName} ${feedback.sender.lastName}</td>
						<td>${feedback.rating}</td>
						<td>${feedback.content}</td>
						<td>${feedback.date}</td>
						</tr>
					</c:forEach>
				</table>
				</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
	<div class="view-profile">
		<div class="image">
			<p class="username">${userprofile.firstName} ${userprofile.lastName}</p> 
			<p><img src="image/${userprofile.id}" onerror="this.src='image/0'" width="170" height="170"></p>
		</div>
		<div class="headline">
			<p>Location</p>
			<p>Job title</p>
			<p>Phone number</p>
			<p>Per hour rate ($)</p>
			<p>About me</p>
			<p>Portfolio</p>
			<p>Rating</p>
		</div>
		<div class="description">
			<c:if test="${country == null}"><p>N/A</p></c:if>
			<c:if test="${country != null}"><p>${country}"</p></c:if>
			<p>${userprofile.jobTitle}</p>
			<p>${userprofile.phone}</p>
			<p>${userprofile.perHourRate}</p>
			<p>${userprofile.aboutMe}</p>
			<p>${userprofile.portfolio}</p>
			<c:if test="${rating == null}"><p>No feedbacks yet</p></c:if>
			<c:if test="${rating != null}"><p>${rating}/5</p></c:if>
		</div>
	</div>
	<c:if test="${userprofile.id != sessionScope.user.id}">
	<form method="GET" action="sendmessage" class="view-send-message">
		<input type="hidden" value="${userprofile.id}" name="id">
		<input type="submit" id="post-job-btn" value="Send Message" class="view-message-button"/>
	</form>
	
	<div id="post-job">
			<!-- <h2 id="search-offers">My Feedbacks</h2> -->
			<div class="post-job search-job my-feedbacks">
				<c:if test="${not empty feedbacks}">
					<table class="table-messages">
						  <tr>
						    <th>Sender name</th>
						    <th>Rating</th>
						    <th>Content</th>  
						    <th>Date & Time</th>
						  </tr>
						<c:forEach var="feedback" items="${feedbacks}">
						<c:if test="${feedback.isRead() eq true}">
							<tr style="background-color: #ECE7E7;">
							<td>${feedback.sender.firstName} ${feedback.sender.lastName}</td>
							<td>${feedback.rating}</td>
							<td>${feedback.content}</td>
							<td>${feedback.date}</td>
							</tr>
						</c:if>
						<c:if test="${feedback.isRead() eq false}">	
							<tr style="background-color: #D9CFD0;">
							<td>${feedback.sender.firstName} ${feedback.sender.lastName}</td>
							<td>${feedback.rating}</td>
							<td>${feedback.content}</td>
							<td>${feedback.date}</td>
							</tr>
						</c:if>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${empty feedbacks}">
					<h2>The user have no feedbacks yet!</h2>
				</c:if>
				</div>
			<div class="post-job-account">
			</div>
		</div>
	</c:if>
<jsp:include page="footer.jsp" />
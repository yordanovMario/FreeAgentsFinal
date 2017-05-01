<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div class="flowe-footer-menu profile">
			<p><a href="mymessages" style="color: #FFA500; font-weight: 600;">My Messages</a></p>
			<p><a href="myjobs">My Jobs</a></p>
			<p><a href="myfeedbacks">My Feedbacks</a></p>
			<p><a href="jobsIwork">Jobs I'm working</a></p>
		</div>
		<div class="flowe-footer-menu profile">
			<p><a href="mymessages" style="color: #FFA500; font-weight: 600;">Inbox</a></p>
			<p><a href="sentmessages">Sent</a></p>
			<p><a href="sendmessage">New message</a></p>
			<p><a href="#">&nbsp;</a></p>
		</div>			
		<div id="post-job">
			<!-- <h2 id="search-offers">My Messages</h2> -->
			<div class="post-job search-job my-messages">
				<c:if test="${not empty messages}">
					<table class="table-messages">
						  <tr>
						    <th>Sender name</th>
						    <th>Title</th> 
						    <th>Date & Time</th>
						  </tr>
						<c:forEach var="message" items="${messages}">
						<c:if test="${message.isRead() eq true}">
						<tr style="background-color: #ECE7E7; cursor: pointer;" onclick="document.location = 'readmessage?id=${message.id}';">
							<td>${message.sender.firstName}&nbsp${message.sender.lastName}</td>
							<td>${message.title}</td>
							<td>${message.date}</td>
						</tr>
						</c:if>
						<c:if test="${message.isRead() eq false}">
						<tr style="background-color: #D9CFD0; cursor: pointer;" onclick="document.location = 'readmessage?id=${message.id}';">
							<td>${message.sender.firstName}&nbsp${message.sender.lastName}</td>
							<td>${message.title}</td>
							<td>${message.date}</td>
							</tr>
						</c:if>		
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${empty messages}">
					<h2>Your Inbox is empty!</h2>
				</c:if>
				</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
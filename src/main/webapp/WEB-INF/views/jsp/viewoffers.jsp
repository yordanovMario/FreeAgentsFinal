<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div class="flowe-footer-menu profile">
				<p><a href="mymessages">My Messages</a></p>
				<p><a href="myjobs">My Jobs</a></p>
				<p><a href="myfeedbacks?id=${user.id}">My Feedbacks</a></p>
 		</div>	
		<div id="post-job">
			<h2 id="search-offers">Offers for job</h2>
			<div class="post-job search-job">
				<c:if test="${not empty offers}">
					<c:forEach var="offer" items="${offers}">
						<div class="search-results">
							<div class="result-description">
								<p>Description</p>
								<p>${offer.content}</p>
							</div>
							<div class="result-budjet">
								<p>Budjet</p>
								<p>${offer.price}</p>
							</div>
							<div class="result-title">
								<p>From</p>
								<p><a href="viewprofile?id=${offer.senderUser.id}">${offer.senderUser.firstName} ${offer.senderUser.lastName}</a></p>
							</div>
							<div class="result-date">
								<p>Date sent</p>
								<p>${offer.date}</p>
							</div>
							<form method="POST" action="acceptoffer">
								<input type="hidden" value="${offer.id}" name="id"/>
								<input type="submit" id="post-job-btn\" value="Accept offer"/>
							</form>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty offers}">
					<h2>There are no offers made for this job!</h2>
				</c:if>
			</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
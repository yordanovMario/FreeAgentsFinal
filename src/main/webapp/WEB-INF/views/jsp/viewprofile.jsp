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
		</div>
		<div class="description">
			<p>PISANKA<c:out value="${country}"/></p>
			<p>${userprofile.jobTitle}</p>
			<p>${userprofile.phone}</p>
			<p>${userprofile.perHourRate}</p>
			<p>${userprofile.aboutMe}</p>
			<p>${userprofile.portfolio}</p>
		</div>
	</div>
	<c:if test="${userprofile.id != sessionScope.user.id}">
	<form method="GET" action="sendmessage" class="view-send-message">
		<input type="hidden" value="${userprofile.id}" name="id">
		<input type="submit" id="post-job-btn" value="Send Message" />
	</form>
	<form method="GET" action="sendfeedback class="view-send-feedback"">
		<input type="hidden" value="${userprofile.id}" name="id">
		<input type="submit" id="post-job-btn" value="Send Feedback" />
	</form>
	</c:if>
<jsp:include page="footer.jsp" />
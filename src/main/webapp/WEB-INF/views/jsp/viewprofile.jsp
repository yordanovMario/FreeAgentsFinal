<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div id="signup">
				<label for="first-name">First name</label>
				<p>${userprofile.firstName}</p>
				<label for="last-name">Last name</label>
				<p>${userprofile.lastName}</p>
				<label for="last-name">Location</label>
				<p><c:out value="${country}"/></p>
				<label for="job-title">Job title</label>
				<p>${userprofile.jobTitle}</p>
				<label for="phone">Phone number</label>
				<p>${userprofile.phone}</p>
				<label for="per-hour-rate">Per hour rate ($)</label>
				<p>${userprofile.perHourRate}</p>
				<label for="aboutme">About me</label>
				<p>${userprofile.aboutMe}</p>
				<label for="portfolio">Portfolio</label>
				<p>${userprofile.portfolio}</p>
		</div>
		<form method="GET" action="sendmessage">
			<input type="hidden" value="${userprofile.id}" name="id">
			<input type="submit" id="post-job-btn" value="Send Message" />
		</form>
		
		<form method="GET" action="sendfeedback">
			<input type="hidden" value="${userprofile.id}" name="id">
			<input type="submit" id="post-job-btn" value="Send Feedback" />
		</form>
<jsp:include page="footer.jsp" />
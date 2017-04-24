<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<section>
	<div class="main-description">
		<div class="main-header">
			<p>${user.getFirstName()}&nbsp${user.getLastName()}</p>
		</div>
		<div class="tips">
			<div class="flowe-footer-menu profile">
				<p><a href="admin/jobs">Jobs</a></p>
				<p><a href="admin/offers">Offers</a></p>
				<p><a href="admin/messages">Messages</a></p>
				<p><a href="admin/feedbacks">Feedbacks</a></p>
				<p><a href="admin/users">Users</a></p>
			</div>
		</div>
	</div>
</section>
<jsp:include page="footer.jsp" />
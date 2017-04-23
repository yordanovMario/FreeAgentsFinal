<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div id="post-job">
			<h2>Post offer</h2>
			<div class="post-job">
				<form method="POST" action="postoffer">
					<textarea rows="4" cols="50" name="content" placeholder="Enter content here..."></textarea>
					<input type="hidden" value="${id}" name="id">
					<input type="number" name="price" placeholder="Price" required="required"/>
					<input type="submit" id="post-job-btn" value="Post Offer" />
				</form>
			</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div id="signup">
			<h2>Sign Up</h2>
			<form method="POST" action="signup">
			<input type="text" name="fname" placeholder="First Name" maxlength="30" />
			<input type="text" name="lname" placeholder="Last Name" maxlength="30" />
			<input type="text" name="email" placeholder="Email" required="required" maxlength="80" />
			<input type="text" name="username" placeholder="Username" required="required" maxlength="20" />
			<input type="password" name="password" placeholder="Password" required="required" maxlength="20" />
			<input type="password" name="password2" placeholder="Confirm password" required="required" maxlength="20" />
			<c:if test="${not empty sessionScope.notifsignup}">
			<p class="error">${sessionScope.notifsignup}</p>
			</c:if>
			<input type="submit" id="submit-btn" value="Sign Up" />
			<script type="in/Login"></script>
			<p>Already registred? Click <a href="login">here</a> to log in.</p>
			</form>
		</div>
<jsp:include page="footer.jsp" />
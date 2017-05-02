<jsp:include page="header.jsp" />
	<div id="login">
		<h2>Forgot your password? We got this!</h2>
		<form method="POST" action="forgotpassword">
			<input type="text" name="email" placeholder="Email address" required="required" maxlength="80"/>
			<input type="submit" id="login-btn" value="Reset Password" />
		</form>
	</div>
<jsp:include page="footer.jsp" />
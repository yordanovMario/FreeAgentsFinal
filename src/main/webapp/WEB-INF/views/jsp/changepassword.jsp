<jsp:include page="header.jsp" />
	<div id="login">
		<h2>Change Password</h2>
		<form method="POST" action="changepassword">
			<input type="password" name="oldpassword" placeholder="Old Password" required="required"/>
			<input type="password" name="newpassword" placeholder="New Password" required="required"/>
			<input type="password" name="confnewpassword" placeholder="Confirm New Password" required="required"/>
			<input type="submit" id="login-btn" value="Save Changes" />
		</form>
	</div>
<jsp:include page="footer.jsp" />
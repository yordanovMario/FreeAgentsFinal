<jsp:include page="header.jsp" />
	<div id="login">
		<h2>Change Password</h2>
		<form method="POST" action="changepassword" class="change-password">
			<input type="password" name="oldpassword" placeholder="Old Password" required="required" maxlength="20"/>
			<input type="password" name="newpassword" placeholder="New Password" required="required" maxlength="20"/>
			<input type="password" name="confnewpassword" placeholder="Confirm New Password" required="required" maxlength="20"/>
			<input type="submit" id="login-btn" value="Save Changes" />
		</form>
	</div>
<jsp:include page="footer.jsp" />
<jsp:include page="header.jsp" />
<div id="login">
			<h2>Log In</h2>
				<form method="POST" action="login" class="login-form">
					<input type="text" name="username" placeholder="Username" required="required"/>
					<input type="password" name="password" placeholder="Password" required="required"/>
					<c:if test="${not empty sessionScope.notifsignup}">
						<p class="error">${sessionScope.notifsignup}</p>
					</c:if>
					<input type="submit" id="login-btn" value="Sign In" />
				</form>
				<div>
					New to our site? Click <a href="signup">here</a> to register.
				</div>
				<div>
					Forgot your password? Click <a href="forgotpassword">here</a>.
				</div>
				
		</div>
<jsp:include page="footer.jsp" />
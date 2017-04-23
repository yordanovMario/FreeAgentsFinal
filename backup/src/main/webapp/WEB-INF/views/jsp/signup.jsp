<jsp:include page="header.jsp" />
		<div id="signup">
			<h2>Sign Up</h2>
			<form method="POST" action="signup">
			<input type="text" name="fname" placeholder="First Name" />
			<input type="text" name="lname" placeholder="Last Name" />
			<input type="text" name="email" placeholder="Email" required="required"/>
			<input type="text" name="username" placeholder="Username" required="required"/>
			<input type="password" name="password" placeholder="Password" required="required"/>
			<input type="password" name="password2" placeholder="Confirm password" required="required"/>
			<c:if test="${not empty sessionScope.notification}">
			<p class="error">${sessionScope.notification}</p>
			</c:if>
			<input type="submit" id="submit-btn" value="Sign Up" />
			<p>Already registred? Click <a href="LogIn.html">here</a> to log in.</p>
			</form>
		</div>
<jsp:include page="footer.jsp" />
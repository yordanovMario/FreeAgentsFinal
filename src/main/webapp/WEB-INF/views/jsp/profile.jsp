<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
		<section>
		<div class="main-description">
		<div class="main-header">
		<p>${user.getFirstName()}&nbsp${user.getLastName()}</p>
		</div>
		<div class="tips">
		<div>
			<div class="flowe-footer-menu profile">
				<p><a href="mymessages">My Messages</a></p>
				<p><a href="myjobs">My Jobs</a></p>
				<p><a href="myfeedbacks?id=${user.id}">My Feedbacks</a></p>
				<p><a href="jobsIwork">Jobs I'm working</a></p>
 			</div>				
			<div id="signup">
				<img src="image/${user.id}" onerror="this.src='image/0'" style="width:170px;height:170px;">
				<form method="POST" enctype="multipart/form-data" action="uploadpic">
					<p>Choose a picture</p>
					<input type="file" id="file" name="failche" accept="image/*" required="required">
					<input type="submit" value="Save changes">
				</form>
				<form method="POST" action="editdata">
				<label for="first-name">First name</label>
				<input type="text" id="first-name" name="firstname" value="${user.firstName}"/>
				<label for="last-name">Last name</label>
				<input type="text" id="last-name" name="lastname" value="${user.lastName}">
				<label for="country">Location</label>
				<select name="country" class="categories">
					<option value="">Select Country</option>
					<c:forEach var="country" items="${countries}">
						<c:if test="${user.country == country.key}">
							<option selected="selected" value="${country.key}">${country.value}</option>
						</c:if>
						<c:if test="${user.country != country.key}">
							<option value="${country.key}">${country.value}</option>
						</c:if>
					</c:forEach>
				</select>
				<label for="job-title">Job title</label>
				<input type="text" id="job-title" name="jobtitle" value="${user.jobTitle}"/><br>
				<label for="phone">Phone number</label>
				<input type="text" id="phone" name="phone" value="${user.phone}"/><br>
				<label for="per-hour-rate">Per hour rate ($)</label>
				<input type="number" id="per-hour-rate" name="perhourrate" value="${user.perHourRate}"/>
				<label for="aboutme">About me</label>
				<textarea rows="4" cols="50" id="aboutme" name="aboutme">${user.aboutMe}</textarea>
				<label for="portfolio">Portfolio</label>
				<textarea rows="4" cols="50" id="portfolio" name="portfolio">${user.portfolio}</textarea>
				<input type="submit" id="post-job-btn" value="Edit my data" />
				</form>
			</div>
			</div>
			</div>
		</div>
		</section>
		<footer>
		<div class="flowe-footer-menu">
				<p>Terms Of Service</p>	
				<p>Privacy Policy</p>
				<p class="flowe-blog">Blog</p>
				<p>Careers</p>
				<p class="flowe-faq">FAQ</p>
 		</div>
		<div class="flowe-footer">
			<div class="flowe-logo-footer"> 
				<img src="logo.png">
			</div>
			<p>&copy; 2017 Free Agents LTD. All rights reserved.</p>
		</div>
	</footer>
</body>
</html>

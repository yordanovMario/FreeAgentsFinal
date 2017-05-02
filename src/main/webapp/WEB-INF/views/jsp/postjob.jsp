<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div id="post-job">
			<h2>Post Job</h2>
			<div class="post-job">
				<form method="POST" action="postjob">
					<input type="text" name="title" placeholder="Title" required="required" maxlength="50"/>
					<!--<input type="textarea" name="description" placeholder="Description" required="required"/> -->
					<textarea rows="4" cols="50" name="description" placeholder="Enter description here..." maxlength="800" ></textarea>
					<input type="number" name="budget" placeholder="Budget (US $)" required="required" min="1" max="1000000"/>
					<select name="expire" class="categories">
						<option value="">Validity of the ad</option>
						<option value="1">7 days</option>
						<option value="2">14 days</option>
						<option value="3">21 days</option>
					</select>
					<select name="category" class="categories">
						<option value="">Select Category</option>
						<c:forEach var="category" items="${categories}">
							<option value="${category.key}">${category.value}</option>
						</c:forEach>
					</select>
					<select name="reqExp" class="categories">
					  <option value="">Select Experience Level</option>
					  <option value="1">Beginner</option>
					  <option value="2">Advanced</option>
					  <option value="3">Expert</option>
					</select>
					
					<input type="submit" id="post-job-btn" value="Post Job" />
				</form>
			</div>
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
		<div id="post-job">
			<h2 id="search-offers">Search offers</h2>
			<div class="post-job search-job">
				<form method="GET" action="browsejobs">
					<select name="category" class="categories">
						<option value="">Select Category</option>
						<c:forEach var="category" items="${categories}">
							<c:if test="${category.key == categoryID}">
								<option selected="selected" value="${category.key}">${category.value}</option>
							</c:if>
							<c:if test="${category.key != categoryID}">
								<option value="${category.key}">${category.value}</option>
							</c:if>
						</c:forEach>
					</select>
					<select name="sort" class="categories">
					  <option value="">Order by</option>
					  <option value="2">Price Ascending</option>
					  <option value="3">Price Descending</option>
					  
					</select>
					
					<input type="submit" id="post-job-btn" value="Show Results" />
				</form>
			</div>
			
			<c:forEach var="job" items="${jobs}">
				<div class="search-results">
					<div class="result-title">
						<p>Title</p>
						<p>${job.title}</p>
					</div>
					<div class="result-budjet">
						<p>Budjet</p>
						<p>${job.budget}</p>
					</div>
					<div class="result-description">
						<p>Description</p>
						<p>${job.description}</p>
					</div>
					<div class="result-title">
						<p>From</p>
						<p><a href="viewprofile?id=${job.employer.id}">${job.employer.firstName} ${job.employer.lastName}</a></p>
					</div>
					<form method="POST" action="postoffer">
						<input type="hidden" value="${job.id}"name="id">
						<input type="submit" id="post-job-btn" value="Send Offer" />
					</form>
				</div>
			</c:forEach>
	
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
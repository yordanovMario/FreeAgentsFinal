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
					<select name="experience" class="categories">
					  <option value="">Required Experience</option>
					  <c:if test="${experience == 1}">
					  <option value="1" selected="selected">Begginer</option>
					  </c:if>
					  
					  <c:if test="${experience != 1}">
					  <option value="1">Begginer</option>
					  </c:if>
					  
					  <c:if test="${experience == 2}">
					  <option value="2" selected="selected">Intermediate</option>
					  </c:if>
					  
					  <c:if test="${experience != 2}">
					  <option value="2">Intermediate</option>
					  </c:if>
					  
					  <c:if test="${experience == 3}">
					  <option value="3" selected="selected">Expert</option>
					  </c:if>
					  
					  <c:if test="${experience != 3}">
					  <option value="3">Expert</option>
					  </c:if>
					</select>
					<select name="sort" class="categories">
					  <option value="">Order by</option>
					  <c:if test="${sortID == 2}">
					  <option value="2" selected="selected">Price Ascending</option>
					  </c:if>
					  
					  <c:if test="${sortID != 2}">
					  <option value="2">Price Ascending</option>
					  </c:if>
					  
					  <c:if test="${sortID == 3}">
					  <option value="3" selected="selected">Price Descending</option>
					  </c:if>
					  
					  <c:if test="${sortID != 3}">
					  <option value="3">Price Descending</option>
					  </c:if>
					  
					  <c:if test="${sortID == 1}">
					  <option value="1" selected="selected">Newly posted</option>
					  </c:if>
					  
					  <c:if test="${sortID != 1}">
					  <option value="1">Newly posted</option>
					  </c:if>
					</select>
					
					<input type="submit" id="post-job-btn" value="Show Results" />
				</form>
			</div>
			<c:if test="${empty jobs}">
				<h3>There are no jobs with this criteria</h3>
			</c:if>
			<c:forEach var="job" items="${jobs}">
				<div class="search-results">
					<div class="result-title">
						<p>Title</p>
						<p>${job.title}</p>
					</div>
					<div class="result-title">
						<p>From</p>
						<p><a href="viewprofile?id=${job.employer.id}">${job.employer.firstName} ${job.employer.lastName}</a></p>
					</div>
					<div class="result-budjet">
						<p>Budjet</p>
						<p>${job.budget}</p>
					</div>
					<!-- <div class="result-description">
						<p>Description</p>
						<p>${job.description}</p>
					</div> -->
					
					<div class="result-description">
						<p>Required Experience</p>
						<c:if test="${job.requiredExp == 1}">
						<p>Beginner</p>
						</c:if>
						<c:if test="${job.requiredExp == 2}">
						<p>Intermediate</p>
						</c:if>
						<c:if test="${job.requiredExp == 3}">
						<p>Expert</p>
						</c:if>
					</div>
					<div class="result-description">
						<p>Category</p>
						<p>${categories.get(job.category)}</p>
					</div>
					<div class="result-title">
						<p>Date posted</p>
						<p>${job.date}</p>
					</div>
					<c:if test="${job.employer.id != sessionScope.user.id}">
					<form method="GET" action="postoffer">
						<input type="hidden" value="${job.id}"name="id">
						<input type="submit" id="post-job-btn" value="View Details" />
					</form>
					</c:if>
				</div>
			</c:forEach>
	
			<div class="post-job-account">
			</div>
		</div>
<jsp:include page="footer.jsp" />
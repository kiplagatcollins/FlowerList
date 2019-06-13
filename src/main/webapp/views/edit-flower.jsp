<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/includes/header.jsp"></jsp:include>
<div class="container">
	<spring:url value="/flower/add" var="add" />
	<form action="${add}" method="POST" enctype="multipart/form-data">
		<div class="w3-container text-center w3-text-blue">
			<h2>Add a Flower</h2>
		</div>
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6">

				<label class="w3-label">Name</label>
				<form:input path="name" cssClass="form-control" />
				<label class="w3-label">Quantity</label>
				<form:input path="quantity" cssClass="form-control" />

				<label class="w3-label">Description</label>
				<form:input path="description" cssClass="form-control" />

				<label class="w3-label">Image</label>
				<form:input path="file" cssClass="form-control" />
				<!--  <button class="btn btn-primary">Upload</button>-->
				<br> <label class="w3-label">Price</label> <input name="price"
					type="number" class="w3-input">

			</div>
			<div class="col-sm-3"></div>
		</div>

		<button class="btn btn-success w3-large w3-right">Save
			product</button>
	</form>
</div>
<br>
<br>

<jsp:include page="/includes/footer.jsp"></jsp:include>
</body>
</html>
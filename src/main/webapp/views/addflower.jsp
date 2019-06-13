<jsp:include page="/includes/header.jsp"></jsp:include>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <div class="container">
   
    	<spring:url value="/flower/farmer/add" var="add"/>
            <form action="${add}" method="POST" id="addform" enctype="multipart/form-data">
                <div class="w3-container text-center w3-text-blue"><h2>Add a Flower</h2></div>
		<div class="row">
	<div class="col-sm-3"></div>
			<div class="col-sm-6">

				<label class="w3-label">Name</label> 
				<input name="name" id="name" class="w3-input" type="text"  required>
				<br>
				<label class="w3-label">Quantity</label>
				<input name="quantity" id="quantity" class="w3-input" type="number" required> 
				<br>
				<label class="w3-label">Description</label>
				<textarea name="description" id="description" rows="4" cols="3" class="form-control" required></textarea>
				<br>
				<label class="w3-label">Image</label> 
				<input name="file" id="file" class="form-control" type="file" width="25" required>
				<!--  <button class="btn btn-primary">Upload</button>-->
				<br>
				<label class="w3-label">Price</label>
				 <input name="price" type="number" id="price" class="w3-input" required>

			</div>
			<div class="col-sm-3">

		</div>
		</div>

		<button class="btn btn-success w3-large w3-right">Save product</button>

                    </form>
     

        </div>
        <br><br>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/additional-methods.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.js"></script>
<jsp:include page="/includes/footer.jsp"></jsp:include>
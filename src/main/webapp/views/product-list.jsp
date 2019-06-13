<jsp:include page="/includes/header.jsp"></jsp:include>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <div class="row">
<div class="col-sm-3"></div>
<div class="col-sm-6">
 <table class="w3-table">
  <tr>
    <th>Photo</th>
    <th>Name</th>
    <th>Quantity</th>
    <th>Price</th>
    <th>Quantity Sold</th>

  </tr>
  <tbody>
  <c:forEach items="${flowerlist}" var="flowers">
  <c:if test="${flowers.deleted==0 }">
  <tr>
  <td>  <img src="data:image/jpg;base64,${flowers.base64image}" class="img1 media-object" alt="${flowers.name }"/></td>
    <td>${flowers.name }<br></td>
    <td>${flowers.quantity }</td>
    <td>${flowers.price }</td>
    <td>${flowers.sold }</td>
    <td><a class="w3-btn w3-green" href='<spring:url value="edit?id=${flowers.flowerId}"></spring:url>'>Update</a></td>
    <td><a class="w3-btn w3-red" href='<spring:url value="delete?id=${flowers.flowerId }"></spring:url>'><span class="glyphicon glyphicon-remove"></span></a></td>
  	
  </tr>
  </c:if>
  </c:forEach>
  </tbody>
</table>

</div>
</div>
 
<footer class="footer">
	<jsp:include page="/includes/footer.jsp"></jsp:include>
</footer>
</body>
</html>
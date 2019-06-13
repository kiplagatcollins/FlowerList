<%@page import="com.sun.org.apache.bcel.internal.generic.IFNULL"%>
<jsp:include page="/includes/header.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <meta charset="UTF-8">
 <meta name="description" content="Free Web tutorials">
 <meta name="keywords" content="HTML,CSS,XML,JavaScript">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <div class="row">
<div class="col-sm-3"></div>
<div class="col-sm-6">
<a href='<spring:url value="/" />'>Continue buying</a>
<table class="w3-table">
 
  <tr>
    <th>Name</th>
    <th>Price</th>
    <th>Quantity</th> 
    <th>Sub-total</th>
  </tr>
       <c:if test="cart.isEmpty()">
      <h2>your cart is empty</h2>
       </c:if>
     <form action='<spring:url value="/flower/customer/buy/${flower.flowerId}" />' method="post" >
     <c:forEach items="${cart}" var="cart" >
     	
		  <tr>
		    <td>${cart.flower.name }</td>
		    <td>${cart.flower.price }</td>
		    <td><input value="${cart.itemquantity}"/></td>
		    <td>${cart.flower.price*cart.itemquantity }</td>
		    <td></td>
		  </tr>
 </c:forEach>
   </form>
 
</table>
<a href='<spring:url value="/flower/customer/clear"/>'>Remove</a>
<a href='<spring:url value="/flower/customer/order"/>' class="btn btn-sucess w3-right">Buy</a>

</div>
<div class="col-sm-3"></div>
</div>
<footer class="footer">
		<jsp:include page="/includes/footer.jsp"></jsp:include>
</footer>
</body>
</html>


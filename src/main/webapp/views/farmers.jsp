<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/admin.jsp"></jsp:include>
   
   <div class="container">
   	<h3 class="text-center">Farmers</h3>
   	<table class="w3-table w3-border w3-striped">
<tr>
  <th>Farmers Name</th>
  <th>Email</th>
  <th>Flowers Company</th>
  <th>Address Location</th>
  <td>Phone Number</td>
  
</tr>
<c:forEach items="${farmerslist }" var="farmers">
<tr>
  <td>${farmers.firstName} &nbsp  ${farmers.lastName }</td>
  <td>${farmers.email }</td>
  <td>Kenya's Flowers</td>
  <td>${farmers.address }</td>
  <td>${farmers.phoneNumber }</td>
</tr>
</c:forEach>
</table>
   </div>
 <footer class="footer">
        <jsp:include page="/includes/footer.jsp"></jsp:include>
     </footer> 
  </body>
</html>
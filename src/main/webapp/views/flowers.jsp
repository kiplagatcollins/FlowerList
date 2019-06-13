<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <jsp:include page="/includes/header.jsp"></jsp:include>
      <div class="container">

        <div class="col-sm-1">
        </div>
        <div class="col-sm-10">

        
        <div class="row">
        
        <c:forEach items="${flowerlist}" var="flowers" >
            <c:if test="${flowers.quantity==0}">
            <div class="col-sm-3 logopad">
            <img src="data:image/jpg;base64,${flowers.base64image}" class="img media-object" alt="${flowers.name }"/>
            <h1 class="">${flowers.name }</h1>
            <p>Price <span>${flowers.price }</span></p>
			<a class="w3-btn w3-middle text-center" style="text-decoration: line-through;">Sold Out</a>
            </div>
            
            </c:if>
            <c:if test="${flowers.quantity>0}">
            <div class="col-sm-3 logopad">
            <img src="data:image/jpg;base64,${flowers.base64image}" class="img media-object" alt="${flowers.name }"/>
            <h1 class="">${flowers.name }</h1>
            <p>Price <span>${flowers.price }</span></p>
			<a class="w3-green w3-btn w3-middle text-center" href='<spring:url value="/flower/customer/${flowers.flowerId}"/>'>Buy</a>
            </div>
            </c:if>
            </c:forEach>
        
           
        </div>
    </div>

    </div>
    <div class="text-center">
            <ul class="pagination ">
                    <li><a href="#">&laquo;</a></li>
                    <li class="active"><a href="<a href='<spring:url value="/?page=${page.number- 1 }"/>'>1</a>"></a></li>
                    <li ><a href="<a href='<spring:url value="/?page=${page.number+ 1 }"/>'>1</a>">2</a></li>
                    <li><a href="<a href='<spring:url value="/?page=${page.number+2  }"/>'>1</a>">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">&raquo;</a></li>
             </ul>
            </div>
<jsp:include page="/includes/footer.jsp"></jsp:include>
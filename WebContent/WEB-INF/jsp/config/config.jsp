<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-definition>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="body"> 
 <div id="bodycontent">
    <div id="main-section">
    	
      <div class="row">
			 <div class="column  grid-4">
			 		<div class="leftpane box">
						<c:choose>
							<c:when test="${section eq 'milestone'}">	
					 			<div class="box-item-selected ps">
					 				<p class="p nm"> <a href="${contextPath}/config/milestone"> <s:label for="ticket.milestone"/> </a> </p>
					 			</div>
				 			</c:when>
				 			<c:otherwise>
				 				<div class="box-item ps">
					 				<p class="p nm"> <a href="${contextPath}/config/milestone"> <s:label for="ticket.milestone"/> </a> </p>
					 			</div>
				 			</c:otherwise>
			 			</c:choose>
			 			<c:choose>
							<c:when test="${section eq 'useradmin'}">	
					 			<div class="box-item-selected ps">
					 				<p class="p nm"> <a href="${contextPath}/list/user/approval"> Users </a> </p>
					 			</div>
				 			</c:when>
				 			<c:otherwise>
				 				<div class="box-item ps">
					 				<p class="p nm"> <a href="${contextPath}/list/user/approval"> Users </a> </p>
					 			</div>
				 			</c:otherwise>
			 			</c:choose>
			 			<c:choose>
							<c:when test="${section eq 'labels'}">	
					 			<div class="box-item-selected ps">
					 				<p class="p nm"> <a href="${contextPath}/config/label"> Labels </a> </p>
					 			</div>
				 			</c:when>
				 			<c:otherwise>
				 				<div class="box-item ps">
					 				<p class="p nm"> <a href="${contextPath}/config/label"> Labels </a> </p>
					 			</div>
				 			</c:otherwise>
			 			</c:choose>
			 			<c:choose>
							<c:when test="${section eq 'resetpwd'}">	
					 			<div class="box-item-selected ps">
					 				<p class="p nm"> <a href="${contextPath}/config"> Reset Password </a> </p>
					 			</div>
				 			</c:when>
				 			<c:otherwise>
				 				<div class="box-item ps">
					 				<p class="p nm"> <a href="${contextPath}/config"> Reset Password </a> </p>
					 			</div>
				 			</c:otherwise>
			 			</c:choose>
			 		
			 		</div>
			 </div>
			 <div class="column  grid-12" >
			 		${body}
			 </div>
			 
		</div>	
			 	
    </div>
  </div>
  </s:layout-component>
  <s:layout-component name="inlineScripts">
	${inlineScripts}
  </s:layout-component>  
</s:layout-render> 
</s:layout-definition>

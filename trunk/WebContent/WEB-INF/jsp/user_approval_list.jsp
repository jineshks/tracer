<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body">  
  	<div id="bodycontent">
		<div class="row">
			<div class="column grid-12">
				<div class="box tac">
					<d:table name="${actionBean.userApprovalList}" id="user">
					<d:column title="User Id">
					<s:link href="${contextPath}/user/${user.userName}"> ${user.userName}
					</s:link>
					</d:column>
					<d:column title="Display Name" property="displayName"></d:column>
					<d:column title="Team" property="team"></d:column>
					<d:column title="Email" property="email"></d:column>
					<%-- 
					<d:column title="Action">
					<s:link href="${contextPath}/user">
						<s:param name="userName" value="${user.userName}"></s:param>
					Approve
					</s:link>
					 | 
					<s:link href="#">
					Reject
					</s:link> 							
					</d:column>
					--%>
					</d:table>					
				</div>
			</div>		
		</div>
	</div>	
</s:layout-component>
<s:layout-component name="inlineScripts">
  $(document).ready(function() {	
  	showInfo();
  });
</s:layout-component>  
</s:layout-render>
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
					<d:table name="${actionBean.items}" id="alert">
					<d:column title="Name">
					<a href="${contextPath}/alert/${alert.id}">${alert.name}</a>					
					</d:column>
					<d:column title="Start Date" property="startDate"></d:column>
					<d:column title="Start Time" property="startTime"></d:column>
					<d:column title="End Date" property="endDate"></d:column>
					<d:column title="End Time" property="endTime"></d:column>
					<d:column title="Action">
						<s:link beanclass="in.espirit.tracer.action.AlertActionBean" event="delete"
							onclick="return confirm('Delete the alert : ${alert.name}?')">
								<s:param name="id" value="${alert.id}"></s:param>
								Delete
							</s:link>
					</d:column>
					
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
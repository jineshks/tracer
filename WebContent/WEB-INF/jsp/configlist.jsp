<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
	<s:layout-component name="body">
		<div id="bodycontent">
			<div class="box">
				<%--   Config List is moved to properties flie..so it is removed
				<c:forEach var="arrList" items="${actionBean.listItems}">
					<br>
					 <h5>${arrList.key}</h5>
					<s:link beanclass="in.espirit.tracer.action.ConfigActionBean">
						<s:param name="config.key">${arrList.key}</s:param>
						[Add]
					</s:link>
					<br>
					<br>
					<d:table name="${arrList.value}" id="list">
						<d:column title="Value" property="value"></d:column>
						<d:column title="Action">
							<s:link beanclass="in.espirit.tracer.action.ConfigActionBean">
								<s:param name="id" value="${list.id}"></s:param>
								Edit
							</s:link> | 
							<s:link beanclass="in.espirit.tracer.action.ConfigActionBean"
								event="delete"
								onclick="return confirm('Delete the configuration : ${list.key} - ${list.value}?')">
								<s:param name="id" value="${list.id}"></s:param>
								Delete
							</s:link>
						</d:column>
					</d:table>
				</c:forEach>
				</div>
				--%>
				<div class="box">
				<br> <br><br> <br/>
				 <h5>Milestone</h5>
				 <s:link href="../milestone/new"> [Add]</s:link>
					<br>

				<d:table name="${actionBean.milestone}" id="milestone">
					<d:column title="Name">
						<s:link href="../milestone/${milestone.id}"> 
							${milestone.name}
						</s:link>

					</d:column>
					<d:column title="Description" property="description"></d:column>
					<d:column title="Start Date" property="startDate"></d:column>
					<d:column title="End Date" property="endDate"></d:column>
					<d:column title="Current Sprint">
						<c:if test="${milestone.current eq 't'}">
							YES
						</c:if>
					</d:column>
				</d:table> 

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
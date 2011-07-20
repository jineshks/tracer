<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
		<div id="bodycontent">
			<s:messages />

			<c:forEach var="arrList" items="${actionBean.listItems}">
				<br>
				<br>
				<span class="milestone">${arrList.key}</span>
				<s:link beanclass="in.espirit.tracer.action.ConfigActionBean">
					<s:param name="config.key">${arrList.key}</s:param>
[Add]</s:link>
				<br>
				<br>
				<d:table name="${arrList.value}" id="list">
					<d:column title="Value" property="value"></d:column>
					<d:column title="Action">
						<s:link beanclass="in.espirit.tracer.action.ConfigActionBean">
							<s:param name="id" value="${list.id}"></s:param>
Edit</s:link> | 
<s:link beanclass="in.espirit.tracer.action.ConfigActionBean"
							event="delete"
							onclick="return confirm('Delete the configuration : ${list.key} - ${list.value}?')">
							<s:param name="id" value="${list.id}"></s:param>
Delete</s:link>
					</d:column>
				</d:table>
			</c:forEach>
			
			<br>
			<br>
			
			<span class="milestone">Milestone</span>
				<s:link href="../milestone/new"> [Add]</s:link>
				<br>
			
			<d:table name="${actionBean.milestone}" id="milestone">
			<d:column title="Name">
			<s:link href="../milestone/${milestone.id}"> ${milestone.name}
			</s:link>
			
			</d:column>
			<d:column title="Description" property="description"></d:column>
			<d:column title="Current Sprint" property="current"></d:column>
			</d:table>


		</div>
	</s:layout-component>
</s:layout-render>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="body">  
  	<div id="bodycontent">
		<div class="row">
			<div class="column grid-12">
				<div class="box tac">
				
					<s:messages/>
					<d:table name="${actionBean.items}" id="ticket">
					<d:column title="Id" property="id"></d:column>
					<d:column title="Short Description">
					<s:link href="/${ticket.type}/${ticket.id}"> ${ticket.shortDesc}
					</s:link></d:column>
					<d:column title="Priority" property="priority"></d:column>
					<d:column title="Status" property="status"></d:column>
					<d:column title="Reporter" property="reporter"></d:column>
					<d:column title="Owner" property="owner"></d:column>
					<d:column title="Component" property="component"></d:column>
					<d:column title="MileStone" property="milestone"></d:column>
					</d:table>
					
				</div>
			</div>
			<div class="column grid-4">
				<div class="box">
					<h4>Filter</h4>
					<jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>
					<s:form beanclass="in.espirit.tracer.action.ListActionBean">
						<s:hidden name="list"></s:hidden>
						<s:hidden name="type"></s:hidden>
						<div class="il">
							<dl>
								<dt> <s:label for="ticket.importance"></s:label> </dt>
			          			<dd>
			          				<s:select name="ticket.importance">
										<s:option value=""></s:option>
										<s:options-collection collection="${configView.importance}"/>
									</s:select> 
								</dd>
			          			<dt> <s:label for="ticket.priority"/> </dt>
			          			<dd> 
			          				<s:select name="ticket.priority">
										<s:option value=""></s:option>
										<s:options-collection collection="${configView.priority}"/>
									</s:select>
								</dd>
								<dt> <s:label for="ticket.status"/> </dt>
								<dd> 
			          				<s:select name="ticket.status">
										<s:option value=""></s:option>
										<s:options-collection collection="${configView.status}"/>
									</s:select>
								</dd>
			          			<dt> <s:label for="ticket.milestone"/> </dt>
			          			<dd> 
			          				<s:select name="ticket.milestone">
									<s:option value=""></s:option>
									<s:options-collection collection="${configView.milestone}"/>
									</s:select>
								</dd>
			          			<dt> <s:label for="ticket.reporter"/> </dt>
			          			<dd>
			          			<s:text name="ticket.reporter" placeholder="Reported by"/></dd>
			          			<dt> <s:label for="ticket.owner"/> </dt>
			          			<dd> <s:text name="ticket.owner" placeholder="Assigned by"/> </dd>
			          			<dt> <s:label for="ticket.related"/> </dt>
			          			<dd><s:text name="ticket.related" placeholder="#related, comma separated"/> </dd>
			          			
			          			 
							</dl>
								<s:submit name="filter" value="Filter"></s:submit>
	
						</div>
					</s:form>
				</div>
				
			</div>
			
		</div>
	</div>	


</s:layout-component>
</s:layout-render>
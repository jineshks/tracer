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
					<d:table name="${actionBean.items}" id="ticket">
					<d:column title="Id" property="id"></d:column>
					<d:column title="Title">
					<s:link href="/${ticket.type}/${ticket.id}"> ${ticket.title}
					</s:link></d:column>
					<d:column title="Priority" property="priority"></d:column>
					<d:column title="Status" property="status"></d:column>
					<d:column title="Importance" property="importance"></d:column>
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
			          				<s:select name="importance">
										<s:option value=""></s:option>
										<s:options-collection collection="${configView.importance}"/>
									</s:select> 
								</dd>
			          			<dt> <s:label for="ticket.priority"/> </dt>
			          			<dd> 
			          				<s:select name="priority">
										<s:option value=""></s:option>
										<s:options-collection collection="${configView.priority}"/>
									</s:select>
								</dd>
								<dt> <s:label for="ticket.status"/> </dt>
								<dd> 
			          				<s:select name="status">
										<s:option value=""></s:option>
										<s:options-collection collection="${configView.status}"/>
									</s:select>
								</dd>
			          			<dt> <s:label for="ticket.milestone"/> </dt>
			          			<dd> 
			          				<s:select name="milestone">
									<s:option value=""></s:option>
									<s:options-collection collection="${configView.milestone}"/>
									</s:select>
								</dd>
			          			<dt> <s:label for="ticket.reporter"/> </dt>
			          			<dd>
			          			<s:text name="reporter" placeholder="Reported by"/></dd>
			          			<dt> <s:label for="ticket.owner"/> </dt>
			          			<dd> <s:text name="owner" placeholder="Assigned by"/> </dd>
			          			<dt> <s:label for="ticket.parent"/></dt>
			          			<dd><s:text name="parentTicket" placeholder="#parent ticket"/> </dd>	
			          			<dt> <s:label for="list.sortByASC"/></dt>
			          			<dd> 
			          				<s:select name="sortBy">
									<s:option value="f_id">ID</s:option>
									<s:option value="f_title">Title</s:option>
									<s:option value="f_priority">Priority</s:option>
									<s:option value="f_status">Status</s:option>
									<s:option value="f_importance">Importance</s:option>
									<s:option value="f_reporter">Reporter</s:option>
									<s:option value="f_owner">Owner</s:option>
									<s:option value="f_component">component</s:option>
									<s:option value="f_milestone">Milestone</s:option>
									</s:select>
								</dd>
							</dl>
							<s:submit name="filter" value="Filter"></s:submit>	
						</div>
					</s:form>
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
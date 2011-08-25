<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body"> 
	<jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>  
	<div id="bodycontent">
		<div class="row">
			<div class="column grid-11">	
				<c:set var="milestone" value="${actionBean.milestone}"></c:set>					
				<div class="box">
						<h4>${milestone.name}</h4>
						<ul>
							<c:forEach var="ticket" items="${actionBean.ticketList}">
							<li id="${ticket.id}" class="p white">
										<span class="hide">${ticket.type}</span>
										<span class="pl"> <a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </span>
										<span class="pl bl"> ${ticket.title} </span>
										<span class="pl bl imp ${ticket.importance}">${ticket.importance}</span>
										<span class="pl bl prio ${ticket.priority}"> ${ticket.priority}</span>
										<span class="pl bl right"><a href="#"> ${ticket.owner} </a></span>  
							</li>								
							</c:forEach>
						</ul>	
				</div>
			</div>
			<div class="column grid-5">
				<div class="box">
						<h4>Filter</h4>
						<s:form beanclass="in.espirit.tracer.action.MilestoneListActionBean" >
							<s:hidden name="milestoneId"></s:hidden>	
							<div class="il">
								<dl>
									<dt> Importance </dt>
				          			<dd>
				          				<s:select name="importance">
											<s:option value=""></s:option>
											<s:options-collection collection="${configView.importance}"/>
										</s:select> 
									</dd>
				          			<dt> Priority </dt>
				          			<dd> 
				          				<s:select name="priority">
											<s:option value=""></s:option>
											<s:options-collection collection="${configView.priority}"/>										
										</s:select>
									</dd>
									<dt> Status </dt>
									<dd> 
				          				<s:select name="status">
											<s:option value=""></s:option>
											<s:options-collection collection="${configView.status}"/>
										</s:select>
									</dd>
				          			
									<dt> Phase </dt>
				          			<dd> 
				          				<s:select name="phase">
										<s:option value=""></s:option>
										<s:options-collection collection="${configView.phase}"/>
										</s:select>
									</dd>
									<dt> Component </dt>
				          			<dd>
				          				<s:text name="component" placeholder="Component"/></dd>
				          			<dt> Reporter </dt>
				          			<dd>
				          				<s:text name="reporter" placeholder="Reported by"/></dd>
				          			<dt> Owner </dt>
				          			<dd> <s:text name="owner" placeholder="Assigned by"/> </dd>
				          			<dt> Tags </dt>
	          						<dd><s:text name="tags" placeholder="Tags, comma separated"/> </dd>
								</dl>
								<s:submit name="open" value="Filter"/>								
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
  
  	$(function() {
		$( "input[name=reporter]" ).autocomplete({
			source: "/tracer/autocomplete?getUsers",
			minLength: 2
		});
		$( "input[name=owner]" ).autocomplete({
			source: "/tracer/autocomplete?getUsers",
			minLength: 2
		});
	});
  
  
  });
</s:layout-component>  
</s:layout-render>

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body"> 
	<link href="${contextPath}/stylesheets/jqueryui-smoothness/jquery-ui-1.8.14.custom.css" rel="stylesheet" type="text/css" />
	<div id="bodycontent">
		<div class="row">
			<div class="column grid-12">				
				<c:forEach var="milestone" items="${actionBean.milestoneList}" >
						<c:set var="divstylename" value="box"></c:set>
							<c:if test="${milestone.current eq 't'}">
								<c:set var="divstylename" value="box alert"></c:set>
							</c:if>
							<div class="${divstylename}">
								<div>
									<div class="column grid-10">
										<h6><a href="milestone/${milestone.id}">${milestone.name}</a></h6>
										<p>
											<span class="milestone">${milestone.description}</span>
										</p>
									</div>
									<div class="column grid-6">
							          			<p> Start Date : ${milestone.startDate} </p>
							          			<p> End Date : ${milestone.endDate} </p>
							          			<p> Total Tickets : ${milestone.totalTickets}  </p>
							          			<p> Velocity : ${milestone.velocity}  </p>
							          			<div class="progressbar-small" title="progress:${milestone.progress}%" >
													<div id="cl-progress" class="progress-green" style="width: ${milestone.progress}%;">
													</div>
												</div>
									</div>
								</div>
							</div>
				</c:forEach>
			</div>	
			<div class="column grid-4">
				<div class="box">
					<h4>Filter</h4>
					<s:form beanclass="in.espirit.tracer.action.MilestoneActionBean">
						<div class="il">
							<dl>
			          			<dt> From Date </dt>
			          			<dd> 
			          				<s:text id="fromDate" name="fromDate" placeholder="From Date"/>
			        			</dd>
			        			<dt> To Date </dt>
			          			<dd> 
			          				<s:text id="toDate" name="toDate" placeholder="To Date"/>
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
  	
  	$.datepicker.formatDate('yy-mm-dd');
	$("#fromDate").datepicker({ dateFormat: 'yy-mm-dd' });
	$("#toDate").datepicker({ dateFormat: 'yy-mm-dd' });
		
	$("form").bind("submit", function(event) {	
		
	});
	
  
  });
</s:layout-component>  
</s:layout-render>

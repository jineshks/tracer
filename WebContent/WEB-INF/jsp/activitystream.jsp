<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="body">  
	<link href="${contextPath}/stylesheets/jqueryui-smoothness/jquery-ui-1.8.14.custom.css" rel="stylesheet" type="text/css" />
  	<div id="bodycontent">
		<div class="row">
			<div class="column grid-12">
				<div class="box tac">			
				
					<d:table name="${actionBean.items}" id="activity" partialList="true" 
					size="${actionBean.resultSize}" pagesize="${actionBean.pageSize}" varTotals="45"
					requestURI="/activitystream">
					<d:column title="Activity" property="activity"></d:column>
					<d:column title="Date Time" property="timeStamp"></d:column>
					</d:table>					
				</div>
			</div>
			
			<div class="column grid-4">
				<div class="box">
					<h4>Filter</h4>
					<s:form beanclass="in.espirit.tracer.action.ActivityStreamActionBean">
						<div class="il">
							<dl>
								<dt> User Name </dt>
			          			<dd>
			          				<s:text name="userName" placeholder="User Name"/>
			        			</dd>
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
	
$(document).ready(function(){
	showInfo();
  	
	$.datepicker.formatDate('yy-mm-dd');
	$("#fromDate").datepicker({ dateFormat: 'yy-mm-dd' });
	$("#toDate").datepicker({ dateFormat: 'yy-mm-dd' });
		
	$("form").bind("submit", function(event) {	
		
	});
	
});


	
	
	</s:layout-component>	
</s:layout-render>
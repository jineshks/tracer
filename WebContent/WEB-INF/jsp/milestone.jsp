<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
	<link href="${contextPath}/stylesheets/jqueryui-smoothness/jquery-ui-1.8.14.custom.css" rel="stylesheet" type="text/css" />
		<div id="bodycontent">
		  <div class="row">    
		  		<s:form beanclass="in.espirit.tracer.action.MilestoneActionBean">
		 		   <div class="column grid-6">
						<div class="box">
							<s:hidden name="milestone.id"></s:hidden>
							<dl>
								<dt>Name</dt>
								<dd><s:text name="milestone.name"></s:text></dd>
								<dt>Description</dt>
								<dd>
									<s:textarea name="milestone.description" rows="4" cols="80"></s:textarea>
								</dd>
							</dl>
						</div>
					</div>
					<div class="column grid-5">
						<div class="box">
					 		<dl>
					 			<dt>Start Date</dt>
								<dd>
								<s:text id="startDate" name="milestone.startDate" placeholder="Start Date"/>
								</dd>				
								<dt>End Date</dt>
								<dd>
								<s:text id="endDate" name="milestone.endDate" placeholder="End Date"/>
								</dd>	
					 		</dl>	
						</div>
					</div>
					<div class="column grid-5">
						<div class="box">
							<dl>
								<dt>Current Sprint</dt>
								<dd>
									<s:checkbox name="milestone.current"  value="TRUE"/> Yes													
								</dd>
							</dl>
							<s:submit name="submit" value="Submit" /> 
						</div>
					</div>
				</s:form>
			</div>
		</div>
	</s:layout-component>
	<s:layout-component name="inlineScripts">
	
$(document).ready(function(){

	$("#startDate").datepicker({ dateFormat: 'yy-mm-dd' });
	$("#endDate").datepicker({ dateFormat: 'yy-mm-dd' });
	
	$("form").bind("submit", function(event) {
		if($("#endDate").val()=='') {
				//showMessage('Please select a end date as well');
				//return false;
			}
	});
	
});
	</s:layout-component>	
</s:layout-render>
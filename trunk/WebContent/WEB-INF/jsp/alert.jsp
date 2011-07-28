<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
	<link href="${contextPath}/stylesheets/jqueryui-smoothness/jquery-ui-1.8.14.custom.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${contextPath}/javascripts/jquery-ui-timepicker-addon.js"></script>
		<s:form beanclass="in.espirit.tracer.action.AlertActionBean">
			<div id="bodycontent">
				<div class="row">
				<s:errors globalErrorsOnly="true"></s:errors>
					<div class="column grid-7">
						<div class="box"><s:hidden name="alert.id" />
						<div class="il">
							<dl>
								<dt>Name</dt>
								<dd><s:text name="alert.name" placeholder="Name of the alert" style="width: 370px" /></dd>
								<dt>Description</dt>
								<dd><s:textarea name="alert.desc" rows="10" cols="50" placeholder="Description of the alert" /></dd>
							</dl>
						</div>
						</div>
					</div>
					<div class="column grid-5">
						<div class="box">
							<div class="il">
							<dl>		
								<dt>Start Date</dt>
								<dd>
								<s:text id="startDate" name="alert.startDate" placeholder="Start Date"/>
								</dd>				
								<dt>Start Time</dt>
								<dd>
								<s:text id="startTime" name="alert.startTime" placeholder="Start Time"/>
								</dd>	
							</dl>
							</div>
						</div>
					</div>
						<div class="column grid-5">
						<div class="box">
							<div class="il">
							<dl>		
								<dt>End Date</dt>
								<dd>
								<s:text id="endDate" name="alert.endDate" placeholder="End Date"/>
								</dd>				
								<dt>End Time</dt>
								<dd>
								<s:text id="endTime" name="alert.endTime" placeholder="End Time"/>
								
								</dd>	
							</dl>
							</div>
						</div>
					</div>	
			<s:submit name="submit" value="Submit" /> 
			<s:submit name="cancel" value="Cancel" /></div>
			</div>
		</s:form>
	</s:layout-component>
	<s:layout-component name="inlineScripts">
	
$(document).ready(function(){
	$("#startDate").datepicker();
	$("#startTime").timepicker({});	
	$("#endDate").datepicker();
	$("#endTime").timepicker({});
	
});
	
	
	</s:layout-component>	
</s:layout-render>



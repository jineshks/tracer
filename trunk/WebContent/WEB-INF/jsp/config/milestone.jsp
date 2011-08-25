<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/config/config.jsp" section="milestone">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
	<s:layout-component name="body">
	<link href="${contextPath}/stylesheets/jqueryui-smoothness/jquery-ui-1.8.14.custom.css" rel="stylesheet" type="text/css" />
				<div class="box">
				<h4>New Milestone</h4>
				<s:form beanclass="in.espirit.tracer.action.ConfigMilestoneActionBean">
					<div class="column  grid-9">
						<s:hidden name="milestone.id"></s:hidden>
						<dl>
							<dt>Name</dt>
							<dd><s:text name="milestone.name" placeholder="Name"></s:text></dd>
							<dt>Description</dt>
							<dd>
								<s:textarea placeholder="Description" name="milestone.description" rows="10" style="width:90%"></s:textarea>
							</dd>
						</dl>
					</div>
					<div class="column  grid-7">
						<dl>
				 			<dt>Start Date</dt>
							<dd>
								<s:text id="startDate" name="milestone.startDate" placeholder="Start Date"/>
							</dd>				
							<dt>End Date</dt>
							<dd>
								<s:text id="endDate" name="milestone.endDate" placeholder="End Date"/>
							</dd>	
							<dt>Current Sprint</dt>
							<dd>
								<s:checkbox name="milestone.current"  value="TRUE"/> Yes													
							</dd>
				 		</dl>
				 		<s:submit name="submit" value="Submit" /> 
						
					</div>
				</s:form>
				
			</div>
			<div class="box">
				 <h4>Milestone</h4>

				<d:table name="${actionBean.milestoneList}" id="milestone">
					<d:column title="Name">
						<s:link href="${contextPath}/config/milestone/${milestone.id}"> 
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
	</s:layout-component>
<s:layout-component name="inlineScripts">
  $(document).ready(function() {	
  	showInfo();
  	
  	$("#startDate").datepicker({ dateFormat: 'yy-mm-dd' });
  	$("#endDate").datepicker({ dateFormat: 'yy-mm-dd' });
  
  });
</s:layout-component>
</s:layout-render>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/config/config.jsp" section="milestone">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
	<s:layout-component name="body">
			<div class="box">
				<h4>New Milestone</h4>
				<s:form beanclass="in.espirit.tracer.action.MilestoneActionBean">
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
	</s:layout-component>
<s:layout-component name="inlineScripts">
  $(document).ready(function() {	
  	showInfo();
  
  });
</s:layout-component>
</s:layout-render>
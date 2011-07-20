<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
		<div id="bodycontent">
		  <div class="row">    
		    <div class="column grid-7">
				<div class="box">
					<s:form beanclass="in.espirit.tracer.action.MilestoneActionBean">
					<s:hidden name="milestone.id"></s:hidden>
					<dl>
						<dt>Name</dt>
						<dd><s:text name="milestone.name"></s:text></dd>
						<dt>Description</dt>
						<dd>
							<s:textarea name="milestone.description" rows="4" cols="80"></s:textarea>
						</dd>
						<dt>Current Sprint</dt>
						<dd>
							<s:radio name="milestone.current" value="TRUE"/>Yes
							<s:radio name="milestone.current" value="FALSE"/>No				
						</dd>
					</dl>
					<s:submit name="submit" value="Submit" /> 
					</s:form>
				</div>
			</div>
		</div>
	  </div>
	</s:layout-component>
</s:layout-render>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
		<s:form beanclass="in.espirit.tracer.action.LinkActionBean">
			<div id="bodycontent">
				<div class="row">
				<s:errors globalErrorsOnly="true"></s:errors>
					<div class="column grid-7">
						<div class="box">
						<div class="il"> <s:hidden name="link.id" />
							<dl>
								<dt>Name</dt>
								<dd><s:text name="link.name" placeholder="Name of the link" style="width: 370px" /></dd>
								<dt>Description</dt>
								<dd><s:textarea name="link.desc" rows="10" cols="60" placeholder="Description of the link" /></dd>
												
							</dl>
						</div>				
						</div>
					</div>
						<div class="column grid-7">
							<div class="box">
								<dl>
									<dt>Link</dt>
									<dd><s:text name="link.target" placeholder="Link" /></dd>				
									<dt>Tags</dt>
									<dd><s:text name="link.tags" placeholder="Tags" /></dd>
									<dt>Public (Check if you want link to be visible to all)</dt>
									<dd>
										<s:checkbox name="link.teamVisible" value="1"/>
									</dd>							
								</dl>			
										<s:submit name="submit" value="Submit" />					
							</div>				
						
						</div>
						
						
					</div>					
			</div>
		
		</s:form>
	</s:layout-component>
	<s:layout-component name="inlineScripts">
	
$(document).ready(function(){
	
	$("form").bind("submit", function(event) {
		
		
	});
	
});


	
	
	</s:layout-component>	
</s:layout-render>



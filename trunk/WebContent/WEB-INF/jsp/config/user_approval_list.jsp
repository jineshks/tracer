<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/config/config.jsp" section="useradmin">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body">  
<jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>
		<div class="box tac">
			<s:form partial="true" action="#">
				<d:table name="${actionBean.userApprovalList}" id="user">				
					<d:column title="User Id">
					<s:link href="${contextPath}/user/${user.userName}"> ${user.userName}
					</s:link>
					</d:column>
					<d:column title="Display Name" property="displayName"></d:column>
					<%-- <d:column title="Email" property="email"></d:column> --%>
					<d:column title="Team"> 
						<input type="text" name="team" id="team">
					</d:column>
					<d:column title="Role">
						<s:select name="role" id="role">
									<option></option>
									<s:options-collection collection="${configView.userRole}"/>									
						</s:select>	
					</d:column>
					<d:column title="Approve">
						<input type="button" value="Approve" class="orange">
					</d:column>
					<d:column title="Reject">
						<input type="button" value="Reject" class="orange">						
					</d:column>							
				</d:table>	
			</s:form>
		</div>
</s:layout-component>
<s:layout-component name="inlineScripts">



  $(document).ready(function() {	
   	showInfo();
  	
  	$(":button").click(function(){
  		var buttonName = $(this).attr('value');
  		var userName = $(this).parent().parent().find('a').text();
  		var team = $(this).parent().parent().find('#team').val();
  		var role = $(this).parent().parent().find('#role').val();
  		
  		if (buttonName=='Approve') {
  			if (team=='' || role=='') {
  				showMessage('Team Name / Role is mandatory');
  				return false;
  			}
  			window.location = '/tracer/signup?approve&userName=' + userName + '&team=' + team + '&role=' + role;			
  		}
  		else if (buttonName=='Reject') {
  			window.location = '/tracer/signup?reject&userName=' + userName;
  		}  		
  	}); 
  	
  });
   
   
   
</s:layout-component>  
</s:layout-render>
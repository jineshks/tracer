<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body">  
  	<div id="bodycontent">
		<div class="row">
			<div class="column grid-15">
				<div class="box tac">
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
						<select name="role" id="role">
									<option></option>
									<option>Viewer</option>
									<option>Editor</option>
									<option>Admin</option>
						</select>	
					</d:column>
					<d:column title="Approve">
						<input type="button" value="Approve" class="orange">
					</d:column>
					<d:column title="Reject">
						<input type="button" value="Reject" class="orange">						
					</d:column>							
				</d:table>	
				</div>
			</div>		
		</div>
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
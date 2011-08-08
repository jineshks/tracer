<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout_external.jsp">
<s:layout-component name="infoPanel"> 
	<s:errors globalErrorsOnly="true"></s:errors>
</s:layout-component>
<s:layout-component name="body">  
<s:form beanclass="in.espirit.tracer.action.UserSignUpActionBean">  
  <div id="bodycontent">
    <div class="row">      
   	   <div class="column grid-5">
   	   				<s:hidden name="user.approvalStatus"></s:hidden>  <!-- only for new users. It will be zero. On approval it is one -->
          <div class="box">
          <h4>Login Details</h4>
          	<div class="il">
          		<dl>
           				<dt>User Id</dt>
          				<dd> <s:text name="user.userName" placeholder="User ID"/></dd>
          				<dt>Password</dt>          													
          				<dd> <s:password name="user.password" placeholder="Password"></s:password></dd>
          				<dt>Confirm Password</dt>
          				<dd> <s:password name="confirmPassword" placeholder="Retype Password"/></dd>
          			</dl>	
          	      		
          	</div>	
          </div>           
      </div>
      <div class="column grid-5">
          <div class="box">
          	<h4>Contact Details</h4>
          	<div class="il">
          		<dl>
          		<dt>Display Name</dt>          													
          			<dd> <s:text name="user.displayName" placeholder="Display name"></s:text></dd>
          		<dt>E-Mail</dt>          													
          			<dd> <s:text name="user.email" placeholder="E-Mail"></s:text></dd> 
          		</dl>         		
          	</div>	
          </div>           
      </div>  
    </div>
    
    <s:submit name="submit" value="Submit"/>    		
    </div>
  </div>
</s:form>
</s:layout-component>
<s:layout-component name="inlineScripts">
	
$(document).ready(function(){
	$("form").bind("submit", function(event) {
		var submitFlag = true;
		if (($("input[name='user.userName']").val()=='') || ($("input[name='user.password']").val()=='') || ($("input[name='user.email']").val()=='') || ($("input [name='user.displayName']").val()=='')) {
			showMessage('All the fields must be filled.');
			return false;
		}	
		if ($("input[name='user.password']").val()!=$("input[name='confirmPassword']").val()) {
			showMessage('Password and Confirm Password doesn\'t match!');
			return false;
		}
						
		var loadUrl = "/tracer/signup/"+$("input[name='user.userName']").val()+"~"+ $("input[name='user.email']").val() +"?checkUserNameEmail";
		$.ajax({
	       		url : loadUrl,  
	           	success : function(responseText){ 
	            			if(responseText=="UserNameEmail") {
	            				showMessage('UserName & E-mail is already taken. Please enter a different Name & E-Mail')
	               				submitFlag = false;	               				
	               			}  
	               			else if (responseText=="UserName") {
	               				showMessage('UserName is already taken. Please enter a different Name')
	               				submitFlag = false;
	               			}             				               			
	               			else if (responseText=="Email") {
	               				showMessage('E-mail is already taken. Please enter a different E-mail')
	               				submitFlag = false;
	               			}             			
	             		},  
	            async : false  
	     	}); 	
	     
	     	if (submitFlag==false) {
	     		return false;
	     	}
		         		
	});
	
});


	
	
	</s:layout-component>	

</s:layout-render>


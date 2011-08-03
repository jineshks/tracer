<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:errors globalErrorsOnly="true"></s:errors>
</s:layout-component>
<s:layout-component name="body">  
<s:form beanclass="in.espirit.tracer.action.UserActionBean">  
  <div id="bodycontent">
    <div class="row">      
   	   <div class="column grid-5">
   	   				<s:hidden name="user.approvalStatus"></s:hidden>  <!-- only for new users. It will be zero. On approval it is one -->
          <div class="box">
          <h4>Login Details</h4>
          	<div class="il">
          		<dl>
          		<c:choose>
          			<c:when test="${actionBean.user.userName eq null}">          		
          				<dt>User Id</dt>
          				<dd> <s:text name="user.userName" placeholder="User ID"/></dd>
          				<dt>Password</dt>          													
          				<dd> <s:password name="user.password" placeholder="Password"></s:password></dd>
          				<dt>Confirm Password</dt>
          				<dd> <s:password name="confirmPassword" placeholder="Retype Password"/></dd>          				    
          			</c:when>
          			<c:otherwise>
          				<s:hidden name="user.userName"></s:hidden>
          				<dt>User Id</dt>
          				<dd>${actionBean.user.userName}</dd>          			
          			</c:otherwise>       
          	</c:choose>	             	
          			<dt>E-Mail</dt>          													
          			<dd> <s:text name="user.email" placeholder="E-Mail"></s:text></dd>          		 
          			</dl>	
          	      		
          	</div>	
          </div>           
      </div>
      <c:if test="${actionBean.user.userName ne null}">
      <div class="column grid-5">
          <div class="box">
          	<h4>Contact Details</h4>
          	<div class="il">
          		<dl><dt>Display Name</dt>          													
          			<dd> <s:text name="user.displayName" placeholder="Display name"></s:text></dd>
          			<dt>E-Mail (Additional)</dt>
          			<dd> <s:text name="user.emailSecond" placeholder="E-Mail - Secondary"></s:text></dd>
          			<dt>Phone</dt>
          			<dd> <s:text name="user.phone" placeholder="Phone"/></dd>
          			<dt>Chat</dt>
          			<dd> <s:text name="user.chatId" placeholder="chat"/></dd>
          			<dt>Web</dt>
          			<dd> <s:text name="user.web" placeholder="Web Page"/></dd>          			
          		</dl>         		
          	</div>	
          </div>           
      </div>
      <div class="column grid-6">
          <div class="box">
			<h4>Yourself!</h4>
				<div class="il">
				<dl>
					<dt>Team</dt>          													
          			<dd> <s:text name="user.team" placeholder="Team"></s:text></dd>
          			<dt>Status</dt>          													
          			<dd> <s:text name="user.status" placeholder="Status"></s:text></dd>
          			<dt>Who am I?</dt>          													
          			<dd> <s:text name="user.whoAmI" placeholder="Who Am I?"></s:text></dd>
          			<dt>Skills</dt>
          			<dd> <s:text name="user.skills" placeholder="Skills"/></dd>
          			<dt>Passions</dt>
          			<dd> <s:text name="user.passion" placeholder="Passion"/></dd>
          		</dl>    
				</div>
			</div>           
      </div> 
    </c:if>
    </div>
    <div class="row">
    <c:choose>
    	<c:when test="${actionBean.user.userName eq null}">
    		<s:submit name="submit" value="Submit"/>
    	</c:when>
    	<c:otherwise>
    		<s:submit name="update" value="Update"/>
    	</c:otherwise>
    </c:choose> 
		
    </div>
  </div>
</s:form>
</s:layout-component>
<s:layout-component name="inlineScripts">
	
$(document).ready(function(){
	
	showInfo();

	$("form").bind("submit", function(event) {
		var submitFlag = true;
		if ($("input[name='user.userName']").val()=='') {
			showMessage('User Name is a mandatory field');
			return false;
		}	
		if ($("input[name='user.password']").val()!=$("input[name='confirmPassword']").val()) {
			showMessage('Password and Confirm Password doesn\'t match!');
			return false;
		}
		
		if ($("input[name='user.email']").val()=='') {
			showMessage('E-mail is a mandatory field');
			return false;
		}
		
		// Make a check for valid email address.. if needed verify against the db.
		
		if ($("form :submit").attr('name')=='submit') { 
			var loadUrl = "/tracer/user/"+$("input[name='user.userName']").val()+"~"+ $("input[name='user.email']").val() +"?checkUserNameEmail";
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
	     }
	         		
	});
	
});


	
	
	</s:layout-component>	

</s:layout-render>


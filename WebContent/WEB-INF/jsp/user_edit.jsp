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
   	      <div class="box">
          <h4>Name Details</h4>
          	<div class="il">
          		<dl>
          				<s:hidden name="user.userName"></s:hidden>
          				<dt>User Id</dt>
          				<dd>${actionBean.user.userName}</dd>    
          				<dt>Display Name</dt>          													
          				<dd> <s:text name="user.displayName" placeholder="Display name"></s:text></dd>   
          				<dt>E-Mail</dt>          													
          				<dd> <s:text name="user.email" placeholder="E-Mail"></s:text></dd>  
          			</dl>	
          	</div>	
          </div>           
      </div>
 
      <div class="column grid-5">
          <div class="box">
          	<h4>Contact Details</h4>
          	<div class="il">
          		<dl>
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
    </div>
    <div class="row">
  
    		<s:submit name="update" value="Update"/>
  	
    </div>
  </div>
</s:form>
</s:layout-component>
<s:layout-component name="inlineScripts">
	
$(document).ready(function(){
	
	showInfo();

	$("form").bind("submit", function(event) {
				
		if (($("input[name='user.email']").val()=='') || ($("input[name='user.displayName']").val()=='')) {
			showMessage('E-mail and Display Name is a mandatory field');
			return false;
		}
		
		
		
	         		
	});
	
});


	
	
	</s:layout-component>	

</s:layout-render>


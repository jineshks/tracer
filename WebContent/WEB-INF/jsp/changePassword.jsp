<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:errors globalErrorsOnly="true"/>
</s:layout-component>
<s:layout-component name="body">  
  <div id="bodycontent">
  	<div class="box">
  	<s:form beanclass="in.espirit.tracer.action.ChangePasswordActionBean">
		<h4>Change Password</h4>
			<div class="il">
			<dl>
				<dt>User Name</dt>
				<dd>${actionBean.context.loggedUser}</dd>
				<dt>Current Password</dt>
				<dd><s:password name="currentPassword"></s:password> 
				<dt>New Password</dt>
				<dd><s:password name="newPassword"></s:password>
				<dt>Re-Type New Password</dt>
				<dd><s:password name="confirmNewPassword"></s:password> </dd>
			</dl>
			</div>
			<s:submit name="submit" value="Submit"></s:submit>	
		</s:form>
  	</div>  
  </div>
  </s:layout-component>
  <s:layout-component name="inlineScripts"> 
$(document).ready(function(){
	showInfo();
		
	$("form").bind("submit", function(event) {
	
		if ($("input[name='newPassword']").val()=="" || $("input[name='currentPassword']").val()=="") {
			showMessage('Password cannot be empty!');
			return false;
		}
		
		if ($("input[name='newPassword']").val()!=$("input[name='confirmNewPassword']").val()) {
			showMessage('New Password and Confirm Password doesn\'t match!');
			return false;
		}	
		
		if ($("input[name='newPassword']").val()==$("input[name='currentPassword']").val()) {
			showMessage('Old and New Password can\'t be same!');
			return false;
		}	
	});
});
</s:layout-component>	
  
</s:layout-render>
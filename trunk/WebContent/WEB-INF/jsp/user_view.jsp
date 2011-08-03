<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body">  
  <div id="bodycontent">
  <c:set var="user" value="${actionBean.user}"></c:set>
    <div class="row">      
   	   <div class="column grid-5">
   	      <div class="box">
          <h4>User Details</h4>
          	<div class="il">
          		<dl>
           			<dt>User Id</dt>
          			<dd>${user.userName}</dd>          			
          			<dt>Display Name</dt>          													
          			<dd> ${user.displayName}</dd>
          		</dl>         	      		
          	</div>	
          </div>           
      </div>
      <div class="column grid-5">
          <div class="box">
          	<h4>Contact Details</h4>
          	<div class="il">
          		<dl>
          			<dt>E-Mail (Primary)</dt>          													
          			<dd> ${user.email}</dd>
          			<dt>E-Mail (Secondary)</dt>    
          			<dd> ${user.emailSecond}</dd>
          			<dt>Phone</dt>
          			<dd> ${user.phone}</dd>
          			<dt>Chat</dt>
          			<dd> ${user.chatId}</dd>
          			<dt>Web</dt>
          			<dd> ${user.web}</dd>          			
          		</dl>         		
          	</div>	
          </div>           
      </div>
      <div class="column grid-6">
          <div class="box">
			<h4>Further Details!</h4>
				<div class="il">
				<dl>
					<dt>Team</dt>          													
          			<dd> ${user.team}</dd>
          			<dt>Status</dt>          													
          			<dd> ${user.status}</dd>
          			<dt>Who am I?</dt>          													
          			<dd> ${user.whoAmI}</dd>
          			<dt>Skills</dt>
          			<dd> ${user.skills}</dd>
          			<dt>Passions</dt>
          			<dd> ${user.passion}</dd>
          		</dl>    
				</div>
			</div>           
      </div> 
   </div>
   
   		<c:if test="${actionBean.context.userAdmin eq true && user.approvalStatus eq 0}">	
							<s:form beanclass="in.espirit.tracer.action.UserActionBean">
								<s:hidden name="userName">${user.userName}</s:hidden>
								<s:submit name="approve" value="Approve"/>
								<s:submit name="reject" value="Reject"/>							
							</s:form>
		</c:if>
  </div>

</s:layout-component>
<s:layout-component name="inlineScripts">
	
$(document).ready(function(){
	showInfo();	
});


	
	
	</s:layout-component>	

</s:layout-render>


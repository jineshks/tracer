<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="body">  
<c:if test="${actionBean.ticket.type eq 'defect'}">
<c:set var="beanclass" value="in.espirit.tracer.action.DefectActionBean"></c:set>
</c:if>
<c:if test="${actionBean.ticket.type eq 'task'}">
<c:set var="beanclass" value="in.espirit.tracer.action.TaskActionBean"></c:set>
</c:if>
<c:if test="${actionBean.ticket.type eq 'requirement'}">
<c:set var="beanclass" value="in.espirit.tracer.action.RequirementActionBean"></c:set>
</c:if>
  <jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>
  <s:form beanclass="${beanclass}">  
  <div id="bodycontent">
    <div class="row">      
   	<s:errors globalErrorsOnly="true"></s:errors>
      <div class="column grid-7">
          <div class="box">
          <s:hidden name="ticket.id"/>
          <s:hidden name="ticket.type"/>
          	<h4>Description </h4>
          	<div class="il">
          		<dl>
          			<dt>Title</dt>
          			<dd> <s:text name="ticket.title" placeholder="Short description of the ticket" style="width: 370px"/></dd>
          			<dt>Description</dt>
          			<dd> <s:textarea name="ticket.desc" rows="10" cols="50" placeholder="Description of the ticket"/></dd>
          			<dt>Comment</dt>
          			<dd> <s:textarea name="ticket.newComments" rows="5" cols="50" placeholder="Comment"/></dd>
          		</dl>
          		
          	</div>	
          </div>           
      </div>
      <div class="column grid-5">
          <div class="box">
          	<h4>Properties</h4>
          	<div class="il">
          		<dl>
          			<dt> Importance </dt>
          			<dd>
          				<s:select name="ticket.importance">
							<s:option value=""></s:option>
							<s:options-collection collection="${configView.importance}"/>
						</s:select> 
					</dd>
          			<dt> Priority </dt>
          			<dd> 
          				<s:select name="ticket.priority">
							<s:option value=""></s:option>
							<s:options-collection collection="${configView.priority}"/>
						</s:select>
					</dd>
					<dt> Status </dt>
					<dd> 
          				<s:select name="ticket.status">
							<s:option value=""></s:option>
							<s:options-collection collection="${configView.status}"/>
						</s:select>
					</dd>
          			<dt> Milestone </dt>
          			<dd> 
          				<s:select name="ticket.milestone">
						<s:option value=""></s:option>
						<s:options-collection collection="${configView.milestone}"/>
						</s:select>
					</dd>
					<dt> Phase </dt>
			        <dd> 
			         	<s:select name="ticket.phase">
						<s:option value=""></s:option>
						<s:options-collection collection="${configView.phase}"/>
						</s:select>
					</dd>
					<dt> Component </dt>
          			<dd>
          			<s:text name="ticket.component" placeholder="Component"/></dd>
          			<dt> Reporter </dt>
          			<dd>
          			<s:text id="reporter" name="ticket.reporter" placeholder="Reported by"/></dd>
          			<dt> Owner </dt>
          			<dd> <s:text id="owner" name="ticket.owner" placeholder="Assigned by"/> </dd>
          			<dt> Parent ticket </dt>
					<dd><s:text name="ticket.parentTicket" placeholder="#Parent Ticket, only one"/> </dd>
					<dt> Progress </dt>
          			<dd><s:text name="ticket.progress" placeholder="0 - 100"/> </dd>
          			<c:if test="${actionBean.ticket.type eq 'requirement'}">       			
          			<dt> Story Point </dt>
          			<dd><s:text name="ticket.storyPoint" placeholder="Story Point"/> </dd> 
          			</c:if>    
          			<dt> Tags </dt>
          			<dd><s:text name="ticket.tags" placeholder="Tags, comma separated"/> </dd>
          		</dl>
          		
          		
          	</div>	
          </div>           
      </div>
      <div class="column grid-4">
          <div class="box">
          	<h4>People</h4>
          	<div>
          		<div class="item white" draggable="true">
					<p class="p">Billy</p>
 				</div>
 				<div class="item white" draggable="true">
					<p class="p">Joe</p>
 				</div>
 				<div class="item white" draggable="true">
					<p class="p">Jack</p>
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

	$(function() {
		$( "#reporter" ).autocomplete({
			source: "/tracer/autocomplete?getUsers",
			minLength: 2
		});
		$( "#owner" ).autocomplete({
			source: "/tracer/autocomplete?getUsers",
			minLength: 2
		});
	});
});

</s:layout-component>

</s:layout-render>


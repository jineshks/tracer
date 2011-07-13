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
      <div class="column grid-7">
          <div class="box">
          <s:hidden name="ticket.id"/>
          <s:hidden name="ticket.type"/>
          	<h4>Step 1: Description </h4>
          	<div class="il">
          		<dl>
          			<dt>Title</dt>
          			<dd> <s:text name="ticket.shortDesc" placeholder="Short description of the ticket" style="width: 370px"/></dd>
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
          	<h4>Step 2: Properties</h4>
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
					<dt> Component </dt>
          			<dd>
          			<s:text name="ticket.component" placeholder="Component"/></dd>
          			<dt> Reporter </dt>
          			<dd>
          			<s:text name="ticket.reporter" placeholder="Reported by"/></dd>
          			<dt> Owner </dt>
          			<dd> <s:text name="ticket.owner" placeholder="Assigned by"/> </dd>
          			<dt> Related tickets </dt>
          			<dd><s:text name="ticket.related" placeholder="#related, comma separated"/> </dd>
          			<dt> Progress </dt>
          			<dd><s:text name="ticket.progress" placeholder="0 - 100"/> </dd>
          			<c:if test="${actionBean.ticket.type eq 'requirement'}">       			
          			<dt> Story Point </dt>
          			<dd><s:text name="ticket.storyPoint" placeholder="Story Point"/> </dd> 
          			</c:if>    
          		</dl>
          		
          		
          	</div>	
          </div>           
      </div>
      <div class="column grid-4">
          <div class="box">
          	<h4>Step 3: People</h4>
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
  <!--  deiva - for testing purposes -->      
   	<s:submit name="submit" value="Submit"/>
	<s:submit name="cancel" value="Cancel"/>	
    </div>
  </div>
</s:form>
</s:layout-component>
</s:layout-render>


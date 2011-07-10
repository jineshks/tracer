<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-definition>
	<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp">
		<s:layout-component name="header">
			<%-- 
<script type="text/javascript">
function select(id) {
var pre = document.getElementById(id).style.display;
if(pre=='none') { document.getElementById(id).style.display='';}
else { document.getElementById(id).style.display='none'; }
}
</script>
--%>
			<div id="header">
				<div id="logo">
					<a href="/"></a>
				</div>
				
				<dl id="tasks">
					<dt>Tasks</dt>
					<dd>
						<c:choose>
							<c:when test="${actionBean.context.currentSection ne 'mytask'}">
								<s:link beanclass="in.espirit.tracer.action.ListActionBean">My Tasks 
									<s:param name="list" value="my"></s:param>
									<s:param name="type" value="task"></s:param>
								</s:link>
							</c:when>
							<c:otherwise> My Tasks </c:otherwise>
						</c:choose>
					</dd>
					<dd>
						<c:choose>
							<c:when test="${actionBean.context.currentSection ne 'alltask'}">
								<s:link beanclass="in.espirit.tracer.action.ListActionBean">All Tasks
									<s:param name="list" value="all"></s:param>
									<s:param name="type" value="task"></s:param>
								</s:link>
							</c:when>
							<c:otherwise>All Tasks</c:otherwise>
						</c:choose>
					</dd>
					<dd>
						<c:choose>
							<c:when test="${actionBean.context.currentSection ne 'newtask'}">
									<s:link beanclass="in.espirit.tracer.action.TaskActionBean"> New Task
									<s:param name="ticket" value="new"></s:param></s:link>
							</c:when>
							<c:otherwise>New Task</c:otherwise>
						</c:choose>
					</dd>
				</dl>

				<dl id="defects">
					<dt>Defects</dt>
					<dd>
						<c:choose>
							<c:when test="${actionBean.context.currentSection ne 'mydefect'}">
								<s:link beanclass="in.espirit.tracer.action.ListActionBean">My Defects 
									<s:param name="list" value="my"></s:param>
									<s:param name="type" value="defect"></s:param>
								</s:link>
							</c:when>
							<c:otherwise> My Defects </c:otherwise>
						</c:choose>
					</dd>
					<dd>
						<c:choose>
							<c:when
								test="${actionBean.context.currentSection ne 'alldefect'}">
								<s:link beanclass="in.espirit.tracer.action.ListActionBean">All Defects
									<s:param name="list" value="all"></s:param>
									<s:param name="type" value="defect"></s:param>
								</s:link>
							</c:when>
							<c:otherwise>All Defects</c:otherwise>
						</c:choose>
					</dd>
					<dd>
						<c:choose>
							<c:when
								test="${actionBean.context.currentSection ne 'newdefect'}">
								<s:link beanclass="in.espirit.tracer.action.DefectActionBean"> New Defect	
								<s:param name="ticket" value="new"></s:param></s:link>								
							</c:when>
							<c:otherwise>New Defect</c:otherwise>
						</c:choose>
					</dd>
				</dl>
				
				<dl id="requirements">
					<dt>Requirements</dt>
					<dd>
						<c:choose>
							<c:when test="${actionBean.context.currentSection ne 'myrequirement'}">
								<s:link beanclass="in.espirit.tracer.action.ListActionBean">My Requirements 
									<s:param name="list" value="my"></s:param>
									<s:param name="type" value="requirement"></s:param>
								</s:link>
							</c:when>
							<c:otherwise> My Requirements </c:otherwise>
						</c:choose>
					</dd>
					<dd>
						<c:choose>
							<c:when
								test="${actionBean.context.currentSection ne 'allrequirement'}">
								<s:link beanclass="in.espirit.tracer.action.ListActionBean">All Requirements
									<s:param name="list" value="all"></s:param>
									<s:param name="type" value="requirement"></s:param>
								</s:link>
							</c:when>
							<c:otherwise>All Requirements</c:otherwise>
						</c:choose>
					</dd>
					<dd>
						<c:choose>
							<c:when
								test="${actionBean.context.currentSection ne 'newrequirement'}">
								<s:link beanclass="in.espirit.tracer.action.RequirementActionBean"> New Requirement
								<s:param name="ticket" value="new"></s:param></s:link>
							</c:when>
							<c:otherwise>New Requirement</c:otherwise>
						</c:choose>
					</dd>
				</dl>
				
				<dl id="milestones">
					<dt>Milestone</dt>
					<dd>
						<c:choose>
							<c:when
								test="${actionBean.context.currentSection ne 'milestone'}">
								<s:link
									beanclass="in.espirit.tracer.action.MilestoneListActionBean">All Milestones</s:link>
							</c:when>
							<c:otherwise>All Milestones</c:otherwise>
						</c:choose>
					</dd>
				</dl>
				<dl id="admin">
					<dt>Admin</dt>
					<dd>
						<c:choose>
							<c:when
								test="${actionBean.context.currentSection ne 'configList'}">
								<s:link beanclass="in.espirit.tracer.action.ConfigListActionBean">Config List</s:link>
							</c:when>
							<c:otherwise>Config List</c:otherwise>
						</c:choose>
					</dd>
				</dl>

				<dl id="logout">

					<dt>Logged in</dt>
					<dd>
						<span class="info"> ${actionBean.context.loggedUser }</span> 
					</dd>
					<dd>
						<s:link beanclass="in.espirit.tracer.action.ChangePasswordActionBean">[Change Password]							
						</s:link>
					</dd>
					<dd>
						<s:link beanclass="in.espirit.tracer.action.LoginActionBean" event="logout">[Log Out]
						</s:link>
					</dd>
				</dl>

			</div>
		</s:layout-component>
		<s:layout-component name="body">
			<div class="bodyContainer"> ${body} </div>
</s:layout-component>
	</s:layout-render>
</s:layout-definition>
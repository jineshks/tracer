<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
		<div id="bodycontent">
			<div id="main-section">
				<div class="row">

					<div class="column grid-4">
						<div class="box">
							<div class="title">
								<h6>Tasks</h6>	
								<span class="right">
									<s:link class="task" href="list/task/all" >All </s:link>
									<s:link class="new ml" href="/task/new" >New </s:link>
								</span>
							</div>
							<div class="pt">
								<ul>
								<c:forEach var="task" items="${actionBean.myTasks}">
									<li>
										<s:link href="/task/${task.id}" >#${task.id} - ${task.title}</s:link>
									</li>
								</c:forEach>								
								</ul>								
							</div>
						</div>
						<div class="box">
							
							<div class="title">
								<h6>Defects</h6>	
								<span class="right">
									<s:link class="defect" href="list/defect/all" >All </s:link>
									<s:link class="new ml" href="/defect/new" >New </s:link>
								</span>
							</div>
							<div class="pt">
								<ul>
								<c:forEach var="defect" items="${actionBean.myDefects}">
									<li>
										<s:link href="/defect/${defect.id}" >#${defect.id} - ${defect.title}</s:link>
									</li>
								</c:forEach>								
								</ul>
							</div>
						</div>
						<div class="box">
							<div class="title">
								<h6>User Stories</h6>	
								<span class="right">
									<s:link class="story" href="list/requirement/all" >All </s:link>
									<s:link class="new ml" href="/requirement/new" >New </s:link>
								</span>
							</div>
							<div class="pt">
								<ul>
								<c:forEach var="requirement" items="${actionBean.myRequirements}">
									<li>
										<s:link href="/requirement/${requirement.id}" >#${requirement.id} - ${requirement.title}</s:link>
									</li>
								</c:forEach>								
								</ul>
							</div>
						</div>
					</div>
					<div class="column grid-8">
						<div class="box">
							<h6>Activity Stream</h6>
						</div>
						<div class="box">
							<h6>Sprint</h6>
							<p>
								<a href="sprint-dashboard.html"> Sprint Dashboard </a>
							</p>
						</div>
						<div class="box">
							<h6>Team</h6>
							<p>
								<a href="team.html"> view people </a>
							</p>
						</div>
					</div>

					<div class="column grid-4">
						<div class="box orange">
							<h6>Alerts</h6>
						</div>

						<div class="box">
							<h6>Messaging</h6>
							<p>
								<a href="chat.html"> Chat </a>
							</p>
						</div>
						<div class="box">
							<h6>Documents</h6>
						</div>

					</div>

				</div>
			</div>
		</div>
	</s:layout-component>
</s:layout-render>
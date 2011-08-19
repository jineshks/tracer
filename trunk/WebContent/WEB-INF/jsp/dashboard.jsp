<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
	<s:layout-component name="body">
		<c:set var="userRole" value="${actionBean.context.userRole}"></c:set>			
		<div id="bodycontent">
			<div id="main-section">
				<div class="row">
					<div class="column grid-4">
						<div class="box">
							<div class="title">
								<h6>Tasks</h6>	
								<span class="right">
									<s:link class="list" href="list/task/all" >All </s:link>
									<c:if test="${userRole eq '2' || userRole eq '3'}">
										<s:link class="new ml" href="/task/new" >New </s:link>
									</c:if>
								</span>
							</div>
							<div class="pt">
								<ul>
								<c:forEach var="task" items="${actionBean.myTasks}">
									<li>
									    <span class="${task.priority}">#${task.id}</span> <span><s:link href="/task/${task.id}" > ${task.title}</s:link></span>
									</li>
								</c:forEach>								
								</ul>								
							</div>
						</div>
						<div class="box">
							
							<div class="title">
								<h6>Defects</h6>	
								<span class="right">
									<s:link class="list" href="list/defect/all" >All </s:link>
									<c:if test="${userRole eq '2' || userRole eq '3'}">
										<s:link class="new ml" href="/defect/new" >New </s:link>
									</c:if>
								</span>
							</div>
							<div class="pt">
								<ul>
								<c:forEach var="defect" items="${actionBean.myDefects}">
									<li>
										<span class="${defect.priority}">#${defect.id}</span> <span><s:link href="/defect/${defect.id}" > ${defect.title}</s:link></span>
									</li>
								</c:forEach>								
								</ul>
							</div>
						</div>
						<div class="box">
							<div class="title">
								<h6>User Stories</h6>	
								<span class="right">
									<s:link class="list" href="list/requirement/all" >All </s:link>
									<c:if test="${userRole eq '2' || userRole eq '3'}">
										<s:link class="new ml" href="/requirement/new" >New </s:link>
									</c:if>
								</span>
							</div>
							<div class="pt">
								<ul>
								<c:forEach var="requirement" items="${actionBean.myRequirements}">
									<li>
										<span class="${requirement.priority}">#${requirement.id}</span> <span><s:link href="/requirement/${requirement.id}" > ${requirement.title}</s:link></span>
									</li>
								</c:forEach>								
								</ul>
							</div>
						</div>
					</div>
					<div class="column grid-8">
						<div class="box">
							<div class="title">
								<h6>Activity Stream</h6>
								<span class="right">
									<s:link class="list" href="/activitystream" >All </s:link>							
								</span>
							</div>
							<div class="pt">
								<ul>
								<c:forEach var="activity" items="${actionBean.activities}">
									<li>
										<p class="">
											<span class="tal">${activity.activity}</span> <span class="tar right">${activity.timeStamp}</span>
										</p>	
									</li>
									
								</c:forEach>								
								</ul>
							</div>
						</div>
						<div class="box">
							<div class="title">
								<h6>Sprint</h6>
							</div>
							<div class="pt">
								
								<div>
									<div class="column grid-8">
										<p>
											<a href="taskboard"> Taskboard </a>
										</p>
										<p>
											<a href="planner"> Sprint Planner </a>
										</p>
										<p>
											<a href="list/milestone"> Sprint View </a>
										</p>
										<p>
											<a href="reports/burndown"> Reports </a>
										</p>
									</div>
									<div class="column grid-8">
										<c:set var="currMile" value="${actionBean.currentMilestone}"></c:set>
												<p> Start Date : ${currMile.startDate}  </p>
							          			<p> End Date : ${currMile.endDate}  </p>
							          			<p> Tickets : ${currMile.totalTickets}  </p>
							          			<p> Stories points : ${currMile.totalTickets}  </p>
							          			<div class="progressbar-small" title="progress:${currMile.progress}%" >
													<div id="cl-progress" class="progress-green" style="width: ${currMile.progress}%;">
													</div>
												</div>
									</div>
								</div>
							</div>
						</div>
						<div class="box">
							<div class="title">
								<h6>Team</h6>
							</div>
							<div class="pt">
								<p>
									<a href="list/user/active"> view people </a>
								</p>
							</div>
						</div>
					</div>

					<div class="column grid-4">
						<div class="box alert">
							<div class="title">
								<h6>Alerts</h6>
									<span class="right">
									<s:link class="list" href="list/alert/all" >All </s:link>
									<c:if test="${userRole eq '2' || userRole eq '3'}">
										<s:link class="new ml" href="/alert/new" >New </s:link>
									</c:if>
								</span>
							</div>
							<div class="pt">
								<ul>
								<c:forEach var="alert" items="${actionBean.alerts}">
									<li>
										<p class="">
											<span class="tal">
											<a href="alert/${alert.id}">${alert.name}</a>	
											</span>
											<span class="tar right">${alert.endDate} ${alert.endTime}</span>
										</p>	
									</li>
									
								</c:forEach>								
								</ul>
							</div>
						</div>

						<div class="box">
							<div class="title">
								<h6>Messaging</h6>
							</div>
							<div class="pt">
								<p>
									<a href="chat"> Chat </a>
								</p>
								<p>
									<a href="notifyEvent"> Notify </a>
								</p>
								
							</div>
						</div>
						<div class="box">
							<div class="title">
								<h6>Links</h6>
									<span class="right">
										<s:link class="list" href="list/link/my" >All </s:link>
										<c:if test="${userRole eq '2' || userRole eq '3'}">
											<s:link class="new ml" href="/link/new" >New </s:link>
										</c:if>
									</span>	
								</div>								
								<div class="pt">
								<ul>
								<c:forEach var="link" items="${actionBean.myLinks}">
									<li>
										<div>
											<p>
												<span class="tal">											
													<a href="${link.target}" target="new">${link.name}</a>	
												</span>
												<span class="tar right descContainer"><a id="linkNote" class="hideDesc"> <span class="hide">+ </span> </a></span>											
											</p>	
											<p id="linkDesc" class="hide">
												<span class="tal" >${link.desc}</span>
											</p>
										</div>
									</li>
								</c:forEach>								
								</ul>
							</div>	
						</div>
					</div>

				</div>
			</div>
		</div>
	</s:layout-component>
	<s:layout-component name="inlineScripts">
  		$(document).ready(function() {
  			showInfo();
  			
  			$('a#linkNote').click(function() {
  				$(this).parent().parent().parent().find('p#linkDesc').slideToggle('fast');	
  				$(this).toggleClass('showDesc hideDesc');	
  			});
  			
  			
  		});	
  		
  	</s:layout-component>
</s:layout-render>
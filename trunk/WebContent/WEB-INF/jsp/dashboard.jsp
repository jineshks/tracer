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
									<s:link class="task" href="list/task/all" >All Tasks </s:link>
									<s:link class="new ml" href="/task/new" >New Task </s:link>
								</span>
							</div>
							<div class="pt">
								<ul>
									<li>
										<s:link href="/task/1" >#1214 - Create new logo for tasks
											<s:param name="list" value="1"></s:param>
											<s:param name="type" value="task"></s:param>
										</s:link>
									</li>
									<li>
										<s:link href="/task/1" >#1215 - Create new logo for defects
											<s:param name="list" value="1"></s:param>
											<s:param name="type" value="task"></s:param>
										</s:link>
									</li>
								</ul>
								
							</div>
						</div>
						<div class="box">
							
							<div class="title">
								<h6>Defects</h6>	
								<span class="right">
									<s:link class="defect" href="list/defect/all" >All Defects </s:link>
									<s:link class="new ml" href="/defect/new" >New Defect </s:link>
								</span>
							</div>
							<div class="pt">
								<ul>
									<li>
										<s:link href="/defect/1" >#125 - Error while adding comments
											<s:param name="list" value="1"></s:param>
											<s:param name="type" value="task"></s:param>
										</s:link>
									</li>
									<li>
										<s:link href="/defect/2" >#234 - Broken links
											<s:param name="list" value="1"></s:param>
											<s:param name="type" value="task"></s:param>
										</s:link>
									</li>
								</ul>
							</div>
						</div>
						<div class="box">
							<h6>Requirements</h6>
							<p>
								<s:link href="/requirement/new" >New Requirement
								</s:link>
							</p>
							<p>
								<s:link href="/list/requirement/all" >All Requirements
								</s:link>
							</p>
							<p>
								<s:link href="/list/requirement/my" >My Requirements
								</s:link>
							</p>
							<p>
								<s:link href="/requirement/1" >#2
									<s:param name="list" value="1"></s:param>
									<s:param name="type" value="defect"></s:param>
								</s:link>
							</p>
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
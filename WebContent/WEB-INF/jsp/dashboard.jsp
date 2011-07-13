<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
		<div id="bodycontent">
			<div id="main-section">
				<div class="row">

					<div class="column grid-4">
						<div class="box">
							<h6>Tasks</h6>
							<p>
								<s:link href="/task/new" >New Task
									<s:param name="list" value="new"></s:param>
									<s:param name="type" value="task"></s:param>
								</s:link>
							</p>
							<p>
								<s:link href="/task/1" >#1214
									<s:param name="list" value="1"></s:param>
									<s:param name="type" value="task"></s:param>
								</s:link>
							</p>
						</div>
						<div class="box">
							<h6>Defects</h6>
							<p>
								<s:link href="/defect/new" >New Defect
									<s:param name="list" value="new"></s:param>
									<s:param name="type" value="defect"></s:param>
								</s:link>
							</p>
							<p>
								<s:link href="/defect/1" >#2
									<s:param name="list" value="1"></s:param>
									<s:param name="type" value="defect"></s:param>
								</s:link>
							</p>
						</div>
						<div class="box">
							<h6>Team</h6>
							<p>
								<a href="team.html"> view people </a>
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
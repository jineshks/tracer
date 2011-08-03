<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-definition>
	<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp">
		<s:layout-component name="inlineScripts">
			<script type="text/javascript">
				${inlineScripts}		
			</script>
		</s:layout-component>
		<s:layout-component name="header">
			<header id="header">
				<div class="row">
					<div class="column grid-2">
						<p class="logoContainer">
							<s:link  href="/dashboard" class="logo"> <span class="hide">TRACER</span>	</s:link>
						</p>
					</div>
					<div class="column grid-1">
						<span class="notifications"></span>
					</div>
					<div class="column grid-1">
						<span class="blank"></span>
					</div>
					<div class="column grid-8">
						<span class="nav"></span>
					</div>
					<div class="column grid-4">
						<span class="actionbar"></span>
					</div>
					
				</div>
			</header>
		</s:layout-component>
		<s:layout-component name="infoPanel">
			<div id="infoPanel">
				${infoPanel}
			</div>
		</s:layout-component>
		<s:layout-component name="body">
			<div class="bodyContainer"> ${body} </div>
		</s:layout-component>
		<s:layout-component name="footer">
			<footer id="footer">
				<div class="row">
					<div class="column grid-3">
						<p class="tar">
							TRACER is under MIT License
						</p>
						<p class="tar">
							<small>Copyright &copy; 2011 </small>
						</p>
					</div>
					<div class="column grid-2">
						<div class="tal">
							<h6>Tasks</h6>
							<ul>
								<li>
									<s:link href="${contextPath}/task/new" >New Task </s:link>
								</li>
								<li>
									<s:link  href="${contextPath}/list/task/my" >My Tasks </s:link>
								</li>
								<li>
									<s:link  href="${contextPath}/list/task/all" >All Tasks </s:link>
								</li>
							</ul>
						</div>
					</div>
					<div class="column grid-2">
						<div class="tal">
							<h6>Defects</h6>
							<ul>
								<li>
									<s:link href="${contextPath}/defect/new" >New Defect </s:link>
								</li>
								<li>
									<s:link  href="${contextPath}/list/defect/my" >My Defects </s:link>
								</li>
								<li>
									<s:link  href="${contextPath}/list/defect/all" >All Defects </s:link>
								</li>
							</ul>
						</div>
					</div>
					<div class="column grid-2">
						<div class="tal">
							<h6>User stories</h6>
							<ul>
								<li>
									<s:link href="${contextPath}/requirement/new" >New Story </s:link>
								</li>
								<li>
									<s:link  href="${contextPath}/list/requirement/my" >My Stories </s:link>
								</li>
								<li>
									<s:link  href="${contextPath}/list/requirement/all" >All Stories </s:link>
								</li>
							</ul>
						</div>
					</div>
					<div class="column grid-2">
						<div class="tal">
							<h6>User</h6>
							<ul>
								<li>
									<s:link href="${contextPath}/user/${actionBean.context.loggedUser}" >View My Profile</s:link>
								</li>
								<li>
									<s:link href="${contextPath}/user/${actionBean.context.loggedUser}/edit" >Edit My Profile</s:link>
								</li>
								<li>
									<s:link href="${contextPath}/user/changepassword" >Change Password</s:link>
								</li>
								<li>
									<s:link href="${contextPath}/login/logout">
									[Log Out] </s:link>
								</li>								
							</ul>
						</div>
					</div>
					<c:if test="${actionBean.context.userAdmin eq true}">		
					<div class="column grid-2">
						<div class="tal">
							<h6>Admin</h6>
							<ul>
								<li>
									<s:link href="${contextPath}/list/config" >Milestone </s:link>
								</li>
								<li>
									<s:link href="${contextPath}/list/user/approval" >User Approval</s:link>
								</li>
							</ul>
						</div>
					</div>
					</c:if>
					<div class="column grid-2">
						<p class="tal">
						</p>
					</div>
					<div class="column grid-2">
						<p class="tal">
						</p>
					</div>
					<div class="column grid-3 right">
							<p class="html5logoContainer">
								<s:link  href="http://www.w3.org/html/" class="html5logo"> <span class="hide">HTML5</span>	</s:link>
							</p>
					</div>
				</div>
			</footer>
		</s:layout-component>
	</s:layout-render>
</s:layout-definition>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout_external.jsp">
	<s:layout-component name="body">

		<div id="bodycontent">
			<div class="row">

				<div class="column grid-8">
					<div>
						<h4>TRACER</h4>
						<p>Design, Develop, Innovate.</p>
					</div>
				</div>

				<div class="column grid-8">
					<div class="box tac">
						<div class="login">
							<s:form beanclass="in.espirit.tracer.action.LoginActionBean">
								<ul>
									<li> <s:text name="user.userName" placeholder="Username"></s:text> </li>
									<li> <s:password name="user.password" placeholder="Password"></s:password> </li>
									<li> <s:submit name="login" value="Login" />	</li>
								</ul>
							</s:form>
						</div>
					</div>
				</div>
				
			</div>
		</div>

	</s:layout-component>
</s:layout-render>

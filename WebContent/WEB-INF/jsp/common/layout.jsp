<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-definition>
	<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp">
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
		<s:layout-component name="body">
			<div class="bodyContainer"> ${body} </div>
		</s:layout-component>
		<s:layout-component name="footer">
			<footer id="footer">
				<div class="row">
					<div class="column grid-16">
						<p class="tal">
							TRACER is under MIT License
						</p>
						<p class="tac">
							<small>Copyright &copy; 2011 </small>
						</p>
					</div>
				</div>
			</footer>
		</s:layout-component>
	</s:layout-render>
</s:layout-definition>
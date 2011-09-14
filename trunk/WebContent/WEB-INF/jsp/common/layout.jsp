<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-definition>
	<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp">
		<s:layout-component name="externalScripts">
				${externalScripts}
		</s:layout-component>
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
		
	</s:layout-render>
</s:layout-definition>
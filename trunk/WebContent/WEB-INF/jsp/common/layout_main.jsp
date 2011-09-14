<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-definition>
<!doctype html>
	<html lang="en">
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<title>Tracer</title>
			<link href="${contextPath}/stylesheets/custom.less" media="all" rel="stylesheet/less" type="text/css" />
			<link rel="shortcut icon" href="${contextPath}/favicon.ico" />
			
			<s:layout-component name="externalScripts">
			</s:layout-component>  
			
			<script type="text/javascript" src="${contextPath}/javascripts/less-1.0.41.js"></script>
			<script type="text/javascript" src="${contextPath}/javascripts/jquery-1.5.1.min.js"> </script>
			<script type="text/javascript" src="${contextPath}/javascripts/jquery-ui-1.8.14.custom.min.js"> </script>
			<script type="text/javascript">
				function showInfo() {
					if($("#infoPanel").html().trim()){
					  $("#infoPanel").animate({top: "+=60px", }, 1000 ).delay(4000).animate({top: "-=60px", }, 1000 );
				      //$("#infoPanel").show("slide", { direction: "up" }, 1000).delay(1000).hide("slide", { direction: "up" }, 1000);
					}
				};
				
				function showMessage(message) {
					  $("#infoPanel").html("<p>"+message+"</p>");
					  $("#infoPanel").animate({top: "+=60px", }, 1000 ).delay(4000).animate({top: "-=60px", }, 1000 );
				};
			</script>
			<!--[if lt IE 9]>
			    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
			  <![endif]-->
			
			
			
			<s:layout-component name="inlineScripts">
			</s:layout-component>
			
		</head>
		<body>
			<s:layout-component name="header">
				<header> </header>
			</s:layout-component>
			
			<s:layout-component name="infoPanel">
			</s:layout-component>
			
			<s:layout-component name="body">
			</s:layout-component>
		
			<s:layout-component name="footer">
					<footer id="footer">
				<c:set var="userRole" value="${actionBean.context.userRole}"></c:set>			
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
							<h6><s:label for="ticket.tasks" class="bold"/></h6>
							<ul>
								<c:if test="${userRole eq '2' || userRole eq '3'}">
									<li>
										<s:link href="${contextPath}/task/new" >New <s:label for="ticket.task"/></s:link>
									</li>
								</c:if>
								<li>
									<s:link  href="${contextPath}/list/task/my" >My <s:label for="ticket.tasks"/></s:link>
								</li>
								<li>
									<s:link  href="${contextPath}/list/task/all" >All <s:label for="ticket.tasks"/></s:link>
								</li>
							</ul>
						</div>
					</div>
					<div class="column grid-2">
						<div class="tal">
							<h6><s:label for="ticket.defects" class="bold"/></h6>
							<ul>
								<c:if test="${userRole eq '2' || userRole eq '3'}">
									<li>
										<s:link href="${contextPath}/defect/new" >New <s:label for="ticket.defect"/></s:link>
									</li>
								</c:if>
								<li>
									<s:link  href="${contextPath}/list/defect/my" >My <s:label for="ticket.defects"/> </s:link>
								</li>
								<li>
									<s:link  href="${contextPath}/list/defect/all" >All <s:label for="ticket.defects"/></s:link>
								</li>
							</ul>
						</div>
					</div>
					<div class="column grid-2">
						<div class="tal">
							<h6><s:label for="ticket.requirements" class="bold"/></h6>
							<ul>
								<c:if test="${userRole eq '2' || userRole eq '3'}">
									<li>
										<s:link href="${contextPath}/requirement/new" >New <s:label for="ticket.requirement"/> </s:link>
									</li>
								</c:if>
								<li>
									<s:link  href="${contextPath}/list/requirement/my" >My <s:label for="ticket.requirements"/></s:link>
								</li>
								<li>
									<s:link  href="${contextPath}/list/requirement/all" >All <s:label for="ticket.requirements"/></s:link>
								</li>
							</ul>
						</div>
					</div>
					<div class="column grid-2">
						<div class="tal">
							<h6>User</h6>
							<ul>
								<li>
									<s:link href="${contextPath}/user/${actionBean.context.loggedUser}" >My Profile</s:link>
								</li>
								<li>
									<s:link href="${contextPath}/rss">RSS Feeds</s:link>
								</li>
								<li>
									<s:link href="${contextPath}/login/logout">
									[Log Out] </s:link>
								</li>								
							</ul>
						</div>
					</div>
					<c:if test="${actionBean.context.userRole eq '3'}">		
					<div class="column grid-2">
						<div class="tal">
							<h6>Admin</h6>
							<ul>
								<li>
									<s:link href="${contextPath}/config/milestone" >Milestone </s:link>
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
		
		</body>
	</html>
</s:layout-definition>
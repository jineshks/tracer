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
				<footer> </footer>
			</s:layout-component>
		
		</body>
	</html>
</s:layout-definition>
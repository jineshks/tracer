<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-definition>
<!doctype html>
	<html lang="en">
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<title>Tracer</title>
			<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
			<link href="${contextPath}/stylesheets/custom.less" media="all" rel="stylesheet/less" type="text/css" />
			<script type="text/javascript" src="${contextPath}/javascripts/less-1.0.41.js"></script>
			<script type="text/javascript" src="${contextPath}/javascripts/jquery-1.5.1.min.js"> </script>
			<!--[if lt IE 9]>
			    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
			  <![endif]-->
			<s:layout-component name="inlineScripts">
			</s:layout-component>  
		</head>
		<body>
			<s:layout-component name="header">
				<div id="header"></div>
			</s:layout-component>
		
			<s:layout-component name="body">
			</s:layout-component>
		
			<s:layout-component name="footer">
				<div id="footer"></div>
			</s:layout-component>
		
		</body>
	</html>
</s:layout-definition>
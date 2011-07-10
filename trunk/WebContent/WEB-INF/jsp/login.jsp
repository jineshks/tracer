<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Login - TRACER</title>

<link href="stylesheets/custom.less" media="all" rel="stylesheet/less"
	type="text/css" />
<script src="javascripts/less-1.0.41.js"></script>
<!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->
</head>
<body>
	<header id="header">
		<div class="row">
			<div class="column grid-16">
				<p class="logoContainer">
					<a href="dashboard.html" class="logo"> <span class="hide">TRACER</span>
					</a>
				</p>
			</div>
		</div>
	</header>
	<div id="bodycontent">
		<div class="row">
			<div class="column grid-8">
				<div>
					<h4>TRACER</h4>
					<p>Design, Develop, Innovate</p>

				</div>

			</div>
			<div class="column grid-8">
				<div class="box tac">
					<div class="login">
						<s:form beanclass="in.espirit.tracer.action.LoginActionBean">
							<ul>
								<li>
									<s:text name="user.userName" placeholder="Username"></s:text>
								</li>
								<li>
									<s:password name="user.password" placeholder="Password"></s:password>
								</li>
								<li><s:submit name="login" value="Login" /></li>
							</ul>
						</s:form>
					</div>
				</div>

			</div>
		</div>
	</div>

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
</body>
</html>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="infoPanel">
		<s:messages />
	</s:layout-component>
	<s:layout-component name="body">
		<div id="bodycontent">
		<div id="main-section">
		<c:forEach var="displayList" items="${actionBean.userList}">
			<div class="group">
			<h5>${displayList.key}</h5>
			<c:forEach var="user" items="${displayList.value}">
				<div class="row">
				<div class="column grid-16">
				<div class="box clear">
				<div class="column grid-2"><img
					src="${contextPath}/images/profile_m.png" alt="profile_img"
					width="150" height="150" /></div>
				<div class="column grid-5 il">
				<dl>
					<dt>Name</dt>
					<dd>${user.displayName}</dd>
					<dt>Status</dt>
					<dd>${user.status}</dd>
					<dt>Who am i?</dt>
					<dd>${user.whoAmI}</dd>
					<dt>Skills</dt>
					<dd>${user.skills}</dd>
					<dt>Passions</dt>
					<dd>${user.passion}</dd>
				</dl>
				</div>
				<div class="column grid-5 il">
				<dl>
					<dt>Phone</dt>
					<dd>${user.phone}</dd>
					<dt>Email</dt>
					<dd><a href="mailto:${user.email}">${user.email}</a></dd>
					<dt>Email</dt>
					<dd>${user.emailSecond}</dd>
					<dt><a href="chat.html"> Chat </a></dt>
					<dd>${user.chatId}</dd>
					<dt>Web</dt>
					<dd>${user.web}</dd>
				</dl>
				</div>

				<div class="column grid-4 il">
				<div class="calendar clear">
				<div class="cal-head">
				<div class="hcell gray-cell"><a href="#"> W </a></div>
				<div class="hcell yellow-cell"><a href="#"> S </a></div>
				<div class="hcell yellow-cell"><a href="#"> M </a></div>
				<div class="hcell yellow-cell"><a href="#"> T </a></div>
				<div class="hcell yellow-cell"><a href="#"> W </a></div>
				<div class="hcell yellow-cell"><a href="#"> T </a></div>
				<div class="hcell yellow-cell"><a href="#"> F </a></div>
				<div class="hcell yellow-cell"><a href="#"> S </a></div>
				</div>

				<div class="cal-body">
				<div class="dcell gray-cell"><a href="#"> 23 </a></div>
				<div class="dcell"><a href="#"> </a></div>
				<div class="dcell green-cell"><a href="#"> 1 </a></div>
				<div class="dcell green-cell"><a href="#"> 2 </a></div>
				<div class="dcell green-cell"><a href="#"> 3 </a></div>
				<div class="dcell green-cell"><a href="#"> 4 </a></div>
				<div class="dcell green-cell"><a href="#"> 5 </a></div>
				<div class="dcell blue-cell"><a href="#"> 6 </a></div>
				<div class="dcell gray-cell"><a href="#"> 24 </a></div>
				<div class="dcell blue-cell"><a href="#"> 7 </a></div>
				<div class="dcell green-cell"><a href="#"> 8 </a></div>
				<div class="dcell green-cell"><a href="#"> 9 </a></div>
				<div class="dcell green-cell"><a href="#"> 10 </a></div>
				<div class="dcell red-cell"><a href="#"> 11 </a></div>
				<div class="dcell green-cell"><a href="#"> 12 </a></div>
				<div class="dcell blue-cell"><a href="#"> 13 </a></div>
				<div class="dcell gray-cell"><a href="#"> 25 </a></div>
				<div class="dcell blue-cell"><a href="#"> 14 </a></div>
				<div class="dcell green-cell"><a href="#"> 15 </a></div>
				<div class="dcell green-cell"><a href="#"> 16 </a></div>
				<div class="dcell green-cell"><a href="#"> 17 </a></div>
				<div class="dcell orange-cell"><a href="#"> 18 </a></div>
				<div class="dcell orange-cell"><a href="#"> 19 </a></div>
				<div class="dcell blue-cell"><a href="#"> 20 </a></div>
				<div class="dcell gray-cell"><a href="#"> 26 </a></div>
				<div class="dcell blue-cell"><a href="#"> 21 </a></div>
				<div class="dcell green-cell"><a href="#"> 22 </a></div>
				<div class="dcell green-cell"><a href="#"> 23 </a></div>
				<div class="dcell green-cell"><a href="#"> 24 </a></div>
				<div class="dcell green-cell"><a href="#"> 25 </a></div>
				<div class="dcell orange-cell"><a href="#"> 26 </a></div>
				<div class="dcell blue-cell"><a href="#"> 27 </a></div>
				<div class="dcell gray-cell"><a href="#"> 27 </a></div>
				<div class="dcell blue-cell"><a href="#"> 28 </a></div>
				<div class="dcell green-cell"><a href="#"> 29 </a></div>
				<div class="dcell green-cell"><a href="#"> 30 </a></div>
				<div class="dcell green-cell"><a href="#"> 31 </a></div>
				<div class="dcell"><a href="#"> </a></div>
				<div class="dcell"><a href="#"> </a></div>
				<div class="dcell"><a href="#"> </a></div>
				</div>
				</div>
				</div>
				</div>
				</div>
				</div>
			</c:forEach></div>
		</c:forEach></div>
		</div>
	</s:layout-component>
	<s:layout-component name="inlineScripts">
  $(document).ready(function() {	
  	showInfo();
  });
</s:layout-component>
</s:layout-render>
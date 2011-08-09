<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body"> 
	<div id="bodycontent">
		<div class="row">
			<div class="column grid-11">				
				<c:forEach var="hashmapList" items="${actionBean.milestoneList}" >
						<c:set var="milestone" value="${hashmapList.key}"/>
						<c:set var="divstylename" value="box"></c:set>
							<c:if test="${milestone.current eq 't'}">
								<c:set var="divstylename" value="box alert"></c:set>
							</c:if>
							<div class="${divstylename}">
							<h6><a href="milestone/${milestone.id}">${milestone.name}</a></h6>
								( ${milestone.startDate} - ${milestone.endDate} ) <br>
							<br>
							<span class="milestone">${milestone.description}</span>
							<br>
							OverallProgress : ${hashmapList.value} </div>					
				</c:forEach>
			</div>		
		</div>	
	</div>
</s:layout-component>
<s:layout-component name="inlineScripts">
  $(document).ready(function() {	
  	showInfo();
  
  });
</s:layout-component>  
</s:layout-render>

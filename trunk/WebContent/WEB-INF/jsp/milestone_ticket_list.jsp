<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body"> 
	<div id="bodycontent">
		<div class="row">
			<div class="column grid-11">	
				<c:set var="milestone" value="${actionBean.milestone}"></c:set>					
				<div class="box">
						<h6>${milestone.name}</h6>
						( ${milestone.startDate} - ${milestone.endDate} )
						 <br>
						<span class="milestone">${milestone.description}</span>
						<hr>	
						<ul>
							<c:forEach var="ticket" items="${actionBean.ticketList}">
							<li id="${ticket.id}" class="p white">
										<span class="hide">${ticket.type}</span>
										<span class="pl"> <a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </span>
										<span class="pl bl"> ${ticket.title} </span>
										<span class="pl bl imp ${ticket.importance}">${ticket.importance}</span>
										<span class="pl bl prio ${ticket.priority}"> ${ticket.priority}</span>
										<span class="pl bl right"><a href="#"> ${ticket.owner} </a></span>  
							</li>								
							</c:forEach>
						</ul>	
				</div>
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

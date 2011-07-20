<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
	<div id="bodycontent">
    <div id="main-section">
    		<div class="row">
					<div class="column grid-2">
						<div class="box">
							<h4>Sprint log</h4>							
							<ul id="sprint-list" class="connectedSortable">														
									<c:forEach var="ticket" items="${actionBean.backlog}">
									<li id="listItem_1" class="white">
											<p class="p1 handle"><a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </p> 
											<p> ${ticket.title} </p>								
									</li>									
									</c:forEach>								
							</ul>													
						</div>		
					</div>
					<div class="column grid-2">
						<div class="box">
							<h4>Development</h4>
							<ul id="dev-list" class="connectedSortable">
									<c:forEach var="ticket" items="${actionBean.development}">
									<li id="listItem_1" class="white">
											<p class="p2 handle"><a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </p> 
											<p> ${ticket.title} </p>								
									</li>									
									</c:forEach>	
								</ul>			
						</div>		
					</div>
					
					<div class="column grid-2">
						<div class="box">
								<h4>Ready for test</h4>
								<ul id="testready-list" class="connectedSortable">
									<c:forEach var="ticket" items="${actionBean.readyForTest}">
									<li id="listItem_1" class="white">
											<p class="p3 handle"><a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </p> 
											<p> ${ticket.title} </p>								
									</li>									
									</c:forEach>
								</ul>		
						</div>
					</div>
					
					<div class="column grid-2">
						<div class="box">
							<h4>Testing</h4>	
							<ul id="test-list" class="connectedSortable">
								<c:forEach var="ticket" items="${actionBean.testing}">
									<li id="listItem_1" class="white">
											<p class="p5 handle"><a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </p> 
											<p> ${ticket.title} </p>								
									</li>									
								</c:forEach>
							</ul>												
						</div>
					</div>    		
					
					<div class="column grid-2">
						<div class="box">
							<h4>Ready for Deploy</h4>
								<ul id="deploy-list" class="connectedSortable">
									<c:forEach var="ticket" items="${actionBean.readyForRelease}">
									<li id="listItem_1" class="white">
											<p class="p4 handle"><a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </p> 
											<p> ${ticket.title} </p>								
									</li>									
									</c:forEach>
								</ul>								
						</div>
					</div> 
					
					<div class="column grid-2">
						<div class="box">
							<h4>Released</h4>
								<ul id="released-list" class="connectedSortable">
									<c:forEach var="ticket" items="${actionBean.released}">
									<li id="listItem_1" class="white">
											<p class="p4 handle"><a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </p> 
											<p> ${ticket.title} </p>								
									</li>									
									</c:forEach>
								</ul>								
						</div>
					</div>       	    		
    		</div>	
    </div>
  </div>
</s:layout-component>


<s:layout-component name="inlineScripts">
  $(document).ready(function() {		  
		$( "#sprint-list,#dev-list, #test-list, #deploy-list, #testready-list, #released-list" ).sortable({
			connectWith: ".connectedSortable"
		}).disableSelection();

	    $("#test-list").sortable({
	      handle : '.handle',
	      update : function () {
			  var order = $('test-list').sortable('serialize');
		  		//$("#info").load("process-sortable.php?"+order);
		      }
		    });
		});
</s:layout-component>
</s:layout-render>


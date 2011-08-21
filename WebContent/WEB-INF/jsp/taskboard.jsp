<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
	<div id="bodycontent">
    <div id="main-section">
    		<div class="row">
					<div class="column grid-3">
						<div class="box">
							<h4>Sprint log</h4>							
							<ul id="Backlog" class="droptrue">														
									<c:forEach var="ticket" items="${actionBean.backlog}">
									<li id="${ticket.id}" class="white">
											<span class="hide">${ticket.type}</span>
											<p class="${ticket.priority} nm"> <a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> <span class="right">${ticket.owner}</span> </p> 
											<div class="progressbar-small" title="progress:${currMile.progress}%" >
													<div id="cl-progress" class="progress-green" style="width: ${ticket.progress}%;">
													</div>
											</div>	
											<p> ${ticket.title} </p>								
									</li>									
									</c:forEach>								
							</ul>													
						</div>		
					</div>
					<div class="column grid-3">
						<div class="box">
							<h4>Development</h4>
							<ul id="Development" class="droptrue">
									<c:forEach var="ticket" items="${actionBean.development}">
									<li id="${ticket.id}" class="white">
											<span class="hide">${ticket.type}</span>
											<p class="${ticket.priority} nm"><a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a><span class="right">${ticket.owner}</span> </p> 
											<div class="progressbar-small" title="progress:${currMile.progress}%" >
													<div id="cl-progress" class="progress-green" style="width: ${ticket.progress}%;">
													</div>
											</div>	
											<p> ${ticket.title} </p>								
									</li>									
									</c:forEach>	
								</ul>			
						</div>		
					</div>
					
					<div class="column grid-3">
						<div class="box">
								<h4>Ready for test</h4>
								<ul id="Ready for Test" class="droptrue">
									<c:forEach var="ticket" items="${actionBean.readyForTest}">
									<li id="${ticket.id}" class="white">
											<span class="hide">${ticket.type}</span>
											<p class="${ticket.priority} nm"><a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> <span class="right">${ticket.owner}</span></p> 
											<div class="progressbar-small" title="progress:${currMile.progress}%" >
													<div id="cl-progress" class="progress-green" style="width: ${ticket.progress}%;">
													</div>
											</div>	
											<p> ${ticket.title} </p>
																		
									</li>									
									</c:forEach>
								</ul>		
						</div>
					</div>
					
					<div class="column grid-3">
						<div class="box">
							<h4>Testing</h4>	
							<ul id="Testing" class="droptrue">
								<c:forEach var="ticket" items="${actionBean.testing}">
									<li id="${ticket.id}" class="white">
											<span class="hide">${ticket.type}</span>
											<p class="${ticket.priority} nm"><a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> <span class="right">${ticket.owner}</span></p> 
											<div class="progressbar-small" title="progress:${currMile.progress}%" >
													<div id="cl-progress" class="progress-green" style="width: ${ticket.progress}%;">
													</div>
											</div>	
											<p> ${ticket.title} </p>								
									</li>									
								</c:forEach>
							</ul>												
						</div>
					</div>    		
					
					<div class="column grid-3">
						<div class="box">
							<h4>Ready for release</h4>
								<ul id="Ready for release" class="droptrue">
									<c:forEach var="ticket" items="${actionBean.readyForRelease}">
									<li id="${ticket.id}" class="white">
											<span class="hide">${ticket.type}</span>
											<p class="${ticket.priority} nm"><a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> <span class="right">${ticket.owner}</span></p> 
											<div class="progressbar-small" title="progress:${currMile.progress}%" >
													<div id="cl-progress" class="progress-green" style="width: ${ticket.progress}%;">
													</div>
											</div>	
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
		var loadUrl = "/tracer/taskboard?persist";
		    
		$( "ul.droptrue" ).sortable({
			connectWith: ".droptrue",
			placeholder: 'dropzone',
			cursor: 'move',
			stop: function(event, ui) { 
				$.get(  
		            loadUrl,  
		            {ticket_id: $(ui.item).attr("id"), phase: $(ui.item).parent().attr("id"),ticket_type:$(ui.item).find(">:first-child").html()},  
		            function(responseText){ 
		                // Do nothing for success, inform for failure
						$("#result").html("");                  
		            },  
		            "html"  
	        	);				
			}
		});
		
		
	});
</s:layout-component>
</s:layout-render>


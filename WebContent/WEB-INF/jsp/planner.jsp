<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
	<div id="bodycontent">
    <div id="main-section">
    		<div class="row">
					<div class="column grid-8">
						<div class="box">
							<h4>${actionBean.leftMilestone}</h4>							
							<ul id="${actionBean.leftMilestone}" class="droptrue">														
									<c:forEach var="ticket" items="${actionBean.backlogTickets}">
									<li id="${ticket.id}" class="p white">
										<span class="hide">${ticket.type}</span>
										<span class="pl"> <a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </span>
										<span class="pl bl"> ${ticket.title} </span>
										<span class="pl bl ${ticket.importance}"><a href="#">${ticket.importance}</a></span>
										<span class="pl bl ${ticket.priority}"><a href="#"> ${ticket.priority}</a></span>
										<span class="pl bl right"><a href="#"> ${ticket.owner} </a></span>  
									</li>								
									</c:forEach>								
							</ul>													
						</div>		
					</div>
					
					<div class="column grid-8">
						<div class="box">
							<h4>${actionBean.rightMilestone} </h4>
							<ul id="${actionBean.rightMilestone}" class="droptrue">
									<c:forEach var="ticket" items="${actionBean.currentSprintTickets}">
									<li id="${ticket.id}" class="p white">
										<span class="hide">${ticket.type}</span>
										<span class="pl"> <a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </span>
										<span class="pl bl"> ${ticket.title} </span>
										<span class="pl bl ${ticket.importance}">${ticket.importance}</span>
										<span class="pl bl ${ticket.priority}"><a href="#"> ${ticket.priority}</a></span>
										<span class="pl bl right"><a href="#"> ${ticket.owner} </a></span>  
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
		var loadUrl = "/tracer/planner?persist";
		    
		$( "ul.droptrue" ).sortable({
			connectWith: ".droptrue",
			placeholder: 'dropzone',
			cursor: 'move',
			stop: function(event, ui) {
				$.get(  
		            loadUrl,  
		            {ticket_id: $(ui.item).attr("id"), milestone: $(ui.item).parent().attr("id"),ticket_type:$(ui.item).find(">:first-child").html()},  
		            function(responseText){ 
		                // Do nothing for success, inform for failure
						$("#result").html("");                  
		            },  
		            "html"
	        	);	
			}
		});
		
		$('.Critical').click(function(){
			$(this).removeClass('Critical');
			$(this).addClass('High');
			$(this).html('High');
		})
		
		$('.High').click(function(){
			$(this).removeClass('High');
			$(this).addClass('Medium');
			$(this).html('Medium');
		})
		
		$('.Medium').click(function(){
			$(this).removeClass('Medium');
			$(this).addClass('Low');
			$(this).html('Low');
		})
		
		$('.Low').click(function(){
			$(this).removeClass('Low');
			$(this).addClass('None');
			$(this).html('None');
		})
		
		$('.None').click(function(){
			$(this).removeClass('None');
			$(this).addClass('Critical');
			$(this).html('Critical');
		})
		
		
		
	});
</s:layout-component>
</s:layout-render>


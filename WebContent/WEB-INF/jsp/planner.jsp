<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
	<div id="bodycontent">
	<div class="quickAction" style="position: absolute; display: none;" id="impmenu">
   		<span class="Critical imp">Critical</span>
   		<span class="High imp">High</span>
   		<span class="Medium imp">Medium</span>
   		<span class="Low imp">Low</span>
   		<span class="None imp">None</span>
	</div>
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
										<span class="pl bl imp ${ticket.importance}"><a href="#">${ticket.importance}</a></span>
										<span class="pl bl prio ${ticket.priority}"><a href="#"> ${ticket.priority}</a></span>
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
										<span class="pl bl imp ${ticket.importance}"><a href="#">${ticket.importance}</a></span>
										<span class="pl bl prio ${ticket.priority}"><a href="#"> ${ticket.priority}</a></span>
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
		var ticket,ticket_type;    
		$( "ul.droptrue" ).sortable({
			connectWith: ".droptrue",
			placeholder: 'dropzone',
			cursor: 'move',
			stop: function(event, ui) {
				$.get(  
		            loadUrl,  
		            {	ticket_id: $(ui.item).attr("id"), 
		            	milestone: $(ui.item).parent().attr("id"),
		            	ticket_type:$(ui.item).find(">:first-child").html(),
		            	operation:'updateMilestone'
		            },  
		            function(responseText){ 
		                // Do nothing for success, inform for failure
						$("#result").html("");                  
		            },  
		            "html"
	        	);	
			}
		});
		
		$("li>.imp").click(function(ev) {
		  var pos = $(this).offset();  
		  $("#impmenu").css( { "left": (pos.left) + "px", "top":(pos.top - 30) + "px" } );
		  $("#impmenu").show();
		  ticket = $(this).parent().attr("id")
		  ticket_type = $(this).parent().find(">:first-child").html();
		});
		
		$(".quickAction>.imp").click(function(ev) {
			$("#impmenu").hide();
			$.get(  
		            loadUrl,  
		            {
		            	ticket_id: ticket, 
		            	importance: $(this).html(),ticket_type:ticket_type,
		            	operation:'updateImportance'
		            },  
		            function(responseText){ 
		                // Do nothing for success, inform for failure
						$("#result").html("");                  
		            },  
		            "html"
	        );	
		});
		
		var myFunction = function ($) { 
		    // Javascript code
		    //{ticket_id: $(ui.item).attr("id"),
		// phase: $(ui.item).parent().attr("id"),
		// ticket_type:$(ui.item).find(">:first-child").html()}, 
		};
		
		
	});
</s:layout-component>
</s:layout-render>


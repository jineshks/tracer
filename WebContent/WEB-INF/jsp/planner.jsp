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
	<div class="quickAction" style="position: absolute; display: none;" id="priomenu">
   		<span class="P1 prio">P1</span>
   		<span class="P2 prio">P2</span>
   		<span class="P3 prio">P3</span>
   		<span class="P4 prio">P4</span>
   		<span class="P5 prio">P5</span>
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
										<span class="pl bl imp ${ticket.importance}">${ticket.importance}</span>
										<span class="pl bl prio ${ticket.priority}"> ${ticket.priority}</span>
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
  </div>
</s:layout-component>


<s:layout-component name="inlineScripts">
  $(document).ready(function() {		  
		var loadUrl = "/tracer/planner?persist";
		var ticket,ticket_type, newClass; 
		
		//move from one list to another based on drop   
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
		  $("#impmenu").css( { "left": (pos.left) + "px", "top":(pos.top - 40) + "px" } );
		  $("#impmenu").show();
		  ticket = $(this).parent().attr("id")
		  ticket_type = $(this).parent().find(">:first-child").html();
		});
		
		$(".quickAction>.imp").click(function(ev) {
			$("#impmenu").hide();
			var impClass = $("#"+ticket).children('.imp').html();
			newClass = $(this).html();
			$("#"+ticket).children('.imp').removeClass(impClass).addClass(newClass);
			$("#"+ticket).children('.imp').html(newClass);
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
		
		$("li>.prio").click(function(ev) {
		  var pos = $(this).offset();  
		  $("#priomenu").css( { "left": (pos.left) + "px", "top":(pos.top - 40) + "px" } );
		  $("#priomenu").show();
		  ticket = $(this).parent().attr("id")
		  ticket_type = $(this).parent().find(">:first-child").html();
		});
		
		$(".quickAction>.prio").click(function(ev) {
			$("#priomenu").hide();
			var prioClass = $("#"+ticket).children('.prio').html();
			newClass = $(this).html();
			$("#"+ticket).children('.prio').removeClass(prioClass).addClass(newClass);
			$("#"+ticket).children('.prio').html(newClass);
			$.get(  
		            loadUrl,  
		            {
		            	ticket_id: ticket, 
		            	priority: $(this).html(),ticket_type:ticket_type,
		            	operation:'updatePriority'
		            },  
		            function(responseText){ 
		                // Do nothing for success, inform for failure
						$("#result").html("");                  
		            },  
		            "html"
	        );	
		});
		
	});
</s:layout-component>
</s:layout-render>


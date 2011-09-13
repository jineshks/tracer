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
		<jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>  
		<s:form action="/planner">
			<div class="column grid-8">
				<s:select name="leftMilestone">
					<s:option value=""></s:option>
					<s:options-collection collection="${configView.milestone}"/>
				</s:select>
			</div>
			<div class="column grid-5">
				<span>
					<s:select name="rightMilestone">
						<s:option value=""></s:option>
						<s:options-collection collection="${configView.milestone}"/>
					</s:select>
				</span>
			</div>
			<div class="column grid-3">
				<span class="right">
					<s:submit name="view" value="Update Planner"></s:submit>
				</span>
			</div>
		
		</s:form>
		</div>
    		<div class="row">
					<div class="column grid-8">
						<div class="box" id="left">
							<h4>${actionBean.leftMilestone}</h4>							
							<ul id="${actionBean.leftMilestone}" class="droptrue">														
									<c:forEach var="ticket" items="${actionBean.backlogTickets}">
									<li id="${ticket.type}-${ticket.id}" class="p white">
										<span class="hide">${ticket.type}</span>
										<span class="pl"> <a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </span>
										<span class="pl bl">${ticket.title}</span>
										<span class="pl bl imp ${ticket.importance}">${ticket.importance}</span>
										<span class="pl bl prio ${ticket.priority}"> ${ticket.priority}</span>
										<span class="pl bl right"><a href="#"> ${ticket.owner} </a></span>  
									</li>								
									</c:forEach>								
							</ul>													
						</div>		
					</div>
					
					<div class="column grid-8">
						<div class="box" id="right">
							<h4>${actionBean.rightMilestone} </h4>
							<ul id="${actionBean.rightMilestone}" class="droptrue">
									<c:forEach var="ticket" items="${actionBean.currentSprintTickets}">
									<li id="${ticket.type}-${ticket.id}" class="p white">
										<span class="hide">${ticket.type}</span>
										<span class="pl"> <a href="${contextPath}/${ticket.type}/${ticket.id}"> #${ticket.id} </a> </span>
										<span class="pl bl">${ticket.title}</span>
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
		
		var before_left;
		var before_right;
		
		var after_left;
		var after_right;
		
		var i = 0;
		var j = 1;
		var k = 0;
		
		//for(i=0 ; i < 100 ; i++) {
			//console.debug("j>" + j + "     k> " + k + "       difference>" + ((j-k) / 2) );
			//j = (j-k) / 2;	
		//}
		
		//move from one list to another based on drop   
		$( "ul.droptrue" ).sortable({
			connectWith: ".droptrue",
			placeholder: 'dropzone',
			cursor: 'move',
			stop: function(event, ui) {	
				
				after_left = $("#left > ul").sortable('toArray');
				after_right = $("#right > ul").sortable('toArray');
					
				if ((before_left.toString() == after_left.toString()) && (before_right.toString() == after_right.toString())) {
					return false;    // This is because if no changes occur in drag and drop.
				}
 						
				move_element = ui.item.attr('id');				
				
				if(before_left.indexOf(move_element) >= 0) {    //This if loop is used for calling which get request;
					move_from = "left";
				}
				else {
					move_from = "right";
				}				
				
				if (after_left.indexOf(move_element) >=0 ) {
					move_to = "left";
					after_temp = after_left;
				}
				else {
					move_to = "right";
					after_temp = after_right;
				}		
				
				if (move_from == move_to) {
					operation = 'updatePosition' ;
				}
				else {
					operation = 'updateMilestone' ;
				}			
				
				var order = after_temp.toString();	 
											
				$.get(  
		            loadUrl,
		            {	ticket_id: $(ui.item).attr("id").split("-")[1], 
		            	milestone: $(ui.item).parent().attr("id"),
		            	ticket_type:$(ui.item).find(">:first-child").html(),
		            	order : order,
		               	operation:operation
		            },  
		            function(responseText){ 
		                // Do nothing for success, inform for failure
						if (responseText == "error" ) {
							showMessage('Error in persisting changes to the drag drop operation.')
						}               
		            },  
		            "html"
	        	);
	        	
	        	before_left = $("#left > ul").sortable('toArray');
				before_right = $("#right > ul").sortable('toArray');	        	
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
		
		
		before_left = $("#left > ul").sortable('toArray');
		before_right = $("#right > ul").sortable('toArray');
		
	});
</s:layout-component>
</s:layout-render>


<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
		<div id="bodycontent">
			<div id="main-section">

				<div class="row">
					<div class="column  grid-5">
						<div class="leftpane box">
							<div class="message-panel ps">
								<span class=""> <input type="button" name="compose" class="blue ps" value="Compose" /> </span> 
								<span class=""> <input type="button" class="orange ps" value="Delete" /> </span>
							</div>
							<div id="msg-list">
						
							</div>
							<div class="pt" id="msg-foot">
								<span id="msg-pagecount" class="hide">${actionBean.pageCount}</span> <span id="msg-totalcount" class="hide">${actionBean.totalCount}</span>
								<span> Showing <span id="msg-start">1</span> - <span id="msg-end"></span></span> <span class="right"> <a
									id="previous" class="hide">Previous</a> | <a id="next" class="hide">Next</a> </span>
							</div>

						</div>
					</div>
					<div class="column  grid-7" id="msg-block" style="visibility:hidden;">
						<div class="box" id="msg-detail">
							<div class="message-panel ps">
								<span class=""> 
									<input type="button" class="blue ps" value="Reply" name="reply"/> </span> 
									<span class=""> 
									<input type="button" class="blue ps" value="Reply All" name="replyall"/> </span>
									<span class=""> 
									<input type="button" class="blue ps" value="Forward" name="forward"/> </span>
							</div>
							<div class="message-header ps">
								<h6></h6>
								<span class="hide" id="msg-id"></span>
								<span class="right" id="msg-date"></span>
								<p class="pt nm">
									<span class="fade">To: <span id="msg-to"></span></span>
									<span class="right fade">From: <span id="msg-from"></span></span>
									<span class="fade">Cc: <span id="msg-cc"></span></span> 
								</p>
							</div>
							<div class="message-body">
								<p id="msg-body">
								</p>								
							</div>

						</div>
					</div>
					<div class="column  grid-4">
						<div class="rightpane">
							<div class="history box">
								<h4>Filter</h4>
								<s:form beanclass="in.espirit.tracer.action.MessagingActionBean">
									<div class="il">
										<dl>
											<dt>Tag</dt>
											<dd>
												<s:text name="userName" placeholder="Tag" />
											</dd>
											<dt>User Name</dt>
											<dd>
												<s:text name="userName" placeholder="User Name" />
											</dd>
											<dt>From Date</dt>
											<dd>
												<s:text id="fromDate" name="fromDate"
													placeholder="From Date" />
											</dd>
											<dt>To Date</dt>
											<dd>
												<s:text id="toDate" name="toDate" placeholder="To Date" />
											</dd>
										</dl>
										<s:submit name="filter" value="Filter"></s:submit>
									</div>
								</s:form>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>	
	</s:layout-component>
	<s:layout-component name="inlineScripts">
		$(document).ready(function() {
			
			//call JSON to load the first list of messages.
			
			if (parseInt($("#msg-totalcount").text())>0) {
				$.getJSON(
					"tracer/messaging?MessageList",
					{offset:0},
					function (data) {
						if (data) {
							$("#msg-list").empty();
							$("#msg-start").text(1);
							end = 0;
							$.each(data, function(json) { 
									var temp = '';
									temp +=	'<div class="box-item ps" id="shMess">';
									temp += '<p>';
									temp +=	'<span> <input class="nm" type="checkbox" value='+ this.id + '> </span> ';	
									temp +=	'<a id="subject">'+ this.subj + '</a>';	
									temp +=	'</p>';
									temp +=	'<span class="fade">'+ this.from + '</span> ';	
									temp +=	'<span class="right fade">'+ this.date + '</span>';	
									temp +=	'</div>';	
									end += 1;
									$("#msg-list").append(temp);		
							});	
							$("#msg-end").text(end);														
						}				
				});			
						
			}
			else {
				$("#msg-foot").hide();	
			}	
						
			if (parseInt($("#msg-totalcount").text())>parseInt($("#msg-pagecount").text())) {		
				$("#next").show();
			}
						
			
			//$("div#shMess").click(function() {
				//alert("open the message but what about check box selection");
				//alert($("#msg-detail").find('h6').text());
				//$("#msg-detail").find('h6').text('New Message subject');			
			//});
			
			
			//$("a#subject").click(function() {
			$("a#subject").live("click", function() {
				$("#msg-block").css("visibility", "hidden");
				$.getJSON(
					"tracer/messaging?MessageDetails",
					{id:$(this).parent().find("input:checkbox").val()},
					function (data) {
						if (data) {
							$("#msg-detail").find('h6').text(data.subj);
							$("#msg-detail").find('#msg-date').text(data.date);
							$("#msg-detail").find('#msg-from').text(data.from);
							$("#msg-detail").find('#msg-to').text(data.to);
							$("#msg-detail").find('#msg-cc').text(data.cc);
							$("#msg-detail").find('#msg-body').text(data.message);
							$("#msg-detail").find('#msg-id').text(data.id);						
							$("#msg-block").css("visibility", "visible");
						}				
				});			
			});			
			
			$("input:button").click(function() {
				var butName = $(this).attr('name');				
				if (butName == "reply" || butName == "replyall" || butName == "forward") {
					location.href = "compose/" + butName + "/" + $("#msg-detail").find('#msg-id').text()
				}
				else if (butName == "compose") {
					location.href="compose";
				}	
			});
			
			$("a#next").click(function(){
				$.getJSON(
					"tracer/messaging?MessageList",
					{offset:$(this).parent().parent().find("#msg-end").text()},
					function (data) {
						if (data) {
							$("#msg-list").empty();
							$("#msg-start").text(parseInt($("#msg-end").text())+1);
							end = parseInt($("#msg-end").text());
							$.each(data, function(json) { 
									var temp = '';
									temp +=	'<div class="box-item ps" id="shMess">';
									temp += '<p>';
									temp +=	'<span> <input class="nm" type="checkbox" value='+ this.id + '> </span> ';	
									temp +=	'<a id="subject">'+ this.subj + '</a>';	
									temp +=	'</p>';
									temp +=	'<span class="fade">'+ this.from + '</span> ';	
									temp +=	'<span class="right fade">'+ this.date + '</span>';	
									temp +=	'</div>';	
									end += 1;
									$("#msg-list").append(temp);		
							});	
							$("#msg-end").text(end);		
							
							if (parseInt($("#msg-end").text())>=parseInt($("#msg-totalcount").text())) {
								$("#next").hide();
							}		
							$("#previous").show();										
						}				
				});
								
			});		
			
			$("a#previous").click(function(){
				$.getJSON(
					"tracer/messaging?MessageList",
					{offset:(parseInt($("#msg-start").text())-parseInt($("#msg-pagecount").text())-1)},
					function (data) {
						if (data) {
							$("#msg-list").empty();
							$("#msg-end").text(parseInt($("#msg-start").text())-1);
							$("#msg-start").text(parseInt($("#msg-start").text())-parseInt($("#msg-pagecount").text()));
							$.each(data, function(json) { 
									var temp = '';
									temp +=	'<div class="box-item ps" id="shMess">';
									temp += '<p>';
									temp +=	'<span> <input class="nm" type="checkbox" value='+ this.id + '> </span> ';	
									temp +=	'<a id="subject">'+ this.subj + '</a>';	
									temp +=	'</p>';
									temp +=	'<span class="fade">'+ this.from + '</span> ';	
									temp +=	'<span class="right fade">'+ this.date + '</span>';	
									temp +=	'</div>';	
									$("#msg-list").append(temp);		
							});
							if (parseInt($("#msg-start").text())<=1) {
								$("#previous").hide();
							}
							$("#next").show();													
						}				
				});										
			});		
			
		});
		
  </s:layout-component>
</s:layout-render>

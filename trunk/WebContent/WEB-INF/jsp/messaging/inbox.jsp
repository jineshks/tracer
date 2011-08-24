<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="infoPanel"> 
	</s:layout-component>
	<s:layout-component name="body">
		<div id="bodycontent">
			<div id="main-section">
					<div class="row">
					<div class="column  grid-5">
						<div class="leftpane box">
							<div class="message-panel ps">
								<span class=""> <input type="button" name="compose" class="blue ps" value="Compose" /> </span> 
								<span class=""> <input type="button" name="delete" class="orange ps" value="Delete" /> </span>
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
					<div class="column  grid-7" id="msg-block" >
						<div class="box" id="msg-display" style="display:none;">
							<div class="message-panel ps">
								<span class=""> 
									<input type="button" class="blue ps" value="Reply" name="reply"/> </span> 
									<span class=""> 
									<input type="button" class="blue ps" value="Reply All" name="replyall"/> </span>
									<span class=""> 
									<input type="button" class="blue ps" value="Forward" name="forward"/> </span>
							</div>
							<div class="message-header ps">
								<h6 id="msg-subj"></h6>
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
						
						<div class="box tac" id="msg-compose"> 
							<div class="messaging">
								<s:form beanclass="in.espirit.tracer.action.MessagingActionBean">
									<ul>
										<li><s:text name="message.to" placeholder="To"></s:text>
										</li>
										<li><s:text name="message.cc" placeholder="CC"></s:text>
										</li>
										<li><s:text name="message.subject" placeholder="Subject"></s:text>
										</li>
										<li><span class="p"> <s:checkbox
													style="display:inline" name="message.important" value="1"></s:checkbox>Important
										</span> <span class="p"><s:checkbox style="display:inline"
													name="message.notify" value="1">Notify</s:checkbox> Copy on
												email</span>
										</li>
										<li><s:textarea name="message.message" 
												placeholder="Message" rows="20"></s:textarea></li>
										<li><s:submit name="sendMessage" value="Send" /></li>
									</ul>
								</s:form>
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
			
			//Function to be used for getting the list of messages at start, next and previous clicks
						
			$.fn.listMsgs = function(page) {
				var offsetval = 0;
				if (page=='next') {
					offsetval = $("#msg-end").text();
					$("#msg-start").text(parseInt($("#msg-end").text())+1);					
				}
				else if (page=='previous') {
					offsetval = parseInt($("#msg-start").text())-parseInt($("#msg-pagecount").text())-1;
					$("#msg-end").text(parseInt($("#msg-start").text())-1);
					$("#msg-start").text(parseInt($("#msg-start").text())-parseInt($("#msg-pagecount").text()));					
				}
				else if (page=='refresh') {    // This is to handle deletion of tickets in that page and display new ones now
					offsetval = (parseInt($("#msg-start").text())-1);
				}
				$("#msg-list").empty();				
				$.getJSON(
					"messaging?messageList",
					{offset:offsetval},
					function (data) {
						if (data) {			
							count = 0;
							$.each(data, function(json) { 
									var temp = '';
									var className = "box-item ps";
									if (this.unread == '1') {
										className = "box-item-unread ps";
									}									
									temp +=	'<div id="msg-item" class="' + className + '">';							
									temp += '<p>';
									temp +=	'<span> <input class="nm" type="checkbox" value='+ this.id + '> </span> ';	
									temp +=	'<a id="subject">'+ this.subj + '</a>';	
									temp +=	'</p>';
									temp +=	'<span class="fade">'+ this.from + '</span> ';	
									temp +=	'<span class="right fade">'+ this.date + '</span>';	
									temp +=	'</div>';	
									count += 1;
									$("#msg-list").append(temp);		
							});	
							$("#msg-end").text(count + parseInt($("#msg-start").text())-1);  // This is wrong and needs to be corrected now.
							
							$("#previous").show();	
							$("#next").show();
													
							//for to hide next						
							if (parseInt($("#msg-end").text())>=parseInt($("#msg-totalcount").text())) {
								$("#next").hide();
							}		
							// for to hide previous							
							if (parseInt($("#msg-start").text())<=1) {
								$("#previous").hide();
							}
						}
						else {  //case may be no data after deletion or null return from server
							if (parseInt($("#msg-start").text())> 1) {   //Try once for getting the list of first page again if the page is not first page.
								$("#msg-start").text('1');
								$.fn.listMsgs();
							}
							else {
								$("#msg-foot").hide();
							}												
						}														
				});				
			};
			
			//call JSON to load the first list of messages.
						
			if (parseInt($("#msg-totalcount").text())>0) {
				$.fn.listMsgs();
			}
			else {
				$("#msg-foot").hide();	
			}					
			
			//$("div#shMess").click(function() {
				//alert("open the message but what about check box selection");
				//alert($("#msg-display").find('h6').text());
				//$("#msg-display").find('h6').text('New Message subject');			
			//});
			
			// Commenting as the changes are not working. instead it causes an error
			//$(".nm").live("click", function() {   // This is for handling the click of the checkbox inside div id - msg-item..if such thing happens dont do anything.i.e don't show the message
			//	element.preventDefault();								
			//});
		
			//$("a#subject").live("click", function() {	
			$("#msg-item").live("click", function() {	
				var read = "0";
				//if ($(this).parent().parent().attr('class')=='box-item-unread') {
				if ($(this).attr('class')=='box-item-unread ps') {
					read = "1";
				}						
				$.getJSON(
					"messaging?messageDetails",
					//{id:$(this).parent().find("input:checkbox").val(), read:read},
					{id:$(this).find("input:checkbox").val(), read:read},
					function (data) {
						if (data) {
							$("#msg-display").find('#msg-subj').text(data.subj);
							$("#msg-display").find('#msg-date').text(data.date);
							$("#msg-display").find('#msg-from').text(data.from);
							$("#msg-display").find('#msg-to').text(data.to);
							$("#msg-display").find('#msg-cc').text(data.cc);
							$("#msg-display").find('#msg-body').text(data.message);
							$("#msg-display").find('#msg-id').text(data.id);
							$("#msg-display").css("visibility", "visible");
							$("#msg-display").show();
							$("#msg-compose").hide();
						}				
				});	
				//$(this).parent().parent().attr('class','box-item ps');   // setting the message to read always.
				$(this).attr('class','box-item ps'); 
						
			});			
			
			$("input:button[name=delete]").live("click", function() {	
				var msgid = "";
				var count = 0;
				var submitFlag = false;
				$(".nm:checked").each(function() {
					msgid += $(this).val() + ",";
					count += 1;				
				});		
				msgid = msgid.substring(0, msgid.length-1);
				if (msgid.length == 0 ) {
					showMessage("Select atleast one message for deletion");
					return false;
				}
				
				var loadUrl = "?deleteMessage&msgid=" + msgid;
				$.ajax({
	       			url : loadUrl,  
	           		success : function(responseText){ 
	            			if(responseText=="success") {
	            				submitFlag = true;	               				
	               			}  
	               			else if (responseText=="failure") {
	               				showMessage('Some problem with deleting messages. Please refresh this page and try again!');
	               				submitFlag = false;
	               			}             			
	             		},  
	            	async : false  
	     		}); 	
	     
	     		if (submitFlag) {
	     			$("#msg-totalcount").text((parseInt($("#msg-totalcount").text())- count));     				
	     			$.fn.listMsgs("refresh");	
	     		}
					
			});
			
			$("input.blue.ps").click(function() { //class blue ps is used for message composing buttons
			
				var butName = $(this).attr('name');
				//set the fields of the messages to what we need and then show it. This shouldn't happen for delete and filter as it should be returned.
				
				$("#msg-display").hide();
				$("#msg-compose").show();
				
				$("#msg-compose").find('input[name=message\\.to]').val('');
				$("#msg-compose").find('input[name=message\\.cc]').val('');
				$("#msg-compose").find('input[name=message\\.subject]').val('');		
				$("#msg-compose").find('textarea[name=message\\.message]').val('');
								
				if (butName == "reply") {
					$("#msg-compose").find('input[name=message\\.to]').val($("#msg-from").text());
					$("#msg-compose").find('input[name=message\\.subject]').val("Re:"+$("#msg-subj").text());
					$("#msg-compose").find('textarea[name=message\\.message]').val($("#msg-body").text());					
				}
				else if (butName == "replyall") {
					$("#msg-compose").find('input[name=message\\.to]').val($("#msg-from").text()+","+$("#msg-to").text());
					$("#msg-compose").find('input[name=message\\.cc]').val($("#msg-cc").text());
					$("#msg-compose").find('input[name=message\\.subject]').val("Re:"+$("#msg-subj").text());
					$("#msg-compose").find('textarea[name=message\\.message]').val($("#msg-body").text());					
				}
				else if (butName == "forward") {
					$("#msg-compose").find('input[name=message\\.subject]').val("Fw:"+$("#msg-subj").text());
					$("#msg-compose").find('textarea[name=message\\.message]').val($("#msg-body").text());
				}			
			});
			
			$("a#next").click(function(){
				$.fn.listMsgs("next");									
			});		
			
			$("a#previous").click(function(){
				$.fn.listMsgs("previous");													
			});					
		});
		
  </s:layout-component>
</s:layout-render>

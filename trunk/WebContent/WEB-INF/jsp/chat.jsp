<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="body"> 
 <div id="bodycontent">
    <div id="main-section">
    	
      <div class="row">
			 <div class="column  grid-5">
			 		<div class="leftpane box">
			 			
			 			<div class="chatlist" id="chatUser">
			 				
			 			</div>
			 		
			 		</div>
			 </div>
			 <div class="column  grid-6" >
			 		<div class="box chatpane ">
			 		<div id="chat">
			 			<div class="item blue">
								<span class="photo"> <img width="48px" height="48px" src="images/m48.png" alt=""> </span>
								<div id="chatHeading"></div>											 				
			 			</div>
			 			
			 			<div class="chatprofile blue" id ="chatMessageArea" >
			 				
			 			</div>
			 			
			 			<div class="send">
			 				<textarea id="messageText" rows="3" cols="35"  class="ta"></textarea>
			 				<input class="orange" type="button"  value="send" onclick="sendChatMessage();"/>
			 				<span class="droparea"> Drop files to send </span>
			 			</div>
			 		</div>
			 		</div>
			 </div>
			 <div class="column  grid-5">
			 		<div class="rightpane">
						<div class="history box">
							<h4>History</h4>		
							<p><a href="" > Yesterday </a></p>
							<p><a href="" > 23-June-2011</a></p>	
							<p><a href="" > 22-June-2011</a></p>
							<p><a href="" > 21-June-2011</a></p>	
							<p><a href="" > 20-June-2011</a></p>					
						</div>
						<div class="history box">
							<h4>Tickets</h4>		
							<p><a href="" > [#1231] Implement the new interface </a></p>
							<p><a href="" > [#1232] New logo for the enterprise </a></p>	
							<p><a href="" > [#1234] Refactoring the data access logic</a></p>
							<p><a href="" > [#1238] Analyse the application security implementation </a></p>	
							
						</div>			 		
			 		</div>
			 </div>
		</div>	
		 
			 	
    </div>
  </div>
  </s:layout-component>
  <s:layout-component name="inlineScripts">
  
		var timerChatList = null;
		var timerChatMessage = null;
		var timerChatListDelay = 2000;
		var timerChatMessageDelay = 1000;
		var selectedChatWindow = "";
		var currentChatWindow= "";
		var oldUserListHTML = "";
		 
		function init(){
			 $("#chat").hide();
			 getChatUserList(); 
		};
		 
		function getChatSession(div) {
			 
			 $("#chat").show();	 
			 selectedChatWindow = div.id;
			 var chatHeader = document.getElementById(selectedChatWindow);	
			 $("#chatHeading").html(chatHeader.textContent.toUpperCase()); 
			 
			 getPastMessages();		
		};
		 
		 
		function getPastMessages() {
			 if(currentChatWindow != selectedChatWindow ){
				
				$.ajax( {
							type : "POST",
							data :  "userWindow="+selectedChatWindow, 
							url : "pastChatMessages",					
							error : function(xhr, ajaxOptions, thrownError) {					
							},
							success : function(data) {
														
									$("#chatMessageArea").html(data);					
							}
						});
				
				currentChatWindow = selectedChatWindow;
			 
				getMessage();
			 }
		};
		
		 
		function getMessage() {
			 
				$.ajax( {
							type : "POST",
							data :  "userWindow="+selectedChatWindow, 
							url : "chatMessages",
							error : function(xhr, ajaxOptions, thrownError) {
								
							},
							success : function(data) {
							     $("#chatMessageArea").append(data);
							}
						});
				
				 timerChatMessage = setTimeout("getMessage()", timerChatMessageDelay);
			
		};
		
		
		function getChatUserList() {
				
					$.ajax( {
							type : "GET",
							data : "userWindow="+selectedChatWindow,
							url  : "chatUsers",
							error : function(xhr, ajaxOptions, thrownError) {
		
							},
							success : function(data) {
								if(data != oldUserListHTML){
									$("#chatUser").html(data);
									oldUserListHTML = data;
								}
							}
					});
				timerChatList = setTimeout("getChatUserList()", timerChatListDelay);
				
		};
		 
		  
		function sendChatMessage() {	
			  
			    var msg = $("#messageText").val();
		
			    $.ajax( {
					        type : "POST",
					        data :  "userWindow="+selectedChatWindow+"&msg="+msg ,
					        url : "SendChatMessage",
					        error : function(xhr, ajaxOptions, thrownError) {
		
					        },
					        success : function() {
						    	$("#messageText").val("");
					        }
				       });
		};
				
	 $(document).ready(function() {
	  	 showInfo();
	  	 init();
	 });
	
	
  </s:layout-component>  
</s:layout-render> 

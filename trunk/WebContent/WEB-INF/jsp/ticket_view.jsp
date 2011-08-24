<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
</s:layout-component>
<s:layout-component name="body">  
<c:if test="${actionBean.ticket.type eq 'defect'}">
<c:set var="beanclass" value="in.espirit.tracer.action.DefectActionBean"></c:set>
</c:if>
<c:if test="${actionBean.ticket.type eq 'task'}">
<c:set var="beanclass" value="in.espirit.tracer.action.TaskActionBean"></c:set>
</c:if>
<c:if test="${actionBean.ticket.type eq 'requirement'}">
<c:set var="beanclass" value="in.espirit.tracer.action.RequirementActionBean"></c:set>
</c:if>
  <c:set var="userRole" value="${actionBean.context.userRole}"></c:set>		
  <jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>  
  	<div id="bodycontent">
		<div class="row">
		<div class="column grid-10"><s:errors globalErrorsOnly="true"></s:errors>
		<div class="box">
		<h4>#${actionBean.ticket.id} - ${actionBean.ticket.title}</h4> 
		<h5>Description</h5> 
		<p id="ticketDesc">${actionBean.ticket.desc}</p>
		<a id="showTicketDescHistory">History</a>
		<ul id="descHistory" class="hide">
			<li>
			<img class='loading' src='../images/load.gif' alt='loading...'/>
			</li>
		</ul>		
		</div>
		<c:set var="parentTicket" value="${actionBean.parentTicket}"></c:set>
		<c:set var="subTickets" value="${actionBean.subTickets}"></c:set> 
		<c:if test="${parentTicket ne null  or subTickets ne null}">
			<div class="box">
			<div><c:if test="${parentTicket ne null}">
				<h4>Parent Ticket</h4>
				<p class="pb"><span class="bold"> <a
					href="${contextPath}/${parentTicket.type}/${parentTicket.id}">#${parentTicket.id}</a>
				</span> ${parentTicket.title }</p>
			</c:if> <c:if test="${subTickets ne null}">
				<h4>Sub Ticket(s)</h4>
				<c:forEach var="subtick" items="${subTickets}">
					<p><span class="bold"><a
						href="${contextPath}/${subtick.type}/${subtick.id}">#${subtick.id}</a>
					</span> ${subtick.title}</p>
				</c:forEach>
			</c:if></div>
			</div>
		</c:if>
		
		<div class="box">
					<div id="attachments" class="comments">  
						<h4>Attachments</h4>	
						<ul>
						<c:forEach var="attachment" items="${actionBean.ticket.attachments}">
							<li>
								<p><span class="bold">
								
								<!--  
								<s:link event="download" beanclass="in.espirit.tracer.action.AttachmentsActionBean">
									<s:param name="fileName" value="${actionBean.ticket.id}-${attachment.fileName}"/>
									${attachment.fileName}
								</s:link>
								-->
								
								<a href="${contextPath}/attachments/download/${actionBean.ticket.id}-${attachment.fileName}"> 
								${attachment.fileName} </a>
								
								</span> (${attachment.userName})
								<span class="tar right">${attachment.timeStamp}</span>
								</p>
							</li>
						</c:forEach>
						</ul>	
						
						<div id="uploadResult">
						
						</div>	
						<c:if test="${userRole eq '2' || userRole eq '3'}">																	
						<s:form name="uploadForm" action="/attachments" target="uploadTarget">	
							<s:hidden id="hTicketId" name="ticket.id" value="${actionBean.ticket.id}"></s:hidden>
							<s:hidden id="hTicketType" name="ticket.type" value="${actionBean.ticket.type}"></s:hidden>
							<div class="il">
							    <dl>
								  <dt> <s:file id="hAttachment" name="attachment"/> 
								  </dt>	
								 						
								<dd>
								<input name="uploadFile" type="button" id="uploadButton" value="Upload File" class="orange" onclick="javascript:startUpload()">		
								 	
								</dd>											
								</dl>							
							</div>	
							<div id="uploadProcess" style="visibility:hidden;"> Uploading is in progress..... (Replace it with image)</div>																												
						</s:form>
						</c:if>
					</div>
													
					<iframe id="uploadTarget" name="uploadTarget" src="#" style="width:0;height:0;border:0px solid #fff;">			
					</iframe>  
		</div>

		<div class="box">
					<div id="comments" class="comments">
						<h4>Comments</h4>
						
						<ul>
							<c:forEach var="comment" items="${actionBean.ticket.comments}" varStatus="loopCount">
							<li>
								<span class="tal"><a id="Comment#${loopCount.count}" href="#Comment#${loopCount.count}">Comment#${loopCount.count}</a></span> <span class="tar right">${comment.timeStamp}</span>
								<p><span class="bold">${comment.userName} :</span> ${comment.comment}</p>
							</li>
							</c:forEach>
						</ul>
						
						<div id="result">
						
						</div>
						<c:if test="${userRole eq '2' || userRole eq '3'}">
						<s:form action="/comments">	
							<s:hidden id="hTicketId" name="ticket.id" value="${actionBean.ticket.id}"></s:hidden>
							<s:hidden id="hTicketType" name="ticket.type" value="${actionBean.ticket.type}"></s:hidden>
						<div class="il">
							<dl>
								<dt>New comment</dt>
								<dd>
									<s:textarea id="newComment" name="ticket.newComments" rows="4" cols="70" placeholder="Comment"/>
								</dd>
							</dl>
							<input name="postComment" type="button" id="commentButton" value="Post comment" class="orange">
						</div>
						</s:form>
						</c:if>
					</div>

				</div>
			</div>
			
			<div class="column grid-6">
			<c:if test="${userRole eq '2' || userRole eq '3'}">
				<div class="box">
					<h4>New</h4>
					<p class="pt">
							<a class="new ml"href="${contextPath}/task/new/${actionBean.ticket.id}">Task</a>
								<c:if test="${actionBean.ticket.type ne 'requirement'}">
									<a class="new ml" href="${contextPath}/defect/new/${actionBean.ticket.id}">Defect</a>
								</c:if>
								<c:if test="${actionBean.ticket.type ne 'defect'}">
								 	<a class="new ml" href="${contextPath}/requirement/new/${actionBean.ticket.id}">Story</a>
								 </c:if>
					</p>
					</div>	
				</c:if>					
				<div class="box">
					<s:form beanclass="${beanclass}">	
					<h4>Properties</h4>
						
						<s:hidden name="ticket.id"></s:hidden>
						<s:hidden name="ticket.type"></s:hidden>
						<div class="il">
							<dl>
								<dt> Importance </dt>
			          			<dd>
			          				<s:select name="ticket.importance">
										<s:option value=""></s:option>
										<s:options-collection collection="${configView.importance}"/>
									</s:select> 
								</dd>
			          			<dt> Priority </dt>
			          			<dd> 
			          				<s:select name="ticket.priority">
										<s:option value=""></s:option>
										<s:options-collection collection="${configView.priority}"/>										
									</s:select>
								</dd>
								<dt> Status </dt>
								<dd> 
			          				<s:select name="ticket.status">
										<s:option value=""></s:option>
										<s:options-collection collection="${configView.status}"/>
									</s:select>
								</dd>
			          			<dt> Milestone </dt>
			          			<dd> 
			          				<s:select name="ticket.milestone">
									<s:option value=""></s:option>
									<s:options-collection collection="${configView.milestone}"/>
									</s:select>
								</dd>
								<dt> Phase </dt>
			          			<dd> 
			          				<s:select name="ticket.phase">
									<s:option value=""></s:option>
									<s:options-collection collection="${configView.phase}"/>
									</s:select>
								</dd>
								<dt> Component </dt>
			          			<dd>
			          			<s:text name="ticket.component" placeholder="Component"/></dd>
			          			<dt> Reporter </dt>
			          			<dd>
			          			<s:text id="reporter" name="ticket.reporter" placeholder="Reported by"/></dd>
			          			<dt> Owner </dt>
			          			<dd> <s:text id="owner" name="ticket.owner" placeholder="Assigned by"/> </dd>
			          			<dt> Parent ticket </dt>
			          			<dd><s:text name="ticket.parentTicket" placeholder="#Parent Ticket, only one"/> </dd>
			          			<dt> Progress </dt>
			          			<dd><s:text name="ticket.progress" placeholder="0 - 100"/> </dd> 
			          			<c:if test="${actionBean.ticket.type eq 'requirement'}">       			
			          			<dt> Story Point </dt>
			          			<dd><s:text name="ticket.storyPoint" placeholder="Story Point"/> </dd> 
			          			</c:if>    
			          			<dt> Tags </dt>
          						<dd><s:text name="ticket.tags" placeholder="Tags, comma separated"/> </dd>
							</dl>
							<c:if test="${userRole eq '2' || userRole eq '3'}">
								<s:submit name="submit" value="Submit"/>
							</c:if>
					</div>
				</s:form>
				</div>
				<div class="box">
					<h4>Commits (Not Yet Implemented!)</h4>

					<a href="#">Change set#223</a>
					<p>Commit comments will appear here. Comments will be listed in
						the chronological order</p>

					<a href="#">Change set#223</a>
					<p>Commit comments will appear here. Comments will be listed in
						the chronological order</p>
				</div>
			</div>

		</div>
	</div>
	
</s:layout-component>
<s:layout-component name="inlineScripts">
 
 function startUpload() { 			
 			var temp = document.getElementById('attachments');
  			var atemp = temp.getElementsByTagName('a');
  			var file = document.getElementById('hAttachment');
  			var i=0;
  			  			
  			if(file.value==''){
  				showMessage('No File selected. Please select one');
  				return false;
  			}	
  			
  			if(file.files[0].size>10485760) {
  				showMessage('Files below 10 MB only can be uploaded. Please select a other file');
  				document.uploadForm.hAttachment.value = '';
  				return false;
  			}
  			  			
  			for(i=0; i < atemp.length; i++) {
  			 	if (atemp[i].innerHTML.trim() == file.value) {
  			  		alert("File with same name is already attached to the ticket. Please select a different file.");
  			  		document.uploadForm.hAttachment.value = '';
  			  		return false;
  			  	}
  			} 			
 			document.getElementById('uploadProcess').style.visibility='visible';
 			document.forms['uploadForm'].submit();
		}
	
	
		function responseUpload(text) { 
			document.getElementById('uploadProcess').style.visibility='hidden';
			fileName = document.uploadForm.hAttachment.value;
			var divTag = document.createElement("div");
			divTag.innerHTML = text;
			document.getElementById('uploadResult').appendChild(divTag);
			document.uploadForm.hAttachment.value = '';
			//document.getElementById('uploadResult').innerHTML = fileName + " The file was uploaded successfully!";			
		}
 
$(document).ready(function(){

	$(function() {
		$( "#reporter" ).autocomplete({
			source: "/tracer/autocomplete?getUsers",
			minLength: 2
		});
		$( "#owner" ).autocomplete({
			source: "/tracer/autocomplete?getUsers",
			minLength: 2
		});
	});

  $("p#ticketDesc").click(function() {  //for editing the ticket description.
  	var existingDesc = $(this).text();
  	inputbox = "<textarea id='description' cols='80' name='description' rows='10'>"+ existingDesc +"</textarea>"
  	$(this).html(inputbox);
  	$("textarea#description").focus();
  	
  	$("textarea#description").blur(function() {
  		var changedDesc = $(this).val();
        if (existingDesc != changedDesc) {
        	$.get(  
	        	"/tracer/ticketdesc?update",  
	            {id: $("#hTicketId").val(),type: $("#hTicketType").val(),desc:changedDesc},  
	            function(responseText){ 
	                if (responseText=='failure') {
	                	showMessage('Updates to ticket description is not saved properly. Please refresh and try again');
	                }
	            },  
	            "html"  
	        );         	
         }
         $("#ticketDesc").text(changedDesc);
    });  	  
  });

  $("a#showTicketDescHistory").click(function() {
  	$("#descHistory").slideToggle('fast');  
  	var alreadyLoaded = $("ul#descHistory").find('img').size();   
  	// 1 means its not loaded. 0 means already loaded beacuase if img is there means already loaded.
  	if (alreadyLoaded == '1') {
  		$.getJSON(
			"/tracer/ticketdesc?history",
			{id:$("#hTicketId").val()},
			function (data) {
				$("ul#descHistory").empty();
				if (data) {
					var items = [];
					$.each(data, function(json) {	
						var temp = ''
						temp = '<li>';
						temp = temp + '<span class="tar right">'+ this.time +'</span>';
						temp = temp + '<p><span class="bold">' + this.user + ':</span>' + this.val + '</p>';
						temp = temp + '</li>'
						items.push(temp);							
					});		
					$("ul#descHistory").append(items.join(''));					
				}
				else {
					showMessage('There is no history of ticket description');
				}
			}				
		);
  	}	  	
  });
  
  
  $.ajaxSetup ({
		cache: false
	});

	var ajax_load = "<img class='loading' src='../images/load.gif' alt='loading...' />";
	var loadUrl = "/tracer/comments";

   $("#commentButton").click(function(event){
     
     if($("#newComment").val()==""){
     	showMessage("Please enter a comment");
     }else{
     	 var commentid = $('.comments li a:last').attr('id');
     	 var ncomment;
     	 if (commentid) {
     	   ncomment = parseInt(commentid.substring(commentid.indexOf("#")+1,commentid.length))+1;
     	 }
     	 else {
       	   ncomment = 1;
       	 }
       	 $("#result").html(ajax_load);  
	        $.get(  
	            loadUrl,  
	            {type: $("#hTicketType").val(), id: $("#hTicketId").val(),comment: $("#newComment").val(),commentid: ncomment},  
	            function(responseText){ 
	                $("#comments ul").append(responseText);
					$("#result").html("");                  
					$("#newComment").val("");
	            },  
	            "html"  
	        ); 
      } 
   });   
 });
 
</s:layout-component>
</s:layout-render>
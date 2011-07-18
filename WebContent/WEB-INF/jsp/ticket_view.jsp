<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">

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
  <jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>  
  	<div id="bodycontent">
		<div class="row">
			<div class="column grid-10">
			<s:errors globalErrorsOnly="true"></s:errors>
				<div class="box">					
					<h4>#${actionBean.ticket.id} - ${actionBean.ticket.title}</h4>
					<h5>Description</h5>
					<p>${actionBean.ticket.desc}</p>					
				</div>
				<c:set var="parentTicket" value="${actionBean.parentTicket}"></c:set>
				<c:set var="subTickets" value="${actionBean.subTickets}"></c:set>	
				<c:if test="${parentTicket ne null  or subTickets ne null}">
				<div class="box">
					<div>						
					<c:if test="${parentTicket ne null}">
						<h4>Parent Ticket</h4>
						<p class="pb">
							<span class="bold"> <a href="${contextPath}/${parentTicket.type}/${parentTicket.id}">#${parentTicket.id}</a> </span> 
							${parentTicket.title }
						</p>
					</c:if>
					<c:if test="${subTickets ne null}">
						<h4>Sub Ticket(s)</h4>					
						<c:forEach var="subtick" items="${subTickets}">
							<p>
							<span class="bold"><a href="${contextPath}/${subtick.type}/${subtick.id}">#${subtick.id}</a> </span> 
							${subtick.title}							
							</p>
						</c:forEach>	
					</c:if>				
					</div>					
				</div>
				</c:if>
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
					</div>

				</div>
			</div>
			
			<div class="column grid-6">
				<div class="box">
					<h4>Properties</h4>
					<s:form beanclass="${beanclass}">	
						<s:hidden name="ticket.id"></s:hidden>
						<s:hidden name="ticket.type"></s:hidden>
						<s:hidden name="ticket.title"></s:hidden>
						<s:hidden name="ticket.desc"></s:hidden>
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
								<dt> Component </dt>
			          			<dd>
			          			<s:text name="ticket.component" placeholder="Component"/></dd>
			          			<dt> Reporter </dt>
			          			<dd>
			          			<s:text name="ticket.reporter" placeholder="Reported by"/></dd>
			          			<dt> Owner </dt>
			          			<dd> <s:text name="ticket.owner" placeholder="Assigned by"/> </dd>
			          			<dt> Parent ticket </dt>
			          			<dd><s:text name="ticket.parentTicket" placeholder="#Parent Ticket, only one"/> </dd>
			          			<dt> Progress </dt>
			          			<dd><s:text name="ticket.progress" placeholder="0 - 100"/> </dd> 
			          			<c:if test="${actionBean.ticket.type eq 'requirement'}">       			
			          			<dt> Story Point </dt>
			          			<dd><s:text name="ticket.storyPoint" placeholder="Story Point"/> </dd> 
			          			</c:if>    
							</dl>
						<s:submit name="submit" value="Submit"/>
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
 $(document).ready(function(){
  
  $.ajaxSetup ({
		cache: false
	});

	var ajax_load = "<img class='loading' src='../images/load.gif' alt='loading...' />";
	var loadUrl = "/tracer/comments";

   $("#commentButton").click(function(event){
     
     if($("#newComment").val()==""){
     	alert("Please enter a comment");
     }else{
     	 var commentid = $('.comments li a:last').attr('id');
       	 var ncomment = parseInt(commentid.substring(commentid.indexOf("#")+1,commentid.length))+1;
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
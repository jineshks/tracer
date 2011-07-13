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
  <s:form beanclass="${beanclass}">	
  	<div id="bodycontent">
		<div class="row">
			<div class="column grid-10">
				<div class="box">
					<s:hidden name="ticket.id"></s:hidden>
					<s:hidden name="ticket.type"></s:hidden>
					<s:hidden name="ticket.shortDesc"></s:hidden>
					<s:hidden name="ticket.desc"></s:hidden>
					<h4>#${actionBean.ticket.id} - ${actionBean.ticket.shortDesc}</h4>
					<h5>Description</h5>
					<p>${actionBean.ticket.desc}</p>					
				</div>

				<div class="box">
					<div>
						<h4>Parent Ticket (Not Yet Implemented!)</h4>
						<p class="pb">
							<span class="bold"> <a href="#">#986</a> </span> The is the
							description of the parent ticket. This section will be shown only
							if there is a parent ticket.
						</p>

						<h4>Sub Tickets (Not Yet Implemented!) </h4>
						<p>
							<span class="bold"><a href="#">#1233</a> </span> The is the
							description of the first child ticket. This section will be shown
							only if there is atleast one ticket.
						</p>
						<p>
							<span class="bold"><a href="#">#1236</a>
							</span> The is the description of the second child ticket. This section
							will be shown only if there is atleast one ticket.
						</p>
					</div>
				</div>

				<div class="box">
					<div class="comments">
						<h4>Comments</h4>
						
						<ul>
							<c:forEach var="comment" items="${actionBean.ticket.comments}" varStatus="loopCount">
							<li>
								<span class="tal"><a href="#">Comment#${loopCount.count}</a></span> <span class="tar right">${comment.timeStamp}</span>
								<p><span class="bold">${comment.userName} :</span> ${comment.comment}</p>
							</li>
							</c:forEach>
						</ul>
						
						<div class="il">
							<dl>
								<dt>New comment</dt>
								<dd>
									<s:textarea name="ticket.newComments" rows="4" cols="70" placeholder="Comment"/>
									<br>
									<span> <input type="button" value="Post comment(Not yet working.use submit)" class="orange"> </span>
								</dd>
							</dl>
						</div>
					</div>

				</div>
			</div>
			<div class="column grid-6">
				<div class="box">
					<h4>Properties</h4>
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
          			<dt> Reporter </dt>
          			<dd>
          			<s:text name="ticket.reporter" placeholder="Reported by"/></dd>
          			<dt> Owner </dt>
          			<dd> <s:text name="ticket.owner" placeholder="Assigned by"/> </dd>
          			<dt> Related tickets </dt>
          			<dd><s:text name="ticket.related" placeholder="#related, comma separated"/> </dd>
          			<dt> Progress </dt>
          			<dd><s:text name="ticket.progress" placeholder="0 - 100"/> </dd> 
          			<c:if test="${actionBean.ticket.type eq 'requirement'}">       			
          			<dt> Story Point </dt>
          			<dd><s:text name="ticket.storyPoint" placeholder="Story Point"/> </dd> 
          			</c:if>    
				</dl>


					</div>
				</div>
				<div class="box">
					<h4>Commits (Not Yet Implemented!)</h4>

					<a href="#">Change set#223</a>
					<p>Commit comments will apear here. Commnets will be listed in
						the chronological order</p>

					<a href="#">Change set#223</a>
					<p>Commit comments will apear here. Commnets will be listed in
						the chronological order</p>


				</div>
			</div>

  <!--  deiva - for testing purposes -->    
  <s:submit name="submit" value="Submit"/>

		</div>
	</div>
	
	</s:form>
</s:layout-component>
</s:layout-render>
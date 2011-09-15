<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="body">  
	<div id="bodycontent">
		<div class="row">
			<div class="column grid-12">
				<div class="box tac">		
						<h4>Tracer RSS Feeds</h4>
						<ul>
							<li>
								<a class="rssFeed" href="${contextPath}/rss/ticket?owner=${actionBean.context.loggedUser}"> My Tickets</a> 
							</li>
							<li>
								<a class="rssFeed" href="${contextPath}/rss/ticket?currentmilestone"> Current Milestone Tickets </a> 
							</li>
							<li>
								<a class="rssFeed" href="${contextPath}/rss/activitystream?date=today"> Activity Stream (Today) </a> 
							</li>
							<li>
								<a class="rssFeed" href="${contextPath}/rss/activitystream?date=yesterday"> Activity Stream (Yesterday) </a> 
							</li>
							<li>
								<a class="rssFeed" href="${contextPath}/rss/activitystream?date=daybeforeyesterday"> Activity Stream (Day Before Yesterday) </a> 
							</li>
							<li>
								RSS Feed for tickets with filtering. - Select the filter options in the ticket listing page and click on icon <a class="rssFeed"> &nbsp; </a> to get the feed.
							</li>
						</ul>		 		
				</div>
			</div>
			
			<div class="column grid-4">
				<div class="box">
					<h4>Feed Readers</h4>
						
				</div>				
			</div>				
		</div>
	</div>
</s:layout-component>
<s:layout-component name="inlineScripts">
	
$(document).ready(function(){
		
});


	
	
	</s:layout-component>	
</s:layout-render>
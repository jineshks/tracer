<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body">  
	<c:set var="userRole" value="${actionBean.context.userRole}"></c:set>	
  	<div id="bodycontent">
		<div class="row">
			<div class="column grid-12">
				<div class="box tac">
				
					<ul id="tab">
						<li class="${actionBean.type eq 'my' ? 'selected' : ''}"><a href="${contextPath}/list/link/my">My Links</a></li>
						<li class="${actionBean.type eq 'team' ? 'selected' : ''}"><a href="${contextPath}/list/link/team">Public Links</a></li>
						<li class="shadow"></li>
					</ul>
				
				
					<ul id="forTest" class="droptrue">
					<c:forEach var="link" items="${actionBean.items}" varStatus="loopCount">
						<li class="p white" id="${link.id}N${link.position}">
							<p class="nm">							
								<span class="tar left descContainer"><a id="linkNote" class="hideDesc"><span class="hide"></span></a></span>					
								<a href="${link.target}" target="new">${link.name}</a>						
							
							<c:if test="${actionBean.context.userRole ne '1' && actionBean.context.loggedUser eq link.userName}">
								<span class="pl bl right">
									<s:link href="${contextPath}/link/${link.id}">	Edit </s:link>				
									| 
									<s:link beanclass="in.espirit.tracer.action.LinkActionBean" event="delete"
										onclick="return confirm('Delete the Link : ${link.name}?')">
											<s:param name="id" value="${link.id}"></s:param>
											Delete
									</s:link>
								</span>
							</c:if>
							</p>
							<p id="linkDesc" class="hide">
								<span class="tal" >${link.desc}</span>
							</p>													
						</li>
					</c:forEach>	
					</ul>						
				</div>
			</div>					
			<div class="column grid-4">
				<div class="box">
					<h4>Tag Search</h4>
					<s:form beanclass="in.espirit.tracer.action.LinkListActionBean">
						<div class="il">
							<dl>
			          			<dt> Tag</dt>
			          			<dd> 
			          				<s:text id="tag" name="tag" placeholder="Tag Name"/>
			        			</dd>			        			
							</dl>
							<s:submit name="search" value="Search"></s:submit>	
						</div>
					</s:form>
				</div>		
			</div>		
		</div>
	</div>
</s:layout-component>
<s:layout-component name="inlineScripts">
  $(document).ready(function() {	
  	showInfo();
  	var before;
  	var loadUrl = "/tracer/list/link?persist";
    
   	$('a#linkNote').click(function() {
  				$(this).parent().parent().parent().find('p#linkDesc').slideToggle('fast');  	
  				$(this).toggleClass('showDesc hideDesc');				 						
  	});
  	
  	$( "ul.droptrue" ).sortable({
			connectWith: ".droptrue",
			update:function() {
			var after = $(this).sortable('toArray');
			var change = new Array();
				for(i=0;i < after.length;i++) {
					 if(before[i] != after[i]) {
					 	temp1 = before[i].split("N");
					 	temp2 = after[i].split("N");
					 	change[change.length] = temp2[0]+"N"+temp1[1];
					 	$(this).find("#"+after[i]).attr('id',temp2[0]+"N"+temp1[1]);					 				 						 	
					}			
				}	
				before = $(this).sortable('toArray');   // we need to set the before to changes done.. This is done so that for the next drop in the same page before has the updated values.
							
				$.get(  
		            loadUrl,  
		            {	changes: change.toString()		            	
		            },  
		            function(responseText){ 
		                // Do nothing for success, inform for failure
		                if (responseText=='error') {
		         			showMessage('Persisting the changes to database failed. Please refresh the page and try again!')
		         		}              
		            },  
		            "html"
	        	);								
			}
	});
  	
  	before = $("ul.droptrue").sortable('toArray');
  	
  });
</s:layout-component>  
</s:layout-render>
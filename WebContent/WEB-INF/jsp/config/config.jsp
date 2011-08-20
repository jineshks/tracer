<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-definition>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="body"> 
 <div id="bodycontent">
    <div id="main-section">
    	
      <div class="row">
			 <div class="column  grid-4">
			 		<div class="leftpane box">
			 			
			 			<div class="inbox-item ps">
			 				<p class="p nm"> Milestones </p>
			 			</div>
			 			<div class="inbox-item ps">
			 				<p class="p nm"> Users </p>
			 			</div>
			 			<div class="inbox-item ps">
			 				<p class="p nm"> Labels </p>
			 			</div>
			 		
			 		</div>
			 </div>
			 <div class="column  grid-12" >
			 		${body}
			 </div>
			 
		</div>	
			 	
    </div>
  </div>
  </s:layout-component>
  <s:layout-component name="inlineScripts">
	${inlineScripts}
  </s:layout-component>  
</s:layout-render> 
</s:layout-definition>

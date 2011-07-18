<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="body">  
  	<div id="bodycontent">
		<div class="row">
			<div class="column grid-12">
				<div class="box tac">				
					<d:table name="${actionBean.items}" id="activity">
					<d:column title="Activity" property="activity"></d:column>
					<d:column title="Date Time" property="timeStamp"></d:column>
					</d:table>					
				</div>
			</div>					
		</div>
	</div>
</s:layout-component>
</s:layout-render>
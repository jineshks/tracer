<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/config/config.jsp"	section="labels">
	<s:layout-component name="infoPanel">		
	</s:layout-component>
	<s:layout-component name="body">
		<div class="column  grid-7">
			<div class="box">
				<div class="il">
				<dl>
				<c:forEach var="arrList" items="${actionBean.labelItems}">
					<dt>
						${arrList.key} 
					</dt>
					<dd>
						<input type="text" name="${arrList.key}" value="${arrList.value}">
					</dd>	
				</c:forEach>
				</dl>				
				<s:form beanclass="in.espirit.tracer.action.ConfigLabelActionBean">
					<s:textarea id="keyvalue" name="keyvalue" class="hide"></s:textarea>
					<s:submit name="update" value="Update"></s:submit>				
				</s:form>
				</div>
			</div>
		</div>
	</s:layout-component>
	<s:layout-component name="inlineScripts">
  $(document).ready(function() { 	
  
  $("form").bind("submit", function(event) {
  	
  //$(":button").click(function() {
  		
  		var text="";
  		var t1 = $(":text");
  	
  		$.each(t1, function() {
  			text += $(this).attr('name') + ":" + $(this).val() + ",";
  		});	
  	
  		$("#keyvalue").val(text);  		 
  	});  	
  });
</s:layout-component>
</s:layout-render>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
		<div id="bodycontent">
			<jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper"
				id="configView"></jsp:useBean>
			<div class="box il">
				<s:form beanclass="in.espirit.tracer.action.ConfigActionBean">
				<s:hidden name="config.id"></s:hidden>
				<s:hidden name="config.key"></s:hidden>
				<dl>
					<dt> </dt>
					<dd><s:text placeholder="Value" name="config.value"></s:text> <s:errors field="config.value"></s:errors> </dd>
					<dt> </dt>
					<dd>
						<s:submit name="submit" value="Submit" /> 
					</dd>
				</dl>
				</s:form>
			</div>
		</div>
	</s:layout-component>
</s:layout-render>
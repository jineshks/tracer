<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/jsp/common/layout_loggeduser.jsp">                                           
<s:layout-component name="body">
<jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>
<s:form beanclass="in.espirit.tracer.action.ConfigActionBean">
<table class="form">
<tr>
<td><s:hidden name="config.id" ></s:hidden></td>

<s:hidden name="config.key"></s:hidden>
<!-- 
<td>
<s:label for="config.key"></s:label></td>
<td><s:select name="config.key">
<s:option value=""></s:option>
<s:option value="milestone">milestone</s:option>
<s:option value="priority">priority</s:option>
<s:option value="status">status</s:option>
</s:select></td>
<td><s:errors field="config.key"></s:errors></td>
</tr>
 -->

<tr>
<td></td>
<td>
<s:label for="config.value"></s:label></td>
<td><s:text name="config.value"></s:text>
</td>
<td>
<s:errors field="config.value"></s:errors></td>
</tr>
<tr>
<td colspan="4">
	<s:submit name="submit" value="Submit"/>
	<s:submit name="cancel" value="Cancel"/>
</td>
</tr>
</table>
</s:form>
</s:layout-component>
</s:layout-render>
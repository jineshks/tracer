<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/jsp/common/layout_loggeduser.jsp">     
<%-- where is the title going to go...check this and make changes..removed the title form slayout renderer now--%>                                      
<s:layout-component name="body">

<jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>
<s:form beanclass="in.espirit.tracer.action.ListActionBean">
<table class="form">
<tr>
<td class="label">
<s:hidden name="list"></s:hidden>
<s:hidden name="type"></s:hidden>
<s:label for="list.priority"></s:label></td>
<td><s:select name="priority">
<s:option value=""></s:option>
<s:options-collection collection="${configView.priority}"/>
</s:select>
</td>

<td class="label">
<s:label for="list.status"></s:label></td>
<td><s:select name="status">
<s:option value=""></s:option>
<s:options-collection collection="${configView.status}"/>
</s:select>
</td>

<c:if test="${actionBean.list eq 'All'}"> 
<td class="label"><s:label for="list.owner"></s:label></td>
<td> 
<s:select name="owner">
<s:option value=""/>
<s:options-collection collection="${configView.userNames}"/>
</s:select>
</td>
</c:if>

<td>
<s:label for="list.milestone"></s:label></td>
<td><s:select name="milestone">
<s:option value=""></s:option>
<s:options-collection collection="${configView.milestone}"/>
</s:select>
</td>
  
<td>
<s:submit name="filter" value="Filter"></s:submit>
</tr>
</table>
</s:form>

<s:messages/>
<d:table name="${actionBean.items}" id="ticket">
<d:column title="Id" property="id"></d:column>
<d:column title="Short Description">
<s:link href="/${ticket.type}/${ticket.id}"> ${ticket.shortDesc}
</s:link></d:column>
<d:column title="Priority" property="priority"></d:column>
<d:column title="Status" property="status"></d:column>
<d:column title="Reporter" property="reporter"></d:column>
<d:column title="Owner" property="owner"></d:column>
<d:column title="Component" property="component"></d:column>
<d:column title="MileStone" property="milestone"></d:column>
</d:table>

</s:layout-component>
</s:layout-render>
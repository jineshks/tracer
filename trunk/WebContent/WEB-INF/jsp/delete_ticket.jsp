<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type='text/javascript'>
function openWindow(url){
winAtts='width=800,height=400,toolbar=no,status=yes,resizable=yes,scrollbars=yes,screenX=150,screenY=50,left=150,top=50';
winInfoWindow = window.open(url,'Related', winAtts);
winInfoWindow.focus();
} 
</script>
<s:layout-render name="/WEB-INF/jsp/common/layout_loggeduser.jsp">                                           
<s:layout-component name="body">
<jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>
<s:messages/>
<s:errors globalErrorsOnly="true"></s:errors>
<c:if test="${actionBean.ticket.type eq 'defect'}">
<c:set var="beanclass" value="in.espirit.tracer.action.DefectActionBean"></c:set>
</c:if>
<c:if test="${actionBean.ticket.type eq 'task'}">
<c:set var="beanclass" value="in.espirit.tracer.action.TaskActionBean"></c:set>
</c:if>
<c:if test="${actionBean.ticket.type eq 'requirement'}">
<c:set var="beanclass" value="in.espirit.tracer.action.RequirementActionBean"></c:set>
</c:if>
<s:form beanclass="${beanclass}">
<s:hidden name="ticket.id" ></s:hidden>
<s:hidden name="ticket.type"/>
<table class="form">
<tr>
<td>
<s:label for="${actionBean.ticket.type}.id"></s:label>
</td>
<td> ${actionBean.ticket.id}
</td>
<td>
<s:label for="ticket.shortDesc"></s:label></td>
<td><s:text name="ticket.shortDesc" size="60"></s:text>
</td>
</tr>
<tr>
<td>
<s:label for="ticket.related"></s:label></td>
<td>
<input id="relatedDisp" type="text" SIZE="10" VALUE="${actionBean.ticket.related}" disabled="disabled">
<s:hidden name="ticket.related" id="related"></s:hidden>
<!--  the related ticket field can be edited..to make it not editable dlot of work?? the key disabled doesn't work as while saving no items are returned -->
<s:url var="relatedUrl" beanclass="in.espirit.tracer.action.RelatedListActionBean">
<s:param name="type">${actionBean.ticket.type}</s:param>
<s:param name="id">${actionBean.ticket.id}</s:param>
</s:url>
<input type="button" value="Select" onClick="javascript:openWindow('${relatedUrl}')">
</td>
</tr>
<tr>
<td>
<s:label for="ticket.importance"></s:label></td>
<td><s:select name="ticket.importance">
<s:option value=""></s:option>
<s:options-collection collection="${configView.importance}"/>
</s:select>
</td>
<td>
<s:label for="ticket.progress"></s:label></td>
<td><s:text name="ticket.progress"></s:text>
</td>
</tr>
<tr>
<td>
<s:label for="ticket.priority"></s:label></td>
<td><s:select name="ticket.priority">
<s:option value=""></s:option>
<s:options-collection collection="${configView.priority}"/>
</s:select>
</td>
<td>
<s:label for="ticket.status"></s:label></td>
<td><s:select name="ticket.status">
<s:option value=""></s:option>
<s:options-collection collection="${configView.status}"/>
</s:select>
</td>
</tr>
<tr>
<td>
<s:label for="ticket.reporter"></s:label></td>
<td>
<s:text name="ticket.reporter" value="${actionBean.context.loggedUser }" ></s:text>

</td>
<td>
<s:label for="ticket.owner"></s:label></td>
<td>
<s:select name="ticket.owner">
<s:option value="" selected="true"></s:option>
<s:options-collection collection="${configView.userNames}"/>
</s:select>

<select>
  <option name="adsf" value="Adsf">
  <option selected name="adf" value="asdfe">
</select>

</td>
</tr>
<tr>
<td>
<s:label for="ticket.component"></s:label></td>
<td><s:text name="ticket.component"></s:text>
</td>
<td>
<s:label for="ticket.milestone"></s:label></td>
<td><s:select name="ticket.milestone">
<s:option value=""></s:option>
<s:options-collection collection="${configView.milestone}"/>
</s:select>
</td>
</tr>


<c:if test="${actionBean.ticket.type eq 'requirement'}">
<tr>
<td colspan=4>
<s:label for="requirement.storyPoint"></s:label></td>
</tr>
<tr>
<td colspan=4>
<s:textarea name="ticket.storyPoint" cols="120" rows="4"></s:textarea>
</td>
</tr>
</c:if>


<tr>
<td colspan=4>
<s:label for="ticket.desc"></s:label></td>
</tr>
<tr>
<td colspan=4>
<s:textarea name="ticket.desc" cols="120" rows="8"></s:textarea>
</td>
</tr>

<tr>
<td colspan=4>
<s:label for="ticket.comments"></s:label></td>
</tr>
<tr>
<td colspan=4>
<s:textarea name="ticket.newComments" cols="120" rows="2"></s:textarea>
</td>
</tr>
<tr>
<td colspan=4>
<s:label for="ticket.commentsHistory"/></td>
</tr>
<tr>
<td colspan=4> ${actionBean.ticket.comments}
</td>
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
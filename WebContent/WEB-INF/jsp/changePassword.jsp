<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/jsp/common/layout_loggeduser.jsp">
<s:layout-component name="body">
<span class="pageInfo">Enter details and click on 'Submit'</span><br><br>
<s:form beanclass="in.espirit.tracer.action.ChangePasswordActionBean">
<s:messages/>
<table class="form">
<tr>
<td>
<s:label for="user.userName"></s:label></td>
<td>${actionBean.context.loggedUser }</td>
</tr>

<tr>
<td>
<s:label for="user.currentPassword"></s:label></td>
<td><s:password name="currentPassword"></s:password>  <s:errors field="currentPassword"></s:errors>
</td>
</tr>

<tr>
<td>
<s:label for="user.newPassword"></s:label></td>
<td><s:password name="newPassword"></s:password>  <s:errors field="newPassword"></s:errors>
</td>
</tr>

<tr>
<td>
<s:label for="user.confirmNewPassword"></s:label></td>
<td><s:password name="confirmNewPassword"></s:password> <s:errors field="confirmNewPassword"></s:errors>
</td> 
</tr>


<!--  
<tr>
<td>
<s:label for="user.email"></s:label></td>
<td><s:text name="user.email"></s:text>  <s:errors field="user.email"></s:errors>
</td>
</tr>
 -->


</table>
<br>
<s:submit name="submit" value="Submit"></s:submit>&nbsp;&nbsp;&nbsp;
<s:button name="cancel" value="Cancel" onclick="javascript:history.go(-1);"></s:button>
</s:form>
</s:layout-component>
</s:layout-render>
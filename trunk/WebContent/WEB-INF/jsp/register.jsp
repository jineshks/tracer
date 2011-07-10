<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp" title="New User Registration">
<s:layout-component name="body">
<span class="pageInfo">Enter your details and click on 'Register'</span><br><br>
<s:form beanclass="in.espirit.tracer.action.RegisterActionBean">
<!--<s:messages/>-->
<table class="form">
<tr>
<td>
<s:label for="user.userName"></s:label></td>
<td><s:text name="user.userName"></s:text> <s:errors field="user.userName"></s:errors>
</td>
</tr>
<tr>
<td>
<s:label for="user.password"></s:label></td>
<td><s:password name="user.password"></s:password>  <s:errors field="user.password"></s:errors>
</td>
</tr>
<tr>
<td>
<s:label for="confirmPassword"></s:label></td>
<td><s:password name="confirmPassword"></s:password> <s:errors field="confirmPassword"></s:errors>
</td> 
</tr>
<tr>
<td>
<s:label for="user.email"></s:label></td>
<td><s:text name="user.email"></s:text>  <s:errors field="user.email"></s:errors>
</td>
</tr>
</table>
<br>
<s:submit name="register" value="Register"></s:submit>&nbsp;&nbsp;&nbsp;
<s:submit name="cancel" value="Cancel"></s:submit>



</s:form>
</s:layout-component>
</s:layout-render>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/jsp/common/layout_loggeduser.jsp"> 
<s:layout-component name="body">


<c:forEach var="ListArr" items="${actionBean.listItems}" >
<br><br>
<div class="container">
<span class="milestone">${ListArr.key}</span>
<br><br>
<d:table name="${ListArr.value}" id="list">
<d:column title="Id" property="id"></d:column>
<d:column title="Short Description">
<s:link href="/${list.type}/${list.id}"> ${list.shortDesc}
</s:link>
</d:column>
<d:column title="Priority" property="priority"></d:column>
<d:column title="Status" property="status"></d:column>
<d:column title="Reporter" property="reporter"></d:column>
<d:column title="Owner" property="owner"></d:column>
<d:column title="Component" property="component"></d:column>
<d:column title="MileStone" property="milestone"></d:column>
<d:column title="Type" property="type"></d:column>
</d:table>
</div>

</c:forEach>

</s:layout-component>
</s:layout-render>

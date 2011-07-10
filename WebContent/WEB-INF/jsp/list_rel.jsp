<html>
<head>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css" >
<title>Related Defects </title>

<script type='text/javascript'>
function selectRelated(){
	if (window.opener) {
		var field = document.forms[0].selectedRelated;
		var allChecked = new Array();
		for(i = 0; i < field.length; i++) {
			if(field[i].checked) {
				allChecked[allChecked.length]=field[i].value;
			}			
		}
		window.opener.document.getElementById('related').value=allChecked.join(",");
		window.opener.document.getElementById('relatedDisp').value=allChecked.join(",");
		window.opener.focus();
		self.close();	
	}	
}

function populateRelated() {
	var field = document.forms[0].selectedRelated;
	var values = window.opener.document.getElementById('related').value.split(",");
	for(i = 0; i < field.length; i++) {
		for(j=0; j < values.length;j++) {
			if(field[i].value==values[j]) {
				field[i].checked = 'true';				
			}	
		}
	}	
}

</script>

</head>
<body onLoad="javascript:populateRelated()">
<form>
<d:table name="${actionBean.relatedList}" id="relList">
<d:column>
<input type="checkbox" name="selectedRelated" value="${relList.id}">
</d:column>
<d:column title="Id" property="id"></d:column>
<d:column title="Short Description" property="shortDesc"></d:column>
<d:column title="Priority" property="priority"></d:column>
<d:column title="Status" property="status"></d:column>
<d:column title="Reporter" property="reporter"></d:column>
<d:column title="Owner" property="owner"></d:column>
<d:column title="Component" property="component"></d:column>
<d:column title="MileStone" property="milestone"></d:column>
</d:table>
<br>
<center>
<input type="button" name="select" value="Select" onClick="javascript:selectRelated()">
</center>
</form>
</body>
</html>





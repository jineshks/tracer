<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body">  
	<c:set var="userRole" value="${actionBean.context.userRole}"></c:set>	
	<jsp:useBean class="in.espirit.tracer.view.ConfigViewHelper" id="configView"></jsp:useBean>  
  	<div id="bodycontent">
		<div class="row">
			<div class="column grid-12">
				<div class="box tac">
				
					<ul id="tab">
						<li class="${actionBean.type eq 'burndown' ? 'selected' : ''}"><a href="burndown">Burn down</a></li>
						<li class="${actionBean.type eq 'velocity' ? 'selected' : ''}"><a href="velocity">Velocity</a></li>
						<li class="${actionBean.type eq 'efficiency' ? 'selected' : ''}"><a href="efficiency">Efficiency</a></li>
						<li class="shadow"></li>
					</ul>
					
					<!--Div that will hold the pie chart-->
				    <div id="chart_div"></div>
											
				</div>
			</div>	
			<div class="column grid-4">
				<div class="box">
					<h4>Filter</h4>
					<s:form beanclass="in.espirit.tracer.action.ReportsActionBean" partial="true">
						<div class="il">
							<dl>
			          			<dt>Sprint </dt>
			          			<dd> 
			          				<s:select name="milestone" id="milestone">
									<s:option value=""></s:option>
									<s:options-collection collection="${configView.milestone}"/>
									</s:select>
			        			</dd>
							</dl>
							<s:button name="open" id="filterButton" value="submit" class="orange"></s:button>								
						</div>
					</s:form>
				</div>				
			</div>	
							
		</div>
	</div>
	
</s:layout-component>
<s:layout-component name="externalScripts">
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
</s:layout-component>
		
<s:layout-component name="inlineScripts">
	 <!--Load the AJAX API-->
    // Load the Visualization API and the lineechart package.
    google.load('visualization', '1', {'packages':['corechart']});
    // Set a callback to run when the Google Visualization API is loaded.
    //google.setOnLoadCallback(drawChart);
    google.setOnLoadCallback(function(){ drawChart('') })
    
    function drawChart(milestone) {
    	var loadURL = "/tracer/reports/burndown?getData";
      	if (milestone != null) {
	      	loadURL = "/tracer/reports/burndown/" + milestone + "?getData";
      	}
      	var jsonData = $.ajax({
        	url: loadURL,
          	dataType:"json",
          	async: false
          	}).responseText;
          
      	// Create our data table out of JSON data loaded from server.
      	var data = new google.visualization.DataTable(jsonData);

      	// Instantiate and draw our chart, passing in some options.
      	var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
      	chart.draw(data, {width: 800, height: 350});      
    }
  	  
	$(document).ready(function(){
		$("#filterButton").click(function() {
			if ($("#milestone").val()=='') {
				showMessage('Select a value in the drop down and click on submit')
				return false;
			}
			drawChart($("#milestone").val());
		});
  	});
  	
</s:layout-component>  
</s:layout-render>
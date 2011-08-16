<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="infoPanel"> 
	<s:messages/>
</s:layout-component>
<s:layout-component name="body">  
	<c:set var="userRole" value="${actionBean.context.userRole}"></c:set>	
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
					<p> Efficiency = Velocity / Work hours </p>
				    <div id="chart_div"></div>
											
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
    // Load the Visualization API and the barchart package.
    google.load('visualization', '1', {'packages':['corechart']});
    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawChart);
    function drawChart() {
      var jsonData = $.ajax({
          url: "/tracer/reports/efficiency?getData",
          dataType:"json",
          async: false
          }).responseText;
          
      // Create our data table out of JSON data loaded from server.
      var data = new google.visualization.DataTable(jsonData);

      // Instantiate and draw our chart, passing in some options.
      var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
      chart.draw(data, {width: 800, height: 350});
    }
  	
	
  	
  	
</s:layout-component>  
</s:layout-render>
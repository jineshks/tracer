<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
<s:layout-component name="body"> 
 <div id="bodycontent">
    <div id="main-section">
    	
      <div class="row">
			 <div class="column  grid-5">
			 		<div class="leftpane box">
			 			<div class="message-panel ps">
			 				<span class=""> <input type="button" class="blue ps" value="Compose" /> </span>
			 				<span class=""> <input type="button" class="orange ps" value="Delete" /> </span>
			 			</div>
			 			
			 			<div class="inbox-item ps">
			 				<p><span> <input class="nm" type="checkbox"/> </span> Mail subject here </p>
			 				<span class="fade">Joe</span> <span class="right fade">2011-Jul-23</span>
			 			</div>
			 			<div class="inbox-item ps">
			 				<p><span> <input class="nm" type="checkbox"/> </span> Mail subject here </p>
			 				<span class="fade">Joe</span> <span class="right fade">2011-Jul-23</span>
			 			</div>
			 			<div class="inbox-item ps">
			 				<p><span> <input class="nm" type="checkbox"/> </span>  Mail subject here </p>
			 				<span class="fade">Joe</span> <span class="right fade">2011-Jul-23</span>
			 			</div>
			 			<div class="inbox-item ps">
			 				<p><span> <input class="nm" type="checkbox"/> </span> Mail subject here </p>
			 				<span class="fade">Joe</span> <span class="right fade">2011-Jul-23</span>
			 			</div>
			 			<div class="pt">
			 				<span > Showing 1 - 50 </span>
			 				<span class="right"> <a href="">Previous</a> | <a href="">Next</a> </span>
			 			</div>
			 		
			 		</div>
			 </div>
			 <div class="column  grid-7" >
			 		<div class="box">
			 			<div class="message-panel ps">
			 				<span class=""> <input type="button" class="blue ps" value="Reply" /> </span>
			 				<span class=""> <input type="button" class="blue ps" value="Reply All" /> </span>
			 				<span class=""> <input type="button" class="blue ps" value="Forward" /> </span>
			 			</div>
			 			<div class="message-header ps">
			 				<h6> Selected Mail subject here </h6> <span class="right"> 2011-Jul-23 12:35 PM</span>
			 				<p class="pt nm">
			 					<span class="fade">To: Joe</span> <span class="right fade"> From: Jack</span>
			 				</p>
			 			</div>
			 			<div class="message-body">
			 				<p>
									The interesting thing, aside from the tablet naming number convention between webOS 3.0 and Android 3.0, is the notifications and settings pop-up. The influence of designer Matias Duarte, previously with Palm and now with Google, could be seen in both operating systems in the screen shots below.
							</p>
							<p>		
									The main difference is that on Android 3.0, known also informally as Honeycomb, the popup to the settings is accessible in the lower right hand space while on webOS 3.0 on the TouchPad, the settings panel shows up on the upper right hand corner.
			 				</p>
			 			</div>
			 		
			 		</div>
			 </div>
			 <div class="column  grid-4">
			 		<div class="rightpane">
						<div class="history box">
							<h4>Filter</h4>		
							<s:form beanclass="in.espirit.tracer.action.MessagingActionBean">
								<div class="il">
									<dl>
										<dt> Tag </dt>
					          			<dd>
					          				<s:text name="userName" placeholder="Tag"/>
					        			</dd>
					        			<dt> User Name </dt>
					          			<dd>
					          				<s:text name="userName" placeholder="User Name"/>
					        			</dd>
					          			<dt> From Date </dt>
					          			<dd> 
					          				<s:text id="fromDate" name="fromDate" placeholder="From Date"/>
					        			</dd>
					        			<dt> To Date </dt>
					          			<dd> 
					          				<s:text id="toDate" name="toDate" placeholder="To Date"/>
					        			</dd>
									</dl>
									<s:submit name="filter" value="Filter"></s:submit>	
								</div>
							</s:form>				
						</div>
								 		
			 		</div>
			 </div>
		</div>	
			 	
    </div>
  </div>
  </s:layout-component>
  <s:layout-component name="inlineScripts">

  </s:layout-component>  
</s:layout-render> 

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
		<div id="bodycontent">
			<div class="row">
				<div class="column grid-16">
					<div class="box tac">
						<div class="">
							<s:form beanclass="in.espirit.tracer.action.MailActionBean">
								<ul>
									<li> <s:text name="from" placeholder="Sender"></s:text> </li>
									<li> <s:text name="to" placeholder="Receiver"></s:text> </li>
									<li> <s:text name="subject" placeholder="Subject"></s:text> </li>
									<li> <s:textarea  name="message" placeholder="Message"></s:textarea> </li>
									<li> <s:submit name="notifyUser" value="Notify" />	</li>
								</ul>
							</s:form>
						</div>
					</div>
				</div>
			</div>
		</div>

	</s:layout-component>
</s:layout-render>

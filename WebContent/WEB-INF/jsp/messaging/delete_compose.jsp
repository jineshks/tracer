<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
		<div id="bodycontent">
			<div class="row">
				<div class="column grid-16">
					<div class="box tac">
						<div class="messaging">
							<s:form beanclass="in.espirit.tracer.action.MessagingActionBean">
								<ul>
									<li> <s:text name="message.to" placeholder="To"></s:text> </li>
									<li> <s:text name="message.cc" placeholder="CC"></s:text> </li>
									<li> <s:text name="message.subject" placeholder="Subject"></s:text> </li>
									<li> <span class="p">  <s:checkbox style="display:inline"  name="message.important" value="1"></s:checkbox>Important </span> <span class="p"><s:checkbox style="display:inline" name="message.notify" value="1">Notify</s:checkbox>  Copy on email</span></li>
									<li> <s:textarea  name="message.message" placeholder="Message" rows="20"></s:textarea> </li>
									<li> <s:submit name="sendMessage" value="Send" />	</li>
								</ul>
							</s:form>
						</div>
					</div>
				</div>
			</div>
		</div>

	</s:layout-component>
</s:layout-render>

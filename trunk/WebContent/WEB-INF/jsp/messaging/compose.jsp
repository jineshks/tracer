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
									<li> <s:text name="to" placeholder="To"></s:text> </li>
									<li> <s:text name="cc" placeholder="CC"></s:text> </li>
									<li> <s:text name="subject" placeholder="Subject"></s:text> </li>
									<li> <span class="p">  <s:checkbox style="display:inline"  name="important" value="Important"></s:checkbox>Important </span> <span class="p"><s:checkbox style="display:inline" name="notify">Notify</s:checkbox>  Copy on email</span></li>
									<li> <s:textarea  name="message" placeholder="Message" rows="20"></s:textarea> </li>
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

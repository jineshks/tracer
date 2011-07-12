<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/common/layout.jsp">
	<s:layout-component name="body">
		<div id="bodycontent">
			<div class="row">

				<div class="column grid-7">
					<div class="box">
						<h4>Step 1: Description</h4>
						<div class="il">
							<dl>
								<dt>Title</dt>
								<dd>
									<input type="text"
										placeholder="Short description of the ticket"
										style="width: 370px">
								</dd>
								<dt>Description</dt>
								<dd>
									<textarea rows="10" cols="50"
										placeholder="Description of the ticket"></textarea>
								</dd>
								<dt>Comment</dt>
								<dd>
									<textarea rows="5" cols="50" placeholder="Comment"></textarea>
								</dd>
							</dl>

						</div>
					</div>
				</div>
				
				<div class="column grid-5">
					<div class="box">
						<h4>Step 2: Properties</h4>
						<div class="il">
							<dl>
								<dt>Importance</dt>
								<dd>
									<select></select>
								</dd>
								<dt>Priority</dt>
								<dd>
									<select></select>
								</dd>
								<dt>Milestone</dt>
								<dd>
									<select></select>
								</dd>
								<dt>Reporter</dt>
								<dd>
									<input type="text" placeholder="Reported by">
								</dd>
								<dt>Owner</dt>
								<dd>
									<input type="text" placeholder="Assigned to">
								</dd>
								<dt>Related tickets</dt>
								<dd>
									<input type="text" placeholder="#related, comma separated">
								</dd>
								<dt>Progress</dt>
								<dd>
									<input type="text" placeholder="0 - 100">
								</dd>
							</dl>

						</div>
					</div>
				</div>
				
				<div class="column grid-4">
					<div class="box">
						<h4>Step 3: People</h4>
						<div>
							<div class="item white" draggable="true">
								<p class="p">Billy</p>
							</div>
							<div class="item white" draggable="true">
								<p class="p">Joe</p>
							</div>
							<div class="item white" draggable="true">
								<p class="p">Jack</p>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>

	</s:layout-component>
</s:layout-render>
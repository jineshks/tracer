package in.espirit.tracer.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/messaging/{event}")
public class MessagingActionBean extends BaseActionBean {
	private static final String INBOX = "/WEB-INF/jsp/messaging/inbox.jsp";
	private static final String COMPOSE = "/WEB-INF/jsp/messaging/compose.jsp";
	private String event;

	@DefaultHandler
	public Resolution view() {
		if(event.equalsIgnoreCase("inbox")){
			return new ForwardResolution(INBOX);
		}else{
			return new ForwardResolution(COMPOSE);
		}
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}

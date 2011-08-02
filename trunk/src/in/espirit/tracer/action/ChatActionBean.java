package in.espirit.tracer.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.log4j.Logger;

/**
 * @author Bineesh
 *
 */
@UrlBinding("/chat")
public class ChatActionBean extends BaseActionBean {
	private static Logger logger = Logger.getLogger(ChatActionBean.class.getName());
	private static final String URL = "/WEB-INF/jsp/chat.jsp";

	@DefaultHandler
	public Resolution view() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("in /chat view()...");
		}

		return new ForwardResolution(URL);
	}
}

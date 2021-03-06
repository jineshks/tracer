package in.espirit.tracer.action;


import in.espirit.tracer.database.dao.ChatDAO;
import in.espirit.tracer.model.Message;

import java.util.Date;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.log4j.Logger;


/**
 * @author Bineesh
 *
 */
@UrlBinding("/SendChatMessage")
public class SendChatMessageAction extends BaseActionBean {
	private static Logger logger = Logger.getLogger(SendChatMessageAction.class.getName());
	
	
	@DefaultHandler
	public Resolution sendMessage() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("sendChatMessage()......");
	     }
		//Get current user chat area
		String userChatArea = this.getContext().getRequest().getParameter("userChatArea");
		String chatMessage = this.getContext().getRequest().getParameter("msg");
		String loggedUser = getContext().getLoggedUser();
		if (logger.isDebugEnabled()) {
			logger.debug("current chat window :"+userChatArea+",Message:"+chatMessage+",From:"+loggedUser);
	     }
		if (chatMessage.trim().length() > 0) {
			Message message = new Message();
			message.setSentBy(loggedUser);
			message.setSentDateTime(new Date());
			message.setText(chatMessage);
			ChatDAO dao = ChatDAO.getInstance();
			dao.sentMessage(userChatArea, message);
		}
		 
        return null;
	}
	

}

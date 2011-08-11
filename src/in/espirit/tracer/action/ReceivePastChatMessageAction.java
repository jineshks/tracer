package in.espirit.tracer.action;


import in.espirit.tracer.database.dao.ChatDAO;
import in.espirit.tracer.model.Emoticons;
import in.espirit.tracer.model.Message;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.log4j.Logger;

/**
 * @author Bineesh
 *
 */
@UrlBinding("/pastChatMessages")
public class ReceivePastChatMessageAction extends BaseActionBean {
	private static Logger logger = Logger.getLogger(ReceivePastChatMessageAction.class.getName());
		
	@DefaultHandler
	public Resolution getPastMessage() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("pastChatMessage()......");
	     }
		
		HttpSession session = this.getContext().getRequest().getSession();
		//Get current user chat Area.
		String userChatArea = this.getContext().getRequest().getParameter("userChatArea");
		String loggedUser = getContext().getLoggedUser();
		Date   loggedInDateTime = (Date)session.getAttribute("loggedInDateTime");
			//First message
	      if (loggedInDateTime == null) {
	    	  loggedInDateTime = new Date();
	      }
	      ChatDAO dao = ChatDAO.getInstance();
	      
	      StringBuffer chatMessage = new StringBuffer();
	      Vector<Message>  messages = dao.getMessages(userChatArea,loggedInDateTime);
	      for (Iterator<Message> it = messages.iterator(); it.hasNext();) {
	    	  String chatBubble ="green-bubble";
	    	  Message message = (Message)it.next();
	          String postedBy = message.getSentBy();
	          String messageText = message.getText();
	          //Different color code for me & their chat message bubble
	          if(postedBy.equals(loggedUser)){	        	  
	        	  chatBubble = "blue-bubble";	        	  
	          }
	          chatMessage.append("<div class=\"" + chatBubble + "\">"+messageText+"</div>");
	        }
	      session.setAttribute("lastDateTime", new Date());
	      return new StreamingResolution("text/html",replaceEmoticons(chatMessage.toString()));        
	}
	
	//Replace smiley code to image icons
	private String replaceEmoticons(String msg) {
		String imgTag = "<img src=\"images/smiley/{image}.gif\">";
		String image = "{image}";		
		Emoticons icons = new Emoticons();
		HashMap<String, String> codeMap = icons.getIconsMap();

		for (Object key: codeMap.keySet()) {
			String val = codeMap.get(key);
			if(msg.contains(key.toString()))
			{
				msg = msg.replace(key.toString(), imgTag.replace(image,val));
			}
		}
		return msg;
	}

}

package in.espirit.tracer.action;


import in.espirit.tracer.database.dao.ChatDAO;
import in.espirit.tracer.model.Message;

import java.util.Date;
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
@UrlBinding("/chatMessages")
public class ReceiveChatMessageAction extends BaseActionBean {
	private static Logger logger = Logger.getLogger(ReceiveChatMessageAction.class.getName());
		
	@DefaultHandler
	public Resolution getMessage() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("getMessage()......");
	     }
		
		HttpSession session = this.getContext().getRequest().getSession();
		//Currently selected user chat session.
		String userWindow = this.getContext().getRequest().getParameter("userWindow");
		Date   lastDateTime = (Date)session.getAttribute("lastDateTime");
		  //If it is first message from user
	      if (lastDateTime == null) {
	        lastDateTime = new Date();
	      }
	      ChatDAO dao      = ChatDAO.getInstance();	      
	      String loggedUser = getContext().getLoggedUser();
	      
	      if (logger.isDebugEnabled()) {
				logger.debug("Logged user :"+loggedUser);
		     }
	      Vector<Message>  messages = dao.getMessages(userWindow,lastDateTime);
	      //Creating a unique session for the logged-in user.
	      session.setAttribute(loggedUser+userWindow+"-lastDateTime", new Date());
	      StringBuffer sb = new StringBuffer();	   
	      for (Iterator<Message> it = messages.iterator(); it.hasNext();) {
	          Message message = (Message)it.next();
	          String postedBy = message.getSentBy();
	          String messageText = message.getText();
	          sb.append( "<div class=\"bubble\"> <span class=\"bold\">"  + postedBy + "</span> "+"     :"+"<span class=\"text\">"+messageText +"</span> </div>");
	          lastDateTime = message.getSentDateTime();
	        }
	      
	      session.setAttribute("lastDateTime", lastDateTime);
	      return new StreamingResolution("text/html", sb.toString());
       
	}
	

}

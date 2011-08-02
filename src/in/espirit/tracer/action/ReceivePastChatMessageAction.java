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
@UrlBinding("/pastChatMessages")
public class ReceivePastChatMessageAction extends BaseActionBean {
	private static Logger logger = Logger.getLogger(ReceivePastChatMessageAction.class.getName());
		
	@DefaultHandler
	public Resolution getPastMessage() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("pastChatMessage()......");
	     }
		
		HttpSession session = this.getContext().getRequest().getSession();
		//get current user chat window.
		String userWindow = this.getContext().getRequest().getParameter("userWindow");
		Date   loggedInDateTime = (Date)session.getAttribute("loggedInDateTime");
		
	      if (loggedInDateTime == null) {
	    	  loggedInDateTime = new Date();
	      }
	      ChatDAO dao      = ChatDAO.getInstance();
	      StringBuffer sb = new StringBuffer();
	      Vector<Message>  messages = dao.getMessages(userWindow,loggedInDateTime);
	      for (Iterator it = messages.iterator(); it.hasNext();) {
	    	  Message message = (Message)it.next();
	          String postedBy = message.getSentBy();
	          String messageText = message.getText();
	          sb.append( "<font color=\"#000000\"><b>"  + postedBy + "</b></font> "+"    :"+"<font color=\"#A52A2A\">"+messageText +"</font><br/>");
	                  
	        }
	      session.setAttribute("lastDateTime", new Date());
	      return new StreamingResolution("text/html", sb.toString());
        
	}
	

}

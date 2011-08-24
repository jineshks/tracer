package in.espirit.tracer.action;

import java.util.ArrayList;

import in.espirit.tracer.database.dao.CustomDao;
import in.espirit.tracer.database.dao.MessageDao;
import in.espirit.tracer.model.Messaging;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/messaging")
public class MessagingActionBean extends BaseActionBean {
	private static final String INBOX = "/WEB-INF/jsp/messaging/inbox.jsp";
	private Messaging message;
	
	@DefaultHandler
	public Resolution view() throws Exception {
		return new ForwardResolution(INBOX);	
	}

	public Resolution sendMessage() throws Exception {
		message.setFrom(getContext().getLoggedUser());
		MessageDao.registerEntry(message);		
		return new RedirectResolution("/messaging");		
	}
	
	public Resolution messageDetails() {
		
		String output = "";
		String id = this.getContext().getRequest().getParameter("id");
		String read = this.getContext().getRequest().getParameter("read");
		
		try {
			message = MessageDao.getMessageDetails(Integer.parseInt(id));
			if (read.equalsIgnoreCase("1")) { 
				MessageDao.changeUnread(getContext().getLoggedUser(), id);    // This is for setting the flag of unread from 0 to 1.
			}
		} catch (Exception e) {
			logger.warn("Failed to retireve the message " + id + " error - " + e.getMessage());
			e.printStackTrace();
		}
		
		output = "{";
		output += "\"id\":\"" + message.getId() + "\",";
		output += "\"subj\":\"" + message.getSubject() + "\",";
		output += "\"date\":\"" + message.getSentdate() + "\",";
		output += "\"from\":\"" + message.getFrom() + "\",";
		output += "\"to\":\"" + message.getTo() + "\",";
		output += "\"cc\":\"" + message.getCc() + "\",";
		output += "\"imp\":\"" + message.getImportant() + "\",";
		output += "\"message\":\"" + message.getMessage() + "\"";
		output += "}";
		
		logger.debug("JSON Ouput for message" + output);
		
		return new StreamingResolution("text/json", output);
	}
	
	public Resolution messageList() {
		String output = null;
		String offset = this.getContext().getRequest().getParameter("offset");
		String from = this.getContext().getRequest().getParameter("from");
		String fromDate = this.getContext().getRequest().getParameter("fromdate");
		String toDate = this.getContext().getRequest().getParameter("todate");
		//String count = this.getContext().getRequest().getParameter("count");
		try {
			ArrayList<Messaging> result = MessageDao.getMessages(getContext().getLoggedUser(), offset, CustomDao.getResourceMessage("message.pagecount"), from, fromDate, toDate);
			output = "[";
			
			for(Messaging message : result) { 
				output += "{";
				output += "\"id\":\"" + message.getId() + "\",";
				output += "\"subj\":\"" + message.getSubject() + "\",";
				output += "\"date\":\"" + message.getSentdate() + "\",";
				output += "\"from\":\"" + message.getFrom() + "\",";
				output += "\"unread\":\"" + message.getUnread() + "\",";
				output += "\"imp\":\"" + message.getImportant() + "\"";
				output +="},";
			}
			output = output.substring(0, output.length()-1);
			if (output.length() > 0) {
				output += "]";
			}
		} catch (Exception e) {
			logger.warn("Failed to retireve the message list error - " + e.getMessage());
			e.printStackTrace();
		}	
		
		return new StreamingResolution("text/json", output);
	}
	
	public Resolution deleteMessage() throws Exception {
		String msgids = this.getContext().getRequest().getParameter("msgid");
		String [] msgid = msgids.split(",");
		boolean flag = true;
		for (int i=0;i<msgid.length;i++) {
			flag = flag && MessageDao.deleteMessage(getContext().getLoggedUser(), msgid[i]);			
		}
		if (flag==true) {
			return new StreamingResolution("text/plain", "success" );
		}
		return new StreamingResolution("text/plain", "failure");
	}
	
	public Resolution filterMsgCount() throws Exception {
		String from = this.getContext().getRequest().getParameter("from");
		String fromDate = this.getContext().getRequest().getParameter("fromdate");
		String toDate = this.getContext().getRequest().getParameter("todate");
		String output = MessageDao.getMessagesCount(getContext().getLoggedUser(),from,fromDate,toDate);
		return new StreamingResolution("text/plain", output);
	}
	
	public String getPageCount() {
		return CustomDao.getResourceMessage("message.pagecount");
	}
	
	public String getTotalCount() throws Exception {
		return MessageDao.getMessagesCount(getContext().getLoggedUser(),null,null,null);
	}
	
	public void setMessage(Messaging message) {
		this.message = message;
	}

	public Messaging getMessage() {
		return message;
	}
}

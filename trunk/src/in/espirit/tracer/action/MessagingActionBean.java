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
		String output = "";
		String offset = this.getContext().getRequest().getParameter("offset");
		//String count = this.getContext().getRequest().getParameter("count");
		try {
			ArrayList<Messaging> result = MessageDao.getMessages(getContext().getLoggedUser(), offset, CustomDao.getResourceMessage("message.pagecount"));
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
			output += "]";
		} catch (Exception e) {
			logger.warn("Failed to retireve the message list error - " + e.getMessage());
			e.printStackTrace();
		}	
		return new StreamingResolution("text/json", output);
	}
	
	public String getPageCount() {
		return CustomDao.getResourceMessage("message.pagecount");
	}
	
	public String getTotalCount() throws Exception {
		return MessageDao.getMessagesCount(getContext().getLoggedUser());
	}
	
	public void setMessage(Messaging message) {
		this.message = message;
	}

	public Messaging getMessage() {
		return message;
	}
}
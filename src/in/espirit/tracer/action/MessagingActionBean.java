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

@UrlBinding("/messaging/{event}/{mailtype}/{id}")
public class MessagingActionBean extends BaseActionBean {
	private static final String INBOX = "/WEB-INF/jsp/messaging/inbox.jsp";
	private static final String COMPOSE = "/WEB-INF/jsp/messaging/compose.jsp";
	private String event;
	private String mailtype;
	
	private Messaging message;
	
	private int id;

	@DefaultHandler
	public Resolution view() throws Exception {
		if(event.equalsIgnoreCase("inbox")){
			return new ForwardResolution(INBOX);
		}else if (mailtype != null){
				message = MessageDao.getMessageDetails(id);
			if (mailtype.equalsIgnoreCase("forward")) {				
				message.setSubject("Fw:" + message.getSubject());  
				message.setTo("");
				message.setCc("");			 //From will be set during mail sending.
			}
			else if (mailtype.equalsIgnoreCase("replyall")) {
				message.setSubject("Re:" + message.getSubject());  
				message.setTo(message.getFrom() + "," + message.getTo());
				message.setCc(message.getCc());				
			}
			else if (mailtype.equalsIgnoreCase("reply")) {
				message.setSubject("Re:" + message.getSubject());  
				message.setTo(message.getFrom());
				message.setCc("");				
			}			
			return new ForwardResolution(COMPOSE);
		}
		else {
			return new ForwardResolution(COMPOSE);
		}
	}

	public Resolution sendMessage() throws Exception {
		message.setFrom(getContext().getLoggedUser());
		MessageDao.registerEntry(message);		
		return new RedirectResolution("/messaging/inbox");		
	}
	
	public Resolution MessageDetails() {
		
		String output = "";
		try {
			message = MessageDao.getMessageDetails(id);			
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
	
	public Resolution MessageList() {
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
	
	public ArrayList<Messaging> getMyMessages() throws Exception {
		return MessageDao.getMessages(getContext().getLoggedUser(),null, CustomDao.getResourceMessage("message.pagecount"));
	}
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setMessage(Messaging message) {
		this.message = message;
	}

	public Messaging getMessage() {
		return message;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setMailtype(String mailtype) {
		this.mailtype = mailtype;
	}

	public String getMailtype() {
		return mailtype;
	}

}

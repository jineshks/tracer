package in.espirit.tracer.action;


import java.util.HashMap;
import java.util.Map;

import in.espirit.tracer.database.dao.CustomDao;
import in.espirit.tracer.database.dao.MailDao;
import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.database.dao.UserDao;
import in.espirit.tracer.model.Comment;
import in.espirit.tracer.model.Mail;
import in.espirit.tracer.model.Ticket;
import in.espirit.tracer.util.DateUtils;
import in.espirit.tracer.util.MailUtils;
import in.espirit.tracer.util.StringUtils;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

public class TicketActionBean extends BaseActionBean implements ValidationErrorHandler{
	protected static final String URL_View = "/WEB-INF/jsp/ticket_view.jsp";
	protected static final String URL_New = "/WEB-INF/jsp/ticket_new.jsp";
	
	//protected static final Logger logger = Logger.getLogger(TicketActionBean.class.getName());
	
	public boolean handleComments(String commentText, String id, String type) throws Exception {		
		boolean flag = false;
		if (commentText!= null) {
			Comment comment = new Comment();
			comment.setUserName(getContext().getLoggedUser());			
			comment.setTimeStamp(DateUtils.getDatetimeInFormat("yyyy/MM/dd HH:mm"));
			comment.setComment(commentText);				
			flag = TicketDao.insertComment(id, comment);			
			String activity = getContext().getLoggedUser() + " has commented " + type + " #" + id;
			TicketDao.handleActivity(activity, getContext().getLoggedUser());	
		}
		return flag;
	}	
	
	public boolean handleEmail(Ticket ticket, String template) {
		boolean flag = false;
		Mail mail;
		try {
			mail = MailDao.getMailTemplate(template);
			String email = "";
			String to = "";
			if (ticket.getOwner() != null) {
				email = UserDao.getUserEmail(ticket.getOwner());
				if (email != null) {
					to = email;
				}			
			}
			
			if (ticket.getReporter() != null) {
				email = UserDao.getUserEmail(ticket.getReporter());
				if (email != null) {
					if (!to.equalsIgnoreCase("")) {
						to+=",";
					}					
					to += email;
				}				
			}
			mail.setTo(to);
			
			Map<String, String> values = new HashMap<String, String>();
								
			values.put("<type>", ticket.getType());
			values.put("<id>", ticket.getId());
			values.put("<applicationhome>", CustomDao.getResourceMessage("applicationhome"));
			values.put("<updater>",getContext().getLoggedUser());
			mail.setMessage(StringUtils.templateToMail(mail.getMessage(), values));				
			flag = MailUtils.sendTextMail(mail);	
		
		} catch (Exception e) {
			logger.warn("Sending email failed with error - " + e.getMessage());
			e.printStackTrace();
		}			
		return flag;	
	}
	
	public Resolution cancel() {
		ForwardResolution res = new ForwardResolution(ListActionBean.class);
		res.addParameter("list", "all");
		res.addParameter("type", "defect");
		return res;		
	}
	
	@Override
	public Resolution handleValidationErrors(ValidationErrors arg0)
			throws Exception {
		if (arg0.hasFieldErrors()) {
			arg0.addGlobalError(new SimpleError("Fields Short Description, Description, Priority and Milestone are required fields"));			
		}
		return null;
	}
}

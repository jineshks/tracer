package in.espirit.tracer.action;


import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Comment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

public class TicketActionBean extends BaseActionBean implements ValidationErrorHandler{
	protected static final String URL_View = "/WEB-INF/jsp/ticket_view.jsp";
	protected static final String URL_New = "/WEB-INF/jsp/ticket_new.jsp";
	
	//protected static final Logger logger = Logger.getLogger(TicketActionBean.class.getName());
	
	public void handleComments(String commentText, String id, String type) throws Exception {		
		if (commentText!= null) {
			Comment comment = new Comment();
			comment.setUserName(getContext().getLoggedUser());			
			Calendar curDate = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String date = df.format(curDate.getTime());			
			comment.setTimeStamp(date);
			comment.setComment(commentText);				
			TicketDao.insertComment(id, comment);			
			String activity = getContext().getLoggedUser() + " has commented " + type + " #" + id;
			TicketDao.handleActivity(activity);	
		}			
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

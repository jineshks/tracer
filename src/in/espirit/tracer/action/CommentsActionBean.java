package in.espirit.tracer.action;

import in.espirit.tracer.model.Task;
import in.espirit.tracer.util.DateUtils;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

import org.apache.log4j.Logger;

@UrlBinding("/comments")
public class CommentsActionBean extends TicketActionBean implements
		ValidationErrorHandler {
	private static Logger logger = Logger.getLogger(CommentsActionBean.class.getName());

	private Task ticket;

	public Task getTicket() {
		return ticket;
	}

	public void setTicket(Task ticket) {
		this.ticket = ticket;
	}

	@DefaultHandler
	public Resolution postComment() {
		boolean flag = false;
		String output = "<p>Sorry! could not post the comment </p>";
		String comment = this.getContext().getRequest().getParameter("comment");
		String commentId = this.getContext().getRequest().getParameter("commentid");

		try {
			flag = handleComments(
					this.getContext().getRequest().getParameter("comment"),
					this.getContext().getRequest().getParameter("id"), this
							.getContext().getRequest().getParameter("type"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		if (flag) {
			
			output = "<li> " +
					"<span class=\"tal\"><a id=\"Comment#"+commentId+"\" href=\"Comment#"+commentId+"\">Comment#"+commentId+"</a></span>"+ 
					"<span class=\"tar right\">"+DateUtils.getDatetimeInFormat("yyyy/MM/dd HH:mm")+"</span>"+
					"<p> <span class=\"bold\">"+getContext().getLoggedUser()+" :</span> "+comment +"</p> </li>";
		}
		return new StreamingResolution("text/html", output);
	}


	@Override
	public Resolution handleValidationErrors(ValidationErrors arg0)
			throws Exception {
		if (arg0.hasFieldErrors()) {
			arg0.addGlobalError(new SimpleError("Comment text is mandatory"));
		}
		return null;
	}
}

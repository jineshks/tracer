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

@UrlBinding("/comments")
public class CommentsActionBean extends TicketActionBean implements
		ValidationErrorHandler {

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
		System.out.println(" Post Comment action");
		String comment = this.getContext().getRequest().getParameter("comment");

		try {
			flag = handleComments(
					this.getContext().getRequest().getParameter("comment"),
					this.getContext().getRequest().getParameter("id"), this
							.getContext().getRequest().getParameter("type"));
		} catch (Exception e) {
			System.out.println(" Error!!");
			e.printStackTrace();
		}
		if (flag) {
			
			output = "<li> <span class=\"tal\"><a href=\"#\">Comment#</a></span>"+ 
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

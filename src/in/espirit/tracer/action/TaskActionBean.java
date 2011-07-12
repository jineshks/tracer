package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Task;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

@UrlBinding("/task/{ticket}")
public class TaskActionBean extends TicketActionBean {
	private static final String URL_View = "/WEB-INF/jsp/task_view.jsp";
	private static final String URL_New = "/WEB-INF/jsp/task_new.jsp";
	
	@ValidateNestedProperties({
			@Validate(field = "shortDesc", required = true, on = "submit"),
			@Validate(field = "priority", required = true, on = "submit"),
			@Validate(field = "desc", required = true, on = "submit"),
			@Validate(field = "milestone", required = true, on = "submit") })
	private Task ticket;

	@DefaultHandler
	public Resolution open() {
		logger.debug("Opening task edit / new");
		getContext().setCurrentSection("new" + ticket.getType());
		if (ticket.getId() == null ){
			return new ForwardResolution(URL_New);
		}
		else {
			return new ForwardResolution(URL_View);
		}		
	}

	@DontValidate
	public Resolution submit() throws Exception {
		System.out.println("getting ticket values");
		ticket = getTicket();
		System.out.println("over!");
		if (ticket.getId() == null) {
			getContext().getMessages().add(
					new SimpleMessage("New " + ticket.getType()	+ " Registered."));
			logger.debug("Registering new ticket of type " + ticket.getType());
			TicketDao.registerTicket(ticket);
		} else {
			getContext().getMessages().add(
					new SimpleMessage(ticket.getType()+ " Successfully edited."));
			logger.debug("Saving edited version of ticket - "+ ticket.getType() + "-" + ticket.getId());
			TicketDao.editTicket(ticket, getContext().getLoggedUser());
		}
		return new RedirectResolution(ListActionBean.class).addParameter(
				"list", "all").addParameter("type", "task");
	}

	public Task getTicket() {
		return ticket;
	}

	public void setTicket(Task ticket) {
		this.ticket = ticket;
	}

}

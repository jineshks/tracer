package in.espirit.tracer.action;

import java.util.ArrayList;

import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Task;
import in.espirit.tracer.model.Ticket;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

@UrlBinding("/task/{ticket}/{parentTicketId}")
public class TaskActionBean extends TicketActionBean {
	
	/*	 
	@ValidateNestedProperties({
			@Validate(field = "shortDesc", required = true, on = "submit"),
			@Validate(field = "priority", required = true, on = "submit"),
			@Validate(field = "desc", required = true, on = "submit"),
			@Validate(field = "milestone", required = true, on = "submit") })
	*/
	
	private Task ticket;
	private String parentTicketId;

	@DefaultHandler
	public Resolution open() {
		logger.debug("Opening task edit / new");
		getContext().setCurrentSection("new" + ticket.getType());
		if (ticket.getId() == null ){
			if (parentTicketId != null) {
				ticket.setParentTicket(parentTicketId);
			}
			return new ForwardResolution(URL_New);
		}
		else {
			return new ForwardResolution(URL_View);
		}		
	}

	//@DontValidate  // Need to remove the validation handlers in the super class action bean and remove validations here.
	public Resolution submit() throws Exception {
		ticket = getTicket();
		if (ticket.getId() == null) {
			getContext().getMessages().add(new SimpleMessage("New " + ticket.getType()	+ " Registered."));
			logger.debug("Registering new ticket of type " + ticket.getType());
			String ticketid = TicketDao.registerTicket(ticket, getContext().getLoggedUser());
			handleComments(ticket.getNewComments(), ticketid, ticket.getType());
			ticket.setId(ticketid);
			if (ticket.getOwner() != null || ticket.getReporter()!= null) {
				handleEmail(ticket, "ticket-new");
			}	
		} else {
			getContext().getMessages().add(new SimpleMessage(ticket.getType()+ " Successfully edited."));
			logger.debug("Saving edited version of ticket - "+ ticket.getType() + "-" + ticket.getId());
			TicketDao.editTicket(ticket, getContext().getLoggedUser());
			handleComments(ticket.getNewComments(), ticket.getId(), ticket.getType());	
			if (ticket.getOwner() != null || ticket.getReporter()!= null) {
				handleEmail(ticket, "ticket-edit");
			}			
		}
		return new RedirectResolution(ListActionBean.class).addParameter(
				"list", "all").addParameter("type", "task");
	}
	
	@ValidationMethod(on="submit")
	public void valMethod(ValidationErrors errors) throws Exception{
		if(ticket.getParentTicket() != null) {
		if (!TicketDao.ticketExists(ticket.getParentTicket())) {
			errors.addGlobalError(new SimpleError("Parent Ticket no is not existing. Please enter a correct id"));
		}	
		}
	}

	public Task getTicket() {
		return ticket;
	}

	public void setTicket(Task ticket) {
		this.ticket = ticket;
	}
	
	public Ticket getParentTicket() throws Exception {	
		return (ticket.getParentTicket()!=null)?TicketDao.getParentTicketDetails(ticket.getParentTicket()):null;			
	}
	
	public ArrayList<Ticket> getSubTickets() throws Exception {
		return TicketDao.getSubTicketDetails(ticket.getId());		
	}

	public void setParentTicketId(String parentTicketId) {
		this.parentTicketId = parentTicketId;
	}

	public String getParentTicketId() {
		return parentTicketId;
	}	
}

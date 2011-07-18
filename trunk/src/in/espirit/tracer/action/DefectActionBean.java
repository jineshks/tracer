package in.espirit.tracer.action;


import java.util.ArrayList;

import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Defect;
import in.espirit.tracer.model.Ticket;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

@UrlBinding("/defect/{ticket}")
public class DefectActionBean extends TicketActionBean {
	
	/*
	 
	@ValidateNestedProperties({
		@Validate(field="title", required=true, on="submit"),
		@Validate(field="priority", required=true, on="submit"),
		@Validate(field="desc", required=true, on="submit"),
		@Validate(field="milestone", required=true, on="submit")
	})
	
	 */
		
	private Defect ticket;
	
	
	@DefaultHandler
	public Resolution open() {
		logger.debug("Opening defect edit / new");
		getContext().setCurrentSection("new" + ticket.getType());
		if (ticket.getId() == null ){
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
			getContext().getMessages().add(new SimpleMessage("New " + ticket.getType() +" Registered."));
			logger.debug("Registering new ticket of type " + ticket.getType());
			String ticketid = TicketDao.registerTicket(ticket, getContext().getLoggedUser());
			handleComments(ticket.getNewComments(), ticketid, ticket.getType());			
		}
		else {
			getContext().getMessages().add(new SimpleMessage(ticket.getType() + " Successfully edited."));
			logger.debug("Saving edited version of ticket - " + ticket.getType()+ "-" + ticket.getId());
			TicketDao.editTicket(ticket, getContext().getLoggedUser());
			handleComments(ticket.getNewComments(), ticket.getId(), ticket.getType());				
		}	
		return new RedirectResolution(ListActionBean.class).addParameter("list", "all").addParameter("type", "defect");		
	}

	@ValidationMethod(on="submit")
	public void valMethod(ValidationErrors errors) throws Exception{
		if(ticket.getParentTicket() != null) {
			if (!TicketDao.ticketExists(ticket.getParentTicket())) {
				errors.addGlobalError(new SimpleError("Parent Ticket no is not existing. Please enter a correct id"));
			}	
		}
	}
		
	public Defect getTicket() {
		return ticket;
	}

	public void setTicket(Defect ticket) {
		this.ticket = ticket;
	}
	
	public Ticket getParentTicket() throws Exception {	
		return (ticket.getParentTicket()!=null)?TicketDao.getParentTicketDetails(ticket.getParentTicket()):null;			
	}
	
	public ArrayList<Ticket> getSubTickets() throws Exception {
		return TicketDao.getSubTicketDetails(ticket.getId());		
	}	
}

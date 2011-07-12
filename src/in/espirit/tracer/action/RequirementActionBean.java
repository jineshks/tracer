package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Requirement;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

@UrlBinding("/requirement/{ticket}")
public class RequirementActionBean extends TicketActionBean {
	
	@ValidateNestedProperties({
		@Validate(field="shortDesc", required=true, on="submit"),
		@Validate(field="priority", required=true, on="submit"),
		@Validate(field="desc", required=true, on="submit"),
		@Validate(field="milestone", required=true, on="submit")
	})	
	private Requirement ticket;
	
	@DefaultHandler
	public Resolution open() {
		logger.debug("Opening requirement edit / new");
		getContext().setCurrentSection("new" + ticket.getType());
		if (ticket.getId() == null ){
			return new ForwardResolution(URL_New);
		}
		else {
			return new ForwardResolution(URL_View);
		}		
	}
	
	@DontValidate  // Need to remove the validation handlers in the super class action bean and remove validations here.
	public Resolution submit() throws Exception {
		ticket = getTicket();
		if (ticket.getId() == null) {
			getContext().getMessages().add(new SimpleMessage("New " + ticket.getType() +" Registered."));
			logger.debug("Registering new ticket of type " + ticket.getType());
			TicketDao.registerTicket(ticket);			
		}
		else {
			getContext().getMessages().add(new SimpleMessage(ticket.getType() + " Successfully edited."));
			logger.debug("Saving edited version of ticket - " + ticket.getType()+ "-" + ticket.getId());
			TicketDao.editTicket(ticket,getContext().getLoggedUser());			
		}	
		return new RedirectResolution(ListActionBean.class).addParameter("list", "all").addParameter("type", "requirement");		
	}

	public Requirement getTicket() {
		return ticket;
	}

	public void setTicket(Requirement ticket) {
		this.ticket = ticket;
	}	
}

package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Ticket;

import java.util.ArrayList;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.log4j.Logger;

@UrlBinding("/planner/{event}/{leftMilestone}/{rightMilestone}")
public class PlannerActionBean extends BaseActionBean {
	private static Logger logger = Logger.getLogger(PlannerActionBean.class
			.getName());
	private static final String URL = "/WEB-INF/jsp/planner.jsp";

	private String leftMilestone;
	private String rightMilestone;

	@DefaultHandler
	public Resolution view() {
		try {
			if (leftMilestone ==null) {
				leftMilestone = "Backlog";
			}
			if (rightMilestone == null) {
				rightMilestone = MilestoneDao.getCurrentMilestone();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ForwardResolution(URL);
	}

	public ArrayList<Ticket> getBacklogTickets() throws Exception {
		//return TicketDao.getTicketForMilestone("Backlog");
		return TicketDao.getTicketForMilestone(leftMilestone);
	}

	public ArrayList<Ticket> getCurrentSprintTickets() throws Exception {
		return TicketDao.getTicketForMilestone(rightMilestone);
		//return TicketDao.getTicketForMilestone(MilestoneDao.getCurrentMilestone());
	}

	// persist
	public Resolution persist() {
		boolean flag = false;
		String output = "<p>Sorry! could not save the new state </p>";
		String ticket_id = this.getContext().getRequest()
				.getParameter("ticket_id");
		String ticket_type = this.getContext().getRequest()
				.getParameter("ticket_type");
		String operation = this.getContext().getRequest().getParameter("operation");
		logger.debug("Operation:"+operation); 
		TicketDao ticket = new TicketDao();
			try {
			if(operation.equalsIgnoreCase("updateImportance")){
				String importance = this.getContext().getRequest().getParameter("importance");
				logger.debug("Ticket id:" + ticket_id + " Importance :" + importance);
				flag = ticket.updateProperty(ticket_id, ticket_type, "importance", importance, getContext()
						.getLoggedUser());
			}else if(operation.equalsIgnoreCase("updatePriority")){
				String priority = this.getContext().getRequest().getParameter("priority");
				logger.debug("Ticket id:" + ticket_id + " Priority :" + priority);
				flag = ticket.updateProperty(ticket_id, ticket_type, "priority", priority, getContext()
						.getLoggedUser());
			}else if (operation.equalsIgnoreCase("updateMilestone")) {
				String milestone = this.getContext().getRequest().getParameter("milestone");
				logger.debug("Ticket id:" + ticket_id + " Milestone :" + milestone);
				flag = ticket.updateProperty(ticket_id, ticket_type, "milestone", milestone, getContext()
						.getLoggedUser());
			}
		
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		if (flag) {
			output = "success";
		} else {
			output = "error";
		}
		return new StreamingResolution("text/html", output);
	}

	public String getLeftMilestone() {
		return leftMilestone;
	}

	public void setLeftMilestone(String leftMilestone) {
		this.leftMilestone = leftMilestone;
	}

	public String getRightMilestone() {
		return rightMilestone;
	}

	public void setRightMilestone(String rightMilestone) {
		this.rightMilestone = rightMilestone;
	}

}

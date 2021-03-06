package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ActivityDao;
import in.espirit.tracer.database.dao.AlertDao;
import in.espirit.tracer.database.dao.LinkDao;
import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Activity;
import in.espirit.tracer.model.Alert;
import in.espirit.tracer.model.Link;
import in.espirit.tracer.model.Milestone;
import in.espirit.tracer.model.Ticket;
import in.espirit.tracer.util.DateUtils;

import java.util.ArrayList;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/dashboard")
public class DashboardActionBean extends BaseActionBean {
	private static final String DASHBOARD = "/WEB-INF/jsp/dashboard.jsp";
	
	@DefaultHandler
	public Resolution view() {
		return new ForwardResolution(DASHBOARD);
	}
	
	public ArrayList<Alert> getAlerts() throws Exception {
		return AlertDao.getActiveAlerts();		
	}
	
	public ArrayList<Activity> getActivities() throws Exception {
		return ActivityDao.getRecentActivities();		
	}

	public ArrayList<Ticket> getMyTasks() throws Exception {
		return TicketDao.getMyTickets("task",getContext().getLoggedUser());
	}

	public ArrayList<Ticket> getMyDefects() throws Exception {
		return TicketDao.getMyTickets("defect",getContext().getLoggedUser());
	}
	
	public ArrayList<Ticket> getMyRequirements() throws Exception {
		return TicketDao.getMyTickets("requirement",getContext().getLoggedUser());
	}
	
	public ArrayList<Link> getMyLinks() throws Exception {
		return LinkDao.getLinks("my", getContext().getLoggedUser(), "5", null);
	}
	
	public Milestone getcurrentMilestone() throws Exception {
		Milestone current = MilestoneDao.getCurrentMilestoneDetails();
		
		//Setting the description to days remaining and using it to displaying the days there.
		if (current != null) {
		current.setDescription(DateUtils.daysRemaining(DateUtils.convertStringToCalendar(current.getEndDate(), "yyyy-MM-dd")));
		current.setProgress(MilestoneDao.calcProgress(current.getName()));
		current.setTotalTickets(MilestoneDao.getSprintTotalTickets(current.getName()));
		current.setVelocity(MilestoneDao.getSprintStoryPoint(current.getName()));
		return current;
		}
		return null;
	}
	
	public static String getDashboard() {
		return DASHBOARD;
	}
}

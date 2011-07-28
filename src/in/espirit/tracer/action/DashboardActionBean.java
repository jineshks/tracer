package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ActivityDao;
import in.espirit.tracer.database.dao.AlertDao;
import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Activity;
import in.espirit.tracer.model.Alert;
import in.espirit.tracer.model.Ticket;

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
		return AlertDao.getAllAlert();		
	}
	
	public ArrayList<Activity> getActivities() throws Exception {
		return ActivityDao.getActivities("5");		
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
	
	public static String getDashboard() {
		return DASHBOARD;
	}
}

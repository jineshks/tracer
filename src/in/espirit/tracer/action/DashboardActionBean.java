package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ActivityDao;
import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Activity;
import in.espirit.tracer.model.Ticket;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	public ArrayList<Activity> getActivities() throws Exception {
		ArrayList<Activity> temp = ActivityDao.getAllActivity();
		for(Activity a : temp) {
			String s1 = a.getActivity();
			// This part can be improved or optimized for better practices.
			Integer pos = s1.lastIndexOf("#");  // This is to determine whether the activity has a number associated with it.
			if (pos > 0) {
				// Pattern will be <username> has <action> <tickettype> #<no.>. Action can be created, edited or commented.
				String[] a1 = s1.split(" ");  
				String id = s1.substring(pos);				
				String link = "<a href='./" + a1[3] + "/" + id.substring(1)  + "'>" + id + "</a>";	
				a.setActivity(a1[0] + " " + a1[1] + " " + a1[2] + " " + a1[3] + " " + link);				
			}		
		}		
		return temp;
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

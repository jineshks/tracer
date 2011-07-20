package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Ticket;

import java.util.ArrayList;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/taskboard")
public class TaskBoardActionBean extends BaseActionBean {
	
	private static final String URL = "/WEB-INF/jsp/taskboard.jsp";
	
	@DefaultHandler
	public Resolution view() {
		return new ForwardResolution(URL);
	}
	
	public ArrayList<Ticket> getBacklog() throws Exception {		
		return TicketDao.getTaskBoardList(MilestoneDao.getCurrentMilestone(), "Backlog");	
	}
	
	public ArrayList<Ticket> getDevelopment() throws Exception {		
		return TicketDao.getTaskBoardList(MilestoneDao.getCurrentMilestone(), "Development");	
	}
	
	public ArrayList<Ticket> getReadyForTest() throws Exception {		
		return TicketDao.getTaskBoardList(MilestoneDao.getCurrentMilestone(), "Ready for Test");	
	}
	
	public ArrayList<Ticket> getTesting() throws Exception {		
		return TicketDao.getTaskBoardList(MilestoneDao.getCurrentMilestone(), "Testing");	
	}
	
	public ArrayList<Ticket> getReadyForRelease() throws Exception {		
		return TicketDao.getTaskBoardList(MilestoneDao.getCurrentMilestone(), "Ready for release");	
	}
	
	public ArrayList<Ticket> getReleased() throws Exception {		
		return TicketDao.getTaskBoardList(MilestoneDao.getCurrentMilestone(), "Released");	
	}

}

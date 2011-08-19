package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.model.Milestone;
import in.espirit.tracer.model.Ticket;

import java.util.ArrayList;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/list/milestone/{milestoneId}")
public class MilestoneListActionBean extends BaseActionBean {
	private static final String MILESTONE_URL = "/WEB-INF/jsp/milestone_list.jsp";
	private static final String MILESTONE_TICKET_URL = "/WEB-INF/jsp/milestone_ticket_list.jsp";
	
	private String milestoneId;

	public ArrayList<Milestone> getMilestoneList() throws Exception {
		ArrayList<Milestone> result  = MilestoneDao.getAllEntries();
		for(Milestone m : result) {	
			m.setProgress(MilestoneDao.calcProgress(m.getName()));
			m.setTotalTickets(MilestoneDao.getSprintTotalTickets(m.getName()));
			m.setVelocity(MilestoneDao.getSprintStoryPoint(m.getName()));				
		}
		return result;
	}
	
	public ArrayList<Ticket> getTicketList() throws Exception {
		return MilestoneDao.getList(getMilestone().getName());
	}
	
	public Milestone getMilestone() throws Exception {
		return MilestoneDao.getEntry(milestoneId);
	}
	
	@DefaultHandler
	public Resolution open() {	
		logger.debug("In the Milestone listing page");
		//getContext().setCurrentSection("milestone");
		if (milestoneId == null) {
			return new ForwardResolution(MILESTONE_URL);
		}
		return new ForwardResolution(MILESTONE_TICKET_URL);	
	}

	public String getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(String milestoneId) {
		this.milestoneId = milestoneId;
	}

	
	
	/*
	
	public HashMap<Milestone, ArrayList<Ticket>> getListItems() throws Exception {
		HashMap<Milestone, ArrayList<Ticket>> result = new HashMap<Milestone, ArrayList<Ticket>>();
		ArrayList<Milestone> milestone = MilestoneDao.getAllEntries();
					
		for(int i = 0;i<milestone.size();i++) {
			result.put(milestone.get(i), MilestoneDao.getList(milestone.get(i).getName()));			
		}
		
		//result.put("one", DefectDao.getAllDefects(null, null, "Open" ));
		//result.put("two", DefectDao.getAllDefects(null, "FYI", null ));		
		return result;				
	}
	
	 */
	
}

package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.model.Milestone;
import in.espirit.tracer.model.Requirement;
import in.espirit.tracer.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;


import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/list/milestone/{milestoneId}")
public class MilestoneListActionBean extends BaseActionBean {
	private static final String MILESTONE_URL = "/WEB-INF/jsp/milestone_list.jsp";
	private static final String MILESTONE_TICKET_URL = "/WEB-INF/jsp/milestone_ticket_list.jsp";
	
	private String milestoneId;

	public HashMap<Milestone, Integer> getMilestoneList() throws Exception {
		ArrayList<Milestone> mlList  = MilestoneDao.getAllEntries();
		HashMap<Milestone, Integer> result = new HashMap<Milestone, Integer>();
		
		//(((sum of task progress)/(no of tasks)x story point)+((sum of task progress)/(no of tasks)x story point)+...)/(sum of story points)
		for(Milestone m : mlList) {	
			ArrayList<Requirement> req = MilestoneDao.getMilestoneStory(m.getName());
			double top = 0;    // has the top value in the division operation.
			double bottom = 0;  // has the bottom value in the division operation.
			for(Requirement r : req) {
				if (r.getStoryPoint()!=null) {
					int storypoint = Integer.parseInt(r.getStoryPoint());
					double prog = MilestoneDao.getTaskProgressTotal(r.getId());
					top += (prog * storypoint);
					bottom += storypoint;		
				}
			}			
			result.put(m, (int) (top/bottom));			
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

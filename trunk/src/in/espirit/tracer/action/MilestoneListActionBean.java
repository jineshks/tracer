package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ConfigDao;
import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;


import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/list/milestone")
public class MilestoneListActionBean extends BaseActionBean {
	private static final String URL = "/WEB-INF/jsp/milestone.jsp";
	
	public HashMap<String, ArrayList<Ticket>> getListItems() throws Exception {
		
		HashMap<String, ArrayList<Ticket>> result = new HashMap<String, ArrayList<Ticket>>();
		
		ArrayList<String> milestone = ConfigDao.getConfig("Milestone");
					
		for(int i = 0;i<milestone.size();i++) {
			result.put(milestone.get(i), MilestoneDao.getList(milestone.get(i)));			
		}
		
		//result.put("one", DefectDao.getAllDefects(null, null, "Open" ));
		//result.put("two", DefectDao.getAllDefects(null, "FYI", null ));		
		return result;				
	}
	
	@DefaultHandler
	public Resolution open() {	
		logger.debug("In the Milestone listing page");
		getContext().setCurrentSection("milestone");
		return new ForwardResolution(URL);		
		
	}

}

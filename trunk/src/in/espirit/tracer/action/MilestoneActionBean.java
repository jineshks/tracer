package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.model.Milestone;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/milestone/{milestone}")
public class MilestoneActionBean extends BaseActionBean {
	private static final String view = "/WEB-INF/jsp/milestone_view.jsp";
	//private static final String list = "/WEB-INF/jsp/milestone_list.jsp";
	
	private Milestone milestone;
	
	@DefaultHandler
	public Resolution open() {	
		return new ForwardResolution(view);		
	}
	
	public Resolution submit() throws Exception {
		if (milestone.getId()==null) {
			MilestoneDao.registerEntry(milestone);	
			getContext().getMessages().add(new SimpleMessage("New Milestone added."));
			logger.debug("Registering new milestone - " + milestone.getName());
		}
		else {
			MilestoneDao.editTicket(milestone);
			getContext().getMessages().add(new SimpleMessage("Milestone successfully edited and saved."));
			logger.debug("Editing / Saving milestone - " + milestone.getName());
		}
		return new RedirectResolution(ConfigListActionBean.class);
	}
	

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public Milestone getMilestone() {
		return milestone;
	}
}

package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ConfigDao;
import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.model.Config;
import in.espirit.tracer.model.Milestone;

import java.util.ArrayList;
import java.util.HashMap;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/config/milestone/{milestone}")
public class ConfigMilestoneActionBean extends BaseActionBean {

	private static final String URL="/WEB-INF/jsp/config/milestone.jsp";

	private Milestone milestone;

	@DefaultHandler
	public Resolution open(){
		getContext().setCurrentSection("configlist");
		return new ForwardResolution(URL);
	}

	public Resolution submit() throws Exception {
		if (milestone.getCurrent() != null) {  // If not equal to null means the current milestone checkbox is selected
			MilestoneDao.changeCurrentMilestone();			
		}
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
		return new RedirectResolution(ConfigMilestoneActionBean.class);
	}

	public ArrayList<Milestone> getMilestoneList() throws Exception {
		return MilestoneDao.getAllEntries();						
	}

	/*
	 * 
	 * 
	 * 

	public ArrayList<Config> getAllConfig() throws Exception {		
		return ConfigDao.getAllConfig();
	}

	public HashMap<String, ArrayList<Config>> getListItems() throws Exception {		
		HashMap<String, ArrayList<Config>> result = new HashMap<String, ArrayList<Config>>();		
		//result.put("Importance", ConfigDao.getConfigList("Importance"));
		//result.put("Milestone", ConfigDao.getConfigList("Milestone"));  
		//result.put("Priority", ConfigDao.getConfigList("Priority"));  now moved to properties file
		//result.put("Status", ConfigDao.getConfigList("Status"));	
		return result;				
	}

	public HashMap<String, ArrayList<Config>> getListItems() throws Exception {		
		HashMap<String, ArrayList<Config>> result = new HashMap<String, ArrayList<Config>>();
		ArrayList<Config> one = ConfigDao.getAllConfig();
		ArrayList<Config> temp = new ArrayList<Config>();

		for(int i=0;i<one.size();i++) {			
			if (i>0) {
				if (one.get(i).getKey().equals(one.get(i-1).getKey())) {
					temp.add(one.get(i));
				}	
				else {		
					if (temp.size()>0) {
						result.put(one.get(i-1).getKey(), (ArrayList<Config>)temp.clone());
						temp.clear();	
						}
					temp.add(one.get(i));					
				}
			}
			else {
					temp.add(one.get(i));
			}			
		}
		if (temp.size()>0) {
			result.put(one.get(one.size()-1).getKey(), (ArrayList<Config>) temp.clone());
			temp.clear();							
		}		
		return result;				
	}

	 */



	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public Milestone getMilestone() {		
		return milestone;
	}

}

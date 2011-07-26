package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ConfigDao;
import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Config;
import in.espirit.tracer.model.Milestone;
import in.espirit.tracer.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/list/config")
public class ConfigListActionBean extends BaseActionBean {

	private static final String URL="/WEB-INF/jsp/configlist.jsp";
	

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
		
	public ArrayList<Milestone> getMilestone() throws Exception {
		return MilestoneDao.getAllEntries();						
	}
	
	/*
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
		
	@DefaultHandler
	public Resolution open(){
		getContext().setCurrentSection("configlist");
		return new ForwardResolution(URL);
	}
	
}

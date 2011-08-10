package in.espirit.tracer.view;

import in.espirit.tracer.database.dao.ConfigDao;
import in.espirit.tracer.database.dao.CustomDao;
import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.database.dao.UserDao;

import java.util.ArrayList;


public class ConfigViewHelper {
			
	public ArrayList<String> getUserNames() throws Exception{
		return UserDao.getUserNames();		
	}
	
	/*
	
	public ArrayList<String> getConfig(String config) throws Exception {
		return ConfigDao.getConfig(config);
	}
	
	*/
	 
	public ArrayList<String> getPriority() throws Exception{
		return CustomDao.getValues("priority");
		//return ConfigDao.getConfig("Priority");
	}
	
	public ArrayList<String> getStatus() throws Exception{
		return CustomDao.getValues("status");
		//return ConfigDao.getConfig("Status");	
	}
	
	public ArrayList<String> getMilestone() throws Exception{
		return MilestoneDao.getMilestoneNames();
		//return ConfigDao.getConfig("Milestone");	
	}
	
	public ArrayList<String> getImportance() throws Exception{
		return CustomDao.getValues("importance");
		//return ConfigDao.getConfig("Importance");	
	}
	
	public ArrayList<String> getPhase() throws Exception{
		return CustomDao.getValues("phase");	
	}
	
	public ArrayList<String> getUserRole() throws Exception{
		return CustomDao.getValues("user.role");	
	}
}

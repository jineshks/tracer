package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.UserDao;
import in.espirit.tracer.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/list/user/{view}")
public class UserListActionBean extends BaseActionBean {
	
	private static final String URL_LISTING = "/WEB-INF/jsp/user_list.jsp";
	private static final String URL_ADMINAPPROVAL = "/WEB-INF/jsp/config/user_approval_list.jsp";
	
	private String view;
	
	public HashMap<String, ArrayList<User>> getUserList() throws Exception {
		HashMap<String, ArrayList<User>> result = new HashMap<String, ArrayList<User>>();
		ArrayList<User> from_db = UserDao.getUserList();
		ArrayList<User> result_disp = new ArrayList<User>();

		try {
			for(int i=0;i<from_db.size();i++) {			
				if (i>0) {
					if (from_db.get(i).getTeam().equals(from_db.get(i-1).getTeam())) {
						result_disp.add(from_db.get(i));
					}	
					else {		
						if (result_disp.size()>0) {
							result.put(from_db.get(i-1).getTeam(), (ArrayList<User>)result_disp.clone());
							result_disp.clear();	
						}
						result_disp.add(from_db.get(i));					
					}
				}
				else {
					result_disp.add(from_db.get(i));
				}			
			}
			if (result_disp.size()>0) {
				result.put(from_db.get(from_db.size()-1).getTeam(), (ArrayList<User>) result_disp.clone());
				result_disp.clear();							
			}		
		}
		catch(Exception e) {
			logger.fatal("error at getting user list--" + e.getMessage());
		}
		
		return result;
	}
		
	public ArrayList<User> getUserApprovalList() throws Exception {
		return UserDao.getUserApprovalList();
	}
	
	
	@DefaultHandler
	public Resolution open() throws Exception {
		//Make a check whether the user is admin or not.
		
		return new ForwardResolution((view.equalsIgnoreCase("approval"))?URL_ADMINAPPROVAL:URL_LISTING);	
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}	
}

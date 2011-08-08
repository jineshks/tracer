package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.UserDao;
import in.espirit.tracer.model.User;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.log4j.Logger;

@UrlBinding("/user/{userName}/{action}")
public class UserActionBean extends BaseActionBean {
	
	private static final Logger logger = Logger.getLogger(UserActionBean.class.getName());
	private static final String URL_EDIT = "/WEB-INF/jsp/user_edit.jsp";
	private static final String URL_VIEW = "/WEB-INF/jsp/user_view.jsp";
	
	private User user;
	private String userName;
	private String action;
	
	@DefaultHandler
	public Resolution open() {
		String displayView="";
		if (action==null) {
			displayView = URL_VIEW;
		}
		else if (action.equalsIgnoreCase("edit") && userName.equalsIgnoreCase(getContext().getLoggedUser())) {
			displayView = URL_EDIT;
		}
		else {  //to handle scenario where person is trying to edit other profiles or something else. 
			getContext().getMessages().add(new SimpleMessage("You can't edit other person profiles. You can only view them"));
			displayView = URL_VIEW;
		}		
		return new ForwardResolution(displayView);		
	}

	public Resolution update() throws Exception {
		user = getUser();
		boolean flag = UserDao.editUser(user);
		if (flag) {
			getContext().getMessages().add(new SimpleMessage("User Profile Edited Sucessfully"));			
		}
		else {
			getContext().getMessages().add(new SimpleMessage("Updates are not saved into database. There is some problem!"));	
		}
		logger.debug("User document edited -" + user.getUserName());		
		return new RedirectResolution(DashboardActionBean.class);			
	}
	

	public User getUser() {
		if (userName!=null) {
			try {
				return UserDao.getUserByName(userName);
			} catch (Exception e) {
				logger.warn("Not able to get the user document for " + userName);
				e.printStackTrace();
			}		
		}		
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
}

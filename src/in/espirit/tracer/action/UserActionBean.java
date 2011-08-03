package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.UserDao;
import in.espirit.tracer.model.User;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import org.apache.log4j.Logger;

@UrlBinding("/user/{userName}/{action}")
public class UserActionBean extends BaseActionBean {
	
	private static final Logger logger = Logger.getLogger(UserActionBean.class.getName());
	private static final String URL_EDIT = "/WEB-INF/jsp/user_edit.jsp";
	private static final String URL_VIEW = "/WEB-INF/jsp/user_view.jsp";
	
	private User user;
	private String userName;
	private String action;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	@DefaultHandler
	public Resolution open() {		
		//String displayView = action==null?URL_VIEW:(action.equalsIgnoreCase("edit")?URL_EDIT:URL_VIEW);
		String displayView;
		if (userName.equalsIgnoreCase("new")) {
			displayView = URL_EDIT;
		}
		else if (getContext().getLoggedUser()==null) {
			getContext().getMessages().add(new SimpleMessage("You need to login first to view details"));
			return new ForwardResolution(LoginActionBean.class);			
		}
		else if (action==null) {
			displayView = URL_VIEW;
		}
		else if (action.equalsIgnoreCase("edit") && userName.equalsIgnoreCase(getContext().getLoggedUser())) {
			displayView = URL_EDIT;
		}
		else {
			getContext().getMessages().add(new SimpleMessage("You can't edit other person profiles. You can only view them"));
			displayView = URL_VIEW;
		}		
		return new ForwardResolution(displayView);		
	}
	
	@ValidationMethod(on="submit")
	public void valMethod(ValidationErrors errors) throws Exception{	
		if (UserDao.userExists(user.getUserName().toLowerCase())) {			
			errors.addGlobalError(new SimpleError("User Name already exists. Please select a different name"));
		}
	}
	
	public Resolution submit() throws Exception {
		user = getUser();
		boolean flag = UserDao.registerUser(user);		
		if (flag) {
			getContext().getMessages().add(new SimpleMessage("User Registered. Email sent for approval."));	
		}
		else {
			getContext().getMessages().add(new SimpleMessage("Problem with Registration. Register Again"));	
		}	
		logger.debug("User regsitered and email sent for approval -" + user.getUserName());		
		return new RedirectResolution(LoginActionBean.class);		
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
	
	public Resolution approve() throws Exception {
		//call function to sent email.
		UserDao.userAdminAction(userName, 1);
		getContext().getMessages().add(new SimpleMessage("User Registration is approved. E-Mail sent to user"));	
		return new RedirectResolution(UserListActionBean.class).addParameter("view", "approval");
	}
	
	public Resolution reject() throws Exception {
		//call function to sent email.
		UserDao.userAdminAction(userName, -1);
		getContext().getMessages().add(new SimpleMessage("User Registration is rejected. E-Mail sent to user"));	
		return new RedirectResolution(UserListActionBean.class).addParameter("view", "approval");
	}
	
	public Resolution checkUserName() throws Exception {
		boolean flag = UserDao.userExists(userName);
		if (flag) {
			return new StreamingResolution("text/plain", "exists");
		}
		return new StreamingResolution("text/plain", "avail");
	}

	public User getUser() {
		if (userName!=null && !userName.equalsIgnoreCase("new")) {
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

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
}

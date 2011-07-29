package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.UserDao;
import in.espirit.tracer.model.User;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.ValidationError;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import org.apache.log4j.Logger;

@UrlBinding("/user/{userName}")
public class UserActionBean extends BaseActionBean {
	
	private static final Logger logger = Logger.getLogger(UserActionBean.class.getName());
	private static final String URL = "/WEB-INF/jsp/user.jsp";
	
	private User user;
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	@DefaultHandler
	public Resolution open() {
		return new ForwardResolution(URL);
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
}

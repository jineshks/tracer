package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ChatDAO;
import in.espirit.tracer.database.dao.UserDao;
import in.espirit.tracer.model.User;

import java.util.Date;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

@UrlBinding("/login/{event}")
public class LoginActionBean extends BaseActionBean{
	private static final String URL = "/login.jsp";
	
	private User user;
	
	public User getUser() {
		return user;
	}

	@ValidateNestedProperties ({
		@Validate(required=true, field="userName"),
		@Validate(required=true, field="password"),		
	})	
	public void setUser(User user) {
		this.user = user;
	}
	
	@ValidationMethod(when=ValidationState.NO_ERRORS)
	public void checkLogin(ValidationErrors errors) throws Exception {
		int status = UserDao.UserApprovalStatus(user.getUserName().toLowerCase());
		status =1;
		if (!UserDao.valueExists("f_userName", user.getUserName().toLowerCase())) {		
			logger.warn("Login Attempt Failed - Incorrect user Name >> " + user.getUserName());
			errors.add("user.userName", new LocalizableError("user.userName.wrong"));	
		}
		else if (!UserDao.checkPassword(user.getUserName().toLowerCase(), user.getPassword())) {
			logger.warn("Login Attempt Failed - Incorrect password >> " + user.getUserName());
			errors.add("user.password", new LocalizableError("user.password.wrong"));			
		}	
		else if (status==0) { //only if status is 1 permit login.
			logger.warn("Login Attempt Failed - User not yet approved >> " + user.getUserName());
			errors.addGlobalError(new LocalizableError("user.waitingforapproval"));	
		}
		else if (status==-1) {
			logger.warn("Login Attempt Failed - User application rejected>> " + user.getUserName());
			errors.addGlobalError(new LocalizableError("user.applicationrejected"));			
		}
	}
	
	@DontValidate
	@DefaultHandler
	public Resolution open() {
		return new ForwardResolution(URL);
	}
		
	public Resolution login() {
		logger.debug("User logged in > " + user.getUserName());
		
		HttpSession session = this.getContext().getRequest().getSession();
		session.setAttribute("loggedInDateTime", new Date());
		if (logger.isDebugEnabled()) {
			logger.debug("Used :"+user.getUserName()+"Logged In:"+new Date());
	     }	
		//Add user in logged in user List.
		ChatDAO.getInstance().logUserIn(user);	
		getContext().setLoggedUser(user.getUserName());
		getContext().setUser(user);
		return new RedirectResolution(DashboardActionBean.class);
	}
		
	@DontValidate
	public Resolution logout() {
		logger.debug("User logged out > " + getContext().getLoggedUser());
		getContext().deleteLoggedUser();
		//Remove from active chat user list
		ChatDAO.getInstance().removeUser(getContext().getUser());
		getContext().getMessages().add(new SimpleMessage("User Logged Out."));	
		return new RedirectResolution("/login/open");		
	}

}

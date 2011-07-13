package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.UserDao;
import in.espirit.tracer.model.User;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

@UrlBinding("/user/{event}")
public class LoginActionBean extends BaseActionBean{
	private static final String URL = "/WEB-INF/jsp/login.jsp";
	private static final String DASHBOARD = "/WEB-INF/jsp/dashboard.jsp";
	
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
		if (!UserDao.userExists(user.getUserName().toLowerCase())) {		
			logger.warn("Login Attempt Failed - Incorrect user Name >> " + user.getUserName());
			errors.add("user.userName", new LocalizableError("user.userName.wrong"));				
		}
		else if (!UserDao.checkPassword(user.getUserName().toLowerCase(), user.getPassword())) {
			logger.warn("Login Attempt Failed - Incorrect password >> " + user.getUserName());
			errors.add("user.password", new LocalizableError("user.password.wrong"));	
		}	
	}
	
	@DontValidate
	@DefaultHandler
	public Resolution signin() {
		return new ForwardResolution(URL);
	}
		
	public Resolution login() {
		logger.debug("User logged in > " + user.getUserName());
		getContext().setLoggedUser(user.getUserName());
		return new ForwardResolution(DASHBOARD);
	}
	
	@DontValidate
	public Resolution cancel() {		
		return new RedirectResolution(LoginActionBean.class);		
	}	
	
	@DontValidate
	public Resolution logout() {
		logger.debug("User logged out > " + getContext().getLoggedUser());
		getContext().deleteLoggedUser();
		getContext().getMessages().add(new SimpleMessage("User Logged Out."));	
		return new RedirectResolution(LoginActionBean.class);		
	}

}

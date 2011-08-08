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
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import org.apache.log4j.Logger;

public class ZZNotUsedRegisterActionBean extends BaseActionBean {
	private static final Logger logger = Logger.getLogger(ZZNotUsedRegisterActionBean.class.getName());
	private static final String URL = "/WEB-INF/jsp/register.jsp";
	
	private User user;
	private String confirmPassword;
	
	public User getUser() {
		return user;
	}
	
	@ValidateNestedProperties({
	@Validate(required=true, field="userName", maxlength=10),
	@Validate(required=true, field="password", maxlength=10),
	@Validate(required=true, field="email", maxlength=35, converter=EmailTypeConverter.class)	
	})	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	@Validate(required=true)
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@ValidationMethod(when=ValidationState.NO_ERRORS)
	public void valMethod(ValidationErrors errors) throws Exception{
		if (!getConfirmPassword().equals(user.getPassword())) {
			errors.add("confirmPassword", new LocalizableError("confirmPassword.mismatch"));			
		}
		//if (UserDao.userExists(user.getUserName().toLowerCase())) {			
		//	errors.add("user.userName", new LocalizableError("user.userName.exists"));				
		//}
	}
	
	@DontValidate
	@DefaultHandler
	public Resolution open() {
		logger.debug("in open()");
		return new ForwardResolution(URL);
	}

	public Resolution register() throws Exception {
		user = getUser();		
		boolean row = UserDao.registerUser(user);		
		if (row) {
			getContext().getMessages().add(new SimpleMessage("User Registered Successfully. Please login now."));	
		}
		else {
			getContext().getMessages().add(new SimpleMessage("Problem with Registration.."));	
		}	
		
		return new RedirectResolution(LoginActionBean.class);		
	}
	
	@DontValidate
	public Resolution cancel() {		
		//getContext().getMessages().add(new SimpleMessage("Action Cancelled"));
		//why can't we go with forward resolution?. Forward resolution causes error??
		return new RedirectResolution(LoginActionBean.class);
	}


}

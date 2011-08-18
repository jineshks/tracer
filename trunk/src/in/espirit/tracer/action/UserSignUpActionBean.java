package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.CustomDao;
import in.espirit.tracer.database.dao.UserDao;
import in.espirit.tracer.model.User;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/signup/{value}")   // usually it will be user/new for open and for checking username email, user/username,email and calling corresponding event.
public class UserSignUpActionBean extends BaseActionBean {
	
	private static final String URL_NEW = "/WEB-INF/jsp/user_new.jsp";
	
	private User user;
	private String userName;
	private String team;
	private String role;
	private String value;

	@DefaultHandler
	public Resolution open() {
		return new ForwardResolution(URL_NEW);		
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
	
	public Resolution checkUserNameEmail() throws Exception {
		String[] val = value.split("~");  // userName shall have two values one is username and email separated by commas.
		String res = "";
		boolean userNameFlag = UserDao.valueExists("f_userName",val[0]);
		if (userNameFlag) {
			res +="UserName";
		}
		boolean emailFlag = UserDao.valueExists("f_email", val[1]);
		if (emailFlag) {
			res +="Email";
		}			
		return new StreamingResolution("text/plain", res);
	}
	
	public Resolution approve() throws Exception {
		//call function to sent email.
		UserDao.adminApprove(userName, team, CustomDao.getIndexValue("user.role", role));
		getContext().getMessages().add(new SimpleMessage("User Registration is approved. E-Mail sent to user"));	
		return new RedirectResolution(UserListActionBean.class).addParameter("view", "approval");
	}
	
	public Resolution reject() throws Exception {
		//call function to sent email.
		UserDao.adminReject(userName);
		getContext().getMessages().add(new SimpleMessage("User Registration is rejected. E-Mail sent to user"));	
		return new RedirectResolution(UserListActionBean.class).addParameter("view", "approval");
	}
		
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	
	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}

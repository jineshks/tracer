package in.espirit.tracer.ext;

import net.sourceforge.stripes.action.ActionBeanContext;

public class MyActionBeanContext extends ActionBeanContext {

	public void setLoggedUser(String userName){
		getRequest().getSession().setAttribute("loggedUser", userName);		
	}	
	
	public String getLoggedUser() {
		return (String) getRequest().getSession().getAttribute("loggedUser");
	}
	
	public void deleteLoggedUser() {
		getRequest().getSession().removeAttribute("loggedUser");
	}
	
	public void setCurrentSection(String section) {
		getRequest().getSession().setAttribute("section", section);		
	}
	
	public String getCurrentSection() {
		return (String) getRequest().getSession().getAttribute("section");	
	}	
}

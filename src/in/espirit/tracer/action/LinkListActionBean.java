package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.LinkDao;
import in.espirit.tracer.model.Link;

import java.util.ArrayList;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/list/link/{type}")
public class LinkListActionBean extends BaseActionBean {
	private static final String URL = "/WEB-INF/jsp/link_list.jsp";
	private String type;
	
	public ArrayList<Link> getItems() throws Exception {		
		return LinkDao.getLinks(type, getContext().getLoggedUser(), "all");		
	}
	
	@DefaultHandler
	public Resolution open() throws Exception {
		if (type ==null ) {
			type = "my";
		}
		return new ForwardResolution(URL);	
	}
	
	public synchronized Resolution persist() throws Exception {  //Making this synchronized to avoid duplication of position changes for the same list...is it a good idea or not correct?
		boolean flag = false;
		String output = "<p>Sorry! could not save the new state </p>";
		
		String changes = this.getContext().getRequest().getParameter("changes");
		
		flag = LinkDao.updatePosition(changes);	
		
		if (flag) {
			output = "success";
		} else {
			output = "error";
		}
		return new StreamingResolution("text", output);
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}

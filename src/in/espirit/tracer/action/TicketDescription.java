package in.espirit.tracer.action;

import java.util.ArrayList;

import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Comment;
import in.espirit.tracer.util.DateUtils;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/ticketdesc")
public class TicketDescription extends BaseActionBean {
	private String id, type, desc;

	public Resolution update() throws Exception {
		boolean flag = false;
		String output = "";
	
		TicketDao.insertTicketDescriptionChange(id, type, getContext().getLoggedUser());
		
		String activity = getContext().getLoggedUser() + " has changed description of " + type + " #" + id;
		TicketDao.handleActivity(activity, getContext().getLoggedUser());
				
		flag = TicketDao.updateTicketDesc(id, type, desc);
		if (flag) {
			output = "success";
		}
		else {
			output = "failure";
		}
		return new StreamingResolution("text", output);
	}

	
	public Resolution history() throws Exception {
		String id = this.getContext().getRequest().getParameter("id");
		ArrayList<Comment> history = TicketDao.getTicketDescHistory(id); 
		String output = "[{no output}]";  //Have to improve error hanndling
		String temp = "";
		if (history.size()>=1) {
			output = "[";
		}
		else {
			return new StreamingResolution("text/json","");
		}
		for(Comment hist:history) {			
			//output = "{/"one/": /"Singular sensation/"}";
			temp = "{\"user\":\"" + hist.getUserName() + 
			"\",\"time\":\"" + hist.getTimeStamp() +  
			"\",\"val\":\"" + hist.getComment() +  
			"\"}";			
			output += temp + ",";
		}

		output = output.substring(0,output.length()-1);
		output +="]";
		logger.debug("JSON output" + output);
		return new StreamingResolution("text/json", output);
	}

	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

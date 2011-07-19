package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Ticket;

import java.util.ArrayList;


import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/list/{type}/{list}")
public class ListActionBean extends BaseActionBean {
	
	private static final String URL = "/WEB-INF/jsp/list.jsp";

	private String list;
	private String type;
	private String priority;
	private String status;
	private String owner;
	private String milestone;
	private String reporter;
	private String importance;
	private String parentTicket;
	private String sortBy;
		
	public ArrayList<Ticket> getItems() throws Exception {
		logger.debug("In the Listing page>> " + list+type);
		getContext().setCurrentSection(list+type);  //Normally it should be MyTask or AllTask or MyDefect or AllDefect
		if (list.equalsIgnoreCase("my")) {
			return TicketDao.getAllTickets(type,getContext().getLoggedUser(), priority, status,milestone,reporter,importance, parentTicket, sortBy);
		}
		else if (list.equalsIgnoreCase("all"))  {
			return TicketDao.getAllTickets(type,owner,priority,status,milestone,reporter,importance, parentTicket, sortBy);	
		}		
		return null;			
	}
		
	@DefaultHandler
	public Resolution open() throws Exception {
		return new ForwardResolution(URL);	
	}
	
	public Resolution filter() throws Exception {	
		return new ForwardResolution(URL);
	}
	
	
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public String getMilestone() {
		return milestone;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public void setParentTicket(String parentTicket) {
		this.parentTicket = parentTicket;
	}

	public String getParentTicket() {
		return parentTicket;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}	
	
}

package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ActivityDao;
import in.espirit.tracer.model.Activity;

import java.util.ArrayList;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/activitystream")
public class ActivityStreamActionBean extends BaseActionBean {
	private static final String URL = "/WEB-INF/jsp/activitystream.jsp";
	
	private String userName;
	private String fromDate, toDate;
	
	public ArrayList<Activity> getItems() throws Exception {
		logger.debug("In the Activity Stream Listing page>>");
		return ActivityDao.getActivities("all", userName, fromDate, toDate);				
	}
	
	@DefaultHandler
	public Resolution open() throws Exception {
		return new ForwardResolution(URL);	
	}
	
	public Resolution filter() throws Exception {
		return new ForwardResolution(URL);
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
}

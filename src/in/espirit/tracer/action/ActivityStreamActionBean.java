package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ActivityDao;
import in.espirit.tracer.database.dao.CustomDao;
import in.espirit.tracer.model.Activity;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/activitystream")
public class ActivityStreamActionBean extends BaseActionBean {
	private static final String URL = "/WEB-INF/jsp/activitystream.jsp";
	
	private String userName;
	private String fromDate, toDate;
	
	public Integer getResultSize() throws Exception {
		Integer count = ActivityDao.getActivitiesCount(userName, fromDate, toDate);
		return count;
	}
	
	public int getPageSize() throws Exception {
		String size = CustomDao.getResourceMessage("activitystream.pagesize");
		return size!=null?Integer.parseInt(size):100;  //default size is hundred.
	}
	
	public ArrayList<Activity> getItems() throws Exception {
		logger.debug("In the Activity Stream Listing page>>");
		//return ActivityDao.getActivities("all", userName, fromDate, toDate);
		int pageNumber = 1 ;
		Enumeration paraname = getContext().getRequest().getParameterNames();
		while (paraname.hasMoreElements()) {
			String key = (String) paraname.nextElement();
			if (key.startsWith("d-") && key.endsWith("-p")) {
				pageNumber = Integer.parseInt((String)getContext().getRequest().getParameter(key));
			}
			String value = (String) getContext().getRequest().getParameter(key);
		}
				
		//if (pageNumber==0) {
		//	pageNumber = 1;
		//}
		int offset = (pageNumber-1) * getPageSize();
		
		return ActivityDao.getListingActivities(userName, fromDate, toDate,offset,getPageSize());		
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

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
	
	public ArrayList<Activity> getItems() throws Exception {
		logger.debug("In the Activity Stream Listing page>>");
		return ActivityDao.getActivities("all");				
	}
	
	@DefaultHandler
	public Resolution open() throws Exception {
		return new ForwardResolution(URL);	
	}

}

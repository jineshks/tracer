package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.AlertDao;
import in.espirit.tracer.model.Alert;

import java.util.ArrayList;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/list/alert")
public class AlertListActionBean extends BaseActionBean {
	private static final String URL = "/WEB-INF/jsp/alertlist.jsp";
	
	public ArrayList<Alert> getItems() throws Exception {
		return AlertDao.getAllAlert();				
	}
	
	@DefaultHandler
	public Resolution open() throws Exception {
		return new ForwardResolution(URL);	
	}

}

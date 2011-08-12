package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.AlertDao;
import in.espirit.tracer.model.Alert;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/alert/{id}")
public class AlertActionBean extends BaseActionBean {
	
	private static final String URL="/WEB-INF/jsp/alert.jsp";
	
	private Alert alert;
	private String id;
	
	@DefaultHandler
	public Resolution open() {
		return new ForwardResolution(URL);
	}
	
	public Resolution cancel() {
		getContext().setCurrentSection("alertlist");
		return new ForwardResolution(AlertListActionBean.class);
	}
	
	public Resolution submit() throws Exception {
		alert = getAlert();
		if (alert.getId()==null) {
			AlertDao.registerAlert(alert);
			getContext().getMessages().add(new SimpleMessage("New Alert Added."));
		}
		else {
			AlertDao.editAlert(alert);
			getContext().getMessages().add(new SimpleMessage("Alert Successfully Edited."));			
		}		
		return new RedirectResolution(AlertListActionBean.class);
	}
	
	public Resolution delete() throws Exception {
		alert = getAlert();
		AlertDao.deleteAlert(alert);
		getContext().getMessages().add(new SimpleMessage("Alert Deleted."));	
		return new RedirectResolution(AlertListActionBean.class);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setAlert(Alert alert) {
		this.alert = alert;
	}
	public Alert getAlert() throws Exception {
		if (id!=null && !id.equalsIgnoreCase("new")) {
			return AlertDao.getAlert(id);		
		}
		return alert;
	}

}

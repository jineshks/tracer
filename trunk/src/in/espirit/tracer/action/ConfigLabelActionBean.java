package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.LabelDao;

import java.util.HashMap;
import java.util.LinkedHashMap;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.SimpleMessage;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/config/label")
public class ConfigLabelActionBean extends BaseActionBean {

	private static final String URL="/WEB-INF/jsp/config/label.jsp";
	
	private String keyvalue;

	@DefaultHandler
	public Resolution open(){
		//System.out.println("REAL PATH" + getContext().getServletContext().getRealPath(""));
		return new ForwardResolution(URL);
	}
	
	public Resolution update() {
		
		HashMap<String, String> pair = new HashMap<String, String>();
		String[] arr = keyvalue.split(",");
		for (String s : arr) {
			String key = s.substring(0, s.indexOf(":"));
			String val = s.substring(s.indexOf(":")+1, s.length());
			pair.put(key, val);			
		}
		
		boolean flag = LabelDao.updateProperties(pair, getContext().getServletContext().getRealPath(""));
		if (!flag) {
			getContext().getMessages().add(new SimpleMessage("Problem with updating the entries."));		
		}		
		return new RedirectResolution(ConfigLabelActionBean.class);
	}

	public LinkedHashMap<String, String> getLabelItems() throws Exception {		
		LinkedHashMap<String, String> result = new LabelDao().getLabelItems(getContext().getServletContext().getRealPath(""));			
		return result;				
	}

	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}

	public String getKeyvalue() {
		return keyvalue;
	} 
}

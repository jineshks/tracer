package in.espirit.tracer.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/reports/{type}")
public class ReportsActionBean extends BaseActionBean {
	private static final String URL = "/WEB-INF/jsp/reports/burndown.jsp";
	private static final String VELOCITY = "/WEB-INF/jsp/reports/velocity.jsp";
	private static final String EFFICIENCY = "/WEB-INF/jsp/reports/efficiency.jsp";
	private String type;

	@DefaultHandler
	public Resolution open() throws Exception {
		if (type ==null ) {
			type = "burndown";
		}else if(type.equalsIgnoreCase("velocity")){
			type = "velocity";
			return new ForwardResolution(VELOCITY);	
		}else if(type.equalsIgnoreCase("efficiency")){
			return new ForwardResolution(EFFICIENCY);	
		}
		return new ForwardResolution(URL);	
	}
	
	public Resolution getData() throws Exception {  
		String data = getJSON(type);
		return new StreamingResolution("text", data);
	}
	
	private String getJSON(String type) {
		String bar = "{"+
		  "\"cols\": ["+
		        "{\"id\":\"\",\"label\":\"Sprint\",\"pattern\":\"\",\"type\":\"string\"},"+
		        "{\"id\":\"\",\"label\":\"Story points\",\"pattern\":\"\",\"type\":\"number\"}"+
		      "],"+
		  "\"rows\": ["+
		        "{\"c\":[{\"v\":\"Sprint A\",\"f\":null},{\"v\":3,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Sprint B\",\"f\":null},{\"v\":1,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Sprint C\",\"f\":null},{\"v\":1,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Sprint D\",\"f\":null},{\"v\":1,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Sprint E\",\"f\":null},{\"v\":2,\"f\":null}]}"+
		      "]"+
		"}";
		String line = "{" +
						"\"cols\":[" +
							"{\"id\":\"\",\"label\":\"x\",\"pattern\":\"\",\"type\":\"string\"}," +
							"{\"id\":\"\",\"label\":\"Ideal\",\"pattern\":\"\",\"type\":\"number\"}," +
							"{\"id\":\"\",\"label\":\"Actual\",\"pattern\":\"\",\"type\":\"number\"}," +
							"{\"id\":\"\",\"label\":\"Target\",\"pattern\":\"\",\"type\":\"number\"}" +
						"]," +
						"\"rows\":[" +
							"{\"c\":[{\"v\":\"A\",\"f\":null},{\"v\":30,\"f\":null},{\"v\":30,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"B\",\"f\":null},{\"v\":28,\"f\":null},{\"v\":30,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"C\",\"f\":null},{\"v\":26,\"f\":null},{\"v\":30,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"D\",\"f\":null},{\"v\":24,\"f\":null},{\"v\":28,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"E\",\"f\":null},{\"v\":22,\"f\":null},{\"v\":26,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"F\",\"f\":null},{\"v\":20,\"f\":null},{\"v\":26,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"G\",\"f\":null},{\"v\":18,\"f\":null},{\"v\":24,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"H\",\"f\":null},{\"v\":16,\"f\":null},{\"v\":20,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"I\",\"f\":null},{\"v\":14,\"f\":null},{\"v\":16,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"J\",\"f\":null},{\"v\":12,\"f\":null},{\"v\":14,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"K\",\"f\":null},{\"v\":10,\"f\":null},{\"v\":10,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"L\",\"f\":null},{\"v\":8,\"f\":null},{\"v\":8,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"M\",\"f\":null},{\"v\":6,\"f\":null},{\"v\":4,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"N\",\"f\":null},{\"v\":0,\"f\":null},{\"v\":4,\"f\":null},{\"v\":4,\"f\":null}]}" +
						"]," +
						"\"p\":null}";
		
		if(type.equalsIgnoreCase("burndown")){
			return line;
		}else{
			return bar;
		}
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
